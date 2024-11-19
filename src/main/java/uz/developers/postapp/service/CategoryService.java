package uz.developers.postapp.service;
import org.springframework.data.domain.Page;
import uz.developers.postapp.payload.CategoryDto;
import java.util.Optional;

public interface CategoryService {


    Page<CategoryDto> getAllCategories(int page, int size);

    Optional<CategoryDto> getCategoryById(Long categoryId);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    void deleteCategory(Long categoryId);













}
