package com.music.beep.utils.hooks;

import com.music.beep.model.entity.EntityBase;

public interface BeforeDelete<TEntity extends EntityBase> {
	void delete(TEntity entity);
}
