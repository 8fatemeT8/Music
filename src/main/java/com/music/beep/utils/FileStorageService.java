package com.music.beep.utils;

import com.music.beep.utils.exceptions.ErrorCodes;
import com.music.beep.utils.exceptions.ExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

	@Value("${file.upload-dir}")
	private Path fileStorageLocation;

	static {
		try {
			Files.createDirectories(Path.of("music")
					.toAbsolutePath().normalize());
		} catch (Exception ex) {
			throw new ExceptionHandler("Could not create the directory where the uploaded files will be stored.", ErrorCodes.ERROR_CODE_CANT_CREATE_DIRECTORY);
		}
	}

	public String storeFile(MultipartFile file) {
		try {
			String fileName = UUID.randomUUID().toString();
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String replaceFile(MultipartFile file, String oldResume) {
		try {
			loadFileAsResource(oldResume).getFile().delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return storeFile(file);
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {

				throw new ExceptionHandler("resume.not.found", ErrorCodes.ERROR_CODE_RESUME_IS_NOT_EXIST);
			}
		} catch (MalformedURLException ex) {
			throw new ExceptionHandler("", ErrorCodes.ERROR_CODE_MALFORMED_URL_EXCEPTION);
		}
	}

	public void deleteFile(String fileName) {
		try {
			loadFileAsResource(fileName).getFile().delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
