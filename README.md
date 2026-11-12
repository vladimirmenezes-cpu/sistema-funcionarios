# sistema-funcionarios

-----  INSTRUÇÕES DE EXECUÇÃO E CONFIGURAÇÃO DA CONEXÃO -----

1. Inicie o MySQL no XAMPP e execute empresa_db.sql no MySQL Workbench.
2. Edite Conexao.java, se necessário, com sua senha do MySQL.
3. Abra a pasta do projeto no VS Code com extensões Java/Maven.
4. Aguarde o Maven carregar as dependências do projeto.
5. Execute Main.java para iniciar o sistema.
6. Utilize o menu para cadastrar, listar, pesquisar, alterar e excluir funcionários.
7. Selecione a opção Sair para encerrar o sistema.

----- Organização da arquitetura MVC -----

Model: Contém Funcionario.java, Gerente.java e Operador.java.
View: Contém FuncionarioView.java.
Controller: Contém FuncionarioController.java.
DAO: Contém FuncionarioDao.java.
Conexão: Contém Conexao.java.

----- Observações -----

O banco de dados precisa estar em execução para que as operações de cadastro, consulta, alteração e exclusão funcionem.
A matrícula é utilizada como chave primária para identificar cada funcionário.
Existem dois tipos de funcionários: gerente e operador, cada um com seus atributos especificos.
Em caso de erro de conexão, verifique o endereço do servidor, a porta, o nome do banco, o usuário e a senha configurados no arquivo Conexao.java.
