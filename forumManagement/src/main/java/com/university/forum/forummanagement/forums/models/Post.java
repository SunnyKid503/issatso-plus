package com.university.forum.forummanagement.forums.models;

import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity

@DiscriminatorValue("POST")
public class Post extends PostBase{
    private int savedCount;
    private PostType postType;


    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Post(int id, int upVoteCount, int downVoteCount, int viewCount,int savedCount, String content, String title, Member author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, List<TaggableObject> tags, List<Comment> comments, List<FileReference> files, PostType postType) {
        super(id, upVoteCount, downVoteCount, viewCount, content, title, author, categories, createdAt, updatedAt, tags, comments, files);
        this.postType = postType;
        this.savedCount = savedCount ;
    }
    public Post() {
    }
    public void upVote (){ upVoteCount ++  ;}
    public void noUpVote(){ upVoteCount --  ;}
    public void downVote (){ downVoteCount ++  ; }
    public void noDownVote(){ downVoteCount --  ;}
    public void saved (){ savedCount ++  ; }
    public void noSaved (){ savedCount --  ;}
    public void seen (){ viewCount ++  ; }
}

