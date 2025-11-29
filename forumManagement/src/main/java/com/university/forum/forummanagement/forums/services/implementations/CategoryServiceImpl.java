package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.admin.dtos.requests.CreateCategoryRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.SimpleCategoryResponseDto;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.models.PostCategory;
import com.university.forum.forummanagement.forums.repositories.PostCategoriesRepository;
import com.university.forum.forummanagement.forums.services.interfaces.CategoryService;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final PostCategoriesRepository postCategoriesRepository;

    public CategoryServiceImpl(PostCategoriesRepository postCategoriesRepository) {
        this.postCategoriesRepository = postCategoriesRepository;
    }

    @Override
    public List<SimpleCategoryResponseDto> get() {
        return postCategoriesRepository.findAll()
                .stream().map(SimpleCategoryResponseDto::toSimpleCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleCategoryResponseDto delete(int id) throws ElementNotFoundException {
        PostCategory category = loadCategory(id);
        postCategoriesRepository.delete(category);
        return new SimpleCategoryResponseDto(category.getId(), category.getTypename());
    }

    @Override
    public SimpleCategoryResponseDto create(CreateCategoryRequestDto dto) throws ElementAlreadyExistsException {
        throwIfCategoryAlreadyExists(dto.getTypename());
        PostCategory category = new PostCategory();
        category.setTypename(dto.getTypename());
        postCategoriesRepository.save(category);
        return new SimpleCategoryResponseDto(category.getId(), category.getTypename());
    }

    protected void throwIfCategoryAlreadyExists(String typename) throws ElementAlreadyExistsException {
        if(postCategoriesRepository.existsByName(typename))
            throw new ElementAlreadyExistsException("A category with the same name already exists");
    }

    protected void throwIfCategoryAlreadyExists(int id) throws ElementAlreadyExistsException {
        if(postCategoriesRepository.existsById(id))
            throw new ElementAlreadyExistsException("A category with the same name already exists");
    }

    protected PostCategory loadCategory(int id) throws ElementNotFoundException {
        return postCategoriesRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Category does not exist."));
    }
}
