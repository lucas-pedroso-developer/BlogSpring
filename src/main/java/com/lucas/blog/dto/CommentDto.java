package com.lucas.blog.dto;

import java.time.LocalDate;

import javax.persistence.Convert;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	
	private Long id;
	private Long post;
	private String author;
	private String text;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate date;
	
	
}
