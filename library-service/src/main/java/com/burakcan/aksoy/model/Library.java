package com.burakcan.aksoy.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libraries")
public class Library {
	@Id
	@Column(name = "library_id")
    @GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ElementCollection
	private List<String> userBooks = new ArrayList<>();

}
