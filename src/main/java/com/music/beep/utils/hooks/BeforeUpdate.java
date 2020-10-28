package com.music.beep.utils.hooks;

import com.music.beep.model.dto.DtoBase;
import com.music.beep.model.entity.EntityBase;

public interface BeforeUpdate<TEntity extends EntityBase, TDto extends DtoBase> {
	void update(TDto dto, TEntity entity);
}
