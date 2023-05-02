package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import customers.Customer;
import static customers.CustomerController.customers;
import static customers.CustomerController.customersServed;
import static game.MenuScreen.loading;
import static helper.MapHelper.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import cooks.Cook;
import cooks.GameEntity;
import customers.CustomerController;
import food.FoodItem;
import food.FoodStack;
import helper.CollisionHelper;
import helper.GameHud;
import helper.InstructionHud;
import helper.MapHelper;
import interactions.InputKey;
import interactions.Interactions;
import stations.*;
import helper.MapHelper;

import java.util.*;

/** A {@link ScreenAdapter} containing certain elements of the game. */
public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private int delay;
    public static boolean youLose;
    private long customerTimer = 10000;
    public static int diffMultiplier;
    private long previousSecond = 0;
    public long lastCustomerSecond = 0;
    private long nextCustomerSecond = 0;
    private int secondsPassed = 0, minutesPassed = 0, hoursPassed = 0;
    private GameHud gameHud;
    private InstructionHud instructionHUD;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private ScreenController screenController;
    // private ShapeRenderer shapeRenderer;
    private World world;
    public Box2DDebugRenderer box2DDebugRenderer;

    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private MapHelper mapHelper;
    private Array<CookInteractable> interactables;
    public CollisionHelper collisionHelper;
    public Array<GameEntity> gameEntities;
    public DrawQueueComparator drawQueueComparator;
    private Array<ServingStation> servingStations;
    private int xOffset = 480;
    private int yOffset = 320;

    //Objects
    public Array<Cook> cooks;
    private Cook cook;

    private int cookIndex;
    public static CustomerController customerController;
    private int customersToServe;

    //this file is being used as a "GameManager" too as there is one instance of this script.
    public static int currentMoney;
    public static int repPoints;

    public String[] powerupStrings = {"SpeedIncr", "CookingSpeedIncr", "MoneyIncr", "CustomerTimerIncr", "NewStationsCostDecr"};
    public String[] powerupFileStrings = {"speed", "cookingSpeed", "money", "time", "stations"};
    public Boolean firstPowerupSpawnBool = Boolean.TRUE; //DO NOT DELETE
    public HashMap<String, Boolean> powerupMemory;
    public int powerupCounter;
    public static boolean powerupOnScreen;

    public static boolean endless;


    /**
     * The constructor for the {@link GameScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param camera The {@link OrthographicCamera} that the game should use.
     */
    public GameScreen(ScreenController screenController, OrthographicCamera camera)
    {
        this.previousSecond = TimeUtils.millis();
        this.lastCustomerSecond = -1;
        this.nextCustomerSecond = -1;
        this.cooks = new Array<>();
        this.interactables = new Array<>();
        this.collisionHelper = CollisionHelper.getInstance();
        this.collisionHelper.setGameScreen(this);
        this.cookIndex = -1;
        this.camera = camera;
        this.screenController = screenController;
        this.batch = screenController.getSpriteBatch();
        this.shape = screenController.getShapeRenderer();
        this.gameEntities = new Array<>();
        this.drawQueueComparator = new DrawQueueComparator();
        this.customerController = new CustomerController(this);



        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.mapHelper = MapHelper.getInstance();
        this.mapHelper.setGameScreen(this);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        this.gameHud = new GameHud(batch, this);
        this.instructionHUD = new InstructionHud(batch);
        this.currentMoney = 0;
        this.repPoints = 3;

        this.powerupCounter = 0;
        powerupMemory = new HashMap<String, Boolean>();
        powerupMemory.put("SpeedIncr", Boolean.FALSE);
        powerupMemory.put("CookingSpeedIncr", Boolean.FALSE);
        powerupMemory.put("MoneyIncr", Boolean.FALSE);
        powerupMemory.put("CustomerTimerIncr", Boolean.FALSE);
        powerupMemory.put("NewStationsCostDecr", Boolean.FALSE);
        this.powerupOnScreen = false;


        this.youLose = false;

        FileInteractor.getInstance();
        StateOfGame.getInstance();

    }

    public enum difficulty{
        EASY,
        MEDIUM,
        HARD
    }


    /**
     * Update the game's values, {@link GameEntity}s and so on.
     * @param delta The time between frames as a float.
     */
    private void update(float delta)
    {
        // First thing, update all inputs
        Interactions.updateKeys();

        gameHud.update();

        if( repPoints <= 0 ){
            youLose = true;
        }

        long diffInMillis = TimeUtils.timeSinceMillis(previousSecond);
        if (diffInMillis >= 1000) {
            previousSecond += 1000;
            secondsPassed += 1;
            if (secondsPassed >= 60) {
                secondsPassed = 0;
                minutesPassed += 1;
                if (minutesPassed >= 60) {
                    minutesPassed = 0;
                    hoursPassed += 1;
                }
            }
        }

        //This spawns the first powerup before a customer is served
        if(secondsPassed == 5 && firstPowerupSpawnBool == Boolean.TRUE){
            firstPowerupSpawnBool = Boolean.FALSE;
            spawnPowerup();
        }


        gameHud.updateTime(hoursPassed, minutesPassed, secondsPassed);
        gameHud.updateRep();
        cameraUpdate();
        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        for (Cook thisCook : cooks) {
            thisCook.getBody().setLinearVelocity(0F,0F);
            if (thisCook == cook) {
                thisCook.userInput();
            }
        }
        if(Interactions.isJustPressed(InputKey.InputTypes.COOK_SWAP)) {
            setCook((cookIndex + 1) % cooks.size);
        }

        // Spawning code to spawn a customer after an amount of time.
        Random rand = new Random();
        int rand1 = rand.nextInt(100);
        int rand2 = rand.nextInt(100);
        if(TimeUtils.millis() >= nextCustomerSecond)
        {
            if (secondsPassed > 30)
            {
                if (minutesPassed > 2)
                {
                    if (rand2 <= 30)
                    {
                        int recipeComplexity = customerController.addCustomer(GameScreen.endless);
                    }
                }
                if (rand1 <= 40)
                {
                    int recipeComplexity = customerController.addCustomer(GameScreen.endless);
                }
            }
            int recipeComplexity = customerController.addCustomer(GameScreen.endless);
            if (recipeComplexity == -1) {
                // If customer couldn't be added, then wait 2 seconds.
                nextCustomerSecond += 2000;
            } else {
                // Wait longer if the recipe has more steps.
                lastCustomerSecond = TimeUtils.millis();
                if (endless) {

                    nextCustomerSecond += (customerTimer/2 * Math.floor(9 + 5.4F * Math.log(recipeComplexity - 0.7)))/(customersServed*0.5+diffMultiplier);
                }
                else {
                    nextCustomerSecond += (customerTimer/2 * Math.floor(9 + 5.4F * Math.log(recipeComplexity - 0.7)))/diffMultiplier;
                }

            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.O) && nextCookID <= 2 && currentMoney >= 20){
            this.increaseCurrentMoney(-20);
            Body body = makeBody(chefRect, false);
            this.addCook(new Cook(chefRect.getWidth(), chefRect.getHeight(), body, this, nextCookID));
            nextCookID++;
        }

        if(Interactions.isJustPressed(InputKey.InputTypes.PAUSE))
        {
            screenController.pauseGameScreen();
        }
        world.step(1/60f,6,2);
        for (GameEntity entity : gameEntities) {
            entity.update(delta);
        }
        for (Customer customer : customers){
            customer.update();
        }

        // - - - DEBUG CONTROLS - - - //
        /* unlock stations cheat
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
            MapHelper.bakeLockedFlag = false;
            for (PreparationStation stationP: mapHelper.prepStationsList) {
                if(stationP.getID() == Station.StationID.oven){
                    stationP.unlock();
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            MapHelper.fryLockedFlag = false;
            for (PreparationStation stationP: mapHelper.prepStationsList) {
                if(stationP.getID() == Station.StationID.fry){
                    stationP.unlock();
                }
            }
        }
        // - - - DEBUG END - - - //
        // */
        this.saveVariables();
    }

    /**
     * Update the {@link #camera}.
     */
    private void cameraUpdate()
    {
        camera.position.set(new Vector3(0 + xOffset,0+yOffset,0));
        camera.update();
    }

    /**
     * The next frame of the game.
     * @param delta The time between frames as a float.
     */
    @Override
    public void render(float delta)
    {

        this.update(delta);

        renderGame(delta);

        if(customersToServe <= customersServed || youLose == true)
        {
            screenController.setScreen((ScreenController.ScreenID.GAMEOVER));
            ((GameOverScreen) screenController.getScreen(ScreenController.ScreenID.GAMEOVER)).setTime(hoursPassed,minutesPassed,secondsPassed);
            ((GameOverScreen) screenController.getScreen(ScreenController.ScreenID.GAMEOVER)).setWin(youLose);
        }
        gameHud.updateMoney(currentMoney);
    }

    /**
     * Render the {@link GameScreen}. It is a separate function to
     * allow rendering of the game from the {@link PauseScreen}.
     * @param delta The time between frames as a float.
     */
    public void renderGame(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.render();
        batch.begin();

        gameEntities.sort(drawQueueComparator);

        for (GameEntity entity : gameEntities) {
            entity.render(batch);
            if (entity == cook) {
                ((Cook) entity).renderControlArrow(batch);
            }
            entity.renderDebug(batch);
        }

        batch.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (GameEntity entity : gameEntities) {
            entity.renderShape(shape);
            // entity.renderShapeDebug(shape);
        }

        shape.end();
        //box2DDebugRenderer.render(world, camera.combined.scl(PPM));
        gameHud.render();
        instructionHUD.render();

    }

    /**
     * A {@link Comparator} used to compare the Y height of two
     * {@link GameEntity}s.
     * If it is negative, then the left entity is higher.
     * If it is positive, then the right entity is higher.
     * If it is 0, then both are at the same height.
     */
    public class DrawQueueComparator implements Comparator<GameEntity> {

        @Override
        public int compare(GameEntity o1, GameEntity o2) {
            float o1Y = o1.getY(),
                  o2Y = o2.getY();
            if (o1Y > o2Y) {
                return -1;
            } else if (o2Y > o1Y) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Get the world that the game is using.
     * @return {@link World} : The {@link GameScreen}'s {@link World}.
     */
    public World getWorld()
    {
        return world;
    }

    /**
     * Sets the currently active {@link #cook} that the game is using.
     * @param cookIndex The index of {@link #cook} in the {@link #cooks} array.
     * @return {@link Cook} : The {@link Cook} that the game has swapped to.
     */
    public Cook setCook(int cookIndex)
    {
        if (cookIndex < 0 || cookIndex > cooks.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.cook = cooks.get(cookIndex);
        this.cookIndex = cookIndex;
        return this.cook;
    }

    /**
     * Adds a new {@link Cook} to the {@link #cooks} {@link Array} for the game to swap between.
     * @param newCook The {@link Cook} to be added to the {@link Array}.
     * @return {@code int} : The index of the new cook in the cooks array.
     */
    public int addCook(Cook newCook) {
        gameEntities.add(newCook);
        cooks.add(newCook);
        return cooks.size-1;
    }

    /**
     * Updates the {@link GameHud} with the correct number of {@link Customer}s.
     * @param customerCount The {@code int} number to set the number of
     *                      {@link Customer}s to.
     */
    public void setCustomerHud(int customerCount) {
        if (endless == false) {
            gameHud.setCustomerCount(customersToServe - customerCount, GameScreen.endless);
        }
        else {
            gameHud.setCustomerCount(customerCount, GameScreen.endless);
        }
    }

    /**
     * Returns the number of customers remaining before the game is finished.
     * @return {@code int} : The value of {@link CustomerController#getCustomersLeft()}.
     */
    public int getCustomerCount() {
        return customerController.getCustomersLeft();
    }

    /**
     * A getter to get the {@link #previousSecond}.
     * <br>The {@link #previousSecond} is used for the timer, by checking when the previous
     * second was so that the game can check if it has been another second or not.
     * @return {@code long} : The {@link #previousSecond}.
     */
    public long getPreviousSecond() {
        return previousSecond;
    }

    /**
     * A setter to set the {@link #previousSecond} to the {@code long} provided.
     * @param newSecond What to set the {@link #previousSecond} to as a {@code long}.
     */
    public void setPreviousSecond(long newSecond) {
        previousSecond = newSecond;
    }

    /**
     * A getter to get the {@link #nextCustomerSecond}.
     * <br>The {@link #nextCustomerSecond} is used for spawning the
     * {@link Customer}s after a short delay.
     * @return {@code long} : The {@link #nextCustomerSecond}.
     */
    public long getNextCustomerSecond() {
        return nextCustomerSecond;
    }

    /**
     * A setter to set the {@link #nextCustomerSecond} to the {@code long} provided.
     * @param newSecond What to set the {@link #nextCustomerSecond} to as a {@code long}.
     */
    public void setNextCustomerSecond(long newSecond) {
        nextCustomerSecond = newSecond;
    }

    /**
     * {@link #interactables} getter. Contains all the {@link #interactables} in the {@link GameScreen}.
     * @return {@link Array}&lt;{@link CookInteractable}&gt; : {@link #interactables}.
     */
    public Array<CookInteractable> getInteractables() {
        return interactables;
    }

    /**
     * Adds a {@link CookInteractable} that a {@link Cook} can interact with to {@link #interactables}.
     * @param cookInteractable The {@link CookInteractable} object that the {@link Cook}
     *                         should be able to interact with.
     */
    public void addInteractable(CookInteractable cookInteractable) {
        interactables.add(cookInteractable);
    }

    /**
     * Adds a game entity to the GameScreen to be rendered and updated.
     * @param entity The {@link GameEntity} to be added.
     */
    public void addGameEntity(GameEntity entity) {
        gameEntities.add(entity);
    }

    /**
     * Intermediate function to allow the {@link MapHelper} to add
     * the {@link ServingStation}s to the {@link CustomerController}.
     * @param station The {@link ServingStation} to add to the {@link CustomerController}.
     */
    public void addServingStation(ServingStation station) { customerController.addServingStation(station); }
    /** Reset the game variables, map and world. */
    public void reset() {
        // Reset all variables
        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;
        cooks.clear();
        gameEntities.clear();
        interactables.clear();
        mapHelper.dispose();
        customerController.clearServingStations();
        dispose();
        mapHelper = MapHelper.newInstance();
        mapHelper.setGameScreen(this);
        world.dispose();
        this.world = new World(new Vector2(0,0), false);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        cookIndex = -1;
        PowerupStatic.resetPowerups();
        powerupMemory.clear();
        powerupCounter = 0;
        powerupOnScreen = false;
        this.youLose = false;
        repPoints = 3;
        currentMoney = 0;
        customersServed = 0;
        customers.clear();
    }

    /**
     * A variable for setting up the game when it starts.
     *
     * @param customers The number of customers that need to be
     *                  served in the game to finish.
     *
     */
    public void startGame(int customers, boolean endless, int difficultyMultiplier) {
        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;
        previousSecond = TimeUtils.millis();
        lastCustomerSecond = TimeUtils.millis();
        nextCustomerSecond = TimeUtils.millis()+customerTimer/difficultyMultiplier;
        diffMultiplier = difficultyMultiplier;

        GameScreen.endless = endless;

        gameHud.setRecipe(null);
        customersToServe = customers;
        customerController.setCustomersLeft(customers);
        if(!loading) {
            customerController.addCustomer(GameScreen.endless);
        }
        setCustomerHud(customers);
        gameHud.setCustomerCount(customers, GameScreen.endless);

    }
    public void startGame(int customers, boolean endless, String difficulty){
        int diffMultiplier = 2;
        switch (difficulty){
            case "Beginner":
                diffMultiplier = 1;
                break;
            case "Normal":
                diffMultiplier = 2;
                break;
            case "Master":
                diffMultiplier = 4;
                break;
        }
        this.startGame(customers,endless,diffMultiplier);
    }
    public void startGame(int customers, boolean endless) {
        this.startGame(customers,endless,"Beginner");
    }

    /**
     * A getter for the {@link CustomerController} of the
     * game.
     * @return {@link CustomerController} : The {@link CustomerController}
     *                                      for the game.
     */
    public CustomerController getCustomerController() {
        return this.customerController;
    }

    /**
     * Getter to get the {@link GameHud}.
     * @return {@link GameHud} : The game's {@link GameHud}.
     */
    public GameHud getGameHud() {
        return gameHud;
    }
    public InstructionHud getInstructionHUD() {
        return instructionHUD;
    }

    public void increaseCurrentMoney(int toAdd){
        currentMoney += toAdd;
        gameHud.updateMoney(currentMoney);
    }

    public void spawnPowerup(){
        Random rand = new Random();
        int i = rand.nextInt(5);
        if(powerupCounter != 5 && powerupOnScreen == false) {
            while (powerupMemory.get(powerupStrings[i]) == Boolean.TRUE) {
                i = rand.nextInt(5);
            }

            gameHud.showNewPowerup(powerupStrings[i], powerupFileStrings[i]);
            powerupMemory.put(powerupStrings[i], Boolean.TRUE);
            powerupCounter += 1;
            powerupOnScreen = true;
        }
    }

    public void saveVariables(){
        StateOfGame.getInstance().customersLeft = customersToServe;
        StateOfGame.getInstance().reputation = repPoints;
        StateOfGame.getInstance().money = currentMoney;
        StateOfGame.getInstance().endless = endless;
        StateOfGame.getInstance().customersServedState = customersServed;

        StateOfGame.getInstance().numberOfChefs = cooks.size;
        StateOfGame.getInstance().hoursTime = hoursPassed;
        StateOfGame.getInstance().minutesTime = minutesPassed;
        StateOfGame.getInstance().secondsTime = secondsPassed;

        StateOfGame.getInstance().chefPositions.clear();
        for(Cook val : cooks){
            StateOfGame.getInstance().chefPositions.add(val.getX());
            StateOfGame.getInstance().chefPositions.add(val.getY());

        }

        if(PowerupStatic.powerups.get("SpeedIncr") == Boolean.TRUE){
            StateOfGame.getInstance().powerups[0] = true;
        }
        if(PowerupStatic.powerups.get("CookingSpeedIncr") == Boolean.TRUE){
            StateOfGame.getInstance().powerups[1] = true;
        }
        if(PowerupStatic.powerups.get("MoneyIncr") == Boolean.TRUE){
            StateOfGame.getInstance().powerups[2] = true;
        }
        if(PowerupStatic.powerups.get("CustomerTimerIncr") == Boolean.TRUE){
            StateOfGame.getInstance().powerups[3] = true;
        }
        if(PowerupStatic.powerups.get("NewStationsCostDecr") == Boolean.TRUE){
            StateOfGame.getInstance().powerups[4] = true;
        }
    }
    public void loadVariables(StateOfGame gameState){
        reset();
        //GameScreen variables
        this.currentMoney = gameState.money;
        this.repPoints = gameState.reputation;
        this.customersToServe = gameState.customersLeft;
        this.endless = gameState.endless;
        customersServed = gameState.customersServedState;
        startGame(gameState.customersLeft, gameState.endless);

        //Chef variables
        List<Float> chefPosSave = gameState.chefPositions;

        //Powerups variables
        boolean[] powerups = gameState.powerups;
        processPowerupsFromLoad(powerups);

        //Setting up for loading counters variables
        FoodStack[] countersSave = gameState.countersFoodStacks;
        List<CounterStation> counters = mapHelper.counterStationsList;

        //Setting up for preparation station variables
        FoodItem.FoodID[] stationFoodsSave = gameState.stationFoods;
        PreparationStation.StationState[] stationStatesSave = gameState.stationStates;
        float[] stationProgressesSave = gameState.stationProgresses;
        boolean[] stationUsageSave = gameState.stationUsage;
        float[] stationBurnsSave = gameState.stationBurns;
        Interactions.InteractionResult[] stationInteractionSave = gameState.interactions;
        List<PreparationStation> preps = mapHelper.prepStationsList;

        int customersSave = gameState.customersWaiting;

        this.secondsPassed = gameState.secondsTime;
        this.minutesPassed = gameState.minutesTime;
        this.hoursPassed = gameState.hoursTime;
        gameHud.updateTime(secondsPassed, minutesPassed, hoursPassed);

        int cookID = 0;
        for(int i = 0; i < gameState.numberOfChefs; i++){
            Body body = makeBody(chefRect, false);
            int cookInd = this.addCook(new Cook(chefRect.getWidth(), chefRect.getHeight(), body, this, cookID));
            if(cookID==0){
                this.setCook(cookInd);
            }
            cookID++;
        }

        MapHelper.bakeLockedFlag = gameState.ovensLocked;
        MapHelper.fryLockedFlag = gameState.fryersLocked;

        if(!gameState.ovensLocked) {
            for (PreparationStation stationP : this.getMapHelper().prepStationsList) {
                if (stationP.getID() == Station.StationID.oven) {
                    stationP.unlock();
                }
            }
        }

        if(!gameState.fryersLocked) {
            for (PreparationStation stationP: this.getMapHelper().prepStationsList) {
                if(stationP.getID() == Station.StationID.fry){
                    stationP.unlock();
                }
            }
        }

        //Counters variables
        for(int i = 0; i< countersSave.length; i++){
            if(counters.get(i).saveID == 0){
                counters.get(i).foodStack = countersSave[0];
            }
            else if(counters.get(i).saveID == 1){
                counters.get(i).foodStack = countersSave[1];
            }
        }

        //Preparation station variables
        for(int i = 0; i < stationFoodsSave.length; i++){
            preps.get(i).foodItem = stationFoodsSave[i];
            preps.get(i).progress = stationProgressesSave[i];
            preps.get(i).state = stationStatesSave[i];
            preps.get(i).inUse = stationUsageSave[i];
            preps.get(i).progBurn = stationBurnsSave[i];
            preps.get(i).interaction = stationInteractionSave[i];
        }

        //Cooks variables
        for(int i = 0; i < cooks.size; i++){
            Cook cCook = cooks.get(i);
            cCook.foodStack = gameState.cooksFoodStacks[i];
            float xPos = gameState.chefPositions.get(i*2);
            float yPos = gameState.chefPositions.get(i*2+1);
            cCook.getBody().setTransform(xPos,yPos,cCook.getBody().getAngle());
        }

        //Customers
        customers.clear();
        for(int i = 0; i < gameState.customersWaiting; i++){
            Array<ServingStation> stations = new Array<>(CustomerController.servingStations);
            int position = gameState.customerPositions[i];
            if(position < 0){position=0;}
            ServingStation station = stations.get(position);
            customerController.addCustomer(endless, station);
        }
        for(int i = 0; i < customers.size; i++){
            Customer customer = customers.get(i);
            customer.setRequest(gameState.customerRequests[i]);

            this.getGameHud().setRecipe(customer);
        }

        gameHud.updateMoney(currentMoney);
    }

    public MapHelper getMapHelper(){return mapHelper;}

    public void processPowerupsFromLoad(boolean[] powerups){
        for(int i = 0; i < powerups.length; i++){
            if(powerups[i]){
                if(i == 0){
                    PowerupStatic.powerups.put("SpeedIncr", Boolean.TRUE);
                } else if (i == 1) {
                    PowerupStatic.powerups.put("CookingSpeedIncr", Boolean.TRUE);
                } else if (i == 2) {
                    PowerupStatic.powerups.put("MoneyIncr", Boolean.TRUE);
                } else if (i == 3) {
                    PowerupStatic.powerups.put("CustomerTimerIncr", Boolean.TRUE);
                } else if (i == 4) {
                    PowerupStatic.powerups.put("NewStationsCostDecr", Boolean.TRUE);
                }
            }
        }
    }
}
