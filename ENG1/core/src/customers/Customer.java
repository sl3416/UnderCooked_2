package customers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import cooks.GameEntity;
import food.FoodStack;
import food.Recipe;
import game.GameScreen;
import helper.Constants;
import stations.ServingStation;
import stations.Station;

import java.util.Random;
import static game.GameScreen.customerController;

/**
 * A {@link Customer} has a request that they want
 * to be served by the player.
 */
public class Customer {

    /** The position of the Customer. */
    public Vector2 position;
    /** The {@link Sprite} of the {@link Customer}. */
    public Sprite sprite;
    /** The name of the {@link Recipe} that the {@link Customer}
     * is requesting. */
    private String request;
    private long spawnTime;
    private int timeLimit;

    /**
     * The constructor for the {@link Customer}.
     * <br>Randomly picks out a {@link Recipe} as a request.
     * @param sprite The {@link Sprite} of the {@link Customer}.
     */
    public Customer(Sprite sprite)
    {
        Random rand = new Random();
        this.sprite = sprite;
        this.position = Constants.customerSpawn;
        this.request = Recipe.randomRecipe();
        this.spawnTime = TimeUtils.millis();
        this.timeLimit = 60;
    }

    /**
     * Another constructor for the {@link Customer}, with a specified position.
     * <br>Randomly picks out a {@link Recipe} as a request.
     * @param sprite The {@link Sprite} of the {@link Customer}.
     * @param position A {@link Vector2} position of the {@link Customer}.
     */
    public Customer(Sprite sprite, Vector2 position) {
        this(sprite);
        this.position = position;
    }

    public String randomRecipe() {
        this.request = Recipe.randomRecipe();
        return request;
    }

    /**
     * Renders the {@link Customer} at their {@link #position}.
     * @param batch The {@link SpriteBatch} of the game.
     */
    public void render(SpriteBatch batch) {
        sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        sprite.draw(batch);
    }

    /**
     * Getter for the {@code x} position of the {@link Customer}.
     * @return {@code float} : The {@code x} of the {@link Customer}.
     */
    public float getX() {
        return position.x;
    }

    /**
     * Getter for the {@code y} position of the {@link Customer}.
     * @return {@code float} : The {@code y} of the {@link Customer}.
     */
    public float getY() {
        return position.y;
    }

    /**
     * Getter to get the name of the request of the {@link Customer}.
     * @return {@link String} : The name of the {@link Customer}'s
     *                          {@link Recipe} request.
     */
    public String getRequestName() {
        return request;
    }

    public void update(){
        long timeElapsed = TimeUtils.timeSinceMillis(spawnTime);
        if(timeElapsed >= timeLimit*1000){
            customerController.removeCustomer(this.getStation());
            GameScreen.repPoints -= 1;
        }
    }

    public ServingStation getStation(){
        Array<ServingStation> stations = new Array<>(CustomerController.servingStations);
        for (int i = stations.size - 1 ; i >= 0 ; i--) {
            if (stations.get(i).getCustomer() == this) {
                return(stations.get(i));
            }
        }
        return null;
    }
}
