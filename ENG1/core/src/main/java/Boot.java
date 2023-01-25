package main.java;

import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends ApplicationAdapter {

  public static Boot INSTANCE;
  private int widthScreen, heightScreen;
  private OrthographicCamera orthographicCamera;
  public Boot()
  {
      INSTANCE = this;
  }

  @Override
  public void create()
  {
      this.widthScreen = Gdx.graphics.getWidth();
      this.heightScreen = Gdx.graphics.getHeight();
      this.orthographicCamera = new OrthographicCamera();
      this.orthographicCamera.setToOrtho(false, widthScreen,heightScreen);
      setScreen(new GameScreen(orthographicCamera));
  }

}
