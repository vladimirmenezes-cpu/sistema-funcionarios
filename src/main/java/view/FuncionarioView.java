package view;
import controller.FuncionarioController;
import model.Funcionario;
import model.Gerente;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.List;
public class FuncionarioView {
    private static final String TITULO = "Sistema de Funcionários";
    private final FuncionarioController controller = new FuncionarioController();

    public void menu() {
        String[] opcoes = {"Cadastrar", "Listar", "Pesquisar", "Alterar", "Excluir", "Sair"};
        int opcao;

        do {
            opcao = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    TITULO,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            switch (opcao) {
                case 0 -> cadastrar();
                case 1 -> listar();
                case 2 -> pesquisar();
                case 3 -> alterar();
                case 4 -> excluir();
                default -> { } 
            }
        } while (opcao != 5 && opcao != JOptionPane.CLOSED_OPTION);

        informar("Sistema encerrado.");
    }

    private void cadastrar() {
        String[] tipos = {"Gerente", "Operador"};
        int tipo = JOptionPane.showOptionDialog(
                null,
                "Qual o tipo de funcionário?",
                TITULO,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        if (tipo == JOptionPane.CLOSED_OPTION) {
            return;
        }

        try {
            Integer matricula = lerInteiro("Matrícula:");
            if (matricula == null) return;

            String nome = pedir("Nome:", "");
            if (nome == null) return;

            Integer idade = lerInteiro("Idade:");
            if (idade == null) return;

            String resultado;

            if (tipo == 0) {
                String departamento = pedir("Departamento:", "");
                if (departamento == null) return;

                resultado = controller.cadastrarGerente(matricula, nome, idade, departamento);
            } else {
                String funcao = pedir("Função:", "");
                if (funcao == null) return;

                resultado = controller.cadastrarOperador(matricula, nome, idade, funcao);
            }

            mostrarResultado(resultado);

        } catch (NumberFormatException e) {
            erro("Digite apenas números na matrícula e na idade!");
        }
    }

    private void listar() {
        List<Funcionario> lista = controller.listarTodos();

        if (lista.isEmpty()) {
            informar("Nenhum funcionário cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Funcionario f : lista) {
            sb.append(f).append("\n-----------------------\n");
        }

        JTextArea area = new JTextArea(sb.toString(), 18, 36);
        area.setEditable(false);
        area.setCaretPosition(0);

        JOptionPane.showMessageDialog(
                null,
                new JScrollPane(area),
                TITULO + " - Lista (" + lista.size() + ")",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void pesquisar() {
        try {
            Integer matricula = lerInteiro("Matrícula:");
            if (matricula == null) return;

            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                erro("Funcionário não encontrado!");
            } else {
                informar(f.toString());
            }
        } catch (NumberFormatException e) {
            erro("Digite apenas números!");
        }
    }

    private void alterar() {
        try {
            Integer matricula = lerInteiro("Matrícula do funcionário que quer alterar:");
            if (matricula == null) return;

            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                erro("Funcionário não encontrado!");
                return;
            }

            informar("Dados atuais:\n\n" + f);

            String nome = pedir("Novo nome:", f.getNome());
            if (nome == null) return;

            Integer idade = lerInteiro("Nova idade:", String.valueOf(f.getIdade()));
            if (idade == null) return;

            String atributo;
            if (f instanceof Gerente g) {
                atributo = pedir("Novo departamento:", g.getDepartamento());
            } else {
                atributo = pedir("Nova função:", ((model.Operador) f).getFuncao());
            }
            if (atributo == null) return;

            mostrarResultado(controller.alterar(matricula, nome, idade, atributo));

        } catch (NumberFormatException e) {
            erro("Digite apenas números na matrícula e na idade!");
        }
    }

    private void excluir() {
        try {
            Integer matricula = lerInteiro("Matrícula do funcionário que quer excluir:");
            if (matricula == null) return;

            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                erro("Funcionário não encontrado!");
                return;
            }

            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    f + "\n\nTem certeza que quer excluir?",
                    TITULO,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                mostrarResultado(controller.excluir(matricula));
            } else {
                informar("Exclusão cancelada.");
            }
        } catch (NumberFormatException e) {
            erro("Digite apenas números!");
        }
    }

    private String pedir(String mensagem, String valorInicial) {
        Object resposta = JOptionPane.showInputDialog(
                null,
                mensagem,
                TITULO,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                valorInicial
        );
        return resposta == null ? null : resposta.toString();
    }

    private Integer lerInteiro(String mensagem) {
        return lerInteiro(mensagem, "");
    }

    private Integer lerInteiro(String mensagem, String valorInicial) {
        String texto = pedir(mensagem, valorInicial);
        if (texto == null) return null;
        return Integer.parseInt(texto.trim());
    }

    private void mostrarResultado(String resultado) {
        if (resultado.startsWith("Erro")) {
            erro(resultado);
        } else {
            informar(resultado);
        }
    }

    private void informar(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    private void erro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO, JOptionPane.ERROR_MESSAGE);
    }
}