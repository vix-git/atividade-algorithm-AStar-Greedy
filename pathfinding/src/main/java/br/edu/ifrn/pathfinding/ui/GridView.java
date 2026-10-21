package br.edu.ifrn.pathfinding.ui;

import br.edu.ifrn.pathfinding.model.Grid;
import br.edu.ifrn.pathfinding.model.Position;
import br.edu.ifrn.pathfinding.model.SearchResult;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridView extends StackPane {

    public enum EditMode {
        OBSTACLE,
        START,
        GOAL
    }

    private static final double CELL_SIZE = 50;

    private static final double EXPLORATION_STEP_MS = 45;

    private static final double PATH_STEP_MS = 55;

    private static final double PLAYER_STEP_MS = 180;

    private static final double SPRITE_SIZE = 46;

    /*
     * A sua GIF possui aproximadamente:
     *
     * 6 frames × 300 ms = 1800 ms
     *
     * Vamos deixá-la visível por esse tempo.
     */
    private static final double BATTLE_DURATION_MS = 1800;

    /*
     * Tamanho da animação de batalha na tela.
     */
    private static final double BATTLE_WIDTH = 260;

    private static final double BATTLE_HEIGHT = 210;

    private final Canvas canvas;

    private final Grid grid;

    private final int rows;

    private final int columns;

    /*
     * Sprites do mapa.
     */
    private final Image floorSprite;

    private final Image obstacleSprite;

    private final Image goalSprite;

    private final List<Image> playerSprites;

    /*
     * Animação de batalha.
     */
    private final ImageView battleImageView;

    /*
     * Pequeno indicador textual enquanto a batalha acontece.
     */
    private final Label battleLabel;

    /*
     * Células exploradas.
     */
    private final Set<Position> animatedExplored =
            new HashSet<>();

    /*
     * Células do caminho.
     */
    private final Set<Position> animatedPath =
            new HashSet<>();

    /*
     * Jogador.
     */
    private Position playerPosition;

    private int playerFrameIndex;

    /*
     * Animação atual.
     */
    private Timeline animation;

    private PauseTransition pause;

    private Timeline battleTimer;

    private boolean animating;

    private boolean battleVisible;

    private EditMode editMode =
            EditMode.OBSTACLE;

    public GridView(Grid grid) {

        this.grid = grid;

        this.rows =
                grid.getRows();

        this.columns =
                grid.getColumns();

        /*
         * =====================================================
         * CANVAS
         * =====================================================
         */

        canvas = new Canvas(
                columns * CELL_SIZE,
                rows * CELL_SIZE
        );

        /*
         * O Canvas é quem recebe os cliques.
         */
        setupMouseInteraction();

        /*
         * =====================================================
         * SPRITES
         * =====================================================
         */

        floorSprite =
                loadImage(
                        "/assets/floor/piso.png"
                );

        obstacleSprite =
                loadImage(
                        "/assets/obstacle/obstaculo.png"
                );

        goalSprite =
                loadImage(
                        "/assets/goal/orcs-removebg-preview.png"
                );

        playerSprites =
                List.of(
                        loadImage(
                                "/assets/player/sprit1.2-removebg-preview.png"
                        ),
                        loadImage(
                                "/assets/player/sprit2.2-removebg-preview.png"
                        ),
                        loadImage(
                                "/assets/player/sprit3.2-removebg-preview.png"
                        ),
                        loadImage(
                                "/assets/player/sprit4.2-removebg-preview.png"
                        )
                );

        /*
         * =====================================================
         * ANIMAÇÃO DE BATALHA
         * =====================================================
         */

        Image battleImage =
                loadImage(
                        "/assets/combat/animacao.gif"
                );

        battleImageView =
                new ImageView(
                        battleImage
                );

        battleImageView.setFitWidth(
                BATTLE_WIDTH
        );

        battleImageView.setFitHeight(
                BATTLE_HEIGHT
        );

        battleImageView.setPreserveRatio(
                true
        );

        battleImageView.setSmooth(
                false
        );

        /*
         * Não queremos que a GIF receba cliques.
         */
        battleImageView.setMouseTransparent(
                true
        );

        battleImageView.setManaged(
                false
        );

        battleImageView.setVisible(
                false
        );

        /*
         * =====================================================
         * TEXTO DA BATALHA
         * =====================================================
         */

        battleLabel =
                new Label(
                        "BATALHA!"
                );

        battleLabel.setStyle(
                """
                -fx-text-fill: white;
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                -fx-effect: dropshadow(
                    gaussian,
                    black,
                    6,
                    0.8,
                    2,
                    2
                );
                """
        );

        battleLabel.setMouseTransparent(
                true
        );

        battleLabel.setVisible(
                false
        );

        battleLabel.setManaged(
                false
        );

        /*
         * =====================================================
         * LAYOUT
         * =====================================================
         */

        getChildren().addAll(
                canvas,
                battleImageView,
                battleLabel
        );

        setAlignment(
                Pos.CENTER
        );

        /*
         * =====================================================
         * JOGADOR
         * =====================================================
         */

        playerPosition =
                grid.getStart();

        playerFrameIndex = 0;

        draw();
    }

    /*
     * =========================================================
     * TAMANHO
     * =========================================================
     */

    @Override
    protected void layoutChildren() {

        super.layoutChildren();

        /*
         * Mantém o Canvas no canto superior esquerdo.
         */
        canvas.relocate(
                0,
                0
        );

        /*
         * Posiciona a animação de batalha
         * próximo ao Orc.
         */
        positionBattleAnimation();
    }

    private void positionBattleAnimation() {

        if (battleImageView == null) {
            return;
        }

        double canvasWidth =
                canvas.getWidth();

        double canvasHeight =
                canvas.getHeight();

        double goalX =
                grid.getGoal().getX()
                        * CELL_SIZE
                        + CELL_SIZE / 2;

        double goalY =
                grid.getGoal().getY()
                        * CELL_SIZE
                        + CELL_SIZE / 2;

        double x =
                goalX
                        - BATTLE_WIDTH / 2;

        double y =
                goalY
                        - BATTLE_HEIGHT / 2;

        /*
         * Não deixa a animação sair do mapa.
         */
        x = Math.max(
                0,
                Math.min(
                        x,
                        canvasWidth
                                - BATTLE_WIDTH
                )
        );

        y = Math.max(
                0,
                Math.min(
                        y,
                        canvasHeight
                                - BATTLE_HEIGHT
                )
        );

        battleImageView.relocate(
                x,
                y
        );

        /*
         * Texto fica acima da animação.
         */
        battleLabel.relocate(
                x + 80,
                Math.max(
                        5,
                        y - 35
                )
        );
    }

    /*
     * =========================================================
     * MODO DE EDIÇÃO
     * =========================================================
     */

    public void setEditMode(
            EditMode editMode
    ) {

        if (editMode == null) {
            return;
        }

        this.editMode =
                editMode;
    }

    public EditMode getEditMode() {

        return editMode;
    }

    /*
     * =========================================================
     * MOUSE
     * =========================================================
     */

    private void setupMouseInteraction() {

        canvas.setOnMouseClicked(
                event -> {

                    if (animating) {
                        return;
                    }

                    Position position =
                            getPositionFromMouse(
                                    event.getX(),
                                    event.getY()
                            );

                    if (position == null) {
                        return;
                    }

                    /*
                     * Botão direito:
                     * remove obstáculo.
                     */
                    if (
                            event.getButton()
                                    == MouseButton.SECONDARY
                    ) {

                        grid.removeObstacle(
                                position
                        );

                        clearSearchVisual();

                        draw();

                        return;
                    }

                    if (
                            event.getButton()
                                    != MouseButton.PRIMARY
                    ) {

                        return;
                    }

                    switch (editMode) {

                        case OBSTACLE ->
                                toggleObstacle(
                                        position
                                );

                        case START ->
                                setStart(
                                        position
                                );

                        case GOAL ->
                                setGoal(
                                        position
                                );
                    }

                    draw();
                }
        );
    }

    private void toggleObstacle(
            Position position
    ) {

        if (
                position.equals(
                        grid.getStart()
                )
        ) {
            return;
        }

        if (
                position.equals(
                        grid.getGoal()
                )
        ) {
            return;
        }

        if (
                grid.isObstacle(
                        position
                )
        ) {

            grid.removeObstacle(
                    position
            );

        } else {

            grid.addObstacle(
                    position
            );
        }

        clearSearchVisual();
    }

    private void setStart(
            Position position
    ) {

        if (
                position.equals(
                        grid.getGoal()
                )
        ) {
            return;
        }

        grid.setStart(
                position
        );

        playerPosition =
                position;

        clearSearchVisual();
    }

    private void setGoal(
            Position position
    ) {

        if (
                position.equals(
                        grid.getStart()
                )
        ) {
            return;
        }

        grid.setGoal(
                position
        );

        clearSearchVisual();
    }

    private Position getPositionFromMouse(
            double mouseX,
            double mouseY
    ) {

        int x =
                (int)
                        (
                                mouseX
                                        / CELL_SIZE
                        );

        int y =
                (int)
                        (
                                mouseY
                                        / CELL_SIZE
                        );

        Position position =
                new Position(
                        x,
                        y
                );

        if (
                !grid.isInside(
                        position
                )
        ) {
            return null;
        }

        return position;
    }

    /*
     * =========================================================
     * CARREGAMENTO DE IMAGEM
     * =========================================================
     */

    private Image loadImage(
            String path
    ) {

        var resource =
                getClass().getResource(
                        path
                );

        if (resource == null) {

            throw new IllegalStateException(
                    "Imagem não encontrada: "
                            + path
            );
        }

        return new Image(
                resource.toExternalForm()
        );
    }

    /*
     * =========================================================
     * ANIMAÇÃO PRINCIPAL
     * =========================================================
     */

    public void animateResult(
            SearchResult result,
            Runnable onFinished
    ) {

        stopAnimation();

        animatedExplored.clear();

        animatedPath.clear();

        hideBattleAnimation();

        playerPosition =
                grid.getStart();

        playerFrameIndex = 0;

        animating = true;

        draw();

        List<Position> explored =
                result.getExploredPositions();

        List<Position> path =
                result.getPath();

        /*
         * Primeiro:
         * exploração.
         */
        animateExploration(
                explored,
                () -> {

                    /*
                     * Sem caminho.
                     */
                    if (!result.isFound()) {

                        pause =
                                new PauseTransition(
                                        Duration.millis(
                                                500
                                        )
                                );

                        pause.setOnFinished(
                                event -> {

                                    animating =
                                            false;

                                    draw();

                                    if (
                                            onFinished
                                                    != null
                                    ) {

                                        onFinished.run();
                                    }
                                }
                        );

                        pause.play();

                        return;
                    }

                    /*
                     * Depois:
                     * caminho.
                     */
                    animatePath(
                            path,
                            () -> {

                                /*
                                 * Finalmente:
                                 * guerreiro anda.
                                 */
                                animatePlayer(
                                        path,
                                        () -> {

                                            /*
                                             * Quando o
                                             * guerreiro chega
                                             * ao Orc:
                                             *
                                             * BATALHA!
                                             */
                                            playBattleAnimation(
                                                    onFinished
                                            );
                                        }
                                );
                            }
                    );
                }
        );
    }

    /*
     * =========================================================
     * EXPLORAÇÃO
     * =========================================================
     */

    private void animateExploration(
            List<Position> explored,
            Runnable onFinished
    ) {

        if (explored.isEmpty()) {

            if (onFinished != null) {
                onFinished.run();
            }

            return;
        }

        animation =
                new Timeline();

        for (
                int i = 0;
                i < explored.size();
                i++
        ) {

            final Position position =
                    explored.get(i);

            animation
                    .getKeyFrames()
                    .add(
                            new KeyFrame(
                                    Duration.millis(
                                            i
                                                    * EXPLORATION_STEP_MS
                                    ),
                                    event -> {

                                        animatedExplored.add(
                                                position
                                        );

                                        draw();
                                    }
                            )
                    );
        }

        animation.setOnFinished(
                event -> {

                    animation = null;

                    if (onFinished != null) {

                        onFinished.run();
                    }
                }
        );

        animation.play();
    }

    /*
     * =========================================================
     * CAMINHO
     * =========================================================
     */

    private void animatePath(
            List<Position> path,
            Runnable onFinished
    ) {

        if (path.isEmpty()) {

            if (onFinished != null) {
                onFinished.run();
            }

            return;
        }

        animation =
                new Timeline();

        for (
                int i = 0;
                i < path.size();
                i++
        ) {

            final Position position =
                    path.get(i);

            animation
                    .getKeyFrames()
                    .add(
                            new KeyFrame(
                                    Duration.millis(
                                            i
                                                    * PATH_STEP_MS
                                    ),
                                    event -> {

                                        animatedPath.add(
                                                position
                                        );

                                        draw();
                                    }
                            )
                    );
        }

        animation.setOnFinished(
                event -> {

                    animation = null;

                    if (onFinished != null) {

                        onFinished.run();
                    }
                }
        );

        animation.play();
    }

    /*
     * =========================================================
     * GUERREIRO
     * =========================================================
     */

    private void animatePlayer(
            List<Position> path,
            Runnable onFinished
    ) {

        if (path.isEmpty()) {

            if (onFinished != null) {
                onFinished.run();
            }

            return;
        }

        animation =
                new Timeline();

        for (
                int i = 0;
                i < path.size();
                i++
        ) {

            final Position position =
                    path.get(i);

            final int frame =
                    i
                            % playerSprites.size();

            animation
                    .getKeyFrames()
                    .add(
                            new KeyFrame(
                                    Duration.millis(
                                            i
                                                    * PLAYER_STEP_MS
                                    ),
                                    event -> {

                                        playerPosition =
                                                position;

                                        playerFrameIndex =
                                                frame;

                                        draw();
                                    }
                            )
                    );
        }

        animation.setOnFinished(
                event -> {

                    animation = null;

                    playerPosition =
                            grid.getGoal();

                    playerFrameIndex = 0;

                    draw();

                    if (onFinished != null) {

                        onFinished.run();
                    }
                }
        );

        animation.play();
    }

    /*
     * =========================================================
     * BATALHA
     * =========================================================
     */

    private void playBattleAnimation(
            Runnable onFinished
    ) {

        /*
         * Garante que o guerreiro e o Orc
         * normais desapareçam durante a batalha.
         */
        battleVisible = true;

        battleImageView.setVisible(
                true
        );

        battleLabel.setVisible(
                true
        );

        positionBattleAnimation();

        /*
         * Reposiciona para garantir que fique
         * exatamente perto do destino.
         */
        battleImageView.toFront();

        battleLabel.toFront();

        /*
         * Durante a batalha mantemos os controles
         * bloqueados.
         */
        animating = true;

        draw();

        /*
         * A GIF possui 6 frames de ~300 ms.
         *
         * Depois de aproximadamente 1,8 segundo
         * encerramos a batalha.
         */
        battleTimer =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(
                                        BATTLE_DURATION_MS
                                ),
                                event -> {

                                    hideBattleAnimation();

                                    animating = false;

                                    draw();

                                    if (
                                            onFinished
                                                    != null
                                    ) {

                                        onFinished.run();
                                    }
                                }
                        )
                );

        battleTimer.play();
    }

    private void hideBattleAnimation() {

        battleVisible = false;

        battleImageView.setVisible(
                false
        );

        battleLabel.setVisible(
                false
        );

        if (battleTimer != null) {

            battleTimer.stop();

            battleTimer = null;
        }
    }

    /*
     * =========================================================
     * CONTROLE
     * =========================================================
     */

    public void stopAnimation() {

        if (animation != null) {

            animation.stop();

            animation = null;
        }

        if (pause != null) {

            pause.stop();

            pause = null;
        }

        hideBattleAnimation();

        animating = false;

        playerPosition =
                grid.getStart();

        playerFrameIndex = 0;
    }

    public boolean isAnimating() {

        return animating;
    }

    public void clearResult() {

        stopAnimation();

        clearSearchVisual();

        playerPosition =
                grid.getStart();

        playerFrameIndex = 0;

        draw();
    }

    public void clearSearchVisual() {

        animatedExplored.clear();

        animatedPath.clear();

        hideBattleAnimation();

        playerPosition =
                grid.getStart();

        playerFrameIndex = 0;

        draw();
    }

    /*
     * =========================================================
     * DESENHO
     * =========================================================
     */

    public void draw() {

        GraphicsContext graphics =
                canvas.getGraphicsContext2D();

        graphics.clearRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
        );

        for (
                int y = 0;
                y < rows;
                y++
        ) {

            for (
                    int x = 0;
                    x < columns;
                    x++
            ) {

                Position position =
                        new Position(
                                x,
                                y
                        );

                drawCell(
                        graphics,
                        position
                );
            }
        }

        /*
         * Não desenha o guerreiro normal durante a
         * batalha porque a própria GIF já contém
         * o guerreiro e o Orc.
         */
        if (!battleVisible) {

            drawPlayer(
                    graphics
            );
        }
    }

    private void drawCell(
            GraphicsContext graphics,
            Position position
    ) {

        double pixelX =
                position.getX()
                        * CELL_SIZE;

        double pixelY =
                position.getY()
                        * CELL_SIZE;

        /*
         * Piso.
         */
        graphics.drawImage(
                floorSprite,
                pixelX,
                pixelY,
                CELL_SIZE,
                CELL_SIZE
        );

        /*
         * Exploração.
         */
        if (
                animatedExplored
                        .contains(
                                position
                        )
        ) {

            graphics.setFill(
                    Color.rgb(
                            80,
                            170,
                            230,
                            0.45
                    )
            );

            graphics.fillRect(
                    pixelX,
                    pixelY,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        /*
         * Caminho.
         */
        if (
                animatedPath
                        .contains(
                                position
                        )
        ) {

            graphics.setFill(
                    Color.rgb(
                            80,
                            220,
                            100,
                            0.50
                    )
            );

            graphics.fillRect(
                    pixelX,
                    pixelY,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        /*
         * Obstáculo.
         */
        if (
                grid.isObstacle(
                        position
                )
        ) {

            graphics.drawImage(
                    obstacleSprite,
                    pixelX,
                    pixelY,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        /*
         * Borda.
         */
        graphics.setStroke(
                Color.rgb(
                        80,
                        80,
                        80,
                        0.50
                )
        );

        graphics.strokeRect(
                pixelX,
                pixelY,
                CELL_SIZE,
                CELL_SIZE
        );

        /*
         * Orc normal.
         *
         * Durante a batalha não mostramos o Orc
         * individual porque a GIF já possui o Orc.
         */
        if (
                !battleVisible
                        && position.equals(
                        grid.getGoal()
                )
        ) {

            drawSprite(
                    graphics,
                    goalSprite,
                    pixelX,
                    pixelY
            );
        }
    }

    private void drawPlayer(
            GraphicsContext graphics
    ) {

        if (playerPosition == null) {
            return;
        }

        Image sprite =
                playerSprites.get(
                        playerFrameIndex
                );

        double pixelX =
                playerPosition.getX()
                        * CELL_SIZE;

        double pixelY =
                playerPosition.getY()
                        * CELL_SIZE;

        drawSprite(
                graphics,
                sprite,
                pixelX,
                pixelY
        );
    }

    private void drawSprite(
            GraphicsContext graphics,
            Image image,
            double cellX,
            double cellY
    ) {

        double x =
                cellX
                        + (
                        CELL_SIZE
                                - SPRITE_SIZE
                )
                        / 2;

        double y =
                cellY
                        + (
                        CELL_SIZE
                                - SPRITE_SIZE
                )
                        / 2;

        graphics.drawImage(
                image,
                x,
                y,
                SPRITE_SIZE,
                SPRITE_SIZE
        );
    }
}