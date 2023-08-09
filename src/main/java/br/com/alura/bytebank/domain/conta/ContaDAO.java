package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
    private Connection connection;

    public ContaDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(DadosAberturaConta dadosAberturaConta, Conta conta) {
        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, status)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setDouble(2, conta.getSaldo());
            preparedStatement.setString(3, dadosAberturaConta.dadosCadastroCliente().nome());
            preparedStatement.setString(4, dadosAberturaConta.dadosCadastroCliente().cpf());
            preparedStatement.setString(5, dadosAberturaConta.dadosCadastroCliente().email());
            preparedStatement.setString(6, String.valueOf(dadosAberturaConta.status()));

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Conta gerandoConta(ResultSet resultSet) {
        try {
            Integer numero = resultSet.getInt(1);
            Double saldo = resultSet.getDouble(2);
            String nome = resultSet.getString(3);
            String cpf = resultSet.getString(4);
            String email = resultSet.getString(5);
            Status status = Status.valueOf(resultSet.getString(6));
            var dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);
            var cliente = new Cliente(dadosCadastroCliente);
            return new Conta(numero, saldo, cliente, status);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Set<Conta> listar() {
        Set<Conta> contas = new HashSet<>();
        try {
            String sql = "SELECT * FROM conta";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contas.add(gerandoConta(resultSet));
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return contas;
    }

    public Conta buscarContaPorNumero(Integer numeroConta) {
        Conta conta = null;
        try {
            String sql = "SELECT * FROM conta WHERE numero = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, numeroConta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conta = gerandoConta(resultSet);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conta;
    }

    public void realizarDeposito(Double valorDeposito, Integer numeroConta) {
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, valorDeposito);
            preparedStatement.setInt(2, numeroConta);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(ex);
        }
    }

    public void realizarSaque(Double saque, Integer numeroConta) {
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, saque);
            preparedStatement.setInt(2, numeroConta);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            }catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void encerrarConta(Integer numeroConta) {
        String sql = "UPDATE conta SET status = ? WHERE numero = ? ";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Status.I));
            preparedStatement.setInt(2, numeroConta);

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }
}
