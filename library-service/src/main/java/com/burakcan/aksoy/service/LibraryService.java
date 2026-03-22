package com.burakcan.aksoy.service;

import org.springframework.stereotype.Service;

import com.burakcan.aksoy.client.BookServiceClient;
import com.burakcan.aksoy.dto.request.AddBookRequest;
import com.burakcan.aksoy.dto.response.LibraryResponse;
import com.burakcan.aksoy.exception.LibraryNotFoundException;
import com.burakcan.aksoy.mapper.LibraryMapper;
import com.burakcan.aksoy.model.Library;
import com.burakcan.aksoy.repository.LibraryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
	private final LibraryRepository libraryRepository;
	private final LibraryMapper libraryMapper;
	private final BookServiceClient bookServiceClient;
	
	public LibraryService(LibraryRepository libraryRepository,LibraryMapper libraryMapper,BookServiceClient bookServiceClient) {
		this.libraryRepository = libraryRepository;
		this.libraryMapper = libraryMapper;
		this.bookServiceClient=bookServiceClient;
	}
	
	private Library getLibraryById(String id) {
	    return libraryRepository.findById(id)
	            .orElseThrow(() -> new LibraryNotFoundException("Library not found with id: " + id));
	}

	public LibraryResponse getAllBooksInLibraryById(String id){
		Library library = getLibraryById(id);
		return libraryMapper.toResponse(library);
	}
	
	public LibraryResponse createLibrary() {
		Library newLibrary = libraryRepository.save(new Library());
		return new LibraryResponse(newLibrary.getId());
	}
	
	public void addBookToLibrary(AddBookRequest request) {
		String bookId = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getBookId();
		Library library = getLibraryById(request.getLibraryId());
		library.getUserBooks().add(bookId);
		libraryRepository.save(library);
	}

	public List<String> getAllLibraries() {
		return libraryRepository.findAll()
				.stream()
				.map(Library::getId)
				.collect(Collectors.toList());
	}
}
