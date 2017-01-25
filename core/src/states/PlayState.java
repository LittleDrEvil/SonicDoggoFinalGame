package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import entities.BoxBody;
import entities.EnemyBody;
import entities.PlayerBody;
import handlers.ContListener;
import managers.GameStateManager;
import newpackage.Box2DMain;
import static utils.Constants.PPM;
import utils.TiledObjectUtil;

public class PlayState extends GameState {

    private final float fSCALE = 2f;
    private PlayerBody bbPlayer;
    private EnemyBody[] arebEnemies = new EnemyBody[7];
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private float fSpeed = 0, fGravity = -0.1f;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Boolean isLeft = false;
    private Texture texture;
    private int nJump = 0, arnPos[] = new int[14], arnX[] = new int[7], arnY[] = new int[7];
    private int height = Box2DMain.V_HEIGHT, width = Box2DMain.V_WIDTH;
    private boolean bDead;
    private FileHandle handle = Gdx.files.internal("spawnpoints.txt");
    private String strPos, arstrPoses[] = new String[14];

    public PlayState(GameStateManager gsm) {
        super(gsm);
        this.world = new World(new Vector2(0, -10f), false);
        this.world.setContactListener(new ContListener());
        b2dr = new Box2DDebugRenderer();
//        System.out.println(handle.readString());
        strPos = handle.readString();
        arstrPoses = strPos.split(" ");
        for (int i = 0; i < 14; i++) {
            arnPos[i] = (Integer.parseInt(arstrPoses[i]));
        }
        for (int i = 0; i < 14; i++) {
            if(arnX[i/2] == 0 && i%2==0){
                arnX[i/2] = arnPos[i];
            } else if(arnY[i/2] == 0){
                arnY[i/2] = arnPos[i];
            }
        }
        
        for (int i = 0; i < 7; i++) {
            arebEnemies[i] = new EnemyBody(world, arnX[i], arnY[i], 10, 10, false, 0f, utils.Constants.Bit_Enemy, 8);
        }
        
        bbPlayer = new PlayerBody(world, "Player", 20, 450, 16, 20, utils.Constants.Bit_Player);
        texture = new Texture("Luigi.png");
        map = new TmxMapLoader().load("TiledMap.tmx");
        
        tmr = new OrthogonalTiledMapRenderer(map);
        
        TiledObjectUtil.parseTiledObjectLayer(world, map, "map");
        TiledObjectUtil.parseTiledObjectLayer(world, map, "death");
        TiledObjectUtil.parseTiledObjectLayer(world, map, "win");
        resize(width, height);
    }
    
    public void Map(){
        
    }

    @Override
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();
        bbPlayer.inputUpdate(delta);
        tmr.setView(camera);
        if(bbPlayer.bDead == true || Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)){
            System.out.println("Reset");
            gsm.setState(GameStateManager.State.PLAY);
            bbPlayer.bDead = false;
            states.SplashState.nLives--;
        }
        if(states.SplashState.nLives <= 0){
            gsm.setState(GameStateManager.State.OVER);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tmr.render();
        world = bbPlayer.Death(world);
        batch.begin();
        if (!bbPlayer.bDead) {
            if (bbPlayer.bLeft) {
                batch.draw(texture, bbPlayer.body.getPosition().x * PPM - 8, bbPlayer.body.getPosition().y * PPM - 10,
                        16, 20, 0, 0, 16, 20, true, false);
            }
            if (!bbPlayer.bLeft) {
                batch.draw(texture, bbPlayer.body.getPosition().x * PPM - 8, bbPlayer.body.getPosition().y * PPM - 10,
                        16, 20, 0, 0, 16, 20, false, false);
            }
        }
        for (int i = 0; i < 7; i++) {
            if (!arebEnemies[i].bDead) {
                batch.draw(arebEnemies[i].tEnemy, arebEnemies[i].body1.getPosition().x * PPM - 8, arebEnemies[i].body1.getPosition().y * PPM - 5, 16, 16);
            }
            world = arebEnemies[i].Action(world, bbPlayer);
        }
        batch.end();
        
        b2dr.render(world, camera.combined.scl(PPM));
        
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2f, height / 2);
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void cameraUpdate() {
        Vector3 position = camera.position;
        // a + (b - a) * lerp
        // b = target 
        // a = current camera position
        if (bbPlayer.body.getPosition().x >= 180 / PPM) {
            position.x = camera.position.x + (bbPlayer.body.getPosition().x * PPM - camera.position.x) * 0.5f;
        }
        if (bbPlayer.body.getPosition().y > (280 / PPM)) {
            position.y = camera.position.y + (bbPlayer.body.getPosition().y * PPM - camera.position.y) * 0.5f;
        }

        camera.position.set(position);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
}
