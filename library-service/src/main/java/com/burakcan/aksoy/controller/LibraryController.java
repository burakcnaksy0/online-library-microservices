package com.burakcan.aksoy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.burakcan.aksoy.dto.request.AddBookRequest;
import com.burakcan.aksoy.dto.response.LibraryResponse;
import com.burakcan.aksoy.service.LibraryService;

import java.util.*;

@RestController
@RequestMapping("/v1/library")
public class LibraryController {
	Logger logger = LoggerFactory.getLogger(LibraryController.class);

	private final LibraryService libraryService;
	private final Environment environment;



	public LibraryController(LibraryService libraryService, Environment environment) {
		this.libraryService = libraryService;
        this.environment = environment;
    }

	@GetMapping("/{id}")
	public ResponseEntity<LibraryResponse> getLibraryById(@PathVariable String id){
		return ResponseEntity.ok(libraryService.getAllBooksInLibraryById(id));
	}

	@PostMapping
	public ResponseEntity<LibraryResponse> createLibrary(){
		logger.info("Library created on port number "+ environment.getProperty("local.server.port"));
		return ResponseEntity.ok(libraryService.createLibrary());
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addBookToLibrary(@RequestBody AddBookRequest bookCreateRequest){
		libraryService.addBookToLibrary(bookCreateRequest);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<String>> getAllLibraries(){
		return ResponseEntity.ok(libraryService.getAllLibraries());
	}

}
