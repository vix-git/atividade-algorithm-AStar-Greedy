package br.edu.ifrn.pathfinding.algorithm;

import br.edu.ifrn.pathfinding.model.Node;
import br.edu.ifrn.pathfinding.model.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class SearchSupport {
    private SearchSupport() { }

    static List<Position> reconstructPath(Node goal) {
        List<Position> path = new ArrayList<>();
        for (Node current = goal; current != null; current = current.getParent()) {
            path.add(current.getPosition());
        }
        Collections.reverse(path);
        return path;
    }
}
