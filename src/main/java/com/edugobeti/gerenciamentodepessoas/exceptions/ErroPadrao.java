package com.edugobeti.gerenciamentodepessoas.exceptions;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@JsonInclude(content = Include.NON_NULL)
@Getter
@Setter
public class ErroPadrao {

	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
}
