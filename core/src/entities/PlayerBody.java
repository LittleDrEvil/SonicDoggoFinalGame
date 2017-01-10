/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
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
    public int nWidth, nJump, nDoubleJump, nDouble, nAirJump;
    public short MaskBit;
    public String sName = "Player";
    
    public PlayerBody(World world, String id, float x, float y, int nWidth, short MaskBit){
        this.id = id;
        this.nWidth = nWidth;
        this.MaskBit = MaskBit;
        createBoxBody(world, x , y);
//        this.body.setLinearDamping(10);
    }
    
    private void createBoxBody(World world, float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x/Constants.PPM, y/Constants.PPM);
//        RevoluteJointDef rj = new RevoluteJointDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(nWidth/Constants.PPM / 2, nWidth/Constants.PPM / 2);
        
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.friction = 0f;
        
        fdef.filter.categoryBits = 4;
        
        fdef.filter.maskBits = utils.Constants.Bit_Map|utils.Constants.Bit_Enemy;
        
        this.body = world.createBody(bdef);
        this.body.createFixture(fdef).setUserData(this);
        System.out.println(this.body.toString());
        
    }
    
    
    public void hit(){
        System.out.println(id + " : hiteroni");
    }
    
    public void inputUpdate(float delta){
        if(this.MaskBit == (short) 4){
        float horizontalForce = 0, x=0 , y=0;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce--;
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce++;
        }
        
        if(this.body.getLinearVelocity().y == 0) {  nJump = 0; nDoubleJump = 0;}
        
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(nJump<3){
                this.body.applyForceToCenter(0, 40, true);
                nJump++;
            }
        } else {
            if(this.body.getLinearVelocity().y>0){
                if(nDoubleJump==0){
                    nJump = 0;
                    nDoubleJump++;
                }
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            this.body.applyForceToCenter(0,40,true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            this.body.applyForceToCenter(0,-100,true);
        }
        this.body.setLinearVelocity(horizontalForce*5, this.body.getLinearVelocity().y);  
//        System.out.println(this.body.getLinearVelocity().y + " " + nJump + " " + nDoubleJump);
        }
    }
}
