# 📋 API de Tarefas

Este é um projeto pessoal desenvolvido com o objetivo de praticar e consolidar conhecimentos em Java, Spring Boot, autenticação com JWT e testes automatizados. A aplicação consiste em uma API REST para gerenciamento de tarefas com autenticação de usuários.

> ⚠️ Este projeto tem fins educacionais e está em constante evolução.

---

## 🚀 Tecnologias e Conceitos Utilizados

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Security** (com autenticação via JWT)
- **JPA / Hibernate**
- **Banco de dados H2 (em memória)**
- **JUnit 5 + Mockito**
- **Testes de integração com MockMvc**
- **Lombok**
- **Maven**

---

## 🔐 Funcionalidades

- Registro de usuários
- Autenticação com geração de token JWT
- CRUD completo de tarefas
- Rotas protegidas com autenticação JWT
- Validações básicas nas requisições

---

## 🧪 Testes Automatizados

O projeto inclui testes unitários e de integração utilizando:

- `JUnit 5`
- `Mockito`
- `MockMvc`

---

## 📂 Endpoints Principais

| Método | Rota           | Descrição                     |
|--------|----------------|-------------------------------|
| POST   | `/auth/registro` | Registra um novo usuário      |
| POST   | `/auth/login`    | Realiza login e retorna JWT   |
| GET    | `/tarefas`       | Lista todas as tarefas        |
| POST   | `/tarefas`       | Cria uma nova tarefa          |
| PUT    | `/tarefas/{id}`  | Atualiza uma tarefa existente |
| DELETE | `/tarefas/{id}`  | Remove uma tarefa             |

---

## 💡 Aprendizados

- Configuração de autenticação com JWT no Spring Security
- Estruturação de uma API REST com boas práticas
- Criação de filtros para proteção de rotas
- Escrita de testes automatizados com MockMvc e Mockito

---

## 🔧 Melhorias Futuras

- Integração com banco de dados real (PostgreSQL ou MySQL)
- Deploy em ambientes de nuvem (Render, Railway, etc.)
- Validações mais robustas e tratamento de exceções

---

## 👩‍💻 Sobre Mim

Sou uma desenvolvedora em formação, focada no backend com Java e Spring Boot. Gosto de aprender construindo projetos práticos e esta API representa uma parte importante da minha jornada.

Fique à vontade para deixar sugestões, abrir issues ou trocar ideias. Feedbacks são sempre bem-vindos! 😊

---
