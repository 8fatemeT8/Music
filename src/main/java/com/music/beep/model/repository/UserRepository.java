package com.music.beep.model.repository;

import com.music.beep.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends RepositoryBase<User> {
	Optional<User> findByUsername(String username);
}
