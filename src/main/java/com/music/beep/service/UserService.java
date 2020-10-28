package com.music.beep.service;

import com.music.beep.config.jwt.JwtResponse;
import com.music.beep.config.jwt.JwtTokenUtil;
import com.music.beep.config.jwt.UserAuthentication;
import com.music.beep.model.domain.UserDomain;
import com.music.beep.model.dto.UserDto;
import com.music.beep.model.entity.User;
import com.music.beep.model.repository.UserRepository;
import com.music.beep.utils.EmailService;
import com.music.beep.utils.ThreadUtils;
import com.music.beep.utils.exceptions.ErrorCodes;
import com.music.beep.utils.exceptions.ExceptionHandler;
import com.music.beep.utils.mapper.UserMapper;
import com.music.beep.utils.validation.Validations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UserService extends ServiceBase<User, UserDto, UserDomain, UserRepository, UserMapper> implements Validations<User> {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private PasswordEncoder encoder;
	private EmailService emailService;
	private JwtTokenUtil jwtTokenUtil;
	private UserAuthentication userAuthentication;

	public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder,
					   EmailService emailService, JwtTokenUtil jwtTokenUtil, UserAuthentication userAuthentication) {
		super(userRepository, userMapper);
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.encoder = encoder;
		this.emailService = emailService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userAuthentication = userAuthentication;
	}

	@Override
	public void validate(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent())
			throw new ExceptionHandler("this username taken before", ErrorCodes.ERROR_CODE_USER_NAME_NOT_UNIQUE);

		if (user.getEmail() != null) {
			ThreadUtils.createThreadAndStart(() -> {
				String token = jwtTokenUtil.generateToken(user);
				try {
//					emailService.sendEmailWithLink(user.getEmail(),
//							"verify email", "hi " + user.getUsername() + " please click on the blew link and verify email to do all you want"
//							, "https://localhost:8081/api/account/verify?token:" + token);
				} catch (/*Messaging*/Exception e) {
				}
			});
		}
		if (user.getEmail() == null) {
			throw new ExceptionHandler("please enter email", ErrorCodes.ERROR_CODE_USER_DETAIL_IS_NOT_COMPLETE);
		}

	}

	@Override
	public UserDomain createAndUpdate(UserDto userDto) {
		User entity = userMapper.toEntity(userDto);

		((Validations<User>) this).validate(entity);
		entity.setPassword(encoder.encode(userDto.getPassword()));

		return userMapper.toDomain(userRepository.save(entity));
	}

	public JwtResponse create(UserDto dto) {
		createAndUpdate(dto);
		return userAuthentication.authenticate(dto.getUsername(), dto.getPassword());
	}

	public JwtResponse login(UserDto dto) {
		return userAuthentication.authenticate(dto.getUsername(), dto.getPassword());
	}

	public void verifyAccount(String token) {
		String username = jwtTokenUtil.getUsernameFromToken(token);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ExceptionHandler("user not found", ErrorCodes.ERROR_CODE_USER_NOT_FOUND));

		user.setEnable(true);

		userRepository.save(user);
	}
}
