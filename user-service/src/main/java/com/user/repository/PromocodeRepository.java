package com.user.repository;

import com.user.entity.PromocodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocodeRepository extends JpaRepository<PromocodeEntity, Long> {

    @Query("select u from PromocodeEntity u where u.couponcode = :couponcode and u.type = :type")
    Optional<PromocodeEntity> findPromocode(String couponcode, String type);

    @Query("select u from PromocodeEntity u where u.isActive = :status order by u.createdAt desc ")
    Page<PromocodeEntity> findStatus(Boolean status, Pageable pageable);
}
