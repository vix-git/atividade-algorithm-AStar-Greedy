package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Node;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import br.edu.ifrn.pathfinding.util.Heuristic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Greedy Best-First Search.
 *
 * Prioridade:
 *
 * f(n) = h(n)
 *
 * O Greedy procura a posição que parece
 * estar mais próxima do objetivo.
 */
public final class GreedySearch
        implements SearchAlgorithm {

    @Override
    public SearchResult search(Grid grid) {

        long startedAt =
                System.nanoTime();

        if (grid == null
                || grid.getStart() == null
                || grid.getGoal() == null) {

            return failed(
                    List.of(),
                    startedAt
            );
        }

        Position start =
                grid.getStart();

        Position goal =
                grid.getGoal();

        PriorityQueue<Node> frontier =
                new PriorityQueue<>(
                        Comparator
                                .comparingDouble(
                                        Node::getF
                                )
                                .thenComparingDouble(
                                        Node::getH
                                )
                                .thenComparingDouble(
                                        Node::getG
                                )
                );

        Set<Position> discovered =
                new HashSet<>();

        List<Position> explored =
                new ArrayList<>();

        Node initial =
                Node.greedy(
                        start,
                        null,
                        0,
                        Heuristic.octileDistance(
                                start,
                                goal
                        )
                );

        frontier.add(initial);

        discovered.add(start);

        while (!frontier.isEmpty()) {

            Node current =
                    frontier.remove();

            explored.add(
                    current.getPosition()
            );

            /*
             * Chegou ao destino.
             */
            if (current.getPosition()
                    .equals(goal)) {

                return found(
                        current,
                        explored,
                        startedAt
                );
            }

            /*
             * Expande vizinhos.
             */
            for (
                    Position neighbor :
                    grid.getValidNeighbors(
                            current.getPosition()
                    )
            ) {

                if (discovered.add(
                        neighbor
                )) {

                    double movementCost =
                            grid.getMovementCost(
                                    current.getPosition(),
                                    neighbor
                            );

                    double g =
                            current.getG()
                                    + movementCost;

                    double h =
                            Heuristic.octileDistance(
                                    neighbor,
                                    goal
                            );

                    frontier.add(
                            Node.greedy(
                                    neighbor,
                                    current,
                                    g,
                                    h
                            )
                    );
                }
            }
        }

        /*
         * A fila ficou vazia:
         * não existe caminho.
         */
        return failed(
                explored,
                startedAt
        );
    }

    private SearchResult found(
            Node goal,
            List<Position> explored,
            long startedAt
    ) {

        return new SearchResult(
                SearchSupport.reconstructPath(
                        goal
                ),
                explored,
                elapsedMillis(
                        startedAt
                ),
                goal.getG(),
                true
        );
    }

    private SearchResult failed(
            List<Position> explored,
            long startedAt
    ) {

        return new SearchResult(
                List.of(),
                explored,
                elapsedMillis(
                        startedAt
                ),
                Double.POSITIVE_INFINITY,
                false
        );
    }

    private long elapsedMillis(
            long startedAt
    ) {

        return (
                System.nanoTime()
                        - startedAt
        )
                / 1_000_000;
    }
}