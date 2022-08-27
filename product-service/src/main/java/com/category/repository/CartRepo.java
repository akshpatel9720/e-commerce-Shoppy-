package com.category.repository;

import com.category.entity.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
//    Optional<CartEntity> deleteProduct(Long userId, List<String> pId);

    @Query("select u from CartEntity u where u.userId = :userId and u.pId = :pId")
    Optional<CartEntity> findProduct(Long userId, List<String> pId);


    @Query("select u from CartEntity u where u.isActive = :status and u.userId = :userId order by u.createdAt desc")
    Page<CartEntity> findStatusAndUserId(Boolean status, Long userId, Pageable pageable);
}
