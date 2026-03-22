package com.burakcan.aksoy.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.burakcan.aksoy.dto.response.BookIdResponse;
import com.burakcan.aksoy.dto.response.BookResponse;


@FeignClient(name = "book-service", path = "/v1/book" , fallbackFactory = BookServiceClientFallbackFactory.class)
public interface BookServiceClient {

	@GetMapping("/isbn/{isbn}")
	ResponseEntity<BookIdResponse> getBookByIsbn(@PathVariable(value = "isbn") String isbn);


	@GetMapping("/book/{id}")
	ResponseEntity<BookResponse> getBookById(@PathVariable(value = "id") String id);

}