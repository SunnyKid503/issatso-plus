package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.dtos.database.FypPostDto;
import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.models.PostCategory;
import com.university.forum.forummanagement.forums.models.PostType;
import com.university.forum.forummanagement.forums.models.UserPostInteraction;
import com.university.forum.forummanagement.membership.dtos.responses.ForumMemberResponseDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FypPostResponseDto extends PostResponseDto{
    private boolean isViewed;
    private boolean isUpVoted;
    private boolean isDownVoted;
    private boolean isSaved;

    public FypPostResponseDto(int id, int upVoteCount, int downVoteCount, int viewCount, String content, String title, ForumMemberResponseDto author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, PostType postType, List<TaggableObjectResponseDto> tags, boolean isViewed, boolean isUpVoted, boolean isDownVoted, boolean isSaved, List<String> fileUrls) {
        super(id, upVoteCount, downVoteCount, viewCount, content, title, author, categories, createdAt, updatedAt, postType, tags, fileUrls);
        this.isViewed = isViewed;
        this.isUpVoted = isUpVoted;
        this.isDownVoted = isDownVoted;
        this.isSaved = isSaved;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public boolean isUpVoted() {
        return isUpVoted;
    }

    public void setUpVoted(boolean upVoted) {
        isUpVoted = upVoted;
    }

    public boolean isDownVoted() {
        return isDownVoted;
    }

    public void setDownVoted(boolean downVoted) {
        isDownVoted = downVoted;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public static FypPostResponseDto toFypPostResponseDto(FypPostDto fypPost)
    {
        UserPostInteraction nullableInteraction = Optional.ofNullable(fypPost.getUserPostInteraction())
                .orElse(new UserPostInteraction());

        return new FypPostResponseDto(
                fypPost.getPost().getId(),
                fypPost.getPost().getUpVoteCount(),
                fypPost.getPost().getDownVoteCount(),
                fypPost.getPost().getViewCount(),
                fypPost.getPost().getContent(),
                fypPost.getPost().getTitle(),
                ForumMemberResponseDto.toMemberResponseDto(fypPost.getPost().getAuthor()),
                fypPost.getPost().getCategories(),
                fypPost.getPost().getCreatedAt(),
                fypPost.getPost().getUpdatedAt(),
                fypPost.getPost().getPostType(),
                fypPost.getPost().getTags().stream().map(TaggableObjectResponseDto::create).toList(),
                nullableInteraction.isSeen(),
                nullableInteraction.isUpVote(),
                nullableInteraction.isDownVote(),
                nullableInteraction.isSaved(),
                fypPost.getPost().getFiles().stream().map(FileReference::getImageUrl).collect(Collectors.toList())
        );
    }
}
