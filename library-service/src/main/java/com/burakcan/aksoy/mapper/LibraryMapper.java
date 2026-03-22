package com.burakcan.aksoy.mapper;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.burakcan.aksoy.client.BookServiceClient;
import com.burakcan.aksoy.dto.response.LibraryResponse;
import com.burakcan.aksoy.model.Library;

@Component
public class LibraryMapper {
	private final BookServiceClient bookServiceClient;
	
	public LibraryMapper(BookServiceClient bookServiceClient) {
		this.bookServiceClient = bookServiceClient;
	}
	
	public LibraryResponse toResponse(Library library) {
		if (library == null) {
			return null;
		}
		return LibraryResponse.builder()
				.id(library.getId())
				.userBookList(library.getUserBooks().stream()
						.map(book -> bookServiceClient.getBookById(book)) // feign client calıştı
						.map(ResponseEntity::getBody)
						.collect(Collectors.toList()))
				.build();
	}

}
