package com.burakcan.aksoy.client;

import org.slf4j.*;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.burakcan.aksoy.dto.response.BookIdResponse;
import com.burakcan.aksoy.dto.response.BookResponse;
import com.burakcan.aksoy.exception.BookNotFoundException;

@Component
public class BookServiceClientFallbackFactory implements FallbackFactory<BookServiceClient> {

	private static final Logger logger = LoggerFactory.getLogger(BookServiceClientFallbackFactory.class);

	@Override
	public BookServiceClient create(Throwable cause) {
		return new BookServiceClient() {
			
			@Override
			public ResponseEntity<BookIdResponse> getBookByIsbn(String isbn) {
				if (cause instanceof BookNotFoundException) {
					throw (BookNotFoundException) cause;
				}
				logger.error("Error occurred during getBookByIsbn for ISBN: {}, fallback triggered! Cause: {}", isbn, cause.getMessage());
				return ResponseEntity.ok(new BookIdResponse("default-book","default-isbn"));
			}
			
			@Override
			public ResponseEntity<BookResponse> getBookById(String id) {
				if (cause instanceof BookNotFoundException) {
					throw (BookNotFoundException) cause;
				}
				logger.error("Error occurred during getBookById for ID: {}, fallback triggered! Cause: {}", id, cause.getMessage());
				return ResponseEntity.ok(new BookResponse(new BookIdResponse("default-book","default-isbn"),"default-title",0,"default-author","default-press-name"));
			}
		};
	}

}
