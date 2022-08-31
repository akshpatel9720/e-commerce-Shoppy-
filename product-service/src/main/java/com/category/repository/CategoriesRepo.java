package com.category.repository;

import com.category.DTO.CategoryListDTO;
import com.category.entity.CartEntity;
import com.category.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepo extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT u FROM CategoryEntity u where " + "u.categoryName LIKE CONCAT('%',:Text,'%')")
    List<CategoryEntity> search(String Text);

    @Query("select u from CategoryEntity u where u.isActive = :status  order by u.createdAt desc")
    Page<CategoryEntity> findStatusAndUserId(Boolean status, Pageable pageable);
}
