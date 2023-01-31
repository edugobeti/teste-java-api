package com.edugobeti.gerenciamentodepessoas.exceptions;


public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	public EntidadeNaoEncontradaException(String msg, Throwable cause) {
		super(cause);
	}	
}


