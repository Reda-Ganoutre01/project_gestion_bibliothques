package dev.library.backend.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    private String cover;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private int copies;
    private Long categoryId;
}
