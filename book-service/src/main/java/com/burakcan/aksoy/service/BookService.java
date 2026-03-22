package com.burakcan.aksoy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.burakcan.aksoy.dto.response.BookIdResponse;
import com.burakcan.aksoy.dto.response.BookResponse;
import com.burakcan.aksoy.exception.BookNotFoundException;
import com.burakcan.aksoy.mapper.BookMapper;
import com.burakcan.aksoy.model.Book;
import com.burakcan.aksoy.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;
	
	public BookService(BookRepository bookRepository,BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
	}
	
	public List<BookResponse> getAllBooks(){
		List<Book> bookList = bookRepository.findAll();
		return bookMapper.toResponseList(bookList);
	}
	
	public BookIdResponse findByIsbn(String isbn) {
		Book book = bookRepository.findBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("The book not found this isbn : "+isbn));
		return bookMapper.toIdResponse(book);
	}
	
	public BookResponse findBookDetailsById(String id) {
		Book book = getBookById(id);
		return bookMapper.toResponse(book);
	}
	
	private Book getBookById(String id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("The book not found this id : "+id));
		return book;
	}
}
