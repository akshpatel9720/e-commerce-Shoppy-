package com.user.repository;

import com.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.email = :username")
    Optional<UserEntity> findOneByEmailIgnoreCase(String username);


    @Query("SELECT u from UserEntity u where u.isActive = :status order by u.createdDate desc")
    Page<UserEntity> findByStatus(Boolean status, Pageable pageable);
}
