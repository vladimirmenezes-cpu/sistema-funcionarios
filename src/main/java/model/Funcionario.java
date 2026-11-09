// Declaração do pacote (ajuste conforme a estrutura do seu projeto)
package model;

// Declaração da classe pública Funcionario
public class Funcionario {
    
    // Atributo privado para armazenar a matrícula do funcionário (número inteiro)[cite: 1]
    private int matricula;
    
    // Atributo privado para armazenar o nome do funcionário (texto)[cite: 1]
    private String nome;
    
    // Atributo privado para armazenar a idade do funcionário (número inteiro)[cite: 1]
    private int idade;

    // Construtor vazio padrão, útil para instanciar o objeto sem dados iniciais
    public Funcionario() {
    // Fim do construtor vazio
    }

    // Construtor com parâmetros para inicializar todos os atributos ao criar o objeto[cite: 2]
    public Funcionario(int matricula, String nome, int idade) {
        // Atribui o valor do parâmetro 'matricula' ao atributo da classe
        this.matricula = matricula;
        // Atribui o valor do parâmetro 'nome' ao atributo da classe
        this.nome = nome;
        // Atribui o valor do parâmetro 'idade' ao atributo da classe
        this.idade = idade;
    // Fim do construtor com parâmetros
    }

    // Método Getter para obter o valor da matrícula[cite: 2]
    public int getMatricula() {
        // Retorna o valor atual da matrícula
        return matricula;
    // Fim do método
    }

    // Método Setter para definir um novo valor para a matrícula[cite: 2]
    public void setMatricula(int matricula) {
        // Atualiza o atributo matrícula com o novo valor passado
        this.matricula = matricula;
    // Fim do método
    }

    // Método Getter para obter o nome do funcionário[cite: 2]
    public String getNome() {
        // Retorna o valor atual do nome
        return nome;
    // Fim do método
    }

    // Método Setter para definir um novo nome para o funcionário[cite: 2]
    public void setNome(String nome) {
        // Atualiza o atributo nome com o novo valor passado
        this.nome = nome;
    // Fim do método
    }

    // Método Getter para obter a idade do funcionário[cite: 2]
    public int getIdade() {
        // Retorna o valor atual da idade
        return idade;
    // Fim do método
    }

    // Método Setter para definir uma nova idade para o funcionário[cite: 2]
    public void setIdade(int idade) {
        // Atualiza o atributo idade com o novo valor passado
        this.idade = idade;
    // Fim do método
    }
// Fim da classe Funcionario
}