package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

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

    public void saveToJSON(){
        Json json = new Json();
        String write = json.prettyPrint(StateOfGame.getInstance());
        FileHandle file = Gdx.files.local("save.txt");
        file.writeString(write, false);
    }

    public void loadFromJSON(GameScreen gameScreen){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.txt");
        String read = file.readString();
        StateOfGame game = json.fromJson(StateOfGame.class, read);
        gameScreen.loadVariables(game);
    }
}
