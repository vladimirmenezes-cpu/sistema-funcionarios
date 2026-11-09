// Declaração do pacote
package model;

// Declaração da classe Operador que herda (extends) da classe Funcionario
public class Operador extends Funcionario {
    
    // Atributo privado e específico do Operador para armazenar a função (texto)
    private String funcao;

    // Construtor vazio padrão
    public Operador() {
        // Chama o construtor vazio da classe mãe (Funcionario)
        super();
    // Fim do construtor vazio
    }

    // Construtor com parâmetros, incluindo os atributos da classe mãe e o específico do operador
    public Operador(int matricula, String nome, int idade, String funcao) {
        // Chama o construtor da classe mãe (Funcionario) passando matrícula, nome e idade
        super(matricula, nome, idade);
        // Atribui o valor do parâmetro 'funcao' ao atributo específico da classe
        this.funcao = funcao;
    // Fim do construtor
    }

    // Método Getter para obter a função do operador
    public String getFuncao() {
        // Retorna o valor atual da função
        return funcao;
    // Fim do método
    }

    // Método Setter para definir uma nova função para o operador
    public void setFuncao(String funcao) {
        // Atualiza o atributo funcao com o novo valor passado
        this.funcao = funcao;
    // Fim do método
    }
    // Retorna os dados do operador (dados comuns + tipo e função)
    @Override
    public String toString() {
        return super.toString() + "\nTipo: Operador\nFunção: " + funcao;
    }
// Fim da classe Operador
}