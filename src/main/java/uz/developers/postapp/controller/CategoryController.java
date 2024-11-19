package uz.developers.postapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.postapp.exceptions.CategoryException;
import uz.developers.postapp.payload.CategoryDto;
import uz.developers.postapp.payload.CustomApiResponse;
import uz.developers.postapp.service.CategoryService;

/**
 * REST controller for managing categories, offering endpoints for
 * creating, updating, retrieving, and deleting category records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;


    /**
     * Retrieve a paginated list of categories.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of categories per page (default is 10)
     * @return a ResponseEntity containing a CustomApiResponse with the paginated CategoryDto list
     */
    @Operation(summary = "Get all Categories with Pagination", description = "Retrieve a paginated list of all categories.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of categories.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<Page<CategoryDto>>> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<CategoryDto> categoryDtos = categoryService.getAllCategories(page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of categories.",
                true,
                categoryDtos), HttpStatus.OK);
    }


    /**
     * Retrieve a category by their unique ID using the provided CategoryDto.
     *
     * @param id the ID of the category to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CategoryDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Category by ID", description = "Retrieve a category by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category.")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the category.",
                true,
                categoryDto), HttpStatus.OK);
    }


    /**
     * Creates a new category.
     *
     * @param categoryDto the DTO containing the category information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved category data
     */
    @Operation(summary = "Create a new Category", description = "Create a new category record.")
    @ApiResponse(responseCode = "201", description = "Category created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Category created successfully",
                true,
                savedCategory), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing category using the provided CategoryDto.
     *
     * @param id          the ID of the category to be updated
     * @param categoryDto the DTO containing updated category details
     * @return a ResponseEntity containing a CustomApiResponse with the updated CategoryDto
     */
    @Operation(summary = "Update category", description = "Update the details of an existing category.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CategoryDto>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Category updated successfully",
                true,
                updatedCategory), HttpStatus.OK);
    }


    /**
     * Delete a category by their ID.
     *
     * @param id the ID of the category to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Category", description = "Delete a room by its ID.")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Category deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }

}
