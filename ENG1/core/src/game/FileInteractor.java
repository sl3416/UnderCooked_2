package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * A {@link FileInteractor} to allow us to save and load in
 * json format to a save txt file.
 */
public class FileInteractor {

    public static FileInteractor INSTANCE;

    public static FileInteractor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileInteractor();

        }
        return INSTANCE;
    }

    public static FileInteractor newInstance() {
        INSTANCE = new FileInteractor();

        return INSTANCE;
    }

    /**
    * Save all the savadata to a text file in the json format.
    */
    public void saveToJSON(){
        Json json = new Json();
        String write = json.prettyPrint(StateOfGame.getInstance());
        FileHandle file = Gdx.files.local("save.txt");
        file.writeString(write, false);
    }

    /**
     * retrieve previously stored data in the json format from the
     * save.txt file.
     */
    public void loadFromJSON(GameScreen gameScreen){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.txt");
        String read = file.readString();
        StateOfGame game = json.fromJson(StateOfGame.class, read);
        gameScreen.loadVariables(game);
    }
}
