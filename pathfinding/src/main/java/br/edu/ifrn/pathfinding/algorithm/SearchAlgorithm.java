package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.SearchResult;

/** Common contract used by the terminal and JavaFX views. */
public interface SearchAlgorithm {
    SearchResult search(Grid grid);
}
