package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import cooks.Cook;
import food.FoodItem;
import game.GameScreen;
import stations.*;

import java.util.ArrayList;
import java.util.List;

import static helper.Constants.PPM;

/** The {@link MapHelper} class helps by setting up the map
 * of the game, and providing the {@link OrthogonalTiledMapRenderer}
 * which is used to draw the {@link TiledMap}.*/
public class MapHelper {
    public static boolean fryLocked;
    public static boolean bakeLocked;
    private GameScreen gameScreen;
    private TiledMap tiledMap;
    private static MapHelper INSTANCE;

    private int nextCookID = 0;
    private int nextPrepStationID = 0;
    private int nextCounterStationID = 0;

    public List<PreparationStation> prepStationsList;
    public List<Cook> cooksList;
    public List<CounterStation> counterStationsList;

    /**
     * The {@link MapHelper} constructor.
     * It is {@code private} as it is a Singleton.
     */
    private MapHelper() { }

    /**
     * The getter function to get the {@link #INSTANCE} of the {@link MapHelper}.
     * @return {@link MapHelper}: The single {@link MapHelper} instance.
     */
    public static MapHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapHelper();

        }
        return INSTANCE;
    }

    /**
     * Forgets the old {@link #INSTANCE}, and creates
     * a new {@link #INSTANCE} of the {@link MapHelper}.
     * @return {@link #INSTANCE} : A new {@link MapHelper} instance.
     */
    public static MapHelper newInstance() {
        INSTANCE = new MapHelper();

        return INSTANCE;
    }

    /**
     * Function to set the MapHelper's gameScreen so that it can access
     * relevant information it may need to know.
     * @param gameScreen {@link GameScreen}
     */
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Sets up the map by loading the StationsMap tilemap, and then using
     * the function {@link #parseMapObjects(MapObjects)} to parse and
     * load it into the game.
     * @return The {@link OrthogonalTiledMapRenderer} used to render the Tilemap.
     */
    public OrthogonalTiledMapRenderer setupMap()
    {
        tiledMap = new TmxMapLoader().load("Maps/StationsMap.tmx");
        parseMapObjects(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    /**
     * Creates a Static {@link Body} added to the map that is used
     * to stop the {@link Cook} from moving through certain places.
     * @param polygonMapObject
     */
    private void createStaticBody(PolygonMapObject polygonMapObject)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    /**
     * Creates a Polygon{@link Shape} using the {@link PolygonMapObject}.
     * <br>It is used to create the {@link Shape} for the
     * {@link #createStaticBody(PolygonMapObject)} function for
     * the {@link Body}'s {@link com.badlogic.gdx.physics.box2d.Fixture}.
     * @param polygonMapObject
     * @return
     */
    private Shape createPolygonShape(PolygonMapObject polygonMapObject)
    {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for(int i = 0;i<vertices.length / 2;i++)
        {
            Vector2 current = new Vector2(vertices[i * 2]/PPM, vertices[i*2+1]/PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;

    }

    /**
     * Makes a {@link Body} using a {@link Rectangle} as a base.
     * @param rectangle The {@link Rectangle} for the {@link Body}'s {@link Shape}.
     * @param isStatic If true, then the {@link Body} is stationary.
     *                 If false, then the {@link Body} is not stationary.
     * @return {@link Body} : The {@link Body} created using {@link BodyHelper}.
     */
    public static Body makeBody(Rectangle rectangle, boolean isStatic) {
        return BodyHelper.createBody(rectangle.x + rectangle.getWidth() /2, rectangle.y +rectangle.getHeight()/2,rectangle.getWidth(), rectangle.getHeight(),isStatic, INSTANCE.gameScreen.getWorld());
    }

    /**
     * Loops through all of the {@link MapObjects} and loads them
     * into the {@link GameScreen}'s {@code world} using the
     * other {@link MapHelper} functions.
     * @param mapObjects The {@link MapObjects} of the map.
     */
    private void parseMapObjects(MapObjects mapObjects)
    {
        prepStationsList = new ArrayList<>();
        cooksList = new ArrayList<>();
        counterStationsList = new ArrayList<>();
        for(MapObject mapObject:mapObjects)
        {
            if(mapObject instanceof PolygonMapObject)
            {
                createStaticBody((PolygonMapObject) mapObject);
            }

            if(mapObject instanceof RectangleMapObject)
            {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if(rectangleName.equals("CookStart"))
                {
                    Body body = makeBody(rectangle, false);
                    int cookInd = gameScreen.addCook(new Cook(rectangle.getWidth(), rectangle.getHeight(), body, gameScreen, nextCookID));
                    gameScreen.setCook(cookInd);
                    nextCookID++;
                    continue;
                }

                if(rectangleName.equals("Cook"))
                {
                    Body body = makeBody(rectangle, false);
                    gameScreen.addCook(new Cook(rectangle.getWidth(), rectangle.getHeight(), body, gameScreen, nextCookID));
                    nextCookID++;
                    continue;
                }

                Rectangle normalRect = new Rectangle(rectangle);
                normalRect.setX(normalRect.getX() * PPM);
                normalRect.setY(normalRect.getY() * PPM);

                if(rectangleName.startsWith("Station")) {
                    // Stations
                    rectangleName = rectangleName.substring("Station".length()).toLowerCase();
                    Station station;
                    switch(rectangleName) {
                        case "cut":
                            PreparationStation stationP = new PreparationStation(rectangle, nextPrepStationID);
                            stationP.setID(Station.StationID.cut);
                            gameScreen.addGameEntity(stationP);
                            nextPrepStationID++;
                            prepStationsList.add(stationP);
                            gameScreen.addInteractable(stationP);
                            break;
                        case "fry":
                            stationP = new PreparationStation(rectangle, nextPrepStationID);
                            stationP.setID(Station.StationID.fry);
                            stationP.lock();
                            fryLocked = true;
                            gameScreen.addGameEntity(stationP);
                            nextPrepStationID++;
                            prepStationsList.add(stationP);
                            gameScreen.addInteractable(stationP);
                            break;
                        case "oven":
                            stationP = new PreparationStation(rectangle, nextPrepStationID);
                            stationP.setID(Station.StationID.oven);
                            stationP.lock();
                            bakeLocked = true;
                            gameScreen.addGameEntity(stationP);
                            nextPrepStationID++;
                            prepStationsList.add(stationP);
                            gameScreen.addInteractable(stationP);
                            break;
                        case "counter":
                            CounterStation stationC = new CounterStation(rectangle, nextCounterStationID);
                            stationC.setID(Station.StationID.counter);
                            gameScreen.addGameEntity(stationC);
                            nextCounterStationID++;
                            counterStationsList.add(stationC);
                            gameScreen.addInteractable(stationC);
                            break;
                        case "bin":
                            BinStation stationB = new BinStation(rectangle);
                            stationB.setID(Station.StationID.bin);
                            gameScreen.addInteractable(stationB);
                            break;
                        case "serving":
                            ServingStation stationS = new ServingStation(rectangle);
                            stationS.setID(Station.StationID.serving);
                            gameScreen.addGameEntity(stationS);
                            gameScreen.addServingStation((ServingStation) stationS);
                            ((ServingStation) stationS).setGameScreen(gameScreen);
                            gameScreen.addInteractable(stationS);
                            break;
                        default:
                            station = new Station(rectangle);
                            station.setID(Station.StationID.none);
                            gameScreen.addInteractable(station);
                            break;

                    }
                }

                if (rectangleName.startsWith("Pantry")) {
                    // Pantries
                    rectangleName = rectangleName.substring("Pantry".length());
                    Pantry pantry = new Pantry(rectangle);
                    gameScreen.addInteractable(pantry);
                    switch(rectangleName) {
                        case "Lettuce":
                            pantry.setItem(FoodItem.FoodID.lettuce);
                            break;
                        case "Tomato":
                            pantry.setItem(FoodItem.FoodID.tomato);
                            break;
                        case "Onion":
                            pantry.setItem(FoodItem.FoodID.onion);
                            break;
                        case "Meat":
                            pantry.setItem(FoodItem.FoodID.meat);
                            break;
                        case "Bun":
                            pantry.setItem(FoodItem.FoodID.bun);
                            break;
                        case "Dough":
                            pantry.setItem(FoodItem.FoodID.dough);
                            break;
                        case "Cheese":
                            pantry.setItem(FoodItem.FoodID.cheese);
                            break;
                        case "RawPotato":
                            pantry.setItem(FoodItem.FoodID.rawPotato);
                            break;
                        default:
                            pantry.setItem(FoodItem.FoodID.none);
                            break;
                    }
                }
            }
        }
    }

    /**
     * A dispose function to dispose of information when it is
     * no longer needed.
     */
    public void dispose() {
        tiledMap.dispose();
    }

}
