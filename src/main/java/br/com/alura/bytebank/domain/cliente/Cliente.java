package br.com.alura.bytebank.domain.cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {

    private String nome;
    private String cpf;
    private String email;

    public Cliente(DadosCadastroCliente dadosCliente) {
        this.nome = dadosCliente.nome();
        this.cpf = dadosCliente.cpf();
        this.email = dadosCliente.email();
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
