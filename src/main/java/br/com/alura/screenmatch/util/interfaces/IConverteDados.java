package br.com.alura.screenmatch.util.interfaces;

public interface IConverteDados {

  <T> T obterDados(String json, Class<T> classe);
}
