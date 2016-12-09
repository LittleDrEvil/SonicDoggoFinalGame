package states;

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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import entities.BoxBody;
import entities.PlayerBody;
import java.util.HashSet;
import managers.GameStateManager;
import newpackage.Box2DMain;
import static utils.Constants.PPM;
import utils.TiledObjectUtil;

public class PlayState extends GameState {
    private final float fSCALE = 2f;
    private PlayerBody bbPlayer;
    private BoxBody  bbObj1, bbObj2, arBodies[];
    private Contact contact;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private float fSpeed;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Boolean isLeft = false;
    private Texture texture;
    private int nJump = 0;
    private int height = Box2DMain.V_HEIGHT, width = Box2DMain.V_WIDTH;
    
    
    private int nArrayMax = 10;
    public PlayState(GameStateManager gsm){
        super(gsm);
        world = new World(new Vector2(0,0f), false);
        b2dr = new Box2DDebugRenderer();
        bbPlayer = new PlayerBody(world, "PLAYER", 0, 150, 20);
        bbObj1 = new BoxBody(world, "OBJ1", 20, 20, 10);
        bbObj2 = new BoxBody(world, "OBJ2", -20, -20, 10);
        map = new TmxMapLoader().load("TiledMap.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision").getObjects());
    }
    
    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);
        inputUpdate(delta);
        cameraUpdate();
        
        tmr.setView(camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
        System.out.println(tmr.getUnitScale());
        tmr.render();
        b2dr.render(world, camera.combined.scl(PPM));
    }
    public void resize(int width, int height){
        camera.setToOrtho(false, width/4, height/4);
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
    }
    
    public void cameraUpdate(){
        Vector3 position = camera.position;
        // a + (b - a) * lerp
        // b = target 
        // a = current camera position
        position.x = camera.position.x + (bbPlayer.body.getPosition().x * PPM - camera.position.x) * 0.5f;
        position.y = camera.position.y + (bbPlayer.body.getPosition().y * PPM - camera.position.y) * 0.5f;
//        position.x = 0;
//        position.y = 0;
        camera.position.set(position);
        camera.update();
    }
    public void inputUpdate(float delta){
        int horizontalForce = 0, x=0 , y=0;
       
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x-=5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x+=5;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            y+=5;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y-=5;
        }
            bbPlayer.body.setLinearVelocity(x, y);
    }
}
