package br.edu.ifrn.pathfinding;

import br.edu.ifrn.pathfinding.algorithm.AStarSearch;
import br.edu.ifrn.pathfinding.algorithm.GreedySearch;
import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;

public class App {

    public static void main(String[] args) {

        Grid grid = createMap();

        System.out.println("=== MAPA ===");
        printGrid(grid);

        System.out.println();
        System.out.println("=== GREEDY ===");

        SearchResult greedyResult = new GreedySearch().search(grid);

        printResult(greedyResult);

        System.out.println();
        System.out.println("=== A* ===");

        SearchResult aStarResult = new AStarSearch().search(grid);

        printResult(aStarResult);
    }

    private static Grid createMap() {

        Grid grid = new Grid(7, 7);

        grid.setStart(new Position(0, 0));
        grid.setGoal(new Position(6, 6));

        grid.addObstacle(new Position(1, 0));
        grid.addObstacle(new Position(3, 0));
        grid.addObstacle(new Position(6, 0));

        grid.addObstacle(new Position(1, 2));

        grid.addObstacle(new Position(2, 4));

        grid.addObstacle(new Position(0, 5));
        grid.addObstacle(new Position(4, 5));
        grid.addObstacle(new Position(6, 5));

        grid.addObstacle(new Position(3, 6));
        grid.addObstacle(new Position(4, 6));

        return grid;
    }

    private static void printGrid(Grid grid) {

        for (int y = 0; y < grid.getRows(); y++) {

            for (int x = 0; x < grid.getColumns(); x++) {

                Position position = new Position(x, y);

                if (position.equals(grid.getStart())) {
                    System.out.print("A ");
                } else if (position.equals(grid.getGoal())) {
                    System.out.print("B ");
                } else if (grid.isObstacle(position)) {
                    System.out.print("# ");
                } else {
                    System.out.print(". ");
                }
            }

            System.out.println();
        }
    }

    private static void printResult(SearchResult result) {

        System.out.println("Encontrou caminho: " + result.isFound());
        System.out.println("Nós explorados: " + result.getExploredNodes());
        System.out.println("Tempo: " + result.getExecutionTimeMillis() + " ms");
        System.out.println("Custo: " + result.getTotalCost());
        System.out.println("Tamanho do caminho: " + result.getPathLength());

        System.out.println();
        System.out.println("Ordem de exploração:");

        int index = 1;

        for (Position position : result.getExploredPositions()) {
            System.out.println(index + ". " + position);
            index++;
        }

        System.out.println();
        System.out.println("Caminho:");

        for (Position position : result.getPath()) {
            System.out.print(position + " ");
        }

        System.out.println();
    }
}