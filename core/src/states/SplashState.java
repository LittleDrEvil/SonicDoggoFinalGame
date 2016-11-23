package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import managers.GameStateManager;

public class SplashState extends GameState {

    float acc = 0f;
    Texture tex;

    public SplashState(GameStateManager gsm) {
        super(gsm);
        tex = new Texture("splash.png");
    }

    public void update(float delta) {
        acc += delta;
        if (acc >= 0) {
            gsm.setState(GameStateManager.State.PLAY);
        }
    }

    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(tex, Gdx.graphics.getWidth() / 4 - tex.getWidth() / 2, Gdx.graphics.getHeight() / 4 - tex.getHeight() / 2 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void dispose() {
    }
}
