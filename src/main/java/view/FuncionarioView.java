package view;

package view; // Declaração do pacote view

import controller.FuncionarioController; // Importa a classe FuncionarioController do pacote controller
import model.Funcionario; // Importa a classe Funcionario do pacote model
import model.Gerente; // Importa a classe Gerente do pacote model
import model.Operador; // Importa a classe Operador do pacote model

import javax.swing.*; // Importa componentes de interface de usuário Swing
import java.awt.Dimension; // Importa a classe Dimension para ajuste de componentes Swing
import java.util.List; // Importa a interface List

// Classe View responsável pela interface de usuário com componentes graficos Swing (JOptionPane)
public class FuncionarioView {

    // Declara o atributo do controlador que gerenciará a lógica de apresentação
    private final FuncionarioController controller;

    // Construtor padrão da classe de visão
    public FuncionarioView() {
        this.controller = new FuncionarioController(); // Instancia o controlador de funcionários
    }

    // Método principal da View responsável por apresentar o menu de navegação
    public void menu() {
        // Opções disponíveis para o usuário no menu interativo
        String[] opcoes = {
            "1 - Cadastrar Funcionário",
            "2 - Listar Todos os Funcionários",
            "3 - Pesquisar por Matrícula",
            "4 - Alterar Funcionário",
            "5 - Excluir Funcionário",
            "0 - Sair"
        };

        // Estrutura de repetição para exibir o menu até que ocorra encerramento voluntário
        while (true) {
            // Exibe a caixa de seleção com as opções do menu
            String opcaoSelecionada = (String) JOptionPane.showInputDialog(
                null, // Componente pai (centralizado na tela)
                "Selecione uma opção:", // Mensagem instrucional
                "Sistema de Controle de Funcionários", // Título da janela
                JOptionPane.QUESTION_MESSAGE, // Tipo da mensagem
                null, // Ícone customizado (não utilizado)
                opcoes, // Lista de opções
                opcoes[0] // Opção selecionada por padrão
            );

            // Verifica se a janela foi fechada ou se a opção escolhida foi "0 - Sair"
            if (opcaoSelecionada == null || opcaoSelecionada.startsWith("0")) {
                // Notifica o usuário sobre o encerramento da aplicação
                JOptionPane.showMessageDialog(null, "Sistema encerrado.");
                break; // Encerra o laço de repetição do menu
            }

            // Direciona o fluxo de execução com base no primeiro caractere da opção selecionada
            switch (opcaoSelecionada.charAt(0)) {
                case '1': // Cadastro
                    cadastrar(); // Executa fluxo de cadastro
                    break;
                case '2': // Listagem
                    listar(); // Executa fluxo de listagem
                    break;
                case '3': // Pesquisa por Matrícula
                    pesquisar(); // Executa fluxo de busca
                    break;
                case '4': // Alteração
                    alterar(); // Executa fluxo de atualização
                    break;
                case '5': // Exclusão
                    excluir(); // Executa fluxo de deleção com confirmação
                    break;
                default: // Opção inválida
                    JOptionPane.showMessageDialog(null, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE); // Alerta erro
            }
        }
    }

    // Método privado para capturar os dados do formulário de cadastro
    private void cadastrar() {
        // Define os tipos de funcionários suportados no sistema
        String[] tipos = {"Gerente", "Operador"};
        // Exibe diálogo com botões para escolha do tipo de funcionário
        int tipoOpcao = JOptionPane.showOptionDialog(
            null,
            "Selecione o tipo de funcionário:",
            "Cadastro de Funcionário",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            tipos,
            tipos[0]
        );

        // Cancela o processo se o diálogo for fechado
        if (tipoOpcao == JOptionPane.CLOSED_OPTION) return;

        try {
            // Captura e valida a matrícula
            String matStr = JOptionPane.showInputDialog(null, "Informe a matrícula:");
            if (matStr == null) return; // Cancela se clicar em Cancelar
            int matricula = Integer.parseInt(matStr); // Converte para int

            // Captura o nome
            String nome = JOptionPane.showInputDialog(null, "Informe o nome:");
            if (nome == null) return; // Cancela se fechar a caixa

            // Captura a idade
            String idadeStr = JOptionPane.showInputDialog(null, "Informe a idade:");
            if (idadeStr == null) return; // Cancela se fechar a caixa
            int idade = Integer.parseInt(idadeStr); // Converte para int

            String resultado; // Guarda o retorno do controlador
            // Se o usuário selecionou "Gerente" (índice 0)
            if (tipoOpcao == 0) {
                // Solicita o departamento do gerente
                String departamento = JOptionPane.showInputDialog(null, "Informe o departamento do gerente:");
                if (departamento == null) return; // Cancela se clicar em Cancelar
                // Aciona a controller para cadastrar gerente
                resultado = controller.cadastrarGerente(matricula, nome, idade, departamento);
            } else { // Se o usuário selecionou "Operador" (índice 1)
                // Solicita a função do operador
                String funcao = JOptionPane.showInputDialog(null, "Informe a função do operador:");
                if (funcao == null) return; // Cancela se clicar em Cancelar
                // Aciona a controller para cadastrar operador
                resultado = controller.cadastrarOperador(matricula, nome, idade, funcao);
            }

            // Exibe a mensagem de resposta (sucesso ou erro)
            exibirMensagemResultado(resultado);

        } catch (NumberFormatException e) {
            // Trata exceção para entradas numéricas inválidas na matrícula ou idade
            JOptionPane.showMessageDialog(null, "Erro: Matrícula e idade devem ser valores inteiros válidos.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método privado para apresentar todos os funcionários em tela
    private void listar() {
        // Solicita a lista de funcionários cadastrados ao controlador
        List<Funcionario> funcionarios = controller.listarTodos();
        // Exibe mensagem caso não haja nenhum registro no banco
        if (funcionarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado.", "Listagem", JOptionPane.INFORMATION_MESSAGE);
            return; // Interrompe o método
        }

        // Utiliza StringBuilder para construir o texto formatado dos registros
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE FUNCIONÁRIOS ===\n\n");
        // Itera sobre a lista chamando o método toString() sobrescrito nas subclasses
        for (Funcionario f : funcionarios) {
            sb.append(f.toString()).append("\n--------------------------------------------------\n");
        }

        // Cria caixa de texto com o conteúdo formatado da lista
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false); // Impede edição de conteúdo pelo usuário
        // Adiciona barras de rolagem à caixa de texto
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 320)); // Define tamanho da janela rolável

        // Exibe o painel com os funcionários
        JOptionPane.showMessageDialog(null, scrollPane, "Funcionários Cadastrados", JOptionPane.PLAIN_MESSAGE);
    }

    // Método privado para buscar e exibir os dados de um funcionário específico
    private void pesquisar() {
        try {
            // Captura a matrícula informada pelo usuário
            String matStr = JOptionPane.showInputDialog(null, "Digite a matrícula para pesquisa:");
            if (matStr == null) return; // Cancela a pesquisa se nulo
            int matricula = Integer.parseInt(matStr); // Converte a matrícula para int

            // Executa a consulta através da controller
            Funcionario f = controller.buscarPorMatricula(matricula);
            // Verifica se o funcionário foi localizado
            if (f != null) {
                // Exibe as informações do funcionário encontrado
                JOptionPane.showMessageDialog(null, "Funcionário encontrado:\n\n" + f.toString(), "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Exibe alerta informando que a matrícula não foi encontrada
                JOptionPane.showMessageDialog(null, "Erro: Funcionário com matrícula " + matricula + " não foi encontrado.", "Pesquisa", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Trata erros de digitação de matrícula com caracteres não numéricos
            JOptionPane.showMessageDialog(null, "Erro: A matrícula deve ser um número inteiro.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método privado para capturar novos dados e efetuar alteração
    private void alterar() {
        try {
            // Captura a matrícula do registro a ser alterado
            String matStr = JOptionPane.showInputDialog(null, "Digite a matrícula do funcionário a alterar:");
            if (matStr == null) return; // Cancela fluxo
            int matricula = Integer.parseInt(matStr); // Converte para inteiro

            // Regra: verifica existência prévia da matrícula antes de abrir os campos
            Funcionario f = controller.buscarPorMatricula(matricula);
            if (f == null) {
                JOptionPane.showMessageDialog(null, "Erro: Matrícula " + matricula + " não encontrada no sistema.", "Alteração", JOptionPane.ERROR_MESSAGE);
                return; // Encerra o método se não existir
            }

            // Oferece o nome atual no campo de entrada
            String novoNome = JOptionPane.showInputDialog(null, "Informe o novo nome:", f.getNome());
            if (novoNome == null) return; // Cancela alteração

            // Oferece a idade atual no campo de entrada
            String novaIdadeStr = JOptionPane.showInputDialog(null, "Informe a nova idade:", String.valueOf(f.getIdade()));
            if (novaIdadeStr == null) return; // Cancela alteração
            int novaIdade = Integer.parseInt(novaIdadeStr); // Converte nova idade

            String novoAtributo; // Variável para o campo específico do cargo
            // Verifica o tipo do funcionário para carregar o campo correto
            if (f instanceof Gerente) {
                Gerente g = (Gerente) f; // Realiza o downcasting para Gerente
                novoAtributo = JOptionPane.showInputDialog(null, "Informe o novo departamento:", g.getDepartamento());
            } else {
                Operador op = (Operador) f; // Realiza o downcasting para Operador
                novoAtributo = JOptionPane.showInputDialog(null, "Informe a nova função:", op.getFuncao());
            }
            if (novoAtributo == null) return; // Cancela caso o usuário feche a janela

            // Envia os dados coletados para atualização na controller
            String resultado = controller.alterar(matricula, novoNome, novaIdade, novoAtributo);
            // Exibe a resposta final da operação
            exibirMensagemResultado(resultado);

        } catch (NumberFormatException e) {
            // Trata falhas na conversão da idade para o formato numérico
            JOptionPane.showMessageDialog(null, "Erro: A idade deve ser um valor inteiro.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método privado para exclusão com solicitação de confirmação
    private void excluir() {
        try {
            // Captura a matrícula do funcionário a ser removido
            String matStr = JOptionPane.showInputDialog(null, "Digite a matrícula do funcionário a ser excluído:");
            if (matStr == null) return; // Cancela operação
            int matricula = Integer.parseInt(matStr); // Converte para inteiro

            // Regra: verifica se a matrícula existe antes de solicitar confirmação
            Funcionario f = controller.buscarPorMatricula(matricula);
            if (f == null) {
                JOptionPane.showMessageDialog(null, "Erro: Matrícula " + matricula + " não encontrada no sistema.", "Exclusão", JOptionPane.ERROR_MESSAGE);
                return; // Interrompe o fluxo de exclusão
            }

            // Exibe caixa de diálogo confirmando explicitamente a exclusão do registro
            int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente excluir o funcionário abaixo?\n\n" + f.toString(), // Detalhes do registro
                "Confirmação de Exclusão", // Título da janela de confirmação
                JOptionPane.YES_NO_OPTION, // Apresenta apenas opções SIM / NÃO
                JOptionPane.WARNING_MESSAGE // Exibe ícone visual de alerta
            );

            // A exclusão só é finalizada se o usuário clicar em "SIM" (YES_OPTION)
            if (confirmacao == JOptionPane.YES_OPTION) {
                String resultado = controller.excluir(matricula); // Executa o delete no Controller/DAO
                exibirMensagemResultado(resultado); // Exibe o resultado obtido
            } else {
                // Alerta que a exclusão foi abortada pelo usuário
                JOptionPane.showMessageDialog(null, "Operação de exclusão cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            // Trata erros de conversão no campo da matrícula
            JOptionPane.showMessageDialog(null, "Erro: A matrícula deve ser um número inteiro.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método utilitário privado para centralizar as saídas de feedback visual
    private void exibirMensagemResultado(String mensagem) {
        // Se a string da mensagem iniciar com "Sucesso", abre diálogo informativo
        if (mensagem.startsWith("Sucesso")) {
            JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Se for de erro, exibe diálogo com ícone visual de erro
            JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
