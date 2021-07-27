package com.music.beep.controller;

import com.music.beep.utils.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/music")
public class MusicController {


	private FileStorageService fileStorageService;

	public MusicController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@GetMapping("/{name}")
	public ResponseEntity<Resource> getMusic(@PathVariable("name") String name) {
		return ResponseEntity.ok(fileStorageService.loadFileAsResource(name));
	}

	@PostMapping
	public ResponseEntity<?> upload(MultipartFile file) {
		return ResponseEntity.ok(fileStorageService.storeFile(file));
	}
}
