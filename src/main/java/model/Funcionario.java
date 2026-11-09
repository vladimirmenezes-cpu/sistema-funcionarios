// Declaração do pacote (ajuste conforme a estrutura do seu projeto)
package model;

// Declaração da classe abstrata Funcionario (classe base de Gerente e Operador)
public abstract class Funcionario {
    
    // Atributo privado para armazenar a matrícula do funcionário (número inteiro)
    private int matricula;
    
    // Atributo privado para armazenar o nome do funcionário (texto)
    private String nome;
    
    // Atributo privado para armazenar a idade do funcionário (número inteiro)
    private int idade;

    // Construtor vazio padrão, útil para instanciar o objeto sem dados iniciais
    public Funcionario() {
    // Fim do construtor vazio
    }

    // Construtor com parâmetros para inicializar todos os atributos ao criar o objeto
    public Funcionario(int matricula, String nome, int idade) {
        // Atribui o valor do parâmetro 'matricula' ao atributo da classe
        this.matricula = matricula;
        // Atribui o valor do parâmetro 'nome' ao atributo da classe
        this.nome = nome;
        // Atribui o valor do parâmetro 'idade' ao atributo da classe
        this.idade = idade;
    // Fim do construtor com parâmetros
    }

    // Método Getter para obter o valor da matrícula
    public int getMatricula() {
        // Retorna o valor atual da matrícula
        return matricula;
    // Fim do método
    }

    // Método Setter para definir um novo valor para a matrícula
    public void setMatricula(int matricula) {
        // Atualiza o atributo matrícula com o novo valor passado
        this.matricula = matricula;
    // Fim do método
    }

    // Método Getter para obter o nome do funcionário
    public String getNome() {
        // Retorna o valor atual do nome
        return nome;
    // Fim do método
    }

    // Método Setter para definir um novo nome para o funcionário
    public void setNome(String nome) {
        // Atualiza o atributo nome com o novo valor passado
        this.nome = nome;
    // Fim do método
    }

    // Método Getter para obter a idade do funcionário
    public int getIdade() {
        // Retorna o valor atual da idade
        return idade;
    // Fim do método
    }

    // Método Setter para definir uma nova idade para o funcionário
    public void setIdade(int idade) {
        // Atualiza o atributo idade com o novo valor passado
        this.idade = idade;
    // Fim do método
    }
    // Retorna os dados comuns do funcionário em formato de texto (as subclasses complementam)
    @Override
    public String toString() {
        return "Matrícula: " + matricula + "\nNome: " + nome + "\nIdade: " + idade;
    }
// Fim da classe Funcionario
}