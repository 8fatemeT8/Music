package com.music.beep.model.repository;

import com.music.beep.model.entity.EntityBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryBase<TEntity extends EntityBase> extends JpaRepository<TEntity, Long>, CrudRepository<TEntity, Long> {
}
