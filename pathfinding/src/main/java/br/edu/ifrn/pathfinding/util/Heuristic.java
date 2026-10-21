package br.edu.ifrn.pathfinding.util;

import br.edu.ifrn.pathfinding.model.Position;

/**
 * Heurísticas utilizadas pelo pathfinding.
 */
public final class Heuristic {

    private static final double STRAIGHT_COST = 10.0;
    private static final double DIAGONAL_COST = 14.0;

    private Heuristic() {
    }

    /**
     * Distância Manhattan.
     *
     * Mantida caso seja necessário comparar
     * com a versão anterior.
     */
    public static double manhattanDistance(
            Position first,
            Position second
    ) {

        return Math.abs(
                first.getX() - second.getX()
        )
                +
                Math.abs(
                        first.getY() - second.getY()
                );
    }

    /**
     * Distância Octile.
     *
     * Considera:
     *
     * movimento reto = 10
     * movimento diagonal = 14
     *
     * Fórmula:
     *
     * 10 * (max - min) + 14 * min
     */
    public static double octileDistance(
            Position first,
            Position second
    ) {

        double dx =
                Math.abs(
                        first.getX()
                                - second.getX()
                );

        double dy =
                Math.abs(
                        first.getY()
                                - second.getY()
                );

        double min =
                Math.min(dx, dy);

        double max =
                Math.max(dx, dy);

        return STRAIGHT_COST
                * (max - min)
                +
                DIAGONAL_COST
                        * min;
    }
}