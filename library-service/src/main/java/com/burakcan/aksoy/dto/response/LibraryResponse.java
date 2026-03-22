package com.burakcan.aksoy.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResponse {
	private String id;
	@Builder.Default
	private List<BookResponse> userBookList = new ArrayList<>();
	
	public LibraryResponse(String id) {
		this.id = id;
	}
}
