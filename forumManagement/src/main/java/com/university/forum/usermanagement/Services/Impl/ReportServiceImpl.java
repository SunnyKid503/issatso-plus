package com.university.forum.usermanagement.Services.Impl;

import com.university.forum.forummanagement.forums.models.Comment;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.repositories.CommentRepository;
import com.university.forum.forummanagement.forums.repositories.PostRepository;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.usermanagement.Dtos.Message.ReportMessage;
import com.university.forum.usermanagement.Dtos.Request.ReportRequest;
import com.university.forum.usermanagement.Models.Enum.TargetType;
import com.university.forum.usermanagement.Services.ReportProducer;
import com.university.forum.usermanagement.Services.ReportService;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ReportServiceImpl implements ReportService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ReportProducer reportProducer;

    public ReportServiceImpl(PostRepository postRepository, CommentRepository commentRepository, MemberRepository memberRepository, ReportProducer reportProducer) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.reportProducer = reportProducer;
    }

    @Override
    public void reportPost(UUID memberId, ReportRequest reportRequest) throws ElementNotFoundException {
        if(!memberRepository.existsById(memberId)) throw new ElementNotFoundException("Member not found");
        Post post = postRepository.findById(reportRequest.getTargetId()).orElseThrow(()-> new ElementNotFoundException("Post not found"));

        ReportMessage report = new ReportMessage(
                memberId,
                String.valueOf(post.getId()),
                TargetType.POST,
                "Post reported with title "+post.getTitle()+" and content "+post.getContent(),
                reportRequest.getReason()
        );

        reportProducer.sendReport(report);
    }

    @Override
    public void reportComment(UUID memberId, ReportRequest reportRequest) throws ElementNotFoundException {
        if(!memberRepository.existsById(memberId)) throw new ElementNotFoundException("Member not found");
        Comment comment = commentRepository.findById(reportRequest.getTargetId()).orElseThrow(()-> new ElementNotFoundException("Comment not found"));

        ReportMessage report = new ReportMessage(
                memberId,
                String.valueOf(comment.getId()),
                TargetType.COMMENT,
                "Comment reported with author "+comment.getAuthor()+" and content "+comment.getContent(),
                reportRequest.getReason()
        );

        reportProducer.sendReport(report);
    }
}
