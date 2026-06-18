# ArenaRank

Sistema web e API REST desenvolvidos em Java com Spring Boot para gerenciamento de jogadores, times e torneios competitivos.

O projeto foi criado como uma aplicação de portfólio, com foco em arquitetura em camadas, regras de negócio, integração com banco de dados, interface web e endpoints REST.

> Status: Em desenvolvimento

---

## Preview

O ArenaRank possui uma interface web para gerenciamento dos dados e também uma API REST para integração com outros sistemas.

### Módulos atuais

* Dashboard
* Jogadores
* Times
* Torneios

---

## Tecnologias utilizadas

* Java 25
* Spring Boot
* Spring Web
* Spring Data JPA
* Thymeleaf
* H2 Database
* Bean Validation
* Lombok
* Maven
* HTML5
* CSS3
* Git
* GitHub

---

## Funcionalidades

### Jogadores

* Cadastrar jogador
* Listar jogadores
* Buscar jogador por ID
* Editar jogador
* Excluir jogador
* Validar nickname, email e função
* Impedir cadastro de email duplicado

### Times

* Cadastrar time
* Listar times
* Buscar time por ID
* Editar time
* Excluir time
* Adicionar jogadores ao time
* Remover jogadores do time
* Limitar o time a no máximo 4 jogadores
* Impedir jogador duplicado no mesmo time
* Impedir tag duplicada

### Torneios

* Cadastrar torneio
* Listar torneios
* Buscar torneio por ID
* Editar torneio
* Excluir torneio
* Iniciar torneio
* Finalizar torneio
* Controlar status do torneio
* Impedir exclusão de torneios em andamento

---

## Regras de negócio

* Um jogador deve ter nickname, email e função.
* O email do jogador deve ser único.
* Um time deve ter nome e tag.
* A tag do time deve ser única.
* Um time pode ter no máximo 4 jogadores.
* Um jogador não pode ser adicionado duas vezes ao mesmo time.
* Um torneio sempre inicia com status `OPEN`.
* Apenas torneios com status `OPEN` podem ser iniciados.
* Apenas torneios com status `IN_PROGRESS` podem ser finalizados.
* Torneios em andamento não podem ser excluídos.
* A data final do torneio não pode ser anterior à data inicial.

---

## Status dos torneios

| Status        | Descrição                              |
| ------------- | -------------------------------------- |
| `OPEN`        | Torneio criado, mas ainda não iniciado |
| `IN_PROGRESS` | Torneio em andamento                   |
| `FINISHED`    | Torneio finalizado                     |

---

## Funções dos jogadores

| Função    | Descrição                                  |
| --------- | ------------------------------------------ |
| `RUSHER`  | Jogador agressivo, responsável por avançar |
| `SUPPORT` | Jogador de suporte                         |
| `SNIPER`  | Jogador de longa distância                 |
| `CAPTAIN` | Líder da equipe                            |
| `FLEX`    | Jogador flexível                           |

---

## Estrutura do projeto

```txt
src/main/java/com/envwrd/arenarank/
│
├── controller/
│   ├── api/
│   │   ├── PlayerApiController.java
│   │   ├── TeamApiController.java
│   │   └── TournamentApiController.java
│   │
│   └── web/
│       ├── DashboardWebController.java
│       ├── PlayerWebController.java
│       ├── TeamWebController.java
│       └── TournamentWebController.java
│
├── domain/
│   ├── Player.java
│   ├── PlayerRole.java
│   ├── Team.java
│   ├── Tournament.java
│   └── TournamentStatus.java
│
├── dto/
│   ├── request/
│   │   ├── PlayerRequest.java
│   │   ├── TeamRequest.java
│   │   └── TournamentRequest.java
│   │
│   └── response/
│       ├── PlayerResponse.java
│       ├── TeamResponse.java
│       └── TournamentResponse.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── repository/
│   ├── PlayerRepository.java
│   ├── TeamRepository.java
│   └── TournamentRepository.java
│
└── service/
    ├── PlayerService.java
    ├── TeamService.java
    └── TournamentService.java
```

---

## Como rodar o projeto

### Pré-requisitos

Antes de rodar o projeto, tenha instalado:

* Java 25
* Maven
* Git
* IntelliJ IDEA ou outra IDE Java

---

### Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/arenarank.git
```

Entre na pasta do projeto:

```bash
cd arenarank
```

Execute o projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em:

```txt
http://localhost:8080
```

---

## Interface web

### Dashboard

```txt
http://localhost:8080
```

### Jogadores

```txt
http://localhost:8080/players
```

### Times

```txt
http://localhost:8080/teams
```

### Torneios

```txt
http://localhost:8080/tournaments
```

---

## Banco de dados H2

O projeto usa H2 Database em memória para facilitar os testes durante o desenvolvimento.

Acesse:

```txt
http://localhost:8080/h2-console
```

Configuração:

```txt
JDBC URL: jdbc:h2:mem:arenarank
User Name: sa
Password:
```

---

## Rotas da API

### Jogadores

| Método   | Rota                | Descrição                |
| -------- | ------------------- | ------------------------ |
| `GET`    | `/api/players`      | Lista todos os jogadores |
| `GET`    | `/api/players/{id}` | Busca um jogador pelo ID |
| `POST`   | `/api/players`      | Cadastra um novo jogador |
| `PUT`    | `/api/players/{id}` | Atualiza um jogador      |
| `DELETE` | `/api/players/{id}` | Remove um jogador        |

Exemplo de criação de jogador:

```json
{
  "nickname": "FabricioZX",
  "email": "fabricio@email.com",
  "role": "RUSHER"
}
```

---

### Times

| Método   | Rota                                     | Descrição                   |
| -------- | ---------------------------------------- | --------------------------- |
| `GET`    | `/api/teams`                             | Lista todos os times        |
| `GET`    | `/api/teams/{id}`                        | Busca um time pelo ID       |
| `POST`   | `/api/teams`                             | Cadastra um novo time       |
| `PUT`    | `/api/teams/{id}`                        | Atualiza um time            |
| `DELETE` | `/api/teams/{id}`                        | Remove um time              |
| `POST`   | `/api/teams/{teamId}/players/{playerId}` | Adiciona um jogador ao time |
| `DELETE` | `/api/teams/{teamId}/players/{playerId}` | Remove um jogador do time   |

Exemplo de criação de time:

```json
{
  "name": "Shadow Squad",
  "tag": "SHDW"
}
```

---

### Torneios

| Método   | Rota                           | Descrição                |
| -------- | ------------------------------ | ------------------------ |
| `GET`    | `/api/tournaments`             | Lista todos os torneios  |
| `GET`    | `/api/tournaments/{id}`        | Busca um torneio pelo ID |
| `POST`   | `/api/tournaments`             | Cadastra um novo torneio |
| `PUT`    | `/api/tournaments/{id}`        | Atualiza um torneio      |
| `DELETE` | `/api/tournaments/{id}`        | Remove um torneio        |
| `PATCH`  | `/api/tournaments/{id}/start`  | Inicia um torneio        |
| `PATCH`  | `/api/tournaments/{id}/finish` | Finaliza um torneio      |

Exemplo de criação de torneio:

```json
{
  "name": "Copa EnvWrd",
  "startDate": "2026-06-20",
  "endDate": "2026-06-25"
}
```

---

## Tratamento de erros

A API possui tratamento básico para erros como:

* Recurso não encontrado
* Dados inválidos
* Regras de negócio violadas
* Campos obrigatórios ausentes

Exemplo de resposta de erro:

```json
{
  "timestamp": "2026-06-17T23:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Não é possível excluir um torneio em andamento"
}
```

---

## Próximas funcionalidades

* Módulo de partidas
* Registro de resultados por partida
* Cálculo automático de pontuação
* Ranking por torneio
* Upload de foto dos jogadores
* Documentação com Swagger/OpenAPI
* Migração para PostgreSQL
* Testes automatizados com JUnit e Mockito
* Autenticação com Spring Security
* Deploy da aplicação

---

## Objetivo do projeto

O ArenaRank foi desenvolvido para demonstrar conhecimentos em:

* Desenvolvimento backend com Java
* Criação de APIs REST
* Arquitetura em camadas
* Integração com banco de dados
* Validação de dados
* Regras de negócio
* Tratamento de exceções
* Interface web com Thymeleaf
* Organização de projeto Spring Boot
* Versionamento com Git e GitHub

---
## Autor

Desenvolvido por Fabricio Samuel.

GitHub: [@samuell7x](https://github.com/samuell7x)

---
## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
