package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import managers.GameStateManager;

public class SplashState extends GameState {

    float acc = 0f;
    Texture tex;
    public static int nLives;
    public static int nScore;
    public static long lstartTime = System.currentTimeMillis(), lTime = 0;
    public SplashState(GameStateManager gsm) {
        super(gsm);
        tex = new Texture("GameScreen.png");
    }

    public void update(float delta) {
        if (Gdx.input.isButtonPressed(Input.Keys.ENTER)) {
            nLives = 3;
            gsm.setState(GameStateManager.State.PLAY);
        }
    }

    public void render() {
        Vector3 position = new Vector3();
        camera.position.x = 0;
        camera.position.y = 0;
        camera.position.set(position);
        camera.update();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(tex, -180, -100, Gdx.graphics.getWidth()*.5f, Gdx.graphics.getHeight()*.5f);
        batch.end();
    }

    public void dispose() {
    }
}
