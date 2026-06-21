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
