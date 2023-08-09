package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraNegocioException;
import br.com.alura.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ContaService {

    private ConnectionFactory connectionFactory;
    private Conta conta;

    public ContaService() {
        this.connectionFactory = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection connection = connectionFactory.recuperarConexao();
        return new ContaDAO(connection).listar();
    }

    public Conta buscarContaPorNumero(Integer numeroConta) {
        Connection connection = connectionFactory.recuperarConexao();
        Conta conta =  new ContaDAO(connection).buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new RegraNegocioException("Não existe conta cadastrada com esse número!");
        }
        return conta;
    }

    public void abrir(DadosAberturaConta dadosAberturaConta) {
        var cliente = new Cliente(dadosAberturaConta.dadosCadastroCliente());
        var conta = new Conta(dadosAberturaConta.numero(), dadosAberturaConta.saldo(), cliente, dadosAberturaConta.status());
        Connection connection = connectionFactory.recuperarConexao();
        new ContaDAO(connection).salvar(dadosAberturaConta, conta);
    }

    public void realizarDeposito(int numeroConta, double valorDeposito) {
        if (valorDeposito <= 0) {
            throw new RegraNegocioException("Valor do deposito deve ser superior a zero!");
        }
        var conta = buscarContaPorNumero(numeroConta);
        var novoValor = conta.getSaldo() + valorDeposito;
        Connection connection = connectionFactory.recuperarConexao();
        new ContaDAO(connection).realizarDeposito(novoValor, conta.getNumero());
    }

    public void realizarSaque(int numeroConta, double valorSaque) {
        var conta = buscarContaPorNumero(numeroConta);
        var saque = conta.getSaldo() - valorSaque;

        if (saque < 0) {
            throw new RegraNegocioException("Saldo insuficiente !");
        }
        if (valorSaque <= 0) {
            throw new RegraNegocioException("Valor do saque deve ser superior a zero!");
        }

        Connection connection = connectionFactory.recuperarConexao();
        new ContaDAO(connection).realizarSaque(saque, conta.getNumero());
    }

    public void realizarTransferencia(int numeroContaOrigem, int numeroContaDestino, double valor) {
        realizarSaque(numeroContaOrigem, valor);
        realizarDeposito(numeroContaDestino, valor);
    }

    public Double consultarSaldo(int numeroConta) {
        var conta = buscarContaPorNumero(numeroConta);
        return  conta.getSaldo();
    }

    public void encerrarConta(Integer numeroConta) {
        var conta = buscarContaPorNumero(numeroConta);
        if (conta.getSaldo() != 0) {
            throw new RegraNegocioException("Conta não pode ser encerrada, pois ainda possui saldo!");
        }
        Connection connection = connectionFactory.recuperarConexao();
        new ContaDAO(connection).encerrarConta(conta.getNumero());
    }
}
