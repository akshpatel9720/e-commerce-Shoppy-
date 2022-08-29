package com.user.repository;

import com.user.entity.RedeemCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RedeemCodeRepository extends JpaRepository<RedeemCodeEntity, Long> {
    @Query("select u from RedeemCodeEntity u where u.isActive = :status order by u.createdAt desc ")
    Page<RedeemCodeEntity> findStatus(Boolean status, Pageable pageable);
}
