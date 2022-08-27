package com.category.repository;

import com.category.entity.HeadersImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadersImageRepo extends JpaRepository<HeadersImageEntity,Long> {
}
