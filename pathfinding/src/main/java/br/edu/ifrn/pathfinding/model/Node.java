package br.edu.ifrn.pathfinding.model;

/** A position in the search frontier, including the route used to reach it. */
public final class Node {
    private final Position position;
    private final Node parent;
    private final double g;
    private final double h;
    private final double f;

    private Node(Position position, Node parent, double g, double h, double f) {
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = f;
    }

    /** Greedy Best-First Search prioritizes only the heuristic: f = h. */
    public static Node greedy(Position position, Node parent, double g, double h) {
        return new Node(position, parent, g, h, h);
    }

    /** A* prioritizes accumulated cost plus heuristic: f = g + h. */
    public static Node aStar(Position position, Node parent, double g, double h) {
        return new Node(position, parent, g, h, g + h);
    }

    public Position getPosition() { return position; }
    public Node getParent() { return parent; }
    public double getG() { return g; }
    public double getH() { return h; }
    public double getF() { return f; }
}
