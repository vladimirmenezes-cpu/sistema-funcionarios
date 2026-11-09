
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.Conexao;

// Sem declaração de pacote (classe situada na raiz da pasta src/main/java)

import view.FuncionarioView; // Importa a classe FuncionarioView do pacote view

// Classe principal responsável pelo ponto de entrada (entry point) da aplicação
public class Main {
    // Método principal executado pela Java Virtual Machine (JVM) ao iniciar o programa
    public static void main(String[] args) {
        // Instancia a classe de interface com o usuário (View)
        FuncionarioView view = new FuncionarioView();
        // Chama o método menu() para iniciar a exibição da interface gráfica Swing
        view.menu();
    } // Fim do método main
} // Fim da classe Main