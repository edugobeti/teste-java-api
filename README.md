# teste-java-api

Teste de conhencimentos Java e SpringBoot

API desenvolvida para cadastro, atualização e consulta de pessoas e seus endereços.

Consiste em 7 endpoints acessados por Métodos HTTP e Json sendo: 
Post: "/pessoas"
Put: "/pessoas/{pessoaId}"
Get: "/pessoas/{pessoaId}"
Get: "/pessoas"
Post: "/{pessoaId}/enderecos"
Get: "/{pessoaId}/enderecos"
Get: "/{pessoaId}/principal"

Utilizando linguagem Java, SpringBoot, Banco de Dados H2 e teste unitários com Junit5.

Para inserção de pessoas e endereços automáticamente ao iniciar a Aplicação descomentar as
linhas 3,4,16,17,21 da classe ApiGerenciamentoPessoaDesafioJavaApplication para executar a classe DbService.
