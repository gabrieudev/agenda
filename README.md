<h1 align="center" style="font-weight: bold;">Agenda Digital 
📅</h1>

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT">
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres">
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</p>

<p align="center">
 <a href="#documentacao">Acessar documentação</a> •
 <a href="#executar">Executar projeto</a> •
 <a href="#como-usar">Como usar</a> • 
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
  <b>O projeto é uma agenda digital para compromissos e atividades, envio de notificações programadas e relatórios de desempenho. Além disso, a API foi desenvolvida seguindo os princípios da <a href=https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c>Arquitetura Limpa</a> e com as melhores e mais atualizadas práticas do mercado para assegurar a integridade dos dados sensíveis.</b>
</p>

<h2 id="documentacao">📄 Acessar documentação</h2>

O projeto está hospedado em um serviço de nuvem. Portanto, você poderá acessar sua [documentação](https://agenda-9cx0.onrender.com/api/v1/swagger-ui/index.html#/), desenvolvida através da Swagger UI, sem precisar executá-lo localmente.

<h2 id="executar">⚙️ Executar projeto</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- Conta Google

<h3>Clonando</h3>

```bash
git clone https://github.com/gabrieudev/agenda.git
```

<h3>Variáveis de Ambiente</h3>

Para executar a aplicação, você precisará criar um arquivo `.env` contendo as seguintes variáveis de ambiente relacionadas à sua conta Google, que será utilizada para enviar as notificações:

```bash
EMAIL_USERNAME=<EMAIL>
EMAIL_PASSWORD=<SENHA_DE_APP>
```

> Caso não saiba como obter as senhas de app, a [Central de Ajuda](https://support.google.com/accounts/answer/185833?hl=pt-BR) Google pode te ajudar.

<h3>Inicializando</h3>

Execute os seguintes comandos:

```bash
cd agenda
docker compose up -d
```

<h2 id="como-usar">🔁 Como usar</h2>

1. Crie um usuário em `POST /users/signup`

2. Faça login em `POST /auth/signin` e copie o valor de `accessToken`

3. Vá até `Authorize` no campo superior da interface Swagger e insira o valor copiado. Desta forma, todas as requisições terão o token de acesso automaticamente embutido

Agora você poderá administrar seus compromissos, tarefas para estes compromissos e notificações agendadas.

<h2 id="contribuir">📫 Contribuir</h2>

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

1. `git clone https://github.com/gabrieudev/agenda.git`
2. `git checkout -b feature/NOME`
3. Siga os padrões de commits.
4. Abra um Pull Request explicando o problema resolvido ou a funcionalidade desenvolvida. Se houver, anexe screenshots das modificações visuais e aguarde a revisão!

<h3>Documentações que podem ajudar</h3>

[📝 Como criar um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrão de commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
