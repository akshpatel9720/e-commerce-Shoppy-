package com.category.repository;

import com.category.entity.HeadersImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadersImageRepo extends JpaRepository<HeadersImageEntity,Long> {
    @Query("select u from HeadersImageEntity u where u.isActive = :status order by u.createdAt desc")
    Page<HeadersImageEntity> findStatusAndUserId(Boolean status, Pageable pageable);
}
