package br.edu.ifrn.pathfinding.ui;

import br.edu.ifrn.pathfinding.algorithm.AStarSearch;
import br.edu.ifrn.pathfinding.algorithm.GreedySearch;
import br.edu.ifrn.pathfinding.algorithm.SearchAlgorithm;
import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import br.edu.ifrn.pathfinding.util.Heuristic;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PathfindingApplication extends Application {

    private Grid grid;

    private GridView gridView;

    private ControlsView controlsView;

    @Override
    public void start(Stage stage) {

        grid = createGrid();

        gridView =
                new GridView(grid);

        controlsView =
                new ControlsView();

        gridView.setEditMode(
                GridView.EditMode.OBSTACLE
        );

        setupActions();

        BorderPane root =
                new BorderPane();

        root.getStyleClass().add(
                "root"
        );

        root.setCenter(
                gridView
        );

        root.setRight(
                controlsView
        );

        Scene scene =
                new Scene(
                        root,
                        1350,
                        1050
                );

        /*
         * Carrega nosso CSS.
         */
        var css =
                getClass()
                        .getResource(
                                "/styles.css"
                        );

        if (css == null) {

            throw new IllegalStateException(
                    "Arquivo styles.css não encontrado."
            );
        }

        scene.getStylesheets().add(
                css.toExternalForm()
        );

        stage.setTitle(
                "RouteQuest"
        );

        stage.setScene(
                scene
        );

        stage.setMaximized(
                true
        );

        stage.show();
    }

    private void setupActions() {

        controlsView
                .getObstacleModeButton()
                .setOnAction(
                        event ->
                                gridView.setEditMode(
                                        GridView.EditMode.OBSTACLE
                                )
                );

        controlsView
                .getStartModeButton()
                .setOnAction(
                        event ->
                                gridView.setEditMode(
                                        GridView.EditMode.START
                                )
                );

        controlsView
                .getGoalModeButton()
                .setOnAction(
                        event ->
                                gridView.setEditMode(
                                        GridView.EditMode.GOAL
                                )
                );

        controlsView
                .getExecuteButton()
                .setOnAction(
                        event ->
                                executeSearch()
                );

        controlsView
                .getClearButton()
                .setOnAction(
                        event ->
                                clear()
                );
    }

    private void executeSearch() {

        if (gridView.isAnimating()) {
            return;
        }

        controlsView.setControlsDisabled(
                true
        );

        SearchAlgorithm algorithm;

        String algorithmName;

        if (controlsView.isGreedySelected()) {

            algorithm =
                    new GreedySearch();

            algorithmName =
                    "Greedy Best-First Search";

        } else {

            algorithm =
                    new AStarSearch();

            algorithmName =
                    "A*";
        }

        SearchResult result =
                algorithm.search(
                        grid
                );

        int minimumCost =
                (int) Heuristic.octileDistance(
                        grid.getStart(),
                        grid.getGoal()
                );

        controlsView.showResult(
                algorithmName,
                result.getExecutionTimeMillis(),
                result.getExploredNodes(),
                grid.getRows()
                        * grid.getColumns(),
                minimumCost,
                result.getTotalCost(),
                result.getPathLength(),
                result.isFound()
        );

        gridView.animateResult(
                result,
                () ->
                        handleSearchFinished(
                                result
                        )
        );
    }

    private void handleSearchFinished(
            SearchResult result
    ) {

        Platform.runLater(
                () -> {

                    if (result.isFound()) {

                        controlsView.setControlsDisabled(
                                false
                        );

                        controlsView.setStatus(
                                "Status: caminho concluído"
                        );

                        return;
                    }

                    controlsView.setControlsDisabled(
                            false
                    );

                    gridView.clearSearchVisual();

                    controlsView.setStatus(
                            "Status: caminho não encontrado"
                    );

                    showNoPathMessage();
                }
        );
    }

    private void showNoPathMessage() {

        Alert alert =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alert.setTitle(
                "Pathfinding"
        );

        alert.setHeaderText(
                "Caminho não encontrado"
        );

        alert.setContentText(
                "O algoritmo explorou todas as "
                        + "possibilidades disponíveis, "
                        + "mas não encontrou uma rota "
                        + "entre A e B."
                        + "\n\n"
                        + "Você pode alterar o mapa "
                        + "e executar novamente."
        );

        alert.show();
    }

    private void clear() {

        gridView.stopAnimation();

        grid.clearObstacles();

        gridView.clearResult();

        controlsView.clearMetrics();

        controlsView.setControlsDisabled(
                false
        );
    }

    private Grid createGrid() {

        Grid grid =
                new Grid(
                        20,
                        20
                );

        grid.setStart(
                new Position(
                        0,
                        0
                )
        );

        grid.setGoal(
                new Position(
                        19,
                        19
                )
        );

        return grid;
    }

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}