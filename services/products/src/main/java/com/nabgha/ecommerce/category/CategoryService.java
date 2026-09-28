package com.nabgha.ecommerce.category;

import com.nabgha.ecommerce.exceptions.CategoryAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        var name = request.name().trim();
        if (categoryRepository.existsByName(name)) {
            throw new CategoryAlreadyExistsException(name);
        }

        try {
            var category = categoryRepository.saveAndFlush(
                    categoryMapper.toCategory(new CategoryRequest(name, request.description()))
            );
            return categoryMapper.toCategoryResponse(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryAlreadyExistsException(name);
        }
    }

    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryResponse);
    }
}
