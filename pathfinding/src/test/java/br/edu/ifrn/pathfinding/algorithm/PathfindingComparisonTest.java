package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathfindingComparisonTest {

    @Test
    void ambosDevemEncontrarCaminhoMasComCustosDiferentesNesteMapa() {

        Grid grid = createMap();

        SearchResult greedy =
                new GreedySearch().search(grid);

        SearchResult aStar =
                new AStarSearch().search(grid);

        assertTrue(greedy.isFound());
        assertTrue(aStar.isFound());

        assertEquals(14.0, greedy.getTotalCost());
        assertEquals(12.0, aStar.getTotalCost());

        assertTrue(
                aStar.getTotalCost() < greedy.getTotalCost()
        );
    }

    private Grid createMap() {

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
}