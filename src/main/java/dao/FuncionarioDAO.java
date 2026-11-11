package dao;
import conexao.Conexao;
import model.Funcionario;
import model.Gerente;
import model.Operador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class FuncionarioDAO {
    public boolean cadastrar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (matricula, nome, idade, tipo, departamento, funcao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, f.getMatricula());
            comando.setString(2, f.getNome());
            comando.setInt(3, f.getIdade());

            if (f instanceof Gerente) {
                Gerente g = (Gerente) f;
                comando.setString(4, "GERENTE");
                comando.setString(5, g.getDepartamento());
                comando.setString(6, null);
            } else {
                Operador o = (Operador) f;
                comando.setString(4, "OPERADOR");
                comando.setString(5, null);
                comando.setString(6, o.getFuncao());
            }

            comando.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionarios";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                int matricula = rs.getInt("matricula");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String tipo = rs.getString("tipo");

                if ("GERENTE".equals(tipo)) {
                    Gerente g = new Gerente(
                            matricula, nome, idade,
                            rs.getString("departamento")
                    );
                    lista.add(g);

                } else {
                    Operador o = new Operador(
                            matricula, nome, idade,
                            rs.getString("funcao")
                    );
                    lista.add(o);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }

    public Funcionario buscarPorMatricula(int matricula) {
        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);

            try (ResultSet rs = comando.executeQuery()) {

                if (rs.next()) {
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String tipo = rs.getString("tipo");

                    if ("GERENTE".equals(tipo)) {
                        return new Gerente(
                                matricula, nome, idade,
                                rs.getString("departamento")
                        );
                    } else {
                        return new Operador(
                                matricula, nome, idade,
                                rs.getString("funcao")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }

        return null;
    }

    public boolean alterar(Funcionario f) {
        String sql = "UPDATE funcionarios SET nome = ?, idade = ?, departamento = ?, funcao = ? WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, f.getNome());
            comando.setInt(2, f.getIdade());

            if (f instanceof Gerente) {
                Gerente g = (Gerente) f;
                comando.setString(3, g.getDepartamento());
                comando.setString(4, null);
            } else {
                Operador o = (Operador) f;
                comando.setString(3, null);
                comando.setString(4, o.getFuncao());
            }

            comando.setInt(5, f.getMatricula());

            int linhas = comando.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int matricula) {
        String sql = "DELETE FROM funcionarios WHERE matricula = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, matricula);

            int linhas = comando.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
}
