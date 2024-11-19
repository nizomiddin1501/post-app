package uz.developers.postapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.developers.postapp.entity.Category;
import uz.developers.postapp.exceptions.CategoryException;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.payload.CategoryDto;
import uz.developers.postapp.repository.CategoryRepository;
import uz.developers.postapp.service.CategoryService;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;


    @Override
    public Page<CategoryDto> getAllCategories(int page, int size) {
        Page<Category> categoriesPage = categoryRepository.findAll(PageRequest.of(page, size));
        return categoriesPage.map(this::categoryToDto);
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
        return Optional.of(categoryToDto(category));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        if (category.getTitle() == null) {
            throw new CategoryException("Category title name must not be null");
        }
        boolean exists = categoryRepository.existsByTitle(category.getTitle());
        if (exists) {
            throw new CategoryException("Category with this title name already exists");
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
        Category categoryDetails = dtoToCategory(categoryDto);
        existingCategory.setTitle(categoryDetails.getTitle());
        existingCategory.setDescription(categoryDetails.getDescription());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
        categoryRepository.delete(category);
    }


    // DTO ---> Entity
    private Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    // Entity ---> DTO
    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

}
