package controller; // Declaração do pacote controller

import dao.FuncionarioDAO; // Importa a classe FuncionarioDAO do pacote dao
import model.Funcionario; // Importa a classe abstrata Funcionario do pacote model
import model.Gerente; // Importa a subclasse Gerente do pacote model
import model.Operador; // Importa a subclasse Operador do pacote model

import java.util.List; // Importa a interface List da biblioteca padrão do Java

// Classe Controller responsável por intermediar a View e a camada DAO
public class FuncionarioController {

    // Declara o objeto DAO como constante para operações de persistência
    private final FuncionarioDAO dao;

    // Construtor do Controller que inicializa o DAO
    public FuncionarioController() {
        this.dao = new FuncionarioDAO(); // Instancia a classe de acesso ao banco de dados
    }

    // Método para validar e solicitar o cadastro de um novo Gerente
    public String cadastrarGerente(int matricula, String nome, int idade, String departamento) {
        // Valida os atributos comuns chamando o método auxiliar
        String erroValidacao = validarDadosGerais(matricula, nome, idade);
        // Se houver mensagem de erro na validação dos dados gerais, interrompe e retorna o erro
        if (erroValidacao != null) {
            return erroValidacao; // Retorna a mensagem de erro encontrada
        }

        // Regra de negócio: verifica se o departamento do gerente foi preenchido
        if (departamento == null || departamento.trim().isEmpty()) {
            return "Erro: O departamento do gerente é obrigatório."; // Retorna mensagem de erro
        }

        // Regra de negócio: garante que a matrícula seja única consultando o banco MySQL
        if (dao.buscarPorMatricula(matricula) != null) {
            return "Erro: Já existe um funcionário cadastrado com a matrícula " + matricula + "."; // Retorna erro se duplicada
        }

        // Instancia o objeto Gerente com os dados validados
        Gerente gerente = new Gerente(matricula, nome.trim(), idade, departamento.trim());
        // Solicita ao DAO a inserção do gerente no banco
        if (dao.cadastrar(gerente)) {
            return "Sucesso: Gerente cadastrado com sucesso!"; // Retorna mensagem de sucesso
        }
        // Retorna mensagem de erro caso a inserção falhe
        return "Erro: Falha ao persistir gerente no banco de dados.";
    }

    // Método para validar e solicitar o cadastro de um novo Operador
    public String cadastrarOperador(int matricula, String nome, int idade, String funcao) {
        // Valida os atributos comuns do funcionário
        String erroValidacao = validarDadosGerais(matricula, nome, idade);
        // Interrompe e retorna o erro caso exista inconformidade nos dados
        if (erroValidacao != null) {
            return erroValidacao; // Retorna a mensagem de erro
        }

        // Regra de negócio: verifica se a função do operador foi preenchida
        if (funcao == null || funcao.trim().isEmpty()) {
            return "Erro: A função do operador é obrigatória."; // Retorna erro se estiver em branco
        }

        // Regra de negócio: verifica a unicidade da matrícula
        if (dao.buscarPorMatricula(matricula) != null) {
            return "Erro: Já existe um funcionário cadastrado com a matrícula " + matricula + "."; // Retorna erro de duplicidade
        }

        // Instancia o objeto Operador com os dados validados
        Operador operador = new Operador(matricula, nome.trim(), idade, funcao.trim());
        // Envia o objeto ao DAO para persistir no MySQL
        if (dao.cadastrar(operador)) {
            return "Sucesso: Operador cadastrado com sucesso!"; // Retorna mensagem de sucesso
        }
        // Retorna mensagem de erro se o comando SQL falhar
        return "Erro: Falha ao persistir operador no banco de dados.";
    }

    // Método que solicita a listagem de todos os funcionários ao DAO
    public List<Funcionario> listarTodos() {
        return dao.listar(); // Retorna a lista vinda do banco de dados
    }

    // Método para consultar um funcionário pela matrícula
    public Funcionario buscarPorMatricula(int matricula) {
        return dao.buscarPorMatricula(matricula); // Retorna a instância encontrada ou null
    }

    // Método para validar e alterar dados de um funcionário existente
    public String alterar(int matricula, String nome, int idade, String atributoEspecifico) {
        // Regra de negócio: verifica se a matrícula existe antes de alterar
        Funcionario existente = dao.buscarPorMatricula(matricula);
        // Cancela o processo se a matrícula não estiver cadastrada
        if (existente == null) {
            return "Erro: Matrícula " + matricula + " não encontrada no sistema."; // Retorna erro
        }

        // Valida se o novo nome foi informado
        if (nome == null || nome.trim().isEmpty()) {
            return "Erro: O nome é obrigatório."; // Retorna erro de validação
        }
        // Valida se a idade é maior que zero
        if (idade <= 0) {
            return "Erro: A idade deve ser um número inteiro maior que zero."; // Retorna erro de validação
        }

        // Verifica polimorficamente se a matrícula pertence a um Gerente
        if (existente instanceof Gerente) {
            // Valida a obrigatoriedade do departamento
            if (atributoEspecifico == null || atributoEspecifico.trim().isEmpty()) {
                return "Erro: O departamento é obrigatório."; // Retorna erro se vazio
            }
            // Instancia o Gerente atualizado
            Gerente gerenteAtualizado = new Gerente(matricula, nome.trim(), idade, atributoEspecifico.trim());
            // Executa a alteração no DAO
            if (dao.alterar(gerenteAtualizado)) {
                return "Sucesso: Dados do gerente alterados com sucesso!"; // Sucesso
            }
        // Verifica polimorficamente se pertence a um Operador
        } else if (existente instanceof Operador) {
            // Valida a obrigatoriedade da função
            if (atributoEspecifico == null || atributoEspecifico.trim().isEmpty()) {
                return "Erro: A função é obrigatória."; // Retorna erro se vazio
            }
            // Instancia o Operador atualizado
            Operador operadorAtualizado = new Operador(matricula, nome.trim(), idade, atributoEspecifico.trim());
            // Executa a alteração no DAO
            if (dao.alterar(operadorAtualizado)) {
                return "Sucesso: Dados do operador alterados com sucesso!"; // Sucesso
            }
        }

        // Retorna erro se a atualização falhar no MySQL
        return "Erro: Falha ao atualizar dados no banco de dados.";
    }

    // Método para excluir um funcionário pela matrícula
    public String excluir(int matricula) {
        // Regra de negócio: verifica existência da matrícula antes de excluir
        Funcionario existente = dao.buscarPorMatricula(matricula);
        // Interrompe se o registro não for localizado
        if (existente == null) {
            return "Erro: Matrícula " + matricula + " não encontrada para exclusão."; // Retorna erro
        }

        // Solicita a exclusão ao DAO
        if (dao.excluir(matricula)) {
            return "Sucesso: Funcionário excluído com sucesso!"; // Retorna confirmação de sucesso
        }
        // Retorna mensagem em caso de falha de exclusão
        return "Erro: Falha ao remover funcionário do banco de dados.";
    }

    // Método privado auxiliar para validação dos atributos comuns de Funcionario
    private String validarDadosGerais(int matricula, String nome, int idade) {
        // Valida se a matrícula é maior que zero
        if (matricula <= 0) {
            return "Erro: A matrícula deve ser um número inteiro positivo."; // Validação da matrícula
        }
        // Valida se o nome foi fornecido
        if (nome == null || nome.trim().isEmpty()) {
            return "Erro: O nome é obrigatório."; // Validação do nome
        }
        // Valida se a idade é maior que zero
        if (idade <= 0) {
            return "Erro: A idade deve ser um inteiro maior que zero."; // Validação da idade
        }
        // Retorna null caso todas as regras tenham sido atendidas
        return null;
    }
}