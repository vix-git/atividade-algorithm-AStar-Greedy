package br.edu.ifrn.pathfinding.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Mapa 2D.
 *
 * Movimentos permitidos:
 *
 * Cima, baixo, esquerda e direita:
 * custo = 10
 *
 * Diagonais:
 * custo = 14
 *
 * A busca não permite atravessar diagonalmente
 * pelo canto de dois obstáculos.
 */
public final class Grid {

    private static final int STRAIGHT_COST = 10;
    private static final int DIAGONAL_COST = 14;

    private static final int[][] DIRECTIONS = {
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1},

            {1, 1},
            {-1, 1},
            {1, -1},
            {-1, -1}
    };

    private final int rows;
    private final int columns;

    private final Set<Position> obstacles =
            new LinkedHashSet<>();

    private Position start;
    private Position goal;

    public Grid(int rows, int columns) {

        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                    "Rows and columns must be positive."
            );
        }

        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isInside(Position position) {

        Objects.requireNonNull(
                position,
                "position"
        );

        return position.getX() >= 0
                && position.getX() < columns
                && position.getY() >= 0
                && position.getY() < rows;
    }

    public boolean isObstacle(Position position) {

        return obstacles.contains(position);
    }

    public void addObstacle(Position position) {

        requireInside(position);

        if (position.equals(start)
                || position.equals(goal)) {

            throw new IllegalArgumentException(
                    "Start and goal cannot be obstacles."
            );
        }

        obstacles.add(position);
    }

    public void removeObstacle(Position position) {

        obstacles.remove(position);
    }

    public void clearObstacles() {

        obstacles.clear();
    }

    public Set<Position> getObstacles() {

        return Collections.unmodifiableSet(
                obstacles
        );
    }

    public void setStart(Position start) {

        requireInside(start);

        this.start = start;

        obstacles.remove(start);
    }

    public void setGoal(Position goal) {

        requireInside(goal);

        this.goal = goal;

        obstacles.remove(goal);
    }

    public Position getStart() {

        return start;
    }

    public Position getGoal() {

        return goal;
    }

    /**
     * Retorna os vizinhos válidos da posição.
     *
     * Agora são permitidas 8 direções.
     */
    public List<Position> getValidNeighbors(
            Position position
    ) {

        requireInside(position);

        List<Position> neighbors =
                new ArrayList<>(8);

        for (int[] direction : DIRECTIONS) {

            int dx = direction[0];
            int dy = direction[1];

            Position neighbor =
                    new Position(
                            position.getX() + dx,
                            position.getY() + dy
                    );

            /*
             * Fora do mapa.
             */
            if (!isInside(neighbor)) {
                continue;
            }

            /*
             * Obstáculo.
             */
            if (isObstacle(neighbor)) {
                continue;
            }

            /*
             * Movimento diagonal.
             */
            boolean diagonal =
                    dx != 0 && dy != 0;

            /*
             * Não permite atravessar pelo canto
             * de dois obstáculos.
             *
             * Exemplo proibido:
             *
             * # .
             * . A
             *
             * A não pode atravessar diagonalmente
             * pelo canto entre os dois obstáculos.
             */
            if (diagonal) {

                Position horizontal =
                        new Position(
                                position.getX() + dx,
                                position.getY()
                        );

                Position vertical =
                        new Position(
                                position.getX(),
                                position.getY() + dy
                        );

                if (isObstacle(horizontal)
                        || isObstacle(vertical)) {

                    continue;
                }
            }

            neighbors.add(neighbor);
        }

        return neighbors;
    }

    /**
     * Retorna o custo de um movimento.
     *
     * Reto:
     * 10
     *
     * Diagonal:
     * 14
     */
    public double getMovementCost(
            Position from,
            Position to
    ) {

        requireInside(from);
        requireInside(to);

        int dx =
                Math.abs(
                        to.getX() - from.getX()
                );

        int dy =
                Math.abs(
                        to.getY() - from.getY()
                );

        if (dx == 1 && dy == 1) {
            return DIAGONAL_COST;
        }

        if (dx + dy == 1) {
            return STRAIGHT_COST;
        }

        throw new IllegalArgumentException(
                "Positions are not adjacent: "
                        + from
                        + " -> "
                        + to
        );
    }

    /**
     * Gera obstáculos aleatórios.
     */
    public void generateRandomObstacles(
            double density,
            Random random
    ) {

        if (density < 0.0
                || density > 1.0) {

            throw new IllegalArgumentException(
                    "Density must be between 0.0 and 1.0."
            );
        }

        Objects.requireNonNull(
                random,
                "random"
        );

        clearObstacles();

        for (int y = 0; y < rows; y++) {

            for (int x = 0; x < columns; x++) {

                Position position =
                        new Position(x, y);

                if (!position.equals(start)
                        && !position.equals(goal)
                        && random.nextDouble()
                        < density) {

                    obstacles.add(position);
                }
            }
        }
    }

    private void requireInside(
            Position position
    ) {

        if (!isInside(position)) {

            throw new IllegalArgumentException(
                    "Position is outside the grid: "
                            + position
            );
        }
    }
}