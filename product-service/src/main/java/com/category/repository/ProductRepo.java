package com.category.repository;

import com.category.entity.CartEntity;
import com.category.entity.CategoryEntity;
import com.category.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT u FROM ProductEntity u where " + "u.name LIKE CONCAT('%',:Text,'%')")
    List<ProductEntity> search(String Text);


    @Query("select u from ProductEntity u where u.isActive = :status and u.categoryId = :categoryId order by u.createdAt desc")
    Page<ProductEntity> findStatusAndUserId(Boolean status, Long categoryId, Pageable pageable);
}
