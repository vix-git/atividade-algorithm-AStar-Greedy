package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GreedySearchTest {

    @Test
    void deveEncontrarCaminho() {

        Grid grid = createMap();

        SearchResult result =
                new GreedySearch().search(grid);

        assertTrue(result.isFound());
        assertFalse(result.getPath().isEmpty());
    }

    @Test
    void caminhoDeveComecarNoInicioETerminarNoDestino() {

        Grid grid = createMap();

        SearchResult result =
                new GreedySearch().search(grid);

        List<Position> path = result.getPath();

        assertEquals(grid.getStart(), path.get(0));
        assertEquals(grid.getGoal(), path.get(path.size() - 1));
    }

    @Test
    void caminhoNaoPodePassarPorObstaculos() {

        Grid grid = createMap();

        SearchResult result =
                new GreedySearch().search(grid);

        for (Position position : result.getPath()) {
            assertFalse(grid.isObstacle(position));
        }
    }

    @Test
    void cadaMovimentoDoCaminhoDeveSerOrtogonal() {

        Grid grid = createMap();

        SearchResult result =
                new GreedySearch().search(grid);

        List<Position> path = result.getPath();

        for (int i = 1; i < path.size(); i++) {

            Position previous = path.get(i - 1);
            Position current = path.get(i);

            int distance =
                    Math.abs(
                            previous.getX() - current.getX()
                    )
                            +
                            Math.abs(
                                    previous.getY() - current.getY()
                            );

            assertEquals(1, distance);
        }
    }

    @Test
    void deveEncontrarOCaminhoDeCustoConhecido() {

        Grid grid = createMap();

        SearchResult result =
                new GreedySearch().search(grid);

        assertEquals(14.0, result.getTotalCost());
        assertEquals(14, result.getPathLength());
    }

    @Test
    void deveInformarFalhaQuandoNaoExisteCaminho() {

        Grid grid = new Grid(5, 5);

        grid.setStart(new Position(0, 0));
        grid.setGoal(new Position(4, 4));

        grid.addObstacle(new Position(1, 0));
        grid.addObstacle(new Position(0, 1));

        SearchResult result =
                new GreedySearch().search(grid);

        assertFalse(result.isFound());
        assertTrue(result.getPath().isEmpty());
        assertTrue(Double.isInfinite(result.getTotalCost()));
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