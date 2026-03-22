package com.burakcan.aksoy.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.burakcan.aksoy.dto.response.BookIdResponse;
import com.burakcan.aksoy.dto.response.BookResponse;
import com.burakcan.aksoy.model.Book;

@Component
public class BookMapper {
	
	public BookResponse toResponse(Book book) {
		if (book == null) {
			return null;
		}
		return BookResponse.builder()
				.bookId(BookIdResponse.builder()
						.bookId(book.getId())
						.isbn(book.getIsbn())
						.build())
				.title(book.getTitle())
				.bookYear(book.getBookYear())
				.author(book.getAuthor())
				.pressName(book.getPressName())
				.build();
	}
	
	public List<BookResponse> toResponseList(List<Book> books){
		if (books == null) {
			return null;
		}
		return books.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public BookIdResponse toIdResponse(Book book) {
		if (book == null) {
			return null;
		}
		return BookIdResponse.builder()
				.bookId(book.getId())
				.isbn(book.getIsbn())
				.build();
	}

}
