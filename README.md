# atividade-algorithm-AStar-Greedy

# RouteQuest — Simulador de Busca e Planejamento de Rotas

Aplicação desktop desenvolvida em **Java 21 e JavaFX** para visualizar e comparar o comportamento de dois algoritmos de busca de caminhos: **Greedy Best-First Search** e **A\* (A-Star)**.

O sistema simula um agente que precisa encontrar uma rota entre um ponto inicial (A) e um destino (B), desviando de obstáculos em um mapa interativo de 20 × 20 células. A aplicação apresenta a exploração do ambiente, o caminho encontrado, métricas de desempenho e animações em pixel art.

## Funcionalidades

- **Mapa interativo 20 × 20:** grade com 400 células para montar diferentes cenários.
- **Posicionamento de origem e destino:** permite selecionar as posições dos pontos A e B pela interface.
- **Edição de obstáculos:** permite adicionar e remover obstáculos diretamente no mapa.
- **Seleção de algoritmo:** alterna entre Greedy Best-First Search e A*.
- **Visualização da exploração:** exibe gradualmente as células exploradas durante a busca.
- **Exibição do caminho:** destaca a rota encontrada pelo algoritmo.
- **Animação do agente:** o guerreiro percorre o caminho encontrado.
- **Pixel art:** utilização de sprites para o piso, obstáculos, personagem e destino.
- **Animação de batalha:** exibe uma animação quando o agente chega ao objetivo.
- **Tratamento de falhas:** informa quando não existe caminho possível entre A e B, permitindo alterar o cenário e tentar novamente.
- **Métricas comparativas:** apresenta informações sobre o desempenho da busca e o custo da rota.

## Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 21 | Linguagem principal e implementação dos algoritmos. |
| JavaFX | Desenvolvimento da interface gráfica desktop. |
| JavaFX Canvas | Desenho da grade, obstáculos, exploração e caminho. |
| JavaFX Timeline | Controle da animação da exploração e do movimento do personagem. |
| JavaFX CSS | Estilização do painel, botões, seções e métricas. |
| Maven | Gerenciamento do projeto, dependências, compilação e execução. |
| PNG e GIF | Recursos visuais, sprites de pixel art e animação de batalha. |
| JUnit 5 | Apoio à validação da lógica durante o desenvolvimento. |
| OpenAI Codex e ChatGPT | Assistência no desenvolvimento, geração inicial de código, análise de erros e evolução da implementação. |

## Algoritmos implementados

### 1. Greedy Best-First Search

A Busca Gulosa seleciona o próximo nó principalmente com base na estimativa de custo restante até o destino.

Sua função de prioridade é:

\[
f(n)=h(n)
\]

Onde:

- \(h(n)\) representa a estimativa de custo entre o nó atual e o objetivo.
- \(f(n)\) determina a prioridade do nó na fila de busca.

**Características:**
- Prioriza posições que parecem mais próximas do destino.
- Pode encontrar soluções rapidamente em determinados cenários.
- Não garante encontrar o caminho de menor custo.

### 2. A* (A-Star)

O algoritmo A* considera tanto o custo acumulado quanto a estimativa do custo restante.

Sua função de avaliação é:

\[
f(n)=g(n)+h(n)
\]

Onde:

- \(g(n)\) é o custo acumulado desde o início até o nó atual.
- \(h(n)\) é a estimativa do custo restante até o objetivo.
- \(f(n)\) é o custo total estimado para chegar ao destino passando pelo nó atual.

**Características:**
- Considera o custo já percorrido.
- Avalia alternativas de rota.
- Com custos de movimento positivos e uma heurística admissível e consistente, como a distância Octile utilizada neste projeto, pode encontrar uma solução de custo ótimo.

## Movimentação e custos

O agente pode se movimentar em oito direções:

| Movimento | Custo |
|---|---:|
| Cima, baixo, esquerda e direita | 10 |
| Quatro diagonais | 14 |

O custo diagonal é uma aproximação de \(10\sqrt{2}\), simplificada para 14.

Os obstáculos bloqueiam a entrada nas células ocupadas. As regras de movimentação também são consideradas na geração dos vizinhos válidos.

### Heurística: distância Octile

Como a movimentação permite diagonais com custo diferente dos movimentos retos, utiliza-se a distância Octile para estimar o custo restante.

\[
h(n)=10(\max(dx,dy)-\min(dx,dy))+14\min(dx,dy)
\]

Onde:

\[
dx=|x_{\text{objetivo}}-x_{\text{atual}}|
\]

\[
dy=|y_{\text{objetivo}}-y_{\text{atual}}|
\]

Por exemplo, em um mapa sem obstáculos, sair de \((0,0)\) e chegar a \((19,19)\) exige 19 movimentos diagonais:

\[
19\times14=266
\]

Portanto, o custo mínimo teórico é 266 unidades de custo.

## Métricas apresentadas

A interface apresenta as seguintes informações:

| Métrica | Significado |
|---|---|
| Tempo de execução | Tempo gasto pelo algoritmo para calcular a busca, exibido em milissegundos. |
| Nós explorados | Quantidade de posições efetivamente processadas durante a busca. |
| Mapa explorado | Percentual de células exploradas em relação às 400 células disponíveis. |
| Custo mínimo teórico | Estimativa inferior do custo entre origem e destino, desconsiderando obstáculos. |
| Custo do caminho | Soma dos custos de todos os movimentos da rota encontrada. |
| Movimentos | Quantidade de transições entre células no caminho final. |
| Desvio do mínimo | Diferença entre o custo encontrado e o custo mínimo teórico. |


O custo mínimo teórico não considera obstáculos. Por isso, a existência de um custo mínimo teórico não significa que necessariamente exista uma rota válida no mapa.

**Observação:** o tempo apresentado representa o cálculo do algoritmo, não a duração das animações. Em mapas pequenos, o valor pode aparecer como 0 ms devido à conversão para milissegundos inteiros.

## Como executar o projeto

### Pré-requisitos

- Java JDK 21 ou compatível.
- Apache Maven instalado.
- Ambiente gráfico compatível com JavaFX.

Verifique as instalações:

```bash
java -version
mvn -version
```

### Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd RouteQuest
```

Substitua `URL_DO_REPOSITORIO` pelo endereço real do repositório no GitHub.

### Executar a aplicação

```bash
mvn clean javafx:run
```

O Maven compilará o projeto, resolverá as dependências configuradas e iniciará a interface JavaFX.

## Como utilizar

1. Abra a aplicação e observe o mapa 20 × 20.
2. Selecione o modo de edição desejado: obstáculos, ponto A ou ponto B.
3. Clique nas células para configurar o cenário.
4. Escolha Greedy Best-First Search ou A*.
5. Clique em **Executar Busca**.
6. Observe a exploração, o caminho encontrado, as métricas e a animação do agente.
7. Compare os resultados dos algoritmos utilizando o mesmo mapa.
8. Caso não exista caminho, altere os obstáculos ou as posições de origem e destino e execute novamente.

O botão **Limpar Mapa** permite remover os obstáculos e reiniciar a visualização para um novo cenário.

## Organização do projeto

A estrutura principal do código é organizada em pacotes com responsabilidades distintas:

```text
RouteQuest/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── br/edu/ifrn/RouteQuest/
    │   │       ├── algorithm/
    │   │       │   ├── AStarSearch.java
    │   │       │   ├── GreedySearch.java
    │   │       │   ├── SearchAlgorithm.java
    │   │       │   └── SearchSupport.java
    │   │       ├── model/
    │   │       │   ├── Grid.java
    │   │       │   ├── Node.java
    │   │       │   ├── Position.java
    │   │       │   ├── SearchResult.java
    │   │       │   └── SearchStep.java
    │   │       ├── ui/
    │   │       │   ├── ControlsView.java
    │   │       │   ├── GridView.java
    │   │       │   └── RouteQuestApplication.java
    │   │       ├── util/
    │   │       │   └── Heuristic.java
    │   │       └── App.java
    │   └── resources/
    │       ├── assets/
    │       │   ├── combat/
    │       │   ├── floor/
    │       │   ├── goal/
    │       │   ├── obstacle/
    │       │   └── player/
    │       └── styles.css
    └── test/
        └── java/
```

**Responsabilidades principais:**

- `algorithm`: implementação e suporte dos algoritmos de busca.
- `model`: representação do mapa, posições, nós e resultados.
- `util`: cálculos heurísticos.
- `ui`: interface gráfica, interação com o usuário e animações.
- `resources/assets`: sprites e recursos gráficos.
- `resources/styles.css`: estilos visuais da interface.

## Uso de inteligência artificial no desenvolvimento

Ferramentas de inteligência artificial, incluindo OpenAI Codex e ChatGPT, foram utilizadas como apoio ao desenvolvimento do projeto.

O uso incluiu auxílio na estruturação inicial do código, geração de implementações preliminares, interpretação de erros de compilação, sugestões de correções e evolução das funcionalidades da interface.

As sugestões foram incorporadas de forma iterativa, com execução do código, análise dos resultados e adaptação às necessidades do projeto. A IA foi utilizada como ferramenta de assistência ao desenvolvimento, e não como substituta da compreensão dos algoritmos e das decisões de implementação.

## Objetivo acadêmico

O principal objetivo do projeto é demonstrar, de forma visual e interativa, as diferenças entre uma estratégia de busca orientada pela heurística e uma estratégia que combina custo acumulado e estimativa restante.

Ao permitir a criação de cenários, a observação da exploração e a comparação das métricas, a aplicação busca facilitar o entendimento do funcionamento do Greedy Best-First Search e do A*.

---

**Projeto acadêmico — IFRN**  
Desenvolvido para estudo de algoritmos de busca e planejamento de rotas.
