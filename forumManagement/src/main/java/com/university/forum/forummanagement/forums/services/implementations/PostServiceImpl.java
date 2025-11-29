package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.UpdatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.FypPostResponseDto;
import com.university.forum.forummanagement.forums.dtos.responses.PostResponseDto;
import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.repositories.FileReferenceRepository;
import com.university.forum.forummanagement.forums.repositories.PostCategoriesRepository;
import com.university.forum.forummanagement.forums.repositories.PostRepository;
import com.university.forum.forummanagement.forums.services.abstracts.PostServiceBase;
import com.university.forum.forummanagement.forums.services.interfaces.PostService;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import com.university.forum.forummanagement.shared.services.FileManagementService;
import com.university.forum.forummanagement.structures.repositories.TaggableObjectRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends PostServiceBase implements PostService {
    private final PostRepository postRepository;
    private final FileManagementService fileManagementService;
    private final FileReferenceRepository fileReferenceRepository;

    public PostServiceImpl(MemberRepository memberRepository,
                           PostCategoriesRepository categoriesRepository,
                           TaggableObjectRepository taggableObjectRepository,
                           PostRepository postRepository,
                           FileReferenceRepository fileReferenceRepository,
                           FileManagementService fileManagementService) {
        super(memberRepository, categoriesRepository, taggableObjectRepository);
        this.postRepository = postRepository;
        this.fileReferenceRepository = fileReferenceRepository;
        this.fileManagementService = fileManagementService;
    }

    @Override
    public List<PostResponseDto> feed() {
        return postRepository.findAll(Sort.by("updatedAt").descending())
                .stream()
                .filter(x -> !x.isArchived())
                .map(PostResponseDto::toPostResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FypPostResponseDto> fyp(UUID userId) throws ElementNotFoundException{
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new ElementNotFoundException("No matches for user."));

        return postRepository.fyp(userId).stream()
                .map(FypPostResponseDto::toFypPostResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto create(UUID memberId, CreatePostRequestDto dto) throws ElementNotFoundException, FileUploadException {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setCreatedAt(dto.getCreatedAt());
        post.setPostType(dto.getPostType());
        post.setContent(dto.getContent());

        post.setCategories(loadCategories(dto.getCategoryIds()));
        post.setTags(loadTaggedObjects(dto.getTagIds()));
        post.setAuthor(loadAuthor(memberId));
        List<FileReference> uploadedFiles = fileManagementService.uploadAll(dto.getFiles());

        if(uploadedFiles != null && !uploadedFiles.isEmpty()) {
            fileReferenceRepository.saveAll(uploadedFiles);
            post.setFiles(uploadedFiles);
        }

        postRepository.save(post);

        return PostResponseDto.toPostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> saved(UUID userId){
        return postRepository.saved(userId)
                .stream().map(PostResponseDto::toPostResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> postedBy(UUID userId) throws ElementNotFoundException {
        return postRepository.postedBy(userId)
                .stream().map(PostResponseDto::toPostResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto delete(UUID userId, int postId) throws ElementNotFoundException, IllegalOperationException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ElementNotFoundException(String.format("No matches for post with id %d", postId))
        );
        if(!post.getAuthor().getId().equals(userId))
            throw new IllegalOperationException("Thou shall not delete.");

        post.setArchived(true);
        postRepository.save(post);
        return PostResponseDto.toPostResponseDto(post);
    }

    @Override
    public PostResponseDto update(UUID userId, UpdatePostRequestDto dto) throws ElementNotFoundException, IllegalOperationException, FileUploadException {
        loadAuthor(userId); //for checking if that mf even exits
        Post post = loadPost(dto.getPostId());
        if(!userId.equals(post.getAuthor().getId()))
            throw new IllegalOperationException("That's not your post, Thou shall not update.");

        post.setPostType(dto.getPostType());
        post.setContent(dto.getContent());
        post.setTitle(dto.getTitle());

        post.setCategories(loadCategories(dto.getCategoryIds()));
        post.setTags(loadTaggedObjects(dto.getTagIds()));
        List<FileReference> updatedPostFiles = handleFileUpdates(dto.getOldFilesUrls(), post.getFiles(),dto.getFiles());
        fileReferenceRepository.saveAll(updatedPostFiles);
        post.setFiles(updatedPostFiles);

        return PostResponseDto.toPostResponseDto(post);
    }

    @Override
    public PostResponseDto getPostById(int postId) throws ElementNotFoundException {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ElementNotFoundException("Post not found")
        );
        return PostResponseDto.toPostResponseDto(post);

    }

    protected List<FileReference> handleFileUpdates(List<String> preservedFileUrls, //this is because the front only holds urls, not the complete file reference
                                                    List<FileReference>  filesBeforeModification,
                                                    MultipartFile[] newFiles) throws FileUploadException {
        if(preservedFileUrls == null || preservedFileUrls.isEmpty())
            preservedFileUrls = new ArrayList<>();
        List<FileReference> preservedFiles = fileReferenceRepository.getAllByUrls(preservedFileUrls);
        System.out.printf("number of preserved files from cloud is %d\n", preservedFiles.size());
        List<FileReference> filesToDelete = filesBeforeModification.stream().filter(
                x ->  preservedFiles.stream().noneMatch(
                        y -> y.getPublicId().equals(x.getPublicId())
                )
        ).collect(Collectors.toList());
        int deletedFilesCount = fileManagementService.deleteAll(filesToDelete);
        System.out.printf("number of deleted files from cloud is %d\n", deletedFilesCount);
        if(filesBeforeModification.removeAll(filesToDelete)){
            System.out.println("files has been deleted from db.");
        }
        filesBeforeModification.addAll(fileManagementService.uploadAll(newFiles));
        return filesBeforeModification;
    }

    protected Post loadPost(int postId) throws ElementNotFoundException {
        return postRepository.findById(postId).orElseThrow(
                () -> new ElementNotFoundException(String.format("No matches for post with id %d", postId))
        );
    }

}
