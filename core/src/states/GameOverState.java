package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import managers.GameStateManager;

public class GameOverState extends GameState {

    float acc = 0f;
    Texture tex;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        tex = new Texture("GameOver.jpg");
    }

    public void update(float delta) {
        if (Gdx.input.isButtonPressed(Input.Keys.ENTER)) {
            gsm.setState(GameStateManager.State.SPLASH);
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
        batch.draw(tex, -350, -250, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void dispose() {
    }
}
