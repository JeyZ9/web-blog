package com.app.blogrestapi.controller;

import com.app.blogrestapi.dto.CategoryDTO;
import com.app.blogrestapi.exception.BlogAPIException;
import com.app.blogrestapi.repository.RoleRepository;
import com.app.blogrestapi.security.JwtTokenProvider;
import com.app.blogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/v1/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {

    private final CategoryService categoryService;
    private final RoleRepository roleRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CategoryController(CategoryService categoryService, RoleRepository roleRepository) {
        this.categoryService = categoryService;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
        try{
            CategoryDTO saveCategory = categoryService.addCategory(categoryDTO);
            return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
        }catch (Exception e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Role Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long categoryId){
        CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category, @PathVariable("id") Long categoryId){
        CategoryDTO updateCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.CREATED);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!.");
    }


}
