package com.SkyPro.SpringAndMocckito.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectSurnameException extends RuntimeException{
}
