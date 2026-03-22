package com.burakcan.aksoy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.burakcan.aksoy.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	Optional<Book> findBookByIsbn(String isbn);

}
