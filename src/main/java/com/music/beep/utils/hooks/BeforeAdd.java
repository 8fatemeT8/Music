package com.music.beep.utils.hooks;

import com.music.beep.model.dto.DtoBase;
import com.music.beep.model.entity.EntityBase;

public interface BeforeAdd<TEntity extends EntityBase, TDto extends DtoBase> {
	void add(TDto dto, TEntity entity);
}
