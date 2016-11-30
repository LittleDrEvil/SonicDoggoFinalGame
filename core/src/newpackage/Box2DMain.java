package newpackage;





import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import managers.GameStateManager;
import static utils.Constants.PPM;
import utils.TiledObjectUtil;

public class Box2DMain extends ApplicationAdapter {
    //DEBUG
    private boolean DEBUG = false;
    
    //Game Info
    public static final String TITLE = "Game";
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 480;
    public static final float SCALE = 2.0f;
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private GameStateManager gsm;
    
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, platform;
    private Texture texture;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);
        batch = new SpriteBatch();
        gsm = new GameStateManager(this);
    }

    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render();
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        gsm.resize((int) (width/SCALE), (int) (height/SCALE));
    }

    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
    }
    
    public OrthographicCamera getCamera() {
        return camera;
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }

    
}