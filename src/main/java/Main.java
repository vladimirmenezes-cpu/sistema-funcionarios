import view.FuncionarioView; // Importa a classe FuncionarioView do pacote view

// Sem declaração de pacote (classe situada na raiz da pasta src/main/java)

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