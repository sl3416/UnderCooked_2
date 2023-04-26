package stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import food.FoodItem;
import game.GameScreen;
import game.GameSprites;
import game.PowerupStatic;
import game.StateOfGame;
import helper.MapHelper;
import interactions.InputKey;
import interactions.Interactions;

import static game.GameScreen.currentMoney;

/**
 * The {@link PreparationStation} class, where the {@link cooks.Cook}
 * process {@link FoodItem}s into different {@link FoodItem}s to prepare
 * them to make a {@link food.Recipe}.
 */
public class PreparationStation extends Station {

    public FoodItem.FoodID foodItem;
    private Interactions.InteractionResult interaction;
    public float progress;
    private int stepNum;
    public StationState state;

    private float tempDelta;

    private GameScreen gameScreen;

    public int saveID;
    private float progBurn;

    /**
     * The constructor for the {@link PreparationStation}.
     * @param rectangle The collision and interaction area of the {@link PreparationStation}.
     */
    public PreparationStation(Rectangle rectangle, int saveId, GameScreen gameScreen) {
        super(rectangle);
        this.gameScreen = gameScreen;
        this.saveID = saveId;
    }

    /**
     * An update function to be used by the {@link game.GameScreen}
     *
     * <br>It updates the {@link #progress} of the {@link interactions.Interactions.InteractionResult}
     * until it requires a {@link InputKey.InputTypes#USE} from the {@link Cook}
     * when the current {@code step} of the {@link interactions.Interactions.InteractionResult}
     * is reached.
     * @param delta The time between frames as a float.
     */
    @Override
    public void update(float delta) {
        if (inUse) {
            progBurn = progBurn + interaction.getSpeed() * delta;
            if (progress < 100) {
                progress = Math.min(progress + interaction.getSpeed() * delta, 100);
                float[] steps = interaction.getSteps();
                if (stepNum < steps.length) {
                    // -1 instant case
                    if (interaction.getSpeed() == -1) {
                        progress = steps[stepNum];
                        state = StationState.NEED_USE;
                    } else {
                        if (progress >= steps[stepNum]) {
                            progress = steps[stepNum];
                            state = StationState.NEED_USE;

                            /* //New Matt Code
                            tempDelta += delta;
                            if(tempDelta > 6F && foodItem == FoodItem.FoodID.meat){
                                interaction.setResult(FoodItem.FoodID.burntPatty);
                                foodItem = FoodItem.FoodID.burntPatty;
                                progress = 100F;
                                tempDelta = 0F;
                            }
                            //End New Matt Code */

                        } else {
                            state = StationState.PREPARING;
                        }
                    }
                } else {
                    if (interaction.getSpeed() == -1) {
                        progress = 100;
                        state = StationState.FINISHED;
                    } else {
                        state = StationState.PREPARING;
                    }
                }
            } else {
                if(state != StationState.FINISHED){
                    state = StationState.FINISHED;
                } else{

                }
            }
            if(progBurn >= 200){
                inUse = false;
            }
            System.out.println(progBurn);
        }

        saveVariables();
    }

    /**
     * The function used to render the {@link PreparationStation}.
     *
     * <br>When no item is on top, it renders the {@link PreparationStation}'s
     * identifying {@link Sprite}.
     * <br>When there is an item on top, it renders the {@link interactions.Interactions.InteractionResult}'s
     * {@link #progress} bar to completion, as well as the {@link FoodItem}
     * on the {@link PreparationStation}.
     * @param batch The {@link SpriteBatch} used to render.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // If in use, render the appropriate foodItem on the Station.
        if(locked){
            Sprite renderItem = gameSprites.getSprite(GameSprites.SpriteID.STATION,"lock");
            renderItem.setScale(3f);
            renderItem.setPosition(x-renderItem.getWidth()/2,y);
            renderItem.draw(batch);

        }
        else if (inUse) {
            Sprite renderItem;
            if (progress < 100) {
                renderItem = gameSprites.getSprite(GameSprites.SpriteID.FOOD,foodItem.toString());
            } else {
                renderItem = gameSprites.getSprite(GameSprites.SpriteID.FOOD,interaction.getResult().toString());
            }
            renderItem.setScale(2F);
            renderItem.setPosition(x-renderItem.getWidth()/2,y);
            renderItem.draw(batch);
        }
    }

    public enum StationState {
        PREPARING,
        NEED_USE,
        FINISHED
    }

    /**
     * The function used to render the {@link PreparationStation}.
     *
     * <br>It draws the {@link #progress} bar of the
     * {@link interactions.Interactions.InteractionResult}.
     * @param shape The {@link ShapeRenderer} used to render.
     */
    @Override
    public void renderShape(ShapeRenderer shape) {
        // Render the progress bar when inUse
        if (inUse) {
            float rectX = x - interactRect.getWidth() / 3,
                  rectY = y + 40,
                  rectWidth = 40,
                  rectHeight = 10;
            // Black bar behind
            shape.rect(rectX, rectY, rectWidth, rectHeight, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
            // Now the progress bar.
            float progressWidth = rectWidth-4;
            Color progressColor = Color.SKY;
            // If preparation is done, show as green.
            switch (state) {
                case NEED_USE:
                    progressColor = Color.YELLOW;
                    break;
                case FINISHED:
                    progressColor = Color.GREEN;
                    break;
                default:
                    break;
            }
            shape.rect(rectX+2,rectY+2,progress/100*progressWidth,rectHeight-4,progressColor,progressColor,progressColor,progressColor);
            if(progress>=100){
                shape.rect(rectX+2,rectY+2,(progBurn-100)/100*progressWidth,rectHeight-4,Color.RED,Color.RED,Color.RED,Color.RED);
            } else if(state == StationState.NEED_USE){
                shape.rect(rectX+2,rectY+2,Math.max((progBurn-95),0)/200*progressWidth,rectHeight-4,Color.RED,Color.RED,Color.RED,Color.RED);
            }
        }
    }

    /**
     * The interact function for the {@link ServingStation}.
     *
     * <br>This allows the {@link Cook} to put a valid {@link FoodItem} onto the
     * {@link PreparationStation}, and start a process of changing it from the
     * {@link FoodItem} to the {@link interactions.Interactions.InteractionResult} {@link FoodItem}.
     * @param cook The cook that interacted with the {@link CookInteractable}.
     * @param inputType The type of {@link InputKey.InputTypes} the player made with
     *                  the {@link CookInteractable}.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if (!locked) {
            // If the Cook is holding a food item, and they use the "Put down" control...
            if (cook.foodStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
                // Start by getting the possible interaction result
                Interactions.InteractionResult newInteraction = interactions.Interactions.interaction(cook.foodStack.peekStack(), stationID);
                // If it's null, just stop here.
                if (newInteraction == null) {
                    return;
                }

                // Check to make sure the station isn't inUse.
                if (!inUse) {
                    // Set the current interaction, and put this station inUse
                    foodItem = cook.foodStack.popStack();
                    interaction = newInteraction;
                    if(foodItem == FoodItem.FoodID.meat){
                        interaction.setResult(FoodItem.FoodID.meatCook);
                    }
                    stepNum = 0;
                    progress = 0;
                    progBurn = 0;
                    inUse = true;
                    state = StationState.PREPARING;
                    if(PowerupStatic.powerups.get("CookingSpeedIncr") == Boolean.TRUE && interaction.getSpeed() != -1){
                        float newSpeed = 35F;
                        interaction.setSpeed(newSpeed);
                    }
                    /*
                    if(gameScreen.powerups.get("SpeedIncr") == Boolean.TRUE && interaction.getSpeed() != -1){
                        interaction.setSpeed(speed / 2);
                    }
                    */


                    // If the speed is -1, immediately set the progress to the first step.
                    float[] steps = interaction.getSteps();
                    if (steps.length > 0) {
                        if (interaction.getSpeed() == -1) {
                            progress = steps[0];
                            state = StationState.NEED_USE;
                        }
                    }
                }
            }
            // The other two inputs require the station being inUse.
            else if (inUse) {
                // If the user instead uses the "Pick Up" option, check if the station is inUse
                if (inputType == InputKey.InputTypes.PICK_UP) {
                    inUse = false;
                    // If it is done, pick up the result instead of the foodItem
                    if (progress >= 100) {
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                    // Take the item from the Station, and change it to not being used
                    cook.foodStack.addStack(foodItem);
                    return; // Return as it the Station is no longer inUse
                }
                // Otherwise, check if the user is trying to use the Station.
                if (inputType == InputKey.InputTypes.USE) {
                    // If progress >= 100, then take the result of the preparation.
                    if (progress >= 100) {
                        inUse = false;
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                    // If currently at a step, move to the next step.
                    float[] steps = interaction.getSteps();
                    if (stepNum < steps.length) {
                        if (progress >= steps[stepNum]) {
                            progress = steps[stepNum];
                            progBurn = 50;
                            stepNum += 1;
                        }
                    }
                }
            }
        }
        else if(currentMoney >= 20){
            if(this.getID() == StationID.oven) {
                if(PowerupStatic.powerups.get("NewStationsCostDecr") == Boolean.TRUE){
                    currentMoney -= 10;
                } else {
                    currentMoney -= 20;
                }
                gameScreen.increaseCurrentMoney(0);
                MapHelper.bakeLockedFlag = false;
                StateOfGame.getInstance().ovensLocked = false;
                for (PreparationStation stationP : gameScreen.getMapHelper().prepStationsList) {
                    if (stationP.getID() == Station.StationID.oven) {
                        stationP.unlock();
                    }
                }
            }
            else if(this.getID() == StationID.fry){

                if(PowerupStatic.powerups.get("NewStationsCostDecr") == Boolean.TRUE){
                    currentMoney -= 10;
                } else {
                    currentMoney -= 20;
                }

                gameScreen.increaseCurrentMoney(0);
                MapHelper.fryLockedFlag = false;
                StateOfGame.getInstance().fryersLocked = false;
                for (PreparationStation stationP: gameScreen.getMapHelper().prepStationsList) {
                    if(stationP.getID() == Station.StationID.fry){
                        stationP.unlock();
                    }
                }
            }
        }
    }

    private void saveVariables(){
        StateOfGame.getInstance().stationFoods[saveID] = this.foodItem;
        StateOfGame.getInstance().stationStates[saveID] = this.state;
        StateOfGame.getInstance().stationProgresses[saveID] = this.progress;

    }
}
