package br.edu.ifrn.pathfinding.model;

public final class SearchStep {

    private final Position position;
    private final double g;
    private final double h;
    private final double f;

    public SearchStep(Position position, double g, double h, double f) {
        this.position = position;
        this.g = g;
        this.h = h;
        this.f = f;
    }

    public Position getPosition() {
        return position;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    @Override
    public String toString() {
        return "SearchStep{" +
                "position=" + position +
                ", g=" + g +
                ", h=" + h +
                ", f=" + f +
                '}';
    }
}