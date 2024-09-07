package com.emazon.security.adapters.driven.jpa.mysql.repository;

import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

}
