package com.university.forum.forummanagement.forums.services.abstracts;

import com.university.forum.forummanagement.forums.models.PostCategory;
import com.university.forum.forummanagement.forums.repositories.PostCategoriesRepository;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import com.university.forum.forummanagement.structures.repositories.TaggableObjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PostServiceBase {
    protected final boolean throwOnWrongValuesInUserProvidedCollections = false;//e.g. tags and categories that are submitted by the user when creating a post
    protected final MemberRepository memberRepository;
    protected final PostCategoriesRepository categoriesRepository;
    protected final TaggableObjectRepository taggableObjectRepository;

    public PostServiceBase(MemberRepository memberRepository,
                           PostCategoriesRepository categoriesRepository,
                           TaggableObjectRepository taggableObjectRepository) {
        this.memberRepository = memberRepository;
        this.categoriesRepository = categoriesRepository;
        this.taggableObjectRepository = taggableObjectRepository;
    }

    protected List<PostCategory> loadCategories(List<Integer> categoriesIds) throws ElementNotFoundException {
        if(categoriesIds == null)
            return new ArrayList<>();
        List<PostCategory> categories = categoriesRepository.findAllById(categoriesIds);
        if(throwOnWrongValuesInUserProvidedCollections && categoriesIds.size() != categories.size())
            throw new ElementNotFoundException("Some of the provided categories are not valid.");

        return categories.stream().toList();
    }

    protected List<TaggableObject> loadTaggedObjects(List<Integer> taggedObjectsIds) throws ElementNotFoundException {
        if(taggedObjectsIds == null)
            return new ArrayList<>();
        List<TaggableObject> taggedObjects = taggableObjectRepository.findAllById(taggedObjectsIds);
        if(throwOnWrongValuesInUserProvidedCollections && taggedObjects.size() != taggedObjectsIds.size())
            throw new ElementNotFoundException("Some of the provided tagged objects are not valid.");

        return taggedObjects.stream().toList();
    }

    protected Member loadAuthor(UUID memberId) throws ElementNotFoundException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ElementNotFoundException("Member does not exist."));
    }

}
