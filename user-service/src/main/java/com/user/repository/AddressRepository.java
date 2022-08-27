package com.user.repository;

import com.user.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Query("select u from AddressEntity u where u.isActive = :status order by u.createdAt desc")
    Page<AddressEntity> findStatus(Boolean status, Pageable pageable);

//    @Query("select u from AddressEntity u where u.isActive = :status order by u.createdAt desc")
//    List<AddressEntity> findStatus(Boolean status, Pageable pageable);
}
