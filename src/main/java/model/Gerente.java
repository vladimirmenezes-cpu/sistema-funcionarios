// Declaração do pacote
package model;

// Declaração da classe Gerente que herda (extends) da classe Funcionario[cite: 1, 2]
public class Gerente extends Funcionario {
    
    // Atributo privado e específico do Gerente para armazenar o departamento (texto)[cite: 1]
    private String departamento;

    // Construtor vazio padrão
    public Gerente() {
        // Chama o construtor vazio da classe mãe (Funcionario)
        super();
    // Fim do construtor vazio
    }

    // Construtor com parâmetros, incluindo os atributos da classe mãe e o específico do gerente[cite: 2]
    public Gerente(int matricula, String nome, int idade, String departamento) {
        // Chama o construtor da classe mãe (Funcionario) passando matrícula, nome e idade
        super(matricula, nome, idade);
        // Atribui o valor do parâmetro 'departamento' ao atributo específico da classe
        this.departamento = departamento;
    // Fim do construtor
    }

    // Método Getter para obter o departamento do gerente[cite: 2]
    public String getDepartamento() {
        // Retorna o valor atual do departamento
        return departamento;
    // Fim do método
    }

    // Método Setter para definir um novo departamento para o gerente[cite: 2]
    public void setDepartamento(String departamento) {
        // Atualiza o atributo departamento com o novo valor passado
        this.departamento = departamento;
    // Fim do método
    }
// Fim da classe Gerente
}