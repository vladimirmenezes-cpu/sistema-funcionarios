package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import conexao.Conexao;
import model.Funcionario;
import model.Gerente;
import model.Operador;

public class FuncionarioDAO {

    // 2. Cadastro de Gerente e Operador
    public boolean cadastrar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (matricula, nome, idade, tipo, departamento, funcao) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, f.getMatricula());
            comando.setString(2, f.getNome());
            comando.setInt(3, f.getIdade());

            if (f instanceof Gerente gerente) {
                comando.setString(4, "GERENTE");
                comando.setString(5, gerente.getDepartamento());
                comando.setNull(6, Types.VARCHAR);
            } else if (f instanceof Operador operador) {
                comando.setString(4, "OPERADOR");
                comando.setNull(5, Types.VARCHAR);
                comando.setString(6, operador.getFuncao());
            }

            comando.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    // 3. Listagem de Funcionários
    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionarios ORDER BY matricula";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                int matricula = resultado.getInt("matricula");
                String nome = resultado.getString("nome");
                int idade = resultado.getInt("idade");
                String tipo = resultado.getString("tipo");

                if ("GERENTE".equalsIgnoreCase(tipo)) {
                    String departamento = resultado.getString("departamento");
                    funcionarios.add(new Gerente(matricula, nome, idade, departamento));
                } else if ("OPERADOR".equalsIgnoreCase(tipo)) {
                    String funcao = resultado.getString("funcao");
                    funcionarios.add(new Operador(matricula, nome, idade, funcao));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    // 4. Pesquisa pela Matrícula
    public Funcionario buscar(int matricula) {
        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    String nome = resultado.getString("nome");
                    int idade = resultado.getInt("idade");
                    String tipo = resultado.getString("tipo");

                    if ("GERENTE".equalsIgnoreCase(tipo)) {
                        String departamento = resultado.getString("departamento");
                        return new Gerente(matricula, nome, idade, departamento);
                    } else if ("OPERADOR".equalsIgnoreCase(tipo)) {
                        String funcao = resultado.getString("funcao");
                        return new Operador(matricula, nome, idade, funcao);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    // 5. Alteração de Dados
    public boolean alterar(Funcionario f) {
        String sql = "UPDATE funcionarios SET nome = ?, idade = ?, departamento = ?, funcao = ? WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, f.getNome());
            comando.setInt(2, f.getIdade());

            if (f instanceof Gerente gerente) {
                comando.setString(3, gerente.getDepartamento());
                comando.setNull(4, Types.VARCHAR);
            } else if (f instanceof Operador operador) {
                comando.setNull(3, Types.VARCHAR);
                comando.setString(4, operador.getFuncao());
            }

            comando.setInt(5, f.getMatricula());
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar funcionário: " + e.getMessage());
            return false;
        }
    }

    // 6. Exclusão de Funcionário
    public boolean remover(int matricula) {
        String sql = "DELETE FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover funcionário: " + e.getMessage());
            return false;
        }
    }
}