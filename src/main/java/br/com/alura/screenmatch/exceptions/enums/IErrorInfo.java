package br.com.alura.screenmatch.exceptions.enums;

import org.springframework.http.HttpStatus;

public interface IErrorInfo {
  String getTitle();
  String getType();
  String getDatils();
  HttpStatus getHttpStatus();
}
