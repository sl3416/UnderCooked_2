package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import customers.Customer;
import food.FoodItem;
import food.FoodStack;
import food.Recipe;
import game.GameScreen;
import game.GameSprites;
import game.PowerupStatic;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

import static game.GameScreen.repPoints;

// import java.awt.*;

/** Responsible for displaying information above the gameplay GameScreen. */
public class GameHud extends Hud {
    /** The label with the current amount of time played. */
    Label timeLabel;
    /** The label with the number of {@link Customer}s left to serve.  */
    Label CustomerLabel;
    Label CustomerScore;
    Label playerMoney;

    Label repLabel;
    ImageButton powerup;

    private float LastScreenX;
    private float LastScreenY;

    /** The {@link SpriteBatch} of the GameHud. Use for drawing {@link food.Recipe}s. */
    private SpriteBatch batch;
    /** The {@link FoodStack} that the {@link GameHud} should render. */
    private FoodStack recipe;
    /** The {@link Customer} to have their request rendered.. */
    private Customer customer;
    // /** The time, in milliseconds, of the last recipe change. */
    // private long lastChange;

    /**
     * The GameHud constructor.
     * @param batch The {@link SpriteBatch} to render
     * @param gameScreen The {@link GameScreen} to render the GameHud on
     */
    public GameHud(SpriteBatch batch, GameScreen gameScreen)
    {
        super(batch);

        timeLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        updateTime(0,0,0);

        CustomerLabel = new Label("CUSTOMERS LEFT:", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        repLabel = new Label("REPUTATION:",new Label.LabelStyle(new BitmapFont(),Color.BLACK));

        playerMoney = new Label("MONEY: 0", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(CustomerLabel).expandX().padTop(80).padRight(60);
        table.add(timeLabel).expandX().padTop(80).padLeft(60);
        table.add(playerMoney).expandX().padTop(80).padLeft(60);
        table.add(repLabel).expandX().padTop(80).padRight(60);

        Gdx.input.setInputProcessor(stage);

        this.batch = batch;
    }

    /**
     * Renders both the {@link Hud} with the game information and
     * the {@link Recipe} required the {@link customers.Customer} selected.
     * <br>The {@link Recipe} displays on the right side of the screen.
     */
    @Override
    public void render() {
        super.render();
        batch.begin();
        GameSprites gameSprites = GameSprites.getInstance();
        float drawX = Constants.RECIPE_X, drawY = Constants.RECIPE_Y;
        // If there is a recipe...
        if (recipe != null) {
            // Loop through on the top right of the screen, and render!
            for (FoodItem.FoodID ingredient : recipe.getStack()) {
                Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, ingredient.toString());
                foodSprite.setScale(2F);
                foodSprite.setPosition(drawX-foodSprite.getWidth()/2,drawY-foodSprite.getHeight()/2);
                foodSprite.draw(batch);
                drawY -= 64;
            }
        }
        batch.end();
    }


    public void update() {
        if(LastScreenX != Gdx.graphics.getWidth() || LastScreenY != Gdx.graphics.getHeight()){
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //System.out.println("UPDATED!");
        }
        LastScreenX = Gdx.graphics.getWidth();
        LastScreenY = Gdx.graphics.getHeight();
    }

    public void resize(int width, int height) {
        // use true here to center the camera
        // that's what you probably want in case of a UI
        stage.getViewport().update(width, height, true);
    }

    /**
     * Sets the recipe to be rendered.
     * @param customer The {@link Customer} who is requesting the {@link #recipe}.
     */
    public void setRecipe(Customer customer) {
        // this.lastChange = TimeUtils.millis();
        this.customer = customer;
        if (customer == null) {
            this.recipe = null;
            return;
        }
        this.recipe = Recipe.randomRecipeOption(customer.getRequestName());
        if(this.recipe == Recipe.firstRecipeOption("Margherita Pizza")){
            FoodStack pizzaIngred = new FoodStack();
            pizzaIngred.addStack(FoodItem.FoodID.cheese);
            pizzaIngred.addStack(FoodItem.FoodID.dough);
            pizzaIngred.addStack(FoodItem.FoodID.tomatoChop);
            this.recipe = pizzaIngred;
        }
        else if(this.recipe == Recipe.firstRecipeOption("Jacket Potato")){
            FoodStack potatoIngred = new FoodStack();
            potatoIngred.addStack(FoodItem.FoodID.cheese);
            potatoIngred.addStack(FoodItem.FoodID.rawPotato);
            this.recipe = potatoIngred;
        }
    }

    /**
     * Update the Timer
     * @param secondsPassed The number of seconds passed
     */
    public void updateTime(int secondsPassed) {
        updateTime(0,0,secondsPassed);
    }

    /**
     * Update the Timer
     * @param minutesPassed The number of minutes passed
     * @param secondsPassed The number of seconds passed
     */
    public void updateTime(int minutesPassed, int secondsPassed) {
        updateTime(0,minutesPassed,secondsPassed);
    }

    /**
     * Update the Timer
     * @param hoursPassed The number of hours passed
     * @param minutesPassed The number of minutes passed
     * @param secondsPassed The number of seconds passed
     */
    public void updateTime(int hoursPassed, int minutesPassed, int secondsPassed)
    {
        timeLabel.setText("TIMER: " + String.format(Util.formatTime(hoursPassed,minutesPassed,secondsPassed)));
    }

    public void updateRep(){
        repLabel.setText(String.format("REPUTATION: %d",repPoints));
    }

    /**
     * Set the Customer Count label
     * @param customerCount New Customer Count
     */
    public void setCustomerCount(int customerCount, boolean endless) {
        if (endless == true)
        {
            CustomerLabel.setText(String.format("CUSTOMERS: ENDLESS"));
        }
        else {
            CustomerLabel.setText(String.format("CUSTOMERS: %d",customerCount));
        }
    }

    /**
     * Getter for the {@link Customer} that has their
     * request being shown.
     * @return {@link Customer} : The {@link Customer} having their
     *                            request shown.
     */
    public Customer getCustomer() {
        return customer;
    }

    public void updateMoney(int newMoney){
        playerMoney.setText("MONEY: " + newMoney);
    }

    public void showNewPowerup(final String powerupName, String powerupFileString){
        powerupFileString = "ENG1/assets/powerups/" + powerupFileString + ".png";
        Texture texture = new Texture(Gdx.files.internal(powerupFileString));
        TextureRegion region = new TextureRegion(texture, 0, 0, 16, 16);
        powerup = new ImageButton(new TextureRegionDrawable(region));
        stage.addActor(powerup);
        Random rand = new Random();
        //int randX = rand.nextInt(100, 800);
        //int randY = rand.nextInt(100, 500);
        int randX = rand.nextInt(800-100)+100;
        int randY = rand.nextInt(500-100)+100;
        powerup.setPosition(randX, randY);
        powerup.setTransform(Boolean.TRUE);
        powerup.scaleBy(3f);
        powerup.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("Up");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                PowerupStatic.powerups.put(powerupName, Boolean.TRUE);
                powerup.setPosition(-100f, -100f);
                GameScreen.powerupOnScreen = false;
                return true;
            }});
    }
}
