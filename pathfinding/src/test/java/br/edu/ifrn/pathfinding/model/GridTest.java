package br.edu.ifrn.pathfinding.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void deveCriarGridComDimensoesCorretas() {

        Grid grid = new Grid(5, 7);

        assertEquals(5, grid.getRows());
        assertEquals(7, grid.getColumns());
    }

    @Test
    void deveReconhecerPosicaoDentroDoGrid() {

        Grid grid = new Grid(5, 5);

        assertTrue(grid.isInside(new Position(0, 0)));
        assertTrue(grid.isInside(new Position(4, 4)));
    }

    @Test
    void deveReconhecerPosicaoForaDoGrid() {

        Grid grid = new Grid(5, 5);

        assertFalse(grid.isInside(new Position(-1, 0)));
        assertFalse(grid.isInside(new Position(5, 0)));
        assertFalse(grid.isInside(new Position(0, 5)));
    }

    @Test
    void deveAdicionarObstaculo() {

        Grid grid = new Grid(5, 5);
        Position obstacle = new Position(2, 2);

        grid.addObstacle(obstacle);

        assertTrue(grid.isObstacle(obstacle));
    }

    @Test
    void deveRemoverObstaculo() {

        Grid grid = new Grid(5, 5);
        Position obstacle = new Position(2, 2);

        grid.addObstacle(obstacle);
        grid.removeObstacle(obstacle);

        assertFalse(grid.isObstacle(obstacle));
    }

    @Test
    void deveRetornarQuatroVizinhosNoCentroDoGrid() {

        Grid grid = new Grid(3, 3);

        Position center = new Position(1, 1);

        List<Position> neighbors =
                grid.getValidNeighbors(center);

        assertEquals(4, neighbors.size());

        assertEquals(
                Set.of(
                        new Position(0, 1),
                        new Position(2, 1),
                        new Position(1, 0),
                        new Position(1, 2)
                ),
                Set.copyOf(neighbors)
        );
    }

    @Test
    void naoDeveRetornarObstaculoComoVizinho() {

        Grid grid = new Grid(3, 3);

        Position center = new Position(1, 1);
        Position obstacle = new Position(1, 0);

        grid.addObstacle(obstacle);

        List<Position> neighbors =
                grid.getValidNeighbors(center);

        assertFalse(neighbors.contains(obstacle));
        assertEquals(3, neighbors.size());
    }

    @Test
    void inicioEDestinoNaoPodemVirarObstaculos() {

        Grid grid = new Grid(5, 5);

        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);

        grid.setStart(start);
        grid.setGoal(goal);

        assertThrows(
                IllegalArgumentException.class,
                () -> grid.addObstacle(start)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> grid.addObstacle(goal)
        );
    }
}