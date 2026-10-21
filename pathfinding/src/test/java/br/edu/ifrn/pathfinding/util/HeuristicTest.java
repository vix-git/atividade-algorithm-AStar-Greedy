package br.edu.ifrn.pathfinding.util;

import br.edu.ifrn.pathfinding.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeuristicTest {

    @Test
    void deveCalcularDistanciaManhattanCorretamente() {

        Position primeira = new Position(0, 0);
        Position segunda = new Position(6, 6);

        double resultado = Heuristic.manhattanDistance(primeira, segunda);

        assertEquals(12, resultado);
    }

    @Test
    void distanciaManhattanDeveSerSimetrica() {

        Position primeira = new Position(2, 5);
        Position segunda = new Position(7, 1);

        double primeiraDirecao =
                Heuristic.manhattanDistance(primeira, segunda);

        double segundaDirecao =
                Heuristic.manhattanDistance(segunda, primeira);

        assertEquals(primeiraDirecao, segundaDirecao);
    }

    @Test
    void distanciaDeUmaPosicaoParaElaMesmaDeveSerZero() {

        Position position = new Position(4, 4);

        assertEquals(
                0,
                Heuristic.manhattanDistance(position, position)
        );
    }
}