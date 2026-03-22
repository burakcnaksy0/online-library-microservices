package com.burakcan.aksoy;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.burakcan.aksoy.model.Book;
import com.burakcan.aksoy.repository.BookRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class BookServiceApplication implements CommandLineRunner {
	private final BookRepository bookRepository;
	
	public BookServiceApplication(BookRepository bookRepository) {
		this.bookRepository=bookRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Book book1 = new Book(null, "Dünyanın Gözü", 1990, "Robert Jordan", "İthaki Yayınları", "9786053751480");
		Book book2 = new Book(null, "Yüzüklerin Efendisi", 1954, "J.R.R. Tolkien", "Metis Yayıncılık", "9789753423473");
		Book book3 = new Book(null, "Harry Potter ve Felsefe Taşı", 1997, "J.K. Rowling", "YKY", "9789750802942");
		
		bookRepository.saveAll(List.of(book1, book2, book3));
		
		System.out.println("Örnek 3 adet kitap veritabanına başarıyla kaydedildi!");
	}

}
