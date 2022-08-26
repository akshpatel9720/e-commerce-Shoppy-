package com.category.repository;

import com.category.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
    @Query("select u from CartEntity u where u.pId = :pId")
    Optional<CartEntity> deleteProduct(List<String> pId);
}
