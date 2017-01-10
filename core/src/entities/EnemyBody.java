package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import utils.Constants;

public class EnemyBody {
    private String id;
    public Body body1, body2;
    private int nWidth;
    private int  nJump, nDoubleJump, nAirJump;
    private short MaskBit;
    public World world;
    private Vector2 vPos = new Vector2();
    
    
    public EnemyBody(World world, float x, float y,int nWidth, int Height, boolean isStatic, float fRest, short MaskBit, int nFilter){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        WeldJointDef jdef= new WeldJointDef();
        PolygonShape shape = new PolygonShape();
        PolygonShape shape2 = new PolygonShape();
        this.MaskBit = MaskBit;
        vPos.x = x/Constants.PPM;
        vPos.y = y/Constants.PPM;
        
        if(isStatic)
            bdef.type = bdef.type.StaticBody;
        if(!isStatic)
            bdef.type = bdef.type.DynamicBody;
        
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x/Constants.PPM, y/Constants.PPM);
        
        body1 = world.createBody(bdef);
        
        jdef.bodyA = body1;
        
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set( x/Constants.PPM, (y+5)/Constants.PPM);
        
        body2 = world.createBody(bdef);
        
        jdef.bodyB = body2;
        
        jdef.collideConnected = false;
        
        shape.setAsBox(nWidth/Constants.PPM / 2, nWidth/Constants.PPM / 2);
        
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.friction = 3f;
        fdef.filter.categoryBits = 8;
        
        jdef.bodyA.createFixture(fdef);
        
        shape.setAsBox(nWidth/2/Constants.PPM / 2, nWidth/2/Constants.PPM / 2);
        fdef.shape = shape;
        fdef.density = 3f;
        fdef.friction = 5f;
        fdef.filter.categoryBits = 8;
        
        jdef.bodyB.createFixture(fdef).setUserData(this);
        
        jdef.initialize(body1, body2, vPos);
        
        world.createJoint(jdef);
//        
        
        if(this.MaskBit == (short) 4){
            fdef.filter.maskBits = utils.Constants.Bit_Map|utils.Constants.Bit_Enemy;
        }
        
        shape.dispose();    
    }
    
    
    public void hit(){
        System.out.println(id + " : hiteroni");
    }
}
