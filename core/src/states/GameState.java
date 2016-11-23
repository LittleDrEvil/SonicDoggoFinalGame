package states;

import box2d.Box2DMain;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import managers.GameStateManager;

public abstract class GameState {
    
    // References
    protected GameStateManager gsm;
    protected Box2DMain app;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    
    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        this.app = gsm.box2dmain();
        batch = app.getBatch();
        camera = app.getCamera();
    }
    
    public void resize(int w, int h){
        camera.setToOrtho(false,w ,h);
    }
    
    public abstract void update(float delta);
    public abstract void render();
    public abstract void dispose();
    
}
