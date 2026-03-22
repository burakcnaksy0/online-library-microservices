package com.burakcan.aksoy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
	private BookIdResponse bookId;
    private String title;
    private int bookYear;
    private String author;
    private String pressName;
}