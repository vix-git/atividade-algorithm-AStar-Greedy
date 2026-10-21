package br.edu.ifrn.pathfinding.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ControlsView extends VBox {

    private final RadioButton greedyButton;
    private final RadioButton aStarButton;

    private final RadioButton obstacleModeButton;
    private final RadioButton startModeButton;
    private final RadioButton goalModeButton;

    private final Button executeButton;
    private final Button clearButton;

    private final Label statusLabel;
    private final Label algorithmLabel;
    private final Label timeLabel;
    private final Label exploredLabel;
    private final Label exploredPercentageLabel;
    private final Label minimumCostLabel;
    private final Label costLabel;
    private final Label pathLengthLabel;
    private final Label differenceLabel;

    public ControlsView() {

        setSpacing(8);

        getStyleClass().add(
                "controls-panel"
        );

        /*
         * =====================================================
         * CABEÇALHO
         * =====================================================
         */

        Label title =
                new Label("RouteQuest");

        title.getStyleClass().add(
                "app-title"
        );

        Label subtitle =
                new Label(
                        "Simulador de busca de rotas"
                );

        subtitle.getStyleClass().add(
                "app-subtitle"
        );

        getChildren().addAll(
                title,
                subtitle,
                new Separator()
        );

        /*
         * =====================================================
         * ALGORITMO
         * =====================================================
         */

        VBox algorithmBox =
                new VBox(5);

        algorithmBox.getStyleClass().add(
                "section-box"
        );

        Label algorithmTitle =
                createSectionTitle(
                        "ALGORITMO"
                );

        greedyButton =
                new RadioButton(
                        "Greedy Best-First Search"
                );

        aStarButton =
                new RadioButton(
                        "A*"
                );

        ToggleGroup algorithmGroup =
                new ToggleGroup();

        greedyButton.setToggleGroup(
                algorithmGroup
        );

        aStarButton.setToggleGroup(
                algorithmGroup
        );

        greedyButton.setSelected(
                true
        );

        algorithmBox.getChildren().addAll(
                algorithmTitle,
                greedyButton,
                aStarButton
        );

        /*
         * =====================================================
         * EDIÇÃO
         * =====================================================
         */

        VBox editBox =
                new VBox(5);

        editBox.getStyleClass().add(
                "section-box"
        );

        Label editTitle =
                createSectionTitle(
                        "EDITAR MAPA"
                );

        obstacleModeButton =
                new RadioButton(
                        "Colocar obstáculos"
                );

        startModeButton =
                new RadioButton(
                        "Definir ponto A"
                );

        goalModeButton =
                new RadioButton(
                        "Definir ponto B"
                );

        ToggleGroup editGroup =
                new ToggleGroup();

        obstacleModeButton.setToggleGroup(
                editGroup
        );

        startModeButton.setToggleGroup(
                editGroup
        );

        goalModeButton.setToggleGroup(
                editGroup
        );

        obstacleModeButton.setSelected(
                true
        );

        editBox.getChildren().addAll(
                editTitle,
                obstacleModeButton,
                startModeButton,
                goalModeButton
        );

        /*
         * =====================================================
         * AÇÕES
         * =====================================================
         */

        executeButton =
                new Button(
                        "EXECUTAR BUSCA"
                );

        executeButton.getStyleClass().addAll(
                "action-button",
                "execute-button"
        );

        clearButton =
                new Button(
                        "LIMPAR MAPA"
                );

        clearButton.getStyleClass().add(
                "action-button"
        );

        executeButton.setMaxWidth(
                Double.MAX_VALUE
        );

        clearButton.setMaxWidth(
                Double.MAX_VALUE
        );

        VBox actionBox =
                new VBox(
                        8,
                        executeButton,
                        clearButton
                );

        /*
         * =====================================================
         * MÉTRICAS
         * =====================================================
         */

        VBox metricsBox =
                new VBox(2);

        metricsBox.getStyleClass().add(
                "section-box"
        );

        Label metricsTitle =
                createSectionTitle(
                        "MÉTRICAS"
                );

        statusLabel =
                createMetricLabel(
                        "Status: pronto"
                );

        statusLabel.getStyleClass().remove(
                "metric-label"
        );

        statusLabel.getStyleClass().add(
                "status-label"
        );

        algorithmLabel =
                createMetricLabel(
                        "Algoritmo: --"
                );

        timeLabel =
                createMetricLabel(
                        "Tempo: --"
                );

        exploredLabel =
                createMetricLabel(
                        "Nós explorados: --"
                );

        exploredPercentageLabel =
                createMetricLabel(
                        "Mapa explorado: --"
                );

        minimumCostLabel =
                createMetricLabel(
                        "Custo mínimo teórico: --"
                );

        costLabel =
                createMetricLabel(
                        "Custo do caminho: --"
                );

        pathLengthLabel =
                createMetricLabel(
                        "Movimentos: --"
                );

        differenceLabel =
                createMetricLabel(
                        "Desvio do mínimo: --"
                );

        metricsBox.getChildren().addAll(
                metricsTitle,
                statusLabel,
                algorithmLabel,
                timeLabel,
                exploredLabel,
                exploredPercentageLabel,
                minimumCostLabel,
                costLabel,
                pathLengthLabel,
                differenceLabel
        );

        /*
         * =====================================================
         * LEGENDA
         * =====================================================
         */

        VBox legendBox =
                createLegendBox();

        /*
         * =====================================================
         * ADICIONA TUDO
         * =====================================================
         */

        getChildren().addAll(
                algorithmBox,
                editBox,
                actionBox,
                metricsBox,
                legendBox
        );

        VBox.setVgrow(
                legendBox,
                Priority.NEVER
        );
    }

    private Label createSectionTitle(
            String text
    ) {

        Label label =
                new Label(text);

        label.getStyleClass().add(
                "section-title"
        );

        return label;
    }

    private Label createMetricLabel(
            String text
    ) {

        Label label =
                new Label(text);

        label.getStyleClass().add(
                "metric-label"
        );

        label.setWrapText(
                true
        );

        return label;
    }

    private VBox createLegendBox() {

        VBox box =
                new VBox(6);

        box.getStyleClass().add(
                "section-box"
        );

        Label title =
                createSectionTitle(
                        "LEGENDA"
                );

        box.getChildren().add(
                title
        );

        box.getChildren().add(
                createLegendRow(
                        "Explorado",
                        "legend-color-blue"
                )
        );

        box.getChildren().add(
                createLegendRow(
                        "Caminho encontrado",
                        "legend-color-green"
                )
        );

        box.getChildren().add(
                createLegendRow(
                        "Obstáculo",
                        "legend-color-gray"
                )
        );

        box.getChildren().add(
                createTextLegendRow(
                        "A",
                        "Ponto inicial"
                )
        );

        box.getChildren().add(
                createTextLegendRow(
                        "B",
                        "Destino"
                )
        );

        return box;
    }

    private HBox createLegendRow(
            String text,
            String styleClass
    ) {

        Region color =
                new Region();

        color.setMinSize(
                14,
                14
        );

        color.setMaxSize(
                14,
                14
        );

        color.getStyleClass().add(
                styleClass
        );

        Label label =
                new Label(text);

        label.getStyleClass().add(
                "legend-label"
        );

        HBox row =
                new HBox(
                        8,
                        color,
                        label
                );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        return row;
    }

    private HBox createTextLegendRow(
            String symbol,
            String text
    ) {

        Label symbolLabel =
                new Label(symbol);

        symbolLabel.setMinWidth(
                14
        );

        symbolLabel.getStyleClass().add(
                "legend-label"
        );

        Label textLabel =
                new Label(text);

        textLabel.getStyleClass().add(
                "legend-label"
        );

        HBox row =
                new HBox(
                        8,
                        symbolLabel,
                        textLabel
                );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        return row;
    }

    /*
     * =========================================================
     * ALGORITMO
     * =========================================================
     */

    public boolean isGreedySelected() {

        return greedyButton.isSelected();
    }

    /*
     * =========================================================
     * EDIÇÃO
     * =========================================================
     */

    public GridView.EditMode getEditMode() {

        if (startModeButton.isSelected()) {

            return GridView.EditMode.START;
        }

        if (goalModeButton.isSelected()) {

            return GridView.EditMode.GOAL;
        }

        return GridView.EditMode.OBSTACLE;
    }

    public RadioButton getObstacleModeButton() {

        return obstacleModeButton;
    }

    public RadioButton getStartModeButton() {

        return startModeButton;
    }

    public RadioButton getGoalModeButton() {

        return goalModeButton;
    }

    /*
     * =========================================================
     * BOTÕES
     * =========================================================
     */

    public Button getExecuteButton() {

        return executeButton;
    }

    public Button getClearButton() {

        return clearButton;
    }

    public void setControlsDisabled(
            boolean disabled
    ) {

        greedyButton.setDisable(
                disabled
        );

        aStarButton.setDisable(
                disabled
        );

        obstacleModeButton.setDisable(
                disabled
        );

        startModeButton.setDisable(
                disabled
        );

        goalModeButton.setDisable(
                disabled
        );

        executeButton.setDisable(
                disabled
        );

        clearButton.setDisable(
                disabled
        );
    }

    /*
     * =========================================================
     * MÉTRICAS
     * =========================================================
     */

    public void showResult(
            String algorithm,
            long executionTime,
            int exploredNodes,
            int totalCells,
            int minimumCost,
            double totalCost,
            int pathLength,
            boolean found
    ) {

        algorithmLabel.setText(
                "Algoritmo: " + algorithm
        );

        timeLabel.setText(
                "Tempo: " + executionTime + " ms"
        );

        exploredLabel.setText(
                "Nós explorados: "
                        + exploredNodes
        );

        double percentage = 0;

        if (totalCells > 0) {

            percentage =
                    exploredNodes
                            * 100.0
                            / totalCells;
        }

        exploredPercentageLabel.setText(
                String.format(
                        "Mapa explorado: %.2f%%",
                        percentage
                )
        );

        minimumCostLabel.setText(
                "Custo mínimo teórico: "
                        + minimumCost
        );

        if (found) {

            costLabel.setText(
                    "Custo do caminho: "
                            + String.format(
                            "%.0f",
                            totalCost
                    )
            );

            pathLengthLabel.setText(
                    "Movimentos: "
                            + pathLength
            );

            int difference =
                    (int) totalCost
                            - minimumCost;

            differenceLabel.setText(
                    "Desvio do mínimo: "
                            + difference
            );

            statusLabel.setText(
                    "Status: caminho encontrado"
            );

        } else {

            costLabel.setText(
                    "Custo do caminho: --"
            );

            pathLengthLabel.setText(
                    "Movimentos: --"
            );

            differenceLabel.setText(
                    "Desvio do mínimo: --"
            );

            statusLabel.setText(
                    "Status: caminho não encontrado"
            );
        }
    }

    public void clearMetrics() {

        statusLabel.setText(
                "Status: pronto"
        );

        algorithmLabel.setText(
                "Algoritmo: --"
        );

        timeLabel.setText(
                "Tempo: --"
        );

        exploredLabel.setText(
                "Nós explorados: --"
        );

        exploredPercentageLabel.setText(
                "Mapa explorado: --"
        );

        minimumCostLabel.setText(
                "Custo mínimo teórico: --"
        );

        costLabel.setText(
                "Custo do caminho: --"
        );

        pathLengthLabel.setText(
                "Movimentos: --"
        );

        differenceLabel.setText(
                "Desvio do mínimo: --"
        );
    }

    public void setStatus(
            String status
    ) {

        statusLabel.setText(
                status
        );
    }
}