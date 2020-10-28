package com.music.beep.controller;

import com.music.beep.config.jwt.JwtResponse;
import com.music.beep.model.dto.UserDto;
import com.music.beep.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	private UserService userService;

	public AccountController(UserService userService) {
		this.userService = userService;
	}

	@PutMapping("/verify")
	public ResponseEntity<?> verify(@RequestParam String token) {
		userService.verifyAccount(token);
		return ResponseEntity.ok().build();
	}


	@PostMapping("/register")
	public ResponseEntity<JwtResponse> register(UserDto dto) {
		return ResponseEntity.ok(userService.create(dto));
	}


	@GetMapping("/login")
	public ResponseEntity<JwtResponse> login(UserDto dto) {
		return ResponseEntity.ok(userService.login(dto));
	}
}