# 🍔 FoodHub API – Clean Architecture

API REST desenvolvida para gerenciamento de usuários, restaurantes, menus e itens de menu, seguindo os princípios da Clean Architecture, com foco em organização, desacoplamento e testabilidade.

---

## 🧱 Arquitetura

O projeto adota o modelo de Clean Architecture, separando claramente as responsabilidades em camadas:

- Domain: entidades e regras de negócio
- Application: casos de uso (Use Cases) e DTOs
- Infra: persistência, gateways e configurações
- Web: controllers, payloads e adaptação HTTP

Essa abordagem facilita manutenção, testes e evolução do sistema.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- MongoDB
- Docker
- Docker Compose
- Maven
- Swagger (OpenAPI)
- Postman
- JUnit 5
- JaCoCo

---

## 📦 Pré-requisitos

Para executar o projeto localmente, é necessário ter instalado:

- Git
- Docker
- Docker Compose

---

## 📥 Clonando o Repositório

    git clone https://github.com/joyce-santos23/foodhub-clean-arch

---

## ⚙️ Configuração de Ambiente

A aplicação utiliza variáveis de ambiente para configuração do banco de dados e da conexão com o MongoDB.

### Arquivo .env

Crie um arquivo .env na raiz do projeto com o seguinte conteúdo:

    MONGO_USER=username
    MONGO_PASSWORD=password
    MONGO_DB=nome_do_banco
    SPRING_DATA_MONGODB_URI=mongodb://username:password@mongodb:27017/nome_do_banco?authSource=admin

Observação: os valores definidos após o sinal = devem ser ajustados conforme a configuração desejada para o ambiente de execução.

---

## 🐳 Executando a Aplicação com Docker

Com o Docker em execução, utilize o comando:

    docker compose up -d --build

A API ficará disponível em:

    http://localhost:8080

---

## 🧪 Testes Automatizados

Os testes automatizados podem ser executados com o Maven:

    mvn clean test jacoco:report

O relatório de cobertura de código gerado pelo JaCoCo estará disponível em:

    target/site/jacoco/index.html

---

## 📘 Documentação da API (Swagger)

A API disponibiliza documentação interativa por meio do Swagger, permitindo visualizar e executar os endpoints diretamente pelo navegador:

    http://localhost:8080/swagger-ui/index.html

---

## 📮 Coleção Postman

Os endpoints também estão organizados em uma coleção pública no Postman:

    https://www.postman.com/joyce2396/workspace/foodhub-clean-architecture/collection/43260270-8cd6de64-d892-463b-a230-6f18bcc2eeb2?action=share&source=copy-link&creator=43260270

Para executar as requisições pelo Postman é necessário possuir uma conta gratuita na plataforma.

---

## 🔗 Endpoints da API

**Base URL:** `/api/v1`

### 👤 Usuários

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | `/users` | Cria um novo usuário |
| GET | `/users` | Lista usuários de forma paginada |
| PATCH | `/users/{userId}` | Atualiza dados do usuário |

---

### 🏠 Endereços do Usuário

| Método | Endpoint | Descrição |
|------|---------|-----------|
| GET | `/users/{userId}/addresses` | Lista todos os endereços do usuário |
| POST | `/users/{userId}/addresses` | Cria um novo endereço |
| PUT | `/users/{userId}/addresses/{userAddressId}` | Atualiza um endereço do usuário |
| DELETE | `/users/{userId}/addresses/{userAddressId}` | Remove um endereço do usuário |

---

### 🍽️ Restaurantes do Usuário

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | `/users/{userId}/restaurants` | Vincula um usuário a um restaurante |
| DELETE | `/users/{userId}/restaurants/{restaurantId}` | Remove o vínculo entre usuário e restaurante |

---

### 🏪 Restaurantes

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | `/restaurants` | Cria um restaurante |
| PUT | `/restaurants/{restaurantId}` | Atualiza um restaurante |
| DELETE | `/restaurants/{restaurantId}` | Remove um restaurante |
| GET | `/restaurants/{restaurantId}` | Busca restaurante por ID |
| GET | `/restaurants` | Lista restaurantes de forma paginada |

---

### ⏰ Horários de Funcionamento

| Método | Endpoint | Descrição |
|------|---------|-----------|
| GET | `/restaurants/{restaurantId}/opening-hours` | Lista horários de funcionamento |
| PUT | `/restaurants/{restaurantId}/opening-hours/{dayOfWeek}` | Atualiza horário de funcionamento |

---

### 📋 Menus

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | `/restaurants/{restaurantId}/menus` | Cria um menu |
| GET | `/restaurants/{restaurantId}/menus` | Lista menus do restaurante |
| DELETE | `/restaurants/{restaurantId}/menus/{menuId}` | Remove um menu |

---

### 🍽️ Itens de Menu

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | `/restaurants/{restaurantId}/menus/{menuId}/items` | Cria um item de menu |
| PUT | `/restaurants/{restaurantId}/menus/{menuId}/items/{menuItemId}` | Atualiza um item de menu |
| DELETE | `/restaurants/{restaurantId}/menus/{menuId}/items/{menuItemId}` | Remove um item de menu |
| GET | `/restaurants/{restaurantId}/menus/{menuId}/items/{menuItemId}` | Busca um item de menu por ID |

---


## 🔐 Headers Importantes

Algumas operações exigem o envio do header:

    X-User-Id

---

## 👩‍💻 Autoria

Projeto desenvolvido para fins acadêmicos, como parte da avaliação da Fase 2 da Pós-graduação em Arquitetura de Software Java, com foco na aplicação de boas práticas de arquitetura, desenvolvimento de APIs REST e organização de código.
