package com.burakcan.aksoy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.burakcan.aksoy.dto.response.BookIdResponse;
import com.burakcan.aksoy.dto.response.BookResponse;
import com.burakcan.aksoy.service.BookService;

import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping()
	public ResponseEntity<List<BookResponse>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@GetMapping("/isbn/{isbn}")
	public ResponseEntity<BookIdResponse> getBookByIsbn(@PathVariable @NotEmpty String isbn){
		return ResponseEntity.ok(bookService.findByIsbn(isbn));
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable @NotEmpty String id){
		return ResponseEntity.ok(bookService.findBookDetailsById(id));
	}
	
}
