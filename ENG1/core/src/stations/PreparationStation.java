package stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import cooks.Cook;
import food.FoodItem;
import game.GameSprites;
import interactions.InputKey;
import interactions.Interactions;

public class PreparationStation extends Station {

    private FoodItem.FoodID foodItem;
    private Interactions.InteractionResult interaction;
    private float progress;
    private int stepNum;

    public PreparationStation(float width, float height, Body body, Rectangle rectangle) {
        super(width, height, body, rectangle);
    }

    @Override
    public void update(float delta) {
        if (inUse) {
            if (progress < 100) {
                float[] steps = interaction.getSteps();
                progress = Math.min(progress + interaction.getSpeed(), 100);
                if (stepNum < steps.length) {
                    // -1 instant case
                    if (interaction.getSpeed() == -1) {
                        progress = steps[stepNum];
                    } else {
                        if (progress >= steps[stepNum]) {
                            progress = steps[stepNum];
                        }
                        progress += interaction.getSpeed() * delta;
                    }
                } else {
                    if (interaction.getSpeed() == -1) {
                        progress = 100;
                    } else {
                        progress += interaction.getSpeed() * delta;
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // If in use, render the appropriate foodItem on the Station.
        if (inUse) {
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
            Color progressColor;
            progressColor = Color.GREEN;
            shape.rect(rectX+2,rectY+2,progress/100*progressWidth,rectHeight-4,progressColor,progressColor,progressColor,progressColor);
        }
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

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
                stepNum = 0;
                progress = 0;
                inUse = true;

                // If the speed is -1, immediately set the progress to the first step.
                float[] steps = interaction.getSteps();
                if (steps.length > 0) {
                    if (interaction.getSpeed() == -1) {
                        progress = steps[0];
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
                // If currently at a step, move to the next step.
                float[] steps = interaction.getSteps();
                if (stepNum < steps.length) {
                    if (progress >= steps[stepNum]) {
                        progress = steps[stepNum];
                        stepNum += 1;
                    }
                }
            }
        }
    }
}