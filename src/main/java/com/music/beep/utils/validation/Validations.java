package com.music.beep.utils.validation;

import com.music.beep.model.entity.EntityBase;

public interface Validations<TEntity extends EntityBase> {
	void validate(TEntity entity);
}
