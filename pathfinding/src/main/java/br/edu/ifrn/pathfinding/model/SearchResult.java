package br.edu.ifrn.pathfinding.model;

import java.util.List;
import java.util.Objects;

/**
 * Resultado de uma execução de um algoritmo de pathfinding.
 */
public final class SearchResult {

    private final List<Position> path;

    private final List<Position> exploredPositions;

    private final List<SearchStep> steps;

    private final int exploredNodes;

    private final long executionTime;

    private final double totalCost;

    private final boolean found;

    /**
     * Construtor completo.
     *
     * Usado quando queremos armazenar também
     * os SearchSteps.
     */
    public SearchResult(
            List<Position> path,
            List<Position> exploredPositions,
            List<SearchStep> steps,
            long executionTime,
            double totalCost,
            boolean found
    ) {

        this.path =
                List.copyOf(
                        Objects.requireNonNull(
                                path,
                                "path"
                        )
                );

        this.exploredPositions =
                List.copyOf(
                        Objects.requireNonNull(
                                exploredPositions,
                                "exploredPositions"
                        )
                );

        this.steps =
                List.copyOf(
                        Objects.requireNonNull(
                                steps,
                                "steps"
                        )
                );

        this.exploredNodes =
                exploredPositions.size();

        this.executionTime =
                executionTime;

        this.totalCost =
                totalCost;

        this.found =
                found;
    }

    /**
     * Construtor simplificado.
     *
     * Os algoritmos atuais não precisam armazenar
     * SearchStep para funcionar.
     *
     * Portanto, quando essa versão é usada,
     * a lista de steps fica vazia.
     */
    public SearchResult(
            List<Position> path,
            List<Position> exploredPositions,
            long executionTime,
            double totalCost,
            boolean found
    ) {

        this(
                path,
                exploredPositions,
                List.of(),
                executionTime,
                totalCost,
                found
        );
    }

    public List<Position> getPath() {

        return path;
    }

    public List<Position> getExploredPositions() {

        return exploredPositions;
    }

    public List<SearchStep> getSteps() {

        return steps;
    }

    public int getExploredNodes() {

        return exploredNodes;
    }

    /**
     * Tempo de execução em milissegundos.
     */
    public long getExecutionTime() {

        return executionTime;
    }

    public long getExecutionTimeMillis() {

        return executionTime;
    }

    public double getTotalCost() {

        return totalCost;
    }

    public boolean isFound() {

        return found;
    }

    /**
     * Quantidade de células presentes no caminho,
     * incluindo início e destino.
     */
    public int getPathSize() {

        return path.size();
    }

    /**
     * Quantidade de movimentos realizados.
     */
    public int getPathLength() {

        return Math.max(
                0,
                path.size() - 1
        );
    }
}