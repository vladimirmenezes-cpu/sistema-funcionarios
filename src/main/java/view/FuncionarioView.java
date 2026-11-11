package view;
import controller.FuncionarioController;
import model.Funcionario;
import model.Gerente;
import java.util.List;
import java.util.Scanner;
public class FuncionarioView {
    private final FuncionarioController controller = new FuncionarioController();
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println();
            System.out.println("=== SISTEMA DE FUNCIONARIOS ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Pesquisar por matricula");
            System.out.println("4 - Alterar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            if (opcao == 1) {
                cadastrar();
            } else if (opcao == 2) {
                listar();
            } else if (opcao == 3) {
                pesquisar();
            } else if (opcao == 4) {
                alterar();
            } else if (opcao == 5) {
                excluir();
            } else if (opcao == 0) {
                System.out.println("Sistema encerrado.");
            } else {
                System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private void cadastrar() {
        System.out.println("1 - Gerente");
        System.out.println("2 - Operador");
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine().trim();

        if (!"1".equals(tipo) && !"2".equals(tipo)) {
            System.out.println("Erro: tipo invalido!");
            return;
        }
        try {
            System.out.print("Matricula: ");
            int matricula = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine().trim());

            if ("1".equals(tipo)) {
                System.out.print("Departamento: ");
                String departamento = scanner.nextLine();

                System.out.println(
                        controller.cadastrarGerente(
                                matricula, nome, idade, departamento
                        )
                );
            } else {
                System.out.print("Funcao: ");
                String funcao = scanner.nextLine();

                System.out.println(
                        controller.cadastrarOperador(
                                matricula, nome, idade, funcao
                        )
                );
            }
        } catch (NumberFormatException e) {
            System.out.println(
                    "Erro: digite apenas numeros na matricula e na idade!"
            );
        }
    }

    private void listar() {
        List<Funcionario> lista = controller.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado.");
        } else {
            for (Funcionario f : lista) {
                System.out.println(f);
                System.out.println("-----------------------");
            }
        }
    }

    private void pesquisar() {
        try {
            System.out.print("Matricula: ");
            int matricula = Integer.parseInt(scanner.nextLine().trim());
            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                System.out.println("Funcionario nao encontrado!");
            } else {
                System.out.println(f);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite apenas numeros!");
        }
    }

    private void alterar() {
        try {
            System.out.print("Matricula do funcionario que quer alterar: ");
            int matricula = Integer.parseInt(scanner.nextLine().trim());
            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                System.out.println("Funcionario nao encontrado!");
                return;
            }
            System.out.println("Dados atuais:");
            System.out.println(f);
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Nova idade: ");
            int idade = Integer.parseInt(scanner.nextLine().trim());
            String atributo;

            if (f instanceof Gerente) {
                System.out.print("Novo departamento: ");
                atributo = scanner.nextLine();
            } else {
                System.out.print("Nova funcao: ");
                atributo = scanner.nextLine();
            }
            System.out.println(
                    controller.alterar(matricula, nome, idade, atributo)
            );
        } catch (NumberFormatException e) {
            System.out.println(
                    "Erro: digite apenas numeros na matricula e na idade!"
            );
        }
    }

    private void excluir() {
        try {
            System.out.print("Matricula do funcionario que quer excluir: ");
            int matricula = Integer.parseInt(scanner.nextLine().trim());
            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                System.out.println("Funcionario nao encontrado!");
                return;
            }
            System.out.println(f);
            System.out.print("Tem certeza que quer excluir? (S/N): ");
            String resposta = scanner.nextLine().trim();
            if ("S".equalsIgnoreCase(resposta)) {
                System.out.println(controller.excluir(matricula));
            } else {
                System.out.println("Exclusao cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite apenas numeros!");
        }
    }
}
