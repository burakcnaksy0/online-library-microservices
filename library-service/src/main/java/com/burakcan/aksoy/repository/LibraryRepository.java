package com.burakcan.aksoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.burakcan.aksoy.model.Library;

public interface LibraryRepository extends JpaRepository<Library, String> {

}
