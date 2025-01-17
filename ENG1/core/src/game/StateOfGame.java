package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import food.FoodItem;
import food.FoodStack;
import interactions.Interactions;
import stations.PreparationStation;
import stations.ServingStation;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the current state of the game, used for creating savedata.
 */
public class StateOfGame {
    public static StateOfGame INSTANCE;

    //Cooks
    public FoodStack[] cooksFoodStacks;
    //Customers
    public String[] customerRequests;
    public int[] customerPositions;
    public int customersWaiting;

    //Preparation Stations
    public FoodItem.FoodID[] stationFoods;
    public PreparationStation.StationState[] stationStates;
    public float[] stationProgresses;
    public boolean[] stationUsage;
    public Interactions.InteractionResult[] interactions;
    public float stationBurns[];
    //Counters
    public FoodStack[] countersFoodStacks;
    //GameScreen
    public int money;
    public int reputation;
    public boolean[] powerups;
    public int customersLeft;
    public boolean endless;
    public int customersServedState;
    public int secondsTime;
    public int minutesTime;
    public int hoursTime;
    public int numberOfChefs;
    public List<Float> chefPositions;

    public boolean ovensLocked;
    public boolean fryersLocked;

    public StateOfGame(){
        cooksFoodStacks = new FoodStack[3];
        stationFoods = new FoodItem.FoodID[6];
        stationStates = new PreparationStation.StationState[6];
        stationProgresses = new float[6];
        stationUsage = new boolean[6];
        stationBurns = new float[6];
        interactions= new Interactions.InteractionResult[6];
        powerups = new boolean[5];
        countersFoodStacks = new FoodStack[2];
        money = 0;
        ovensLocked = true;
        fryersLocked = true;
        customersServedState = 0;
        chefPositions = new ArrayList<>();
        customerRequests = new String[4];
        customerPositions = new int[4];
        customersWaiting = 0;
    }

    public static StateOfGame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StateOfGame();

        }
        return INSTANCE;
    }

    public static StateOfGame newInstance() {
        INSTANCE = new StateOfGame();

        return INSTANCE;
    }
}
