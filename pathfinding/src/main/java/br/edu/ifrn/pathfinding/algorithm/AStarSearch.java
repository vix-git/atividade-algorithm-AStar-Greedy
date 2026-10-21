package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Node;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import br.edu.ifrn.pathfinding.util.Heuristic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A* Search.
 *
 * f(n) = g(n) + h(n)
 *
 * g(n) = custo acumulado
 * h(n) = distância Octile
 */
public final class AStarSearch
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

        Map<Position, Double> bestCosts =
                new HashMap<>();

        Set<Position> closed =
                new HashSet<>();

        List<Position> explored =
                new ArrayList<>();

        double initialH =
                Heuristic.octileDistance(
                        start,
                        goal
                );

        Node initial =
                Node.aStar(
                        start,
                        null,
                        0,
                        initialH
                );

        frontier.add(initial);

        bestCosts.put(
                start,
                0.0
        );

        while (!frontier.isEmpty()) {

            Node current =
                    frontier.remove();

            /*
             * Ignora entrada antiga da fila.
             */
            double bestCost =
                    bestCosts.getOrDefault(
                            current.getPosition(),
                            Double.POSITIVE_INFINITY
                    );

            if (current.getG() != bestCost) {
                continue;
            }

            /*
             * Evita processar a mesma posição
             * novamente depois que já foi fechada.
             */
            if (!closed.add(
                    current.getPosition()
            )) {
                continue;
            }

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

                double movementCost =
                        grid.getMovementCost(
                                current.getPosition(),
                                neighbor
                        );

                double tentativeG =
                        current.getG()
                                + movementCost;

                double knownG =
                        bestCosts.getOrDefault(
                                neighbor,
                                Double.POSITIVE_INFINITY
                        );

                /*
                 * Encontramos uma rota melhor.
                 */
                if (tentativeG < knownG) {

                    bestCosts.put(
                            neighbor,
                            tentativeG
                    );

                    /*
                     * Caso já estivesse fechada,
                     * permitimos reconsideração.
                     */
                    closed.remove(
                            neighbor
                    );

                    double h =
                            Heuristic.octileDistance(
                                    neighbor,
                                    goal
                            );

                    frontier.add(
                            Node.aStar(
                                    neighbor,
                                    current,
                                    tentativeG,
                                    h
                            )
                    );
                }
            }
        }

        /*
         * Não existe caminho.
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