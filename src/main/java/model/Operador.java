package model;
public class Operador extends Funcionario {
    private String funcao;

    public Operador() {super();}

    public Operador(int matricula, String nome, int idade, String funcao) {
        super(matricula, nome, idade);
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    @Override
    public String toString() {
        return super.toString() + "\nTipo: Operador\nFunção: " + funcao;
    }
}