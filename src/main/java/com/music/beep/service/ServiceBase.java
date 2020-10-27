package com.music.beep.service;

import com.music.beep.model.domain.DomainBase;
import com.music.beep.model.dto.DtoBase;
import com.music.beep.model.entity.EntityBase;
import com.music.beep.model.repository.RepositoryBase;
import com.music.beep.utils.exceptions.ErrorCodes;
import com.music.beep.utils.exceptions.ExceptionHandler;
import com.music.beep.utils.hooks.BeforeAdd;
import com.music.beep.utils.hooks.BeforeDelete;
import com.music.beep.utils.hooks.BeforeUpdate;
import com.music.beep.utils.mapper.MapperBase;
import com.music.beep.utils.validation.Validations;


public class ServiceBase<TEntity extends EntityBase, TDto extends DtoBase,
		TDomain extends DomainBase, TRepository extends RepositoryBase<TEntity>, TMapper extends MapperBase<TEntity, TDto, TDomain>> {
	private TRepository repository;
	private TMapper mapper;

	public ServiceBase(TRepository repository, TMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public TDomain createAndUpdate(TDto dto) {
		TEntity entity = mapper.toEntity(dto);

		((Validations<TEntity>) this).validate(entity);
		if (this instanceof BeforeAdd && dto.getId() == null) {
			((BeforeAdd<TEntity, TDto>) this).add(dto, entity);
		}
		if (this instanceof BeforeUpdate && dto.getId() != null) {
			((BeforeUpdate<TEntity, TDto>) this).update(dto, entity);
		}
		return mapper.toDomain(repository.save(entity));
	}

	public TDomain getOne(Long id) throws ExceptionHandler {
		return mapper.toDomain(repository.findById(id)
				.orElseThrow(() -> new ExceptionHandler("entity not found", ErrorCodes.ERROR_CODE_ENTITY_NOT_FOUND)));
	}

	public void delete(Long id) {
		TEntity entity = repository.findById(id)
				.orElseThrow(() -> new ExceptionHandler("entity not found", ErrorCodes.ERROR_CODE_ENTITY_NOT_FOUND));
		if (this instanceof BeforeDelete)
			((BeforeDelete<TEntity>) this).delete(entity);

		repository.delete(entity);
	}
}
