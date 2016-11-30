package entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import utils.Constants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author karnh7634
 */
public class BoxBody {
    public Body body;
    public String id;
    public int nWidth;
    public BoxBody(World world, String id, float x, float y, int nWidth){
        this.id = id;
        this.nWidth = nWidth;
        createBoxBody(world, x , y);
        body.setLinearDamping(20f);
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
        
        this.body = world.createBody(bdef);
        this.body.createFixture(fdef).setUserData(this);
        
    }
    
    
    public void hit(){
        System.out.println(id + " : hiteroni");
    }
}
