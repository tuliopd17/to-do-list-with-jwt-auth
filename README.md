# 📋 To-Do List API

API RESTful completa para gerenciamento de tarefas (To-Do List) desenvolvida em Java com Spring Boot. Este projeto foi desenvolvido como um portfólio de alta qualidade, demonstrando boas práticas de desenvolvimento, arquitetura em camadas, segurança com JWT e documentação automática.

## 🚀 Tecnologias Utilizadas

- **Java 21** (LTS) - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Spring Security 6** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **JWT (JSON Web Token)** - Autenticação stateless
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **PostgreSQL** - Banco de dados relacional (produção)
- **Jakarta Validation** - Validação de dados
- **OpenAPI/Swagger** - Documentação automática da API
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

## 📐 Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

```
┌─────────────────┐
│   Controllers   │  ← Camada de apresentação (REST endpoints)
├─────────────────┤
│    Services     │  ← Camada de lógica de negócio
├─────────────────┤
│   Repositories  │  ← Camada de acesso a dados
├─────────────────┤
│    Entities     │  ← Modelo de domínio (JPA)
└─────────────────┘
```

### Princípios SOLID Aplicados

- **Single Responsibility**: Cada classe tem uma única responsabilidade
- **Open/Closed**: Extensível sem modificar código existente
- **Liskov Substitution**: Interfaces bem definidas
- **Interface Segregation**: Interfaces específicas e coesas
- **Dependency Inversion**: Dependências injetadas via construtores

## 🔐 Segurança

- Autenticação stateless via JWT (JSON Web Token)
- Senhas criptografadas com BCrypt
- Endpoints protegidos com Spring Security 6
- Isolamento de dados: usuários só acessam suas próprias tarefas
- Validação de entrada com Jakarta Validation

## 📦 Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

- **Java 17 ou 21** (JDK)
- **Maven 3.6+**
- **PostgreSQL** (opcional, apenas se quiser usar em vez do H2)

### Verificando Instalações

```bash
# Verificar versão do Java
java -version

# Verificar versão do Maven
mvn -version
```

## 🛠️ Como Executar a Aplicação

### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd to-do-list
```

### 2. Compile o Projeto

```bash
mvn clean install
```

### 3. Execute a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

### 4. Acesse a Documentação Swagger

Após iniciar a aplicação, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs

### 5. Console H2 (Desenvolvimento)

Se estiver usando H2 Database, acesse o console em:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:todolistdb`
- **Username**: `sa`
- **Password**: (deixe em branco)

## 📚 Endpoints da API

### Autenticação (Públicos)

#### 1. Registrar Novo Usuário

```http
POST /auth/register
Content-Type: application/json

{
  "username": "usuario123",
  "email": "usuario@example.com",
  "password": "senha123"
}
```

**Resposta de Sucesso (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "message": "Usuário registrado com sucesso"
}
```

#### 2. Login

```http
POST /auth/login
Content-Type: application/json

{
  "usernameOrEmail": "usuario123",
  "password": "senha123"
}
```

**Resposta de Sucesso (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "message": "Login realizado com sucesso"
}
```

### Tarefas (Protegidos - Requerem Token JWT)

Todos os endpoints de tarefas requerem autenticação. Inclua o token no header:

```http
Authorization: Bearer <seu-token-jwt>
```

#### 3. Criar Tarefa

```http
POST /tasks
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de segurança e JWT",
  "completed": false
}
```

**Resposta de Sucesso (201 Created):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de segurança e JWT",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 4. Listar Todas as Tarefas

```http
GET /tasks
Authorization: Bearer <token>
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Estudar Spring Boot",
    "description": "Revisar conceitos de segurança e JWT",
    "completed": false,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  },
  {
    "id": 2,
    "title": "Fazer exercícios",
    "description": null,
    "completed": true,
    "createdAt": "2024-01-15T11:00:00",
    "updatedAt": "2024-01-15T12:00:00"
  }
]
```

#### 5. Buscar Tarefa por ID

```http
GET /tasks/1
Authorization: Bearer <token>
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de segurança e JWT",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 6. Atualizar Tarefa

```http
PUT /tasks/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Estudar Spring Boot - Atualizado",
  "description": "Revisar conceitos de segurança e JWT",
  "completed": true
}
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot - Atualizado",
  "description": "Revisar conceitos de segurança e JWT",
  "completed": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T14:00:00"
}
```

#### 7. Deletar Tarefa

```http
DELETE /tasks/1
Authorization: Bearer <token>
```

**Resposta de Sucesso (204 No Content):**
(Sem corpo na resposta)

## 🧪 Testando a API

### Usando cURL

#### 1. Registrar Usuário
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "senha123"
  }'
```

#### 2. Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "senha123"
  }'
```

Copie o `token` da resposta e use nos próximos comandos.

#### 3. Criar Tarefa
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "title": "Minha primeira tarefa",
    "description": "Descrição da tarefa",
    "completed": false
  }'
```

#### 4. Listar Tarefas
```bash
curl -X GET http://localhost:8080/tasks \
  -H "Authorization: Bearer <seu-token>"
```

### Usando Postman/Insomnia

1. Importe a coleção de endpoints (disponível no Swagger UI)
2. Configure a autenticação Bearer Token nas requisições protegidas
3. Use o token retornado no login/registro

## ⚙️ Configuração para PostgreSQL

Se preferir usar PostgreSQL em vez do H2:

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE todolistdb;
```

2. Edite o arquivo `src/main/resources/application.properties` ou use o profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgresql
```

Ou copie as configurações de `application-postgresql.properties` para `application.properties`.

## 🔍 Tratamento de Erros

A API retorna erros padronizados no formato JSON:

### Exemplo de Erro (400 Bad Request)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Erro de Validação",
  "message": "Dados inválidos fornecidos",
  "path": "/auth/register",
  "details": [
    "username: O nome de usuário é obrigatório",
    "email: Email deve ter um formato válido"
  ]
}
```

### Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida
- **201 Created**: Recurso criado com sucesso
- **204 No Content**: Recurso deletado com sucesso
- **400 Bad Request**: Erro de validação ou dados inválidos
- **401 Unauthorized**: Token inválido ou ausente
- **403 Forbidden**: Acesso negado (tentativa de acessar recurso de outro usuário)
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## 📁 Estrutura do Projeto

```
to-do-list/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/portfolio/todolist/
│   │   │       ├── TodoListApplication.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── TaskController.java
│   │   │       ├── dto/
│   │   │       │   ├── request/
│   │   │       │   │   ├── LoginRequest.java
│   │   │       │   │   ├── RegisterRequest.java
│   │   │       │   │   └── TaskRequest.java
│   │   │       │   └── response/
│   │   │       │       ├── AuthResponse.java
│   │   │       │       ├── ErrorResponse.java
│   │   │       │       └── TaskResponse.java
│   │   │       ├── entity/
│   │   │       │   ├── Task.java
│   │   │       │   └── User.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── repository/
│   │   │       │   ├── TaskRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── security/
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   └── SecurityConfig.java
│   │   │       └── service/
│   │   │           ├── JwtService.java
│   │   │           ├── TaskService.java
│   │   │           └── UserService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-postgresql.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🎯 Funcionalidades Implementadas

✅ Registro e autenticação de usuários  
✅ Geração e validação de tokens JWT  
✅ CRUD completo de tarefas  
✅ Isolamento de dados por usuário  
✅ Validação de entrada com Jakarta Validation  
✅ Tratamento global de exceções  
✅ Documentação automática com Swagger/OpenAPI  
✅ Arquitetura em camadas  
✅ Padrão DTO (não expõe entidades JPA)  
✅ Segurança com Spring Security 6  

## 📝 Notas Importantes

- O token JWT expira após 24 horas (86400000 ms) por padrão. Isso pode ser configurado em `application.properties`.
- A chave secreta do JWT está configurada em `application.properties`. **Em produção, use uma chave segura e armazene-a de forma segura (variáveis de ambiente, secrets manager, etc.)**.
- O H2 Database é em memória, então os dados são perdidos ao reiniciar a aplicação. Use PostgreSQL para persistência real.

## 🤝 Contribuindo

Este é um projeto de portfólio, mas sugestões e melhorias são bem-vindas!

## 📄 Licença

Este projeto é de código aberto e está disponível para uso educacional e de portfólio.

## 👨‍💻 Autor

Desenvolvido como projeto de portfólio demonstrando habilidades em Java, Spring Boot e desenvolvimento de APIs RESTful.

---

**Desenvolvido com ❤️ usando Spring Boot**
