package com.music.beep.utils.mapper;

import com.music.beep.model.domain.UserDomain;
import com.music.beep.model.dto.UserDto;
import com.music.beep.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, uses = {})
public interface UserMapper extends MapperBase<User, UserDto, UserDomain> {

	default User createNew() {
		return new User();
	}
}
