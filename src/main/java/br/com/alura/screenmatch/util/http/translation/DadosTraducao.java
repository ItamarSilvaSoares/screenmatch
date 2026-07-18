package br.com.alura.screenmatch.util.http.translation;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosTraducao(@JsonAlias(value = "responseData") DadosResposta dadosResposta) {

}
