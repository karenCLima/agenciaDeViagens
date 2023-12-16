# Projeto ADA_BTG: Criar uma APIRest utilizando Spring Boot
A Agencia de Viagens é um projeto desenvolvido como parte do curso Back-end Java da Ada desenvolvido em dois módulos diferentes do curso.
## :paperclips: Requisitos do Projeto

- Um endpoint de cadastro, leitura, atualização e deleção (lógica ou física) de usuário que será usado no login da aplicação.
- Endpoints de buscas que recebem filtros opcionais e realizam consultas na camada de dados (de acordo com o tema do projeto).
- Testes Unitários
- Testes de Integração na Controller e no Repository
- Teste end2end com 2 cenários (https://github.com/karenCLima/agenciaDeViagens-test)

## :paperclips: Requisitos Não Funcionais:

- Deve ser uma aplicação Spring Boot.
- Utilização do banco de dados H2.
- Linguagem Java 11 - 17.
- Deve ser inicializado a base de dados para utilização dos endpoints.
- Deve conter autenticação básica utilizando Spring Security.
- Deve usar JACOCO para medir cobertura dos métodos dos testes (min: 40%)

## :pushpin: Projeto Agencia de Viagens

É uma APIRest com as funcionalidades Basicas de CRUD (CREATE, READ, UPDATE AND DELETE) com seus devidos endpoints. Além disso, contém filtros opcionais de busca baseado em querys e queryDSL.

### :pushpin: Funcionalidades

- Cadastro de login com autenticação por token.
- Cadastro, leitura, atualização e exclusão de Cliente.
- Cadastro, leitura, atualização e exclusão de Pacote de Viagem.
- Cadastro, leitura, atualização e exclusão de Passagem.
- Filtros opcionais de busca.
- Validação de Email, Nome, CPF e Numero do Passaporte.

## :man_technologist: Conhecimentos aplicados
- **Linguagem de Programação:** Java
- **Framework:** Spring Boot
- **Banco de Dados:** H2
- **Módulos utilizadas:** JUnit, Jacoco, spring security
- **Arquitetura:** O projeto segue os princípios de SOLID e adota o padrão de arquitetura MVC (Model-View-Controller).
- **Metodologia de Desenvolvimento:** Scrum

## ⚙️ Como Executar
1. Clone o repositório em uma pasta de preferência
  ```
  https://github.com/karenCLima/agenciaDeViagens
  ```
## 📚 Documentação (endpoints)
### Observações

Abaixo seguem exemplos que podem ser inseridos em cada endpoint para testar a aplicação. Os CPFs foram gerados pelo site: [4devs.com.br](https://www.4devs.com.br/gerador_de_cpf).

OBS: O atributo ticketType é um Enum e pode receber os valores: ECONOMICA, EXECUTIVA E PRIMEIRA_CLASSE.
## Testando o Login:  
<summary> Cadastro (POST) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `POST` | Realiza o login do cliente no sistema | `http://localhost:8080/customer`    
  
  **Request Body**
```json
{
	"email": "julia@teste.com",
	"password": "Teste#123"
}

```
**Response**    
```json
{
	"token": string
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST OR 403 FORBIDEN  

## Testando o Customer:
<summary> Cadastro (POST) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `POST` | Realiza o cadastro do cliente no sistema | `http://localhost:8080/customer`    
  
**Request Body**
```json
{
	"name":"Julia",
	"cpf": "18080050074",
	"email": "julia@teste.com",
	"passportNumber": "CS152369",
	"password": "Teste#123"
}
```
**Response**    
```json
{
	"id":1,
	"name":"Julia",
	"cpf": "18080050074",
	"email": "julia@teste.com"
}
```
**HTTP status:**: 201 CREATED or 400 BAD REQUEST  
<summary> Busca (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca de clientes no sistema | `http://localhost:8080/customer`   
  
**Response**    
```json
[
	{
		"id":1,
		"name":"Julia",
		"cpf": "18080050074",
		"email": "julia@teste.com"
	}
]
```
**HTTP status:**: 200 OK or 400 BAD REQUEST OR 403 FORBIDEN 

<summary> Busca pelo ID (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca de clientes no sistema pelo Id | `http://localhost:8080/customer/{id}`   
  
**Response**    
```json
{
	"id":1,
	"name":"Julia",
	"cpf": "18080050074",
	"email": "julia@teste.com"
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST OR 403 FORBIDEN  

<summary> Busca pelo CPF (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca de clientes no sistema pelo CPF | `http://localhost:8080/customer/cpf/{cpf}`   
  
**Response**    
```json
{
	"id":1,
	"name":"Julia",
	"cpf": "18080050074",
	"email": "julia@teste.com"
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST OR 403 FORBIDEN  

<summary> Atualização (PUT) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `PUT` | Realiza a atualização de clientes no sistema  | `http://localhost:8080/customer/{id}`    

**Request Body**
```json
{
	"name":"Amalia",
	"cpf": "32643701070",
	"email": "amalia@teste.com",
	"passportNumber": "MN123658",
	"password": "Teste#456"
}
```
**Response**    
```json
{
	"id":1,
	"name":"Amalia",
	"cpf": "32643701070",
	"email": "amalia@teste.com"
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST OR 403 FORBIDEN    

<summary> Deletar (Delete) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `DELETE` | Realiza deleção de clientes no sistema | `http://localhost:8080/customer/{id}`   
  
**Response**  
**HTTP status:** 200 OK or 400 BAD REQUEST OR 403 FORBIDEN
  
## Testando o Travel Package:  
<summary> Cadastro (POST) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `POST` | Realiza o cadastro do pacote de viagem no sistema | `http://localhost:8080/travel-package`  

 **Request Body**
```json
{
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```
**Response**    
```json
{
	"id":1,
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```
**HTTP status:** 201 CREATED or 400 BAD REQUEST or 403 FORBIDEN  

<summary> Busca (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca pelos pacotes de viagem no sistema | `http://localhost:8080/travel-package`  

 **Response**    
```json
[
	{
		"id":1,
		"country":"Brasil",
		"place":"Rio de Janeiro",
		"travelDurationInDays": 4,
		"price":2200.00
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN     

<summary> Busca pelo Id (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca pelos pacotes de viagem no sistema pelo Id | `http://localhost:8080/travel-package/{id}`  

 **Response**    
```json
{
	"id":1,
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN    

<summary> Busca pelo country (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca pelos pacotes de viagem no sistema pelo País | `http://localhost:8080/travel-package/country?country="nomeDoPais"`  

 **Response**    
```json
[
	{
		"id":1,
		"country":"Brasil",
		"place":"Rio de Janeiro",
		"travelDurationInDays": 4,
		"price":2200.00
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Busca pelo preço (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca pelos pacotes de viagem no sistema pelo preço | `http://localhost:8080/travel-package/price?minValue=valorMinimo&maxValue=ValorMaximo`  

 **Response**    
```json
[
	{
		"id":1,
		"country":"Brasil",
		"place":"Rio de Janeiro",
		"travelDurationInDays": 4,
		"price":2200.00
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN  

<summary> Atualização (PUT) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `PUT` | Realiza a atualização do pacote de viagem no sistema | `http://localhost:8080/travel-package/{id}`  

 **Request Body**
```json
{
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```
**Response**    
```json
{
	"id":1,
	"country":"Brasil",
	"place":"Rio de Janeiro",
	"travelDurationInDays": 4,
	"price":2200.00
}
```
**HTTP status:** 201 CREATED or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Deletar (DELETE) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `DELETE` | Realiza a deleção do pacote de viagem no sistema  | `http://localhost:8080/travel-package/{id}`  

**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   


## Testando o Ticket:    
<summary> Cadastro (POST) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `POST` | Realiza o cadastro da passagem no sistema | `http://localhost:8080/ticket`  

 **Request Body**
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
**Response**  
```json
{
	"id":1
	"customer_Id":1,
	"bookedDate":"2023-10-02T14:30:00.000",
	"travelDate":"2024-01-25T07:30:00.000",
	"ticketType":"PRIMEIRA_CLASSE",
	"flightNumber":525652521555522,
	"travelPackage_Id":1
}
```
**HTTP status:** 201 CREATED or 400 BAD REQUEST or 403 FORBIDEN      

<summary> Busca (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca da passagem no sistema | `http://localhost:8080/ticket`  

**Response**  
```json
[
	{
		"id":1
		"customer_Id":1,
		"bookedDate":"2023-10-02T14:30:00.000",
		"travelDate":"2024-01-25T07:30:00.000",
		"ticketType":"PRIMEIRA_CLASSE",
		"flightNumber":525652521555522,
		"travelPackage_Id":1
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Busca pelo Id (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca da passagem no sistema pelo Id | `http://localhost:8080/ticket/{id}`  

**Response**  
```json
{
	"id":1
	"customer_Id":1,
	"bookedDate":"2023-10-02T14:30:00.000",
	"travelDate":"2024-01-25T07:30:00.000",
	"ticketType":"PRIMEIRA_CLASSE",
	"flightNumber":525652521555522,
	"travelPackage_Id":1
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Busca pelo Id do cliente (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca da passagem no sistema pelo Id do cliente | `http://localhost:8080/ticket/{customer_id}`  

**Response**  
```json
[
	{
		"id":1
		"customer_Id":1,
		"bookedDate":"2023-10-02T14:30:00.000",
		"travelDate":"2024-01-25T07:30:00.000",
		"ticketType":"PRIMEIRA_CLASSE",
		"flightNumber":525652521555522,
		"travelPackage_Id":1
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Busca pelo tipo de passagem (GET) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `GET` | Realiza a busca da passagem no sistema pelo tipo de passagem | `http://localhost:8080/ticket/ticket_type?ticketType=ECONOMICA`  

**Response**  
```json
[
	{
		"id":1
		"customer_Id":1,
		"bookedDate":"2023-10-02T14:30:00.000",
		"travelDate":"2024-01-25T07:30:00.000",
		"ticketType":"PRIMEIRA_CLASSE",
		"flightNumber":525652521555522,
		"travelPackage_Id":1
	}
]
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN   

<summary> Atualização (PUT) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `PUT` | Realiza a atualização da passagem no sistema | `http://localhost:8080/ticket/{id}`  

 **Request Body**
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
**Response**  
```json
{
	"id":1
	"customer_Id":1,
	"bookedDate":"2023-10-02T14:30:00.000",
	"travelDate":"2024-01-25T07:30:00.000",
	"ticketType":"PRIMEIRA_CLASSE",
	"flightNumber":525652521555522,
	"travelPackage_Id":1
}
```
**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN  

<summary> Deletar (DELETE) </summary>
    <br>

  | Método | Funcionalidade | URL |
  |---|---|---|
  | `DELETE` | Realiza a deleção da passagem no sistema  | `http://localhost:8080/ticket/{id}`  

**HTTP status:** 200 OK or 400 BAD REQUEST or 403 FORBIDEN 
