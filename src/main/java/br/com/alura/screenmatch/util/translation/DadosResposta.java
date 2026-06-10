package br.com.alura.screenmatch.util.translation;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosResposta(@JsonAlias(value = "translatedText") String textoTraduzido) {

}
