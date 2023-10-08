# Projeto ADA_BTG: Criar uma APIRest utilizando Spring Boot

## Requisitos do projeto:

- Um endpoint de cadastro, leitura, atualização e deleção (lógica ou física) de usuário que será usado no login da aplicação.
- Endpoints de buscas que recebem filtros opcionais e realizam consultas na camada de dados (de acordo com o tema do projeto).

## Requisitos Não Funcionais:

- Deve ser uma aplicação Spring Boot.
- Utilização do banco de dados H2.
- Linguagem Java 11 - 17.
- Deve ser inicializado a base de dados para utilização dos endpoints.
- Deve conter autenticação básica utilizando Spring Security.

## Projeto Agencia de Viagens

É uma APIRest com as funcionalidades Basicas de CRUD (CREATE, READ, UPDATE AND DELETE) com seus devidos endpoints. Além disso, contém filtros opcionais de busca baseado em querys e queryDSL.

### Funcionalidades

- Cadastro de login com autenticação por token.
- Cadastro, leitura, atualização e exclusão de Cliente.
- Cadastro, leitura, atualização e exclusão de Pacote de Viagem.
- Cadastro, leitura, atualização e exclusão de Passagem.
- Filtros opcionais de busca.
- Validação de Email, Nome, CPF e Numero do Passaporte.

### Cadastro

Abaixo seguem exemplos que podem ser inseridos em cada endpoint para testar a aplicação. Os CPFs foram gerados pelo site: [4devs.com.br](https://www.4devs.com.br/gerador_de_cpf).

OBS: O atributo ticketType é um Enum e pode receber os valores: ECONOMICA, EXECUTIVA E PRIMEIRA_CLASSE.

#### Testando o Customer:

```json
{
	"name":"Julia",
	"cpf": "18080050074",
	"email": "julia@teste.com",
	"passportNumber": "CS152369",
	"password": "Teste#123"
}

{
	"name":"Amalia",
	"cpf": "32643701070",
	"email": "amalia@teste.com",
	"passportNumber": "MN123658",
	"password": "Teste#456"
}
```
#### Testando o Travel Package:  
```json
{
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```  
#### Testando o Ticket:  
```json
{
	"customer_Id":1,
	"bookedDate":"2023-10-02T14:30:00.000",
	"travelDate":"2024-01-25T07:30:00.000",
	"ticketType":"PRIMEIRA_CLASSE",
	"flightNumber":525652521555522,
	"travelPackage_Id":1
}
```

 
