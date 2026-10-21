package br.edu.ifrn.pathfinding.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void deveConsiderarDuasPosicoesIguaisQuandoPossuemMesmoXEY() {

        Position primeira = new Position(2, 3);
        Position segunda = new Position(2, 3);

        assertEquals(primeira, segunda);
    }

    @Test
    void deveConsiderarDuasPosicoesDiferentesQuandoPossuemCoordenadasDiferentes() {

        Position primeira = new Position(2, 3);
        Position segunda = new Position(3, 3);

        assertNotEquals(primeira, segunda);
    }

    @Test
    void posicoesIguaisDevemPossuirMesmoHashCode() {

        Position primeira = new Position(2, 3);
        Position segunda = new Position(2, 3);

        assertEquals(primeira.hashCode(), segunda.hashCode());
    }

    @Test
    void deveRetornarCoordenadasCorretamente() {

        Position position = new Position(5, 7);

        assertEquals(5, position.getX());
        assertEquals(7, position.getY());
    }
}