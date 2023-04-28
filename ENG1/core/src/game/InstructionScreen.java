package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import game.ScreenController.ScreenID;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

/**
 * The {@link InstructionScreen}, which provides the
 * player with instructions on how to play the game.
 */
public class InstructionScreen extends ScreenAdapter {

    private ScreenID prevScreenID = ScreenID.MENU;
    private OrthographicCamera camera;
    private ScreenController screenController;
    private FitViewport viewport;
    private Stage stage;
    private SpriteBatch batch;

    /**
     * The constructor for the {@link PauseScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public InstructionScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);
        stage = new Stage(viewport, batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Instructions", font);
        gameOverLabel.setFontScale(2);
        table.add(gameOverLabel).expandX();

        table.row();

        String[] instructions = new String[] {
                String.format("MOVE using WASD keys/Arrow keys"),
                "",
                String.format("SWAP COOKS using %s.",Interactions.getKeyString(InputKey.InputTypes.COOK_SWAP)),
                "",
                String.format("USE A STATION using %s while facing it.", Interactions.getKeyString(InputKey.InputTypes.USE)),
                "",
                String.format("PICKUP ITEMS using %s.", Interactions.getKeyString(InputKey.InputTypes.PICK_UP)),
                "Pantries have an infinite number of resources, and the Cook has no carry limit.",
                "",
                String.format("PUT DOWN ITEMS using %s.", Interactions.getKeyString(InputKey.InputTypes.PUT_DOWN)),
                "Counters can do this for any item, but preparation stations require valid ingredients.",
                "",
                String.format("INGREDIENT PREP requires using (%s) on the station until bar is full.", Interactions.getKeyString(InputKey.InputTypes.USE)),
                // // I feel the below are unnecessary and the player can figure this out themselves.
                // "",
                // "Buns are added to the stack by giving you the opposite of the highest bun,",
                // "This means if your highest bun is a bottom bun, then you'll get a top bun.",
                // "",
                "",
                String.format("TRASH ITEMS with the bin. (%s or %s)",
                        Interactions.getKeyString(InputKey.InputTypes.USE),
                        Interactions.getKeyString(InputKey.InputTypes.PUT_DOWN)),
                "",
                "Each customer has a range of different foods they can request from.",
                "You can give them their request by remaking the recipe shown on the right,",
                String.format("and then using (%s) the serving counter while holding it.",Interactions.getKeyString(InputKey.InputTypes.USE)),
                "",
                "Only one customer's order is shown at a time. At any time you may interact with any",
                String.format("customer to show their order on the right. (%s, %s or %s)",
                        Interactions.getKeyString(InputKey.InputTypes.USE),
                        Interactions.getKeyString(InputKey.InputTypes.PICK_UP),
                        Interactions.getKeyString(InputKey.InputTypes.PUT_DOWN)),
                "",
                "When power-ups appear, you can click them to gain benefits",
                "",
                "To make Jacket Potatoes and Pizza, just hold the ingredients shown in the recipe and you will create a single uncooked food from them.",
                "",
                "You can save your game in the pause menu and load the last saved game from the main menu. Only one saved game is supported.",
                "",
                String.format("PAUSE using %s.", Interactions.getKeyString(InputKey.InputTypes.PAUSE)),
                ""
        };

        for (String instruction : instructions) {
            Label instLabel = new Label(instruction, font);
            instLabel.setFontScale(0.9f);
            table.add(instLabel).expandX();
            table.row();
        }

        Label extraText = new Label("GO BACK, PRESS I", font);
        extraText.setFontScale(1.5F);
        table.add(extraText);

        stage.addActor(table);
    }

    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        // Check for input.
        Interactions.updateKeys();
        if (Interactions.isJustPressed(InputKey.InputTypes.INSTRUCTIONS)) {
            screenController.setScreen(prevScreenID);
        }
    }

    /**
     * The function used to render the {@link PauseScreen}.
     *
     * <br>Draws the {@link #stage} of the {@link PauseScreen},
     * which contains all the text as {@link Label}s.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        this.update(delta);
    }

    /**
     * Sets the variable {@link #prevScreenID} to the input,
     * which allows the {@link PauseScreen} to return the
     * player to the screen they opened it from.
     * @param scID The {@link ScreenController.ScreenID} of the previous {@link ScreenAdapter}.
     */
    public void setPrevScreenID(ScreenID scID) {
        prevScreenID = scID;
    }
}
