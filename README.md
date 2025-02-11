# PÓS TECH - FARMABUSCA
O FarmaBusca é um sistema desenvolvido para facilitar o acesso a medicamentos em postos públicos de saúde. Com ele, os usuários podem buscar por postos próximos com base em sua localização ou inserir um endereço específico para verificar a disponibilidade de um medicamento.

## 🛠 Tecnologias

- **Spring Boot**: Framework para construção do backend
- **Spring Security & JWT**: Segurança e autenticação via tokens JWT
- **PostgreSQL**: Banco de dados relacional

### 🔹 **Pré-requisitos**
Para rodar o projeto localmente, você precisa ter instalado:
- [Java 21](https://www.oracle.com/br/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)

## Features do sistema

### Criar um usuário

- 📌 Endpoint: POST /auth/register
- 📌 URL: http://localhost:8080/auth/register
- 📌 Exemplo de requisição:

```
{
    "email": "test@test.com",
    "password": "senhadificil123",
    "role": "USER",
    "name": "POS TECH",
    "street": "Rua 1",
    "number": 45,
    "complement": "Apartamento 02",
    "neighborhood": "Bairro 2",
    "city": "São Paulo",
    "state": "SP",
    "zipCode": 11222300,
    "country": "Brasil"
}

```

### Fazer login

- 📌 Endpoint: POST /auth/login
- 📌 URL: http://localhost:8080/auth/login
- 📌 Exemplo de requisição:

```
{
    "email": "test@test.com",
    "password": "senhadificil123",
    "keepLoggedIn": true
}
```

- 📌 Resposta esperada:

```
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

#### ⚠️ O token JWT deve ser incluído no cabeçalho Authorization para acessar endpoints protegidos.
Exemplo:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI...
```
