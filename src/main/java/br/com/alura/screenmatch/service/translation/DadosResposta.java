package br.com.alura.screenmatch.service.translation;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosResposta(@JsonAlias(value = "translatedText") String textoTraduzido) {}
