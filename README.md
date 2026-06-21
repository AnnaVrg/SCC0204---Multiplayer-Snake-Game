# 🐍MultiplayerSnakeGame🐍 - Jogo multiplayer baseado no Snake Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)
![POO](https://img.shields.io/badge/POO-Programação_Orientada_a_Objetos-success?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

---

# 👥Integrantes

| Nome | NUSP |
|---|---|
| Aline Mayumi Uekita | 16983070 |
| Anna Vitória Rocha Gonçalves | 14558885 |
| Higor Campos Fernandes | 15583489 |

---

# 🐍 Sobre o Jogo

O **Multiplayer Snake Game** é um jogo arcade competitivo para dois jogadores, baseado no clássico *Snake Game*, desenvolvido em **Java** utilizando o framework **LibGDX**.

O projeto foi construído dividindo a mesma tela (*local co-op*), onde os jogadores competem simultaneamente em tempo real para ver quem domina o tabuleiro e alcança a maior pontuação.

---

## 🎮 Mecânicas e Controles

O sistema divide o gerenciamento e o controle das entidades de forma independente:

* **🟢 Jogador 1 (Cobra Verde):** Controlado de forma intuitiva através das **setas direcionais** do teclado.
* **🔵 Jogador 2 (Cobra Azul):** Controlado simultaneamente através das teclas **`W`**, **`A`**, **`S`** e **`D`**.

---

## ⚡ Funcionalidades Principais

* 🍎 **Sistema de Frutas Dinâmico:** Frutas de cores distintas surgem aleatoriamente pelo cenário. Cada cor corresponde a uma pontuação e recompensa diferente ao ser consumida.
* 💾 **Placar com Memória Local:** Armazenamento persistente das maiores pontuações (*High Scores*), garantindo a competição mesmo após o jogo ser fechado.
* ⚖️ **Mecânica Antisuicídio (Equilíbrio):** A partida se encerra quando qualquer uma das cobras morre. Contudo, se um jogador causar a própria morte intencionalmente para congelar o placar, o sistema aplica uma severa **penalidade de pontos** na sua pontuação final.
* 🖼️ **Gráficos Responsivos:** Ajuste automático de resolução de tela que preserva a proporção dos elementos visuais sem distorcer o grid do jogo.

---

## 🛠️ Tecnologias Utilizadas

O projeto foi estruturado utilizando ferramentas modernas para o ecossistema Java de jogos:

* **Java:** Linguagem base para toda a lógica de Programação Orientada a Objetos (POO).
* **LibGDX:** Framework multimídia para o gerenciamento de telas, texturas, inputs e o ciclo de vida do *Game Loop*.
* **Gradle / Gdx-Liftoff:** Ferramentas utilizadas para a gerência de dependências e setup modular do projeto.

---

# 📋 Requisitos 
O sistema foi desenvolvido para atender aos seguintes requisitos gerais de funcionamento e jogabilidade:

* **Configuração e Interface:** Funcionar como uma aplicação desktop via LibGDX, dispondo de menu principal interativo, tela de instruções clara e tela de encerramento (*Game Over*).
* **Mecânicas de Movimentação:** Controlar duas cobras simultâneas em um tabuleiro do tipo *pac-man* (onde as bordas se conectam), com crescimento progressivo e aceleração a cada item consumido.
* **Sistema de Colisões:** Detectar colisões fatais da cobra contra o próprio corpo ou contra a cobra adversária para determinar o fim da partida.
* **Suporte Multijogador Independente:** Diferenciar os jogadores por cores exclusivas e mapear controles isolados (Setas direcionais vs. Teclas WASD) no mesmo teclado.
* **Feedback Sonoro e Visual:** Exibir um placar dinâmico em tempo real e reproduzir efeitos sonoros para eventos críticos, como consumo de alimentos e mortes.
* **Persistência de Dados (Ranking):** Salvar e carregar localmente um histórico com as 5 maiores pontuações (*High Scores*), disponibilizando uma tela de recordes no menu principal.
* **Equilíbrio e Variedade (Opcionais):** Implementar frutas especiais com pontuações distintas e um sistema de penalização de pontos para coibir o suicídio proposital de jogadores.

As funcionalidades específicas que implementam esses requisitos estão detalhadas logo abaixo, na seção **⚡ Funcionalidades Principais**.

---

# 🖥️ Interfaces do Jogo

A interface visual do jogo foi desenvolvida utilizando o motor gráfico do **LibGDX**, estruturada através do gerenciamento de telas (`Screen`) e renderização de textos via *BitmapFont* para garantir uma navegação fluida e responsiva.

As capturas de tela abaixo representam as principais interfaces que guiam os usuários desde a inicialização até o encerramento das partidas.

---

# 📷 Demonstração das Interfaces

## Menu Principal

![Menu Principal](assets/menu-principal.png)

*Apresenta o título do jogo e fornece os pontos de entrada para iniciar a partida, visualizar as instruções, conferir as pontuações máximas ou encerrar o aplicativo.*

---

## 📖 Tela de Instruções (Como Jogar)

![Tela de Instruções](assets/instrucoes.png)

*Explicita as regras de pontuação, o funcionamento das bordas conectadas, o sistema de penalidades por suicídio e o mapeamento dos controles (Setas para o Jogador 1 e WASD para o Jogador 2).*

---

## 🎮 Tela da Partida (Ambiente do Jogo)

![Tela do Jogo](assets/partida-ativa.png)

*O cenário ativo em tempo real onde o grid, as cobras (Verde e Azul), a geração dinâmica de frutas e o placar simultâneo no topo da tela são renderizados.*

---

## 🏆 Tela de Pontuações Máximas (High Scores)

![Tela de Recordes](assets/high-scores.png)

*Exibe o painel de ranking local alimentado pela persistência de dados, mostrando o histórico das 5 melhores pontuações registradas.*

---

## 💀 Tela de Fim de Jogo (Game Over)

![Tela Game Over](assets/game-over.png)

*Interface acionada no momento da colisão fatal. Exibe o resultado final de pontos de cada jogador, destaca o vencedor com as devidas penalidades aplicadas e oferece os botões de ação para "Jogar Novamente" ou "Sair".*

# Estrutura do Projeto

## 📂 Principais Classes

| Classe | Responsabilidade |
|---|---|
| `GameObject` | Classe abstrata base para todas as entidades renderizáveis do cenário. |
| `Snake` | Representa as cobras, gerenciando o corpo, tamanho, direção e lógica de movimento. |
| `Food` | Representa os itens consumíveis (frutas), controlando posições e valores de pontos. |
| `ScoreBoard` | Gerencia a pontuação ativa, penalidades e o histórico de recordes (*High Scores*). |
| `GameScreen` | Tela principal do jogo onde ocorre o *Game Loop* (entrada, física e renderização). |
| `MainMenuScreen`| Tela de menu inicial com opções de navegação do usuário. |
| `InstructionScreen`| Tela explicativa que apresenta as regras e o mapeamento dos controles. |
| `GameOverScreen`| Tela de encerramento que exibe o placar final e opções de reinicialização. |
| `SnakeGame` | Classe central (extensão de `Game`) que coordena a transição entre as telas. |
| `Lwjgl3Launcher`| Inicializador nativo desktop que configura e dispara a aplicação. |

---

# 🛠️ Conceitos de POO Aplicados

## 1️⃣ Herança

A classe abstrata `GameObject` (ou equivalente estrutural no seu projeto) foi utilizada como classe base para `Snake` e `Food`. Ela encapsula propriedades compartilhadas por elementos do cenário, como coordenadas posicionais (`x, y`) e dimensões, permitindo reutilização de código de forma limpa.

---

## 2️⃣ Encapsulamento

Os estados internos críticos — como a lista de segmentos que formam o corpo da cobra ou o mapa de pontuações do `ScoreBoard` — foram protegidos utilizando o modificador `private`. O acesso e a modificação desses dados ocorrem estritamente através de métodos públicos bem definidos (como `grow()`, `changeDirection()` e `addPoints()`), garantindo a integridade das regras do jogo.

---

## 3️⃣ Polimorfismo

Utilizado amplamente no ciclo de vida das telas através da interface `Screen` do LibGDX. As classes `MainMenuScreen`, `GameScreen` e `GameOverScreen` sobrescrevem o método `@Override render(float delta)` para implementar comportamentos de desenho e atualização lógica completamente distintos, embora todas sejam tratadas de forma genérica pela classe principal `SnakeGame`.

---

## 4️⃣ Abstração

A complexidade de renderização de baixo nível da GPU e a captura direta de eventos do teclado foram abstraídas pelas ferramentas do framework LibGDX. No escopo do projeto, as classes se concentram apenas nas regras de negócio essenciais da simulação arcade (como vetores de direção, detecção geométrica de colisões e controle de tempo do passo lógico).

---

## 5️⃣ Persistência de Dados

A persistência do ranking local foi implementada utilizando a classe nativa `Preferences` do LibGDX. Ela abstrai a escrita em arquivos físicos no sistema operacional, permitindo salvar e carregar as chaves de pontuação máxima no disco de maneira limpa e transparente ao usuário.

---

# Descrição geral do Jogo

## 📌 Fluxo Geral de Navegação e Mecânicas

```mermaid
flowchart TD
    A([Início: Execução do Jogo]) --> B[Tela de Menu Principal]
    
    %% NAVEGAÇÃO DO MENU PRINCIPAL
    B --> C{Escolha do Usuário}
    
    C -->|Instruções| D[Tela de Instruções]
    D -->|Voltar| B
    
    C -->|High Scores| E[Tela de Pontuações Máximas]
    E -->|Voltar| B
    
    C -->|Sair| F([Encerrar Aplicativo])
    
    C -->|Novo Jogo| G[Tela da Partida: Game Screen]

    %% LOOP DA PARTIDA (GAME LOOP)
    G --> H[Capturar Entradas Simultâneas: Setas P1 / WASD P2]
    H --> I[Atualizar Posições no Tabuleiro]
    I --> J{Cobra colidiu com Fruta?}

    %% CONSUMO DE FRUTAS
    J -->|Sim| K[Identificar Cor da Fruta]
    K --> L[Incrementar Pontos Respectivos no ScoreBoard]
    L --> M[Aumentar Comprimento e Velocidade da Cobra]
    M --> N{Qualquer Cobra Morreu?}

    J -->|Não| N

    %% VERIFICAÇÃO DE MORTE / COLISÃO
    N -->|Não| H
    N -->|Sim| O{A Morte foi por Suicídio?}

    %% TRATAMENTO DE PENALIDADE E FIM DE JOGO
    O -->|Sim| P[Aplicar Penalidade: Reduzir Pontos no ScoreBoard]
    P --> Q[Mudar para Tela de Fim de Jogo: Game Over]
    
    O -->|Não| Q

    %% TELA DE GAME OVER
    Q --> R[Exibir Pontuações Finais e Vencedor]
    R --> S{Verificar Novo Recorde?}
    
    S -->|Sim| T[Salvar no Ranking Local via Preferences]
    T --> U{Nova Escolha do Jogador}
    
    S -->|Não| U

    U -->|Jogar Novamente| G
    U -->|Sair para o Menu| B
```

---

# Comentários sobre o código

O projeto foi organizado seguindo o princípio da separação de responsabilidades e a arquitetura baseada em estados (telas) do framework LibGDX, o que facilita a manutenção do código, a reutilização de componentes e futuras expansões.

A classe central `SnakeGame` atua como a coordenadora principal do ciclo de vida do aplicativo, sendo a responsável por gerenciar a transição entre as diferentes telas do jogo através do método `setScreen`.

A camada visual e o fluxo do usuário foram divididos em classes de tela independentes: `MainMenuScreen` gerencia a interface inicial e navegação; `InstructionScreen` apresenta as diretrizes e mapeamento de comandos; `GameOverScreen` processa o desfecho da partida e opções de reinício; e a `GameScreen` concentra o coração da aplicação, encapsulando o *Game Loop* dinâmico (processamento de entradas do teclado, atualização da física e renderização gráfica).

As regras de negócio e a simulação do jogo são isoladas em entidades específicas do domínio. A classe `Snake` controla de forma autônoma a movimentação, crescimento e autocolisão de cada jogador. A classe `Food` gerencia a lógica de spawn aleatório e as propriedades nutricionais (pontuação) das frutas no tabuleiro.

O gerenciamento de estado técnico, feedback e persistência foi distribuído em módulos especializados:
* `ScoreBoard`: Centraliza o controle de pontuação simultânea, aplica as penalidades de pontos em caso de suicídio e manipula a API `Preferences` do LibGDX para ler e gravar o ranking das 5 melhores pontuações em um arquivo local permanente.
* **Gerenciamento de Áudio e Assets**: Os efeitos sonoros (como o som de mordida ao consumir frutas e o som de impacto na colisão) e a trilha sonora de fundo são manipulados de forma otimizada pelas classes nativas `Sound` e `Music`. Isso garante o carregamento assíncrono e a execução fluida do áudio em tempo real, sem interromper ou travar os frames de renderização.

A arquitetura adotada busca manter um baixo acoplamento entre a renderização gráfica e a lógica matemática das colisões, garantindo alta coesão dentro de cada entidade e respeitando as boas práticas de Programação Orientada a Objetos aplicada ao desenvolvimento de jogos.
