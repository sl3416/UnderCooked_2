package game;

import com.badlogic.gdx.utils.Array;
import food.FoodItem;
import food.FoodStack;
import stations.PreparationStation;

import java.util.ArrayList;
import java.util.List;

public class StateOfGame {
    public static StateOfGame INSTANCE;

    //Cooks
    public FoodStack[] cooksFoodStacks;
    //Preparation Stations
    public FoodItem.FoodID[] stationFoods;
    public PreparationStation.StationState[] stationStates;
    public float[] stationProgresses;
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
        cooksFoodStacks = new FoodStack[100];
        stationFoods = new FoodItem.FoodID[6];
        stationStates = new PreparationStation.StationState[6];
        stationProgresses = new float[6];
        powerups = new boolean[5];
        countersFoodStacks = new FoodStack[2];
        money = 0;
        ovensLocked = true;
        fryersLocked = true;
        customersServedState = 0;
        chefPositions = new ArrayList<>();
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
