package br.com.alura.screenmatch.util.http.translation;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosResposta(@JsonAlias(value = "translatedText") String textoTraduzido) {

}
