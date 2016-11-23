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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import java.util.HashSet;
import java.util.Set;
import managers.GameStateManager;
import static utils.Constants.PPM;
import utils.TiledObjectUtil;

public class PlayState extends GameState {

    
    
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body body1, body2;
    
    private Body body3, body4;
    
    private Texture texture;
    
    public PlayState(GameStateManager gsm){
        super(gsm);
        
        world = new World(new Vector2(0,-9.8f), false);
        b2dr = new Box2DDebugRenderer();
        
        body1 = createBox(140,160,32,32, false, true);
        body2 = createBox(140,130,64,32,true, true);
        
        
        texture = new Texture("0.png");
        
       map = new TmxMapLoader().load("MapTest2.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
        initBodies();
    }
    
    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        world.step(1/60f, 6, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.X)){
        body3.setType(BodyDef.BodyType.DynamicBody);
        }
        inputUpdate();
        
        cameraUpdate();
        
        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(texture, body1.getPosition().x * PPM - texture.getWidth()/2, body1.getPosition().y * PPM - texture.getHeight()/2);
        batch.end();
        tmr.render();
        
        b2dr.render(world, camera.combined.scl(PPM));
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
        position.x = camera.position.x + (body1.getPosition().x * PPM - camera.position.x) * 0.1f;
        position.y = camera.position.y + (body1.getPosition().y * PPM - camera.position.y) * 0.1f;
        camera.position.set(position);
        camera.update();
    }
    
    private void initBodies(){
        body3 = createBox(140,250,32,32,true, false);
        body4 = createCircle(140,250,12,false, false);
        
        RevoluteJointDef rDef = new RevoluteJointDef();
        rDef.bodyA = body3;
        rDef.bodyB = body4;
        rDef.collideConnected = false;
        rDef.localAnchorA.set(0,32/PPM);
        rDef.localAnchorB.set(32/PPM,0);
        world.createJoint(rDef);
    }
    
    public Body createBox(int nX, int nY, int nWidth, int nHeight, boolean isStatic, boolean fixedRotation){
        Body pBody;
        BodyDef def = new BodyDef();
        
        if(isStatic) 
            def.type = BodyDef.BodyType.StaticBody;
        else 
            def.type = BodyDef.BodyType.DynamicBody;
        
        def.position.set(nX/PPM, nY/PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(nWidth / 2 / PPM, nHeight / 2 / PPM);
        
        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
    
    public Body createCircle(int x, int y, int radius, boolean isStatic, boolean fixedRotation) { 
        
        Body cBody; 
        BodyDef def = new BodyDef();    
        
        if(isStatic)def.type = BodyDef.BodyType.StaticBody;    
        else def.type = BodyDef.BodyType.DynamicBody;  
        def.fixedRotation = fixedRotation; 
        def.position.set(x/PPM, y/PPM);     
        cBody = world.createBody(def);     
        CircleShape shape = new CircleShape();   
        shape.setRadius(radius/PPM);
        cBody.createFixture(shape,1.0f);      
        shape.dispose();      
        return cBody;   
    }
    
    public void inputUpdate(){
        int horizontalForce = 0;
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -=1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce +=1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            body1.applyForceToCenter(0, 300, false);
        }
        
        body1.setLinearVelocity(horizontalForce * 5, body1.getLinearVelocity().y);
    }
}
