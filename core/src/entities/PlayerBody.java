/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import java.util.HashSet;
import java.util.Set;
import utils.Constants;

/**
 *
 * @author karnh7634
 */
public class PlayerBody {
    public Body body;
    public String id;
    public int nWidth;
    public float fSpeed, fGravity  = -0.1f;
    public PlayerBody(World world, String id, float x, float y, int nWidth){
        this.id = id;
        this.nWidth = nWidth;
        createBoxBody(world, x , y);
//        this.body.setLinearDamping(10);
    }
    
    private void createBoxBody(World world, float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x/Constants.PPM, y/Constants.PPM);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(nWidth/Constants.PPM / 2, nWidth/Constants.PPM / 2);
        
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.friction = 0f;
        
        this.body = world.createBody(bdef);
        this.body.setAngularDamping(3);
        this.body.createFixture(fdef).setUserData(this);
        
    }
    
    
    public void hit(){
        System.out.println(id + " : hiteroni");
    }
    
    public void inputUpdate(float delta, float fDy){
        float horizontalForce = 0, x=0 , y=0;
        fSpeed += fGravity;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce++;
        }
//        if(bbPlayer.body.getLinearVelocity().y == fSpeed)
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            this.body.applyForceToCenter(0,100,true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            this.body.applyForceToCenter(0,-100,true);
        }
        this.body.setLinearVelocity(horizontalForce*5, this.body.getLinearVelocity().y);            
    }
}
