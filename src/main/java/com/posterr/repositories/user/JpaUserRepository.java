package com.posterr.repositories.user;

import com.posterr.models.entities.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
