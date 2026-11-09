package view;

import controller.FuncionarioController; // Importa a classe FuncionarioController do pacote controller
import model.Funcionario; // Importa a classe Funcionario do pacote model
import model.Gerente; // Importa a classe Gerente do pacote model
import model.Operador; // Importa a classe Operador do pacote model

import java.util.List; // Importa a interface List
import java.util.Scanner; // Importa o Scanner para ler os dados digitados no terminal

// Classe View responsável pela interface de usuário em modo texto (terminal)
public class FuncionarioView {

    // Declara o atributo do controlador que gerenciará a lógica de apresentação
    private final FuncionarioController controller;

    // Scanner usado para ler tudo o que o usuário digitar no terminal
    private final Scanner scanner;

    // Construtor padrão da classe de visão
    public FuncionarioView() {
        this.controller = new FuncionarioController(); // Instancia o controlador de funcionários
        this.scanner = new Scanner(System.in); // Instancia o leitor da entrada padrão
    }

    // Método principal da View responsável por apresentar o menu de navegação
    public void menu() {
        int opcao; // Guarda a opção escolhida pelo usuário

        // Repete o menu até que o usuário escolha sair (opção 0)
        do {
            System.out.println();
            System.out.println("=== SISTEMA DE CONTROLE DE FUNCIONÁRIOS ===");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Listar Todos os Funcionários");
            System.out.println("3 - Pesquisar por Matrícula");
            System.out.println("4 - Alterar Funcionário");
            System.out.println("5 - Excluir Funcionário");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Escolha uma opção: "); // Lê a opção já validada como número

            // Direciona o fluxo de execução com base na opção digitada
            switch (opcao) {
                case 1: // Cadastro
                    cadastrar();
                    break;
                case 2: // Listagem
                    listar();
                    break;
                case 3: // Pesquisa por Matrícula
                    pesquisar();
                    break;
                case 4: // Alteração
                    alterar();
                    break;
                case 5: // Exclusão
                    excluir();
                    break;
                case 0: // Saída
                    System.out.println("Sistema encerrado.");
                    break;
                default: // Opção inválida
                    System.out.println("Erro: Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close(); // Libera o leitor ao encerrar o sistema
    }

    // Método privado para capturar os dados do cadastro
    private void cadastrar() {
        System.out.println();
        System.out.println("--- Cadastro de Funcionário ---");
        System.out.println("1 - Gerente");
        System.out.println("2 - Operador");
        int tipo = lerInteiro("Tipo de funcionário: ");

        // Cancela o cadastro se o tipo não for válido
        if (tipo != 1 && tipo != 2) {
            System.out.println("Erro: Tipo inválido. Escolha 1 (Gerente) ou 2 (Operador).");
            return;
        }

        // Captura os dados comuns aos dois tipos de funcionário
        int matricula = lerInteiro("Matrícula: ");
        String nome = lerTexto("Nome: ");
        int idade = lerInteiro("Idade: ");

        String resultado; // Guarda o retorno do controlador
        if (tipo == 1) {
            // Solicita o departamento e aciona o controller para cadastrar gerente
            String departamento = lerTexto("Departamento: ");
            resultado = controller.cadastrarGerente(matricula, nome, idade, departamento);
        } else {
            // Solicita a função e aciona o controller para cadastrar operador
            String funcao = lerTexto("Função: ");
            resultado = controller.cadastrarOperador(matricula, nome, idade, funcao);
        }

        // Exibe a mensagem de resposta (sucesso ou erro)
        System.out.println(resultado);
    }

    // Método privado para apresentar todos os funcionários na tela
    private void listar() {
        // Solicita a lista de funcionários cadastrados ao controlador
        List<Funcionario> funcionarios = controller.listarTodos();

        // Exibe mensagem caso não haja nenhum registro no banco
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("=== LISTA DE FUNCIONÁRIOS ===");
        // Itera sobre a lista chamando o método toString() sobrescrito nas subclasses
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            System.out.println("--------------------------------------------------");
        }
    }

    // Método privado para buscar e exibir os dados de um funcionário específico
    private void pesquisar() {
        System.out.println();
        System.out.println("--- Pesquisa por Matrícula ---");
        int matricula = lerInteiro("Digite a matrícula: ");

        // Executa a consulta através da controller
        Funcionario f = controller.buscarPorMatricula(matricula);
        if (f != null) {
            System.out.println();
            System.out.println("Funcionário encontrado:");
            System.out.println(f);
        } else {
            System.out.println("Erro: Funcionário com matrícula " + matricula + " não foi encontrado.");
        }
    }

    // Método privado para capturar novos dados e efetuar alteração
    private void alterar() {
        System.out.println();
        System.out.println("--- Alterar Funcionário ---");
        int matricula = lerInteiro("Matrícula do funcionário a alterar: ");

        // Verifica a existência da matrícula antes de pedir os novos dados
        Funcionario f = controller.buscarPorMatricula(matricula);
        if (f == null) {
            System.out.println("Erro: Matrícula " + matricula + " não encontrada no sistema.");
            return;
        }

        // Mostra os dados atuais e explica como manter um campo sem alterar
        System.out.println();
        System.out.println("Dados atuais:");
        System.out.println(f);
        System.out.println();
        System.out.println("(Pressione Enter para manter o valor atual)");

        String novoNome = lerTextoComPadrao("Novo nome", f.getNome());
        int novaIdade = lerInteiroComPadrao("Nova idade", f.getIdade());

        String novoAtributo; // Campo específico do cargo (departamento ou função)
        if (f instanceof Gerente) {
            Gerente g = (Gerente) f; // Downcasting para Gerente
            novoAtributo = lerTextoComPadrao("Novo departamento", g.getDepartamento());
        } else {
            Operador op = (Operador) f; // Downcasting para Operador
            novoAtributo = lerTextoComPadrao("Nova função", op.getFuncao());
        }

        // Envia os dados coletados para atualização na controller e exibe o resultado
        String resultado = controller.alterar(matricula, novoNome, novaIdade, novoAtributo);
        System.out.println(resultado);
    }

    // Método privado para exclusão com solicitação de confirmação
    private void excluir() {
        System.out.println();
        System.out.println("--- Excluir Funcionário ---");
        int matricula = lerInteiro("Matrícula do funcionário a ser excluído: ");

        // Verifica se a matrícula existe antes de pedir a confirmação
        Funcionario f = controller.buscarPorMatricula(matricula);
        if (f == null) {
            System.out.println("Erro: Matrícula " + matricula + " não encontrada no sistema.");
            return;
        }

        // Mostra o registro e pede confirmação explícita
        System.out.println();
        System.out.println("Funcionário a ser excluído:");
        System.out.println(f);
        String confirmacao = lerTexto("Deseja realmente excluir? (S/N): ");

        // A exclusão só é executada se o usuário digitar "S"
        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.println(controller.excluir(matricula));
        } else {
            System.out.println("Operação de exclusão cancelada.");
        }
    }

    // ---------- Métodos auxiliares de leitura do terminal ----------

    // Lê uma linha de texto digitada pelo usuário (sem espaços nas pontas)
    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    // Lê um número inteiro, repetindo a pergunta até o usuário digitar um valor válido
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada); // Converte para int
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número inteiro válido.");
            }
        }
    }

    // Lê um texto; se o usuário apenas apertar Enter, mantém o valor atual
    private String lerTextoComPadrao(String rotulo, String atual) {
        System.out.print(rotulo + " [" + atual + "]: ");
        String entrada = scanner.nextLine().trim();
        return entrada.isEmpty() ? atual : entrada;
    }

    // Lê um inteiro; se o usuário apenas apertar Enter, mantém o valor atual
    private int lerInteiroComPadrao(String rotulo, int atual) {
        while (true) {
            System.out.print(rotulo + " [" + atual + "]: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return atual;
            }
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número inteiro válido.");
            }
        }
    }
}