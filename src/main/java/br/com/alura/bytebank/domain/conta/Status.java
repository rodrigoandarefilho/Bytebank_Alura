package br.com.alura.bytebank.domain.conta;

import lombok.Getter;

@Getter
public enum Status {
    A("Ativo"),
    I("Inativo");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }
}
