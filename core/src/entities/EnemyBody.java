package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import utils.Constants;

public class EnemyBody {
    private String id;
    public Body body;
    private int nWidth;
    private int  nJump, nDoubleJump, nAirJump;
    private short MaskBit;
    
    
    public EnemyBody(World world, float x, float y,int nWidth, int Height, boolean isStatic, float fRest, short MaskBit, int nFilter){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        this.MaskBit = MaskBit;
        
        
        if(isStatic)
            bdef.type = bdef.type.StaticBody;
        if(!isStatic)
            bdef.type = bdef.type.DynamicBody;
        
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x/Constants.PPM, y/Constants.PPM);
        
        this.body = world.createBody(bdef);
        
        
        shape.setAsBox(nWidth/Constants.PPM / 2, nWidth/Constants.PPM / 2);
        
        
        
        
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.friction = 3f;
        fdef.filter.categoryBits = this.MaskBit;
        
        if(this.MaskBit == (short) 4){
            fdef.filter.maskBits = utils.Constants.Bit_Map|utils.Constants.Bit_Enemy;
        }
        
        this.body.createFixture(fdef).setUserData(this);
         
        shape.dispose();
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
                if(nDoubleJump == 1){
                    this.body.setLinearVelocity(this.body.getLinearVelocity().x, 5);
                    System.out.print("yeah");
                } else
                this.body.applyForceToCenter(0,40,true);
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
