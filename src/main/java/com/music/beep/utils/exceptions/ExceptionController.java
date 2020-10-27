package com.music.beep.utils.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(com.music.beep.utils.exceptions.ExceptionHandler.class)
	public ResponseEntity<?> baseExceptionHandler(com.music.beep.utils.exceptions.ExceptionHandler exceptionHandler) {
		return ResponseEntity.status(exceptionHandler.getCode()).body(exceptionHandler.getMessage());
	}
}
