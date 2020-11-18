package com.victor.campanha.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class StandardError {

	private HttpStatus responseStatus;
	private String message;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime localDateTime;

	public StandardError(final HttpStatus responseStatus, final String message) {
		this.responseStatus = responseStatus;
		this.message = message;
		this.localDateTime = LocalDateTime.now();
	}
}
