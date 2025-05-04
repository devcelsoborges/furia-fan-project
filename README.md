# FURIA FanChat

Uma aplicação web para fãs da FURIA se conectarem, acompanharem resultados de partidas e participarem de um fórum da comunidade.

## Sobre o Projeto

O FURIA FanChat é um projeto desenvolvido em Angular com o objetivo de criar um espaço digital para a comunidade de fãs da organização brasileira de esports FURIA. A aplicação oferece funcionalidades essenciais para manter os fãs engajados e informados, incluindo a exibição dos últimos resultados das partidas, uma interface de chat interativa (conectada a uma IA) e um fórum completo para discussões sobre diversos tópicos relacionados à FURIA.

## Funcionalidades

* **Acompanhamento de Partidas:** Veja os resultados recentes das partidas da FURIA, com opção de "Mostrar Mais" para carregar resultados adicionais.
* **Chat Interativo:** Uma sala de chat onde os fãs podem interagir (atualmente conectada a uma IA para simular conversas).
* **Fórum da Comunidade:**
    * Listagem de tópicos criados pelos fãs.
    * Visualização detalhada de cada tópico, incluindo o post inicial e as respostas.
    * Criação de novos tópicos e posts (funcionalidade de formulário implementada, dependendo da integração com backend).
* **Navegação Intuitiva:** Navegue facilmente entre as seções de partidas, chat, fórum e sobre nós.
* **Estilo Temático:** Design visual inspirado nas cores e identidade da FURIA.
* **Estrutura Modular:** Projeto organizado em componentes Angular para manutenibilidade.

## Tecnologias Utilizadas

* **Angular:** Framework principal para a construção da Single Page Application (SPA).
* **TypeScript:** Linguagem de programação utilizada, que adiciona tipagem estática ao JavaScript.
* **SCSS (Sass):** Pré-processador CSS para estilos mais organizados e poderosos.
* **HTML5:** Estrutura do conteúdo.
* **Angular Router:** Para gerenciar a navegação entre as diferentes visualizações da aplicação.
* **HttpClientModule:** Para fazer requisições HTTP (ex: buscar dados de partidas, interagir com a API do fórum).
* **Git:** Sistema de controle de versão.

## Pré-requisitos

Antes de começar, certifique-se de ter o Node.js, npm (ou yarn) e o Angular CLI instalados na sua máquina:

* **Node.js:** [https://nodejs.org/](https://nodejs.org/)
* **npm (gerenciador de pacotes do Node.js):** Geralmente vem com o Node.js.
* **Angular CLI:** Instale globalmente via npm:
    ```bash
    npm install -g @angular/cli
    ```

Você pode verificar as versões instaladas com os seguintes comandos:

```bash
node -v
npm -v
ng version
Instalação
Siga estes passos para configurar o projeto localmente:

Clone o Repositório:
Bash

git clone <URL_DO_SEU_REPOSITORIO_GITHUB>
(Substitua <URL_DO_SEU_REPOSITORIO_GITHUB> pela URL do seu repositório após criá-lo no GitHub)
Navegue até o Diretório do Projeto:
Bash

cd nome-da-pasta-do-seu-projeto
Instale as Dependências:
Bash

npm install
# ou use yarn: yarn install
Executando a Aplicação
Para iniciar o servidor de desenvolvimento local:

Bash

ng serve
Abra seu navegador em http://localhost:4200/. A aplicação será recarregada automaticamente conforme você faz alterações nos arquivos.

Estrutura do Projeto
As pastas e arquivos principais incluem:

src/app/: Contém os componentes, serviços, modelos e lógica da aplicação.
src/app/components/: (Ou componentes diretamente em src/app/) Onde estão seus componentes como landing-page, forum, topic-detail, etc.
src/app/models/: Define as interfaces ou classes para a estrutura dos dados (partidas, tópicos, posts).
src/app/services/: Contém os serviços (como ForumService, MatchService) para interagir com APIs ou gerenciar lógica de negócio.
src/app/app.routes.ts: Configuração das rotas de navegação.
src/assets/: Contém assets estáticos como imagens (logos, backgrounds) e arquivos de favicon.
src/index.html: O arquivo HTML principal onde a aplicação Angular é carregada.
angular.json: Arquivo de configuração do Angular CLI.
package.json: Arquivo que lista as dependências do projeto e scripts de build.
Próximos Passos (Melhorias Futuras)
Implementar um backend real para o fórum e chat para persistência de dados e interação em tempo real.
Adicionar autenticação de usuários.
Expandir as funcionalidades do chat (usuários online, mensagens privadas).
Melhorar a interface do fórum com paginação, ordenação, pesquisa, etc.
Adicionar perfis de usuário.
Implementar testes unitários e de integração.
Contribuindo
Contribuições são bem-vindas! Se você quiser contribuir, por favor:

Faça um fork do projeto.
Crie uma branch para sua feature (git checkout -b feature/MinhaNovaFeature).
Commit suas mudanças (git commit -m 'feat: Adiciona MinhaNovaFeature').
Faça push para a branch (git push origin feature/MinhaNovaFeature).
Abra um Pull Request.
Licença
Este projeto está licenciado sob a Licença MIT.

Contato
Se você tiver alguma dúvida ou sugestão, 1  sinta-se à vontade para entrar em contato.
