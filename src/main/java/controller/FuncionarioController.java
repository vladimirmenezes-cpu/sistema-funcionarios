package controller;
import java.util.List;
import dao.FuncionarioDAO;
import model.Funcionario;
import model.Gerente;
import model.Operador;

public class FuncionarioController {
    private final FuncionarioDAO dao = new FuncionarioDAO();

    public String cadastrarGerente(int matricula, String nome, int idade, String departamento) {
        return cadastrar(matricula, nome, idade, departamento, true);
    }

    public String cadastrarOperador(int matricula, String nome, int idade, String funcao) {
        return cadastrar(matricula, nome, idade, funcao, false);
    }

    private String cadastrar(int matricula, String nome, int idade,
                             String atributo, boolean gerente) {

        String erro = validarDadosGerais(matricula, nome, idade);

        if (erro != null) {
            return erro;
        }

        if (atributo == null || atributo.trim().isEmpty()) {

            if (gerente) {
                return "Erro: O departamento do gerente é obrigatório.";
            } else {
                return "Erro: A função do operador é obrigatória.";
            }
        }

        if (dao.buscarPorMatricula(matricula) != null) {
            return "Erro: Já existe um funcionário cadastrado com a matrícula "
                    + matricula + ".";
        }

        Funcionario funcionario;

        if (gerente) {
            funcionario = new Gerente(
                    matricula, nome.trim(), idade, atributo.trim()
            );
        } else {
            funcionario = new Operador(
                    matricula, nome.trim(), idade, atributo.trim()
            );
        }

        boolean cadastrado = dao.cadastrar(funcionario);

        if (cadastrado) {

            if (gerente) {
                return "Sucesso: Gerente cadastrado com sucesso!";
            } else {
                return "Sucesso: Operador cadastrado com sucesso!";
            }
        }

        return "Erro: Falha ao persistir funcionário no banco de dados.";
    }

    public List<Funcionario> listarTodos() {
        return dao.listar();
    }

    public Funcionario buscarPorMatricula(int matricula) {
        return dao.buscarPorMatricula(matricula);
    }

    public String alterar(int matricula, String nome, int idade, String atributo) {

        Funcionario existente = dao.buscarPorMatricula(matricula);

        if (existente == null) {
            return "Erro: Matrícula " + matricula
                    + " não encontrada no sistema.";
        }

        String erro = validarDadosGerais(matricula, nome, idade);

        if (erro != null) {
            return erro;
        }

        if (atributo == null || atributo.trim().isEmpty()) {

            if (existente instanceof Gerente) {
                return "Erro: O departamento é obrigatório.";
            } else {
                return "Erro: A função é obrigatória.";
            }
        }

        Funcionario atualizado;

        if (existente instanceof Gerente) {
            atualizado = new Gerente(
                    matricula, nome.trim(), idade, atributo.trim()
            );
        } else {
            atualizado = new Operador(
                    matricula, nome.trim(), idade, atributo.trim()
            );
        }

        if (dao.alterar(atualizado)) {

            if (existente instanceof Gerente) {
                return "Sucesso: Dados do gerente alterados com sucesso!";
            } else {
                return "Sucesso: Dados do operador alterados com sucesso!";
            }
        }

        return "Erro: Falha ao atualizar dados no banco de dados.";
    }

    public String excluir(int matricula) {

        if (dao.buscarPorMatricula(matricula) == null) {
            return "Erro: Matrícula " + matricula
                    + " não encontrada para exclusão.";
        }

        if (dao.excluir(matricula)) {
            return "Sucesso: Funcionário excluído com sucesso!";
        }

        return "Erro: Falha ao remover funcionário do banco de dados.";
    }

    private String validarDadosGerais(int matricula, String nome, int idade) {

        if (matricula <= 0) {
            return "Erro: A matrícula deve ser um número inteiro positivo.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "Erro: O nome é obrigatório.";
        }

        if (idade <= 0) {
            return "Erro: A idade deve ser um inteiro maior que zero.";
        }

        return null;
    }
}
