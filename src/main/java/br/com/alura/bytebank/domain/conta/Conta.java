package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conta {

    private Integer numero;
    private Double saldo;
    private Cliente titular;
    private Status status;

    public Conta(Integer numero, Double saldo, Cliente cliente, Status status) {
        this.numero = numero;
        this.saldo = saldo;
        this.titular = cliente;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", saldo=" + saldo +
                ", titular=" + titular +
                '}';
    }
}
