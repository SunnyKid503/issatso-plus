package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.requests.PostInteractionRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.PostInteractionResponseDto;
import com.university.forum.forummanagement.forums.models.InteractionActionType;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.models.UserPostInteraction;
import com.university.forum.forummanagement.forums.models.UserPostInteractionId;
import com.university.forum.forummanagement.forums.repositories.InteractionRepository;
import com.university.forum.forummanagement.forums.repositories.PostRepository;
import com.university.forum.forummanagement.forums.services.interfaces.PostInteractionService;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostInteractionServiceImpl implements PostInteractionService {
    private final InteractionRepository interactionRepository ;
    private final PostRepository postRepository ;
    private final MemberRepository memberRepository ;

    public PostInteractionServiceImpl(InteractionRepository interactionRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.interactionRepository = interactionRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public Post findPost(int postId) throws ElementNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ElementNotFoundException(
                "Post not found"));
        return post;
    }

    public Member findMember(UUID userId) throws ElementNotFoundException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new ElementNotFoundException(
                "Member not found"));
        return member;
    }

    public UserPostInteraction findOrCreateUserPostInteraction(UUID userId, PostInteractionRequestDto dto){
        UserPostInteractionId interactionId = new UserPostInteractionId(userId, dto.getPostId()) ;
        UserPostInteraction userPostInteraction = interactionRepository.findById(interactionId)
                .orElse(new UserPostInteraction() );
        userPostInteraction.setId(interactionId);
        return userPostInteraction ;
    }

    @Override
    public PostInteractionResponseDto upVote(UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException {
        Post post = findPost(dto.getPostId());
        Member member = findMember(userId);
        UserPostInteraction userPostInteraction = findOrCreateUserPostInteraction(userId, dto);
        if (userPostInteraction.isDownVote()){
            userPostInteraction.setDownVote(false);
            post.noDownVote();
        }
        if (userPostInteraction.isUpVote()){
            userPostInteraction.setUpVote(false);
            post.noUpVote();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId , InteractionActionType.NEUTRAL);
        } else {
            userPostInteraction.setUpVote(true);
            post.upVote();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId , InteractionActionType.UPVOTE);
        }
    }
    @Override
    public PostInteractionResponseDto downVote(UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException {
        Post post = findPost(dto.getPostId());
        Member member = findMember(userId);
        UserPostInteraction userPostInteraction = findOrCreateUserPostInteraction(userId, dto);

        if (userPostInteraction.isUpVote()){
            userPostInteraction.setUpVote(false);
            post.noUpVote();
        }
        if (userPostInteraction.isDownVote()){
            userPostInteraction.setDownVote(false);
            post.noDownVote();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId , InteractionActionType.NEUTRAL);
        } else {
            userPostInteraction.setDownVote(true);
            post.downVote();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId , InteractionActionType.DOWNVOTE);
        }
    }

    @Override
    public PostInteractionResponseDto save(UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException {
        Post post = findPost(dto.getPostId());
        Member member = findMember(userId);
        UserPostInteraction userPostInteraction = findOrCreateUserPostInteraction(userId, dto);

        if (userPostInteraction.isSaved()){
            userPostInteraction.setSaved(false);
            post.noSaved();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId, InteractionActionType.UNSAVED);
        } else {
            userPostInteraction.setSaved(true);
            post.saved();
            postRepository.save(post);
            interactionRepository.save(userPostInteraction);
            return new PostInteractionResponseDto(dto.getPostId(), userId, InteractionActionType.SAVED);
        }
    }
    @Override
    public PostInteractionResponseDto seen(UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException {
        Post post = findPost(dto.getPostId());
        Member member = findMember(userId);
        UserPostInteraction userPostInteraction = findOrCreateUserPostInteraction(userId, dto);

        userPostInteraction.setSeen(true);
        post.seen();
        postRepository.save(post);
        interactionRepository.save(userPostInteraction);
        return new PostInteractionResponseDto(dto.getPostId(), userId, InteractionActionType.SEEN);
    }
}
