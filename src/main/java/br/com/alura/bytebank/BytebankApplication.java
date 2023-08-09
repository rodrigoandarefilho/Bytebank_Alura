package br.com.alura.bytebank;

import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.Conta;
import br.com.alura.bytebank.domain.conta.ContaService;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;
import br.com.alura.bytebank.domain.conta.Status;

import java.math.BigDecimal;
import java.util.Scanner;

public class BytebankApplication {
    private static ContaService service = new ContaService();
    private static Scanner teclado = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var opcao = exibirMenu();
        while (opcao != 9) {
            switch (opcao) {
                case 1:
                    listarContas();
                    break;
                case 2:
                    abrirConta();
                    break;
                case 3:
                    encerrarConta();
                    break;
                case 4:
                    consultaSaldo();
                    break;
                case 5:
                    realizarSaque();
                    break;
                case 6:
                    realizarDeposito();
                    break;
                case 7:
                    buscarContaPorNumero();
                    break;
                case 8:
                    realizarTransferencia();
                    break;
            }
            opcao = exibirMenu();
        }
        System.out.println("Finalizando aplicação.");
    }

    private static int exibirMenu() {
        System.out.println("""
                BYTEBANK - ESCOLHA UMA OPCAO:
                1 - Listar contas abertas
                2 - Abertura de conta
                3 - Encerramento de conta
                4 - Consultar saldo de uma conta
                5 - Realizar saque em uma conta
                6 - Realizar depósito em uma conta
                7 - Pesquisar conta por número
                8 - Transferência de dinheiro entre contas
                9 - sair
                """);
        return teclado.nextInt();
    }

    private static void listarContas() {
        System.out.println("Contas cadastradas: ");
        var contas = service.listarContasAbertas();
        contas.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void abrirConta() {
        System.out.println("Digite o número da conta:");
        var numeroConta = teclado.nextInt();

        System.out.println("Digite o nome do cliente:");
        var nome = teclado.next();

        System.out.println("Digite o cpf do cliente");
        var cpf = teclado.next();

        System.out.println("Digite o email do cliente");
        var email = teclado.next();

        service.abrir(new DadosAberturaConta(numeroConta, 0.00, new DadosCadastroCliente(nome, cpf, email), Status.A));

        System.out.println("Conta aberta com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void encerrarConta() {
        System.out.println("Digite o número da conta que deseja encerrar:");
        var numeroConta = teclado.nextInt();

        service.encerrarConta(numeroConta);

        System.out.println("Conta encerrada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void consultaSaldo() {
        System.out.println("Digite o número da conta que deseja consultar o saldo:");
        var numeroConta = teclado.nextInt();
        System.out.println("Saldo da conta é de: " + service.consultarSaldo(numeroConta));

        System.out.println("Consulta de saldo realizada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarDeposito() {
        System.out.println("Qual o numero da conta que deseja depositar ?");
        var numeroConta = teclado.nextInt();

        System.out.println("Digite o valor que deseja depositar:");
        var valorDeposito = teclado.nextDouble();

        service.realizarDeposito(numeroConta, valorDeposito);
        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarSaque() {
        System.out.println("Qual o numero da conta que deseja sacar ?");
        var numeroConta = teclado.nextInt();

        System.out.println("Digite o valor que deseja sacar:");
        var valorSaque = teclado.nextDouble();

        service.realizarSaque(numeroConta, valorSaque);
        System.out.println("Saque realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void buscarContaPorNumero() {
        System.out.println("Digite o número da conta que deseja pesquisar:");
        var numeroConta = teclado.nextInt();
        var conta = service.buscarContaPorNumero(numeroConta);

        System.out.println("*************************");
        System.out.println("Conta cadastrada: ");
        System.out.println(conta);

        System.out.println("Busca por conta realizada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarTransferencia() {
        System.out.println("Digite o número da conta de origem:");
        var numeroContaOrigem = teclado.nextInt();

        System.out.println("Qual o número da conta destino:");
        var numeroContaDestino = teclado.nextInt();

        System.out.println("Digite o valor a ser transferido:");
        var valor = teclado.nextDouble();

        service.realizarTransferencia(numeroContaOrigem, numeroContaDestino, valor);
        System.out.println("Transferência realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }
}
