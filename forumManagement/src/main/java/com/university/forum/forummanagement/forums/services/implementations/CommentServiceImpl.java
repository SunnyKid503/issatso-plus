package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.requests.CreateCommentRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.CommentResponseDto;
import com.university.forum.forummanagement.forums.models.Comment;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.repositories.CommentRepository;
import com.university.forum.forummanagement.forums.repositories.PostRepository;
import com.university.forum.forummanagement.forums.services.interfaces.CommentService;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public CommentServiceImpl(MemberRepository memberRepository, PostRepository postRepository, CommentRepository commentRepository)
    {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public List<CommentResponseDto> getForPost(int postId) {
        return commentRepository.findByPost(postId)
                .stream().map(CommentResponseDto::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDto> getForUser(UUID userId) {
        return commentRepository.findByAuthor(userId)
                .stream().map(CommentResponseDto::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDto> getForComment(int commentId) {

        return commentRepository.findByComment(commentId)
                .stream().map(CommentResponseDto::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto create(UUID userId, CreateCommentRequestDto dto) throws ElementNotFoundException, IllegalOperationException {
        Comment parentComment = null;
        if(dto.getParentCommentId() > 0)
            parentComment = loadIfValidParentComment(dto.getParentCommentId());
        Post post = loadIfValidPost(dto.getPostId());
        if(parentComment != null && parentComment.getPost().getId() != post.getId())
            throw new IllegalOperationException("Invalid post id.");

        Comment comment = new Comment();
        comment.setAuthor(loadIfValidMember(userId));
        comment.setParentComment(parentComment);
        comment.setContent(dto.getContent());
        comment.setPost(post);

        commentRepository.save(comment);
        return CommentResponseDto.toCommentResponseDto(comment);
    }

    protected Post loadIfValidPost(int postId) throws ElementNotFoundException, IllegalOperationException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format("No matches found for post with id %d.", postId))
                );
        if(post.isArchived())
            throw new IllegalOperationException(
                    String.format("Post with %d has been deleted.", postId)
            );

        return post;
    }

    protected Member loadIfValidMember(UUID memberId) throws ElementNotFoundException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format("No matches found for post with id %s.", memberId.toString()))
                );
    }

    protected Comment loadIfValidParentComment(int commentId) throws ElementNotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format("No matches found for comment with id %d.", commentId))
                );
    }
}
