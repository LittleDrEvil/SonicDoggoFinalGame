package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import managers.GameStateManager;
import static states.SplashState.lTime;

public class WinState extends GameState {

    float acc = 0f;
    Texture tex;
    public static int nLives;
    public static int nScore;
    private BitmapFont font;
    public WinState(GameStateManager gsm) {
        super(gsm);
        tex = new Texture("GameScreen.png");
        FileHandle fontFile = Gdx.files.internal("arial.ttf");
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        FreeTypeFontGenerator gener = new FreeTypeFontGenerator(fontFile);
        font = gener.generateFont(parameter);
        gener.dispose();
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
        font.setColor(Color.BLACK);
        font.draw(batch, "WINNER", camera.position.x - 25, camera.position.y);
        font.draw(batch, "Lives = " + states.SplashState.nLives, camera.position.x + 135, camera.position.y+110);
        font.draw(batch, "Score = " + states.SplashState.nScore, camera.position.x - 175, camera.position.y+110);
        batch.end();
    }

    public void dispose() {
    }
}
