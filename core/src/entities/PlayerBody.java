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
    public float nX, nY;
    public short MaskBit;
    public String sName = "Player";
    public boolean bJump, bDone, bHit;

    public PlayerBody(World world, String id, float x, float y, int nWidth, short MaskBit) {
        nX = x;
        nY = y;
        this.id = id;
        this.nWidth = nWidth;
        this.MaskBit = MaskBit;
        createBoxBody(world, x, y);
//        this.body.setLinearDamping(10);
    }

    private void createBoxBody(World world, float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(nWidth / Constants.PPM / 2, nWidth / Constants.PPM / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.friction = 0f;
        fdef.restitution = 0f;
        fdef.filter.categoryBits = 4;

        fdef.filter.maskBits = utils.Constants.Bit_Map | utils.Constants.Bit_Enemy;

        this.body = world.createBody(bdef);
        this.body.createFixture(fdef).setUserData(this);

    }

    public void hitEnemy() {
        System.out.println(id + " : hiteroni");
    }

    public void hitMap() {
        if(nDouble == 0)
            System.out.println("hit " + nJump);
        nJump = 0;
        nDouble = 0;
//        System.out.println((int)this.body.getLinearVelocity().y);
    }

    public void inputUpdate(float delta) {
        if (this.MaskBit == (short) 4) {
            float fMovement = 0, x = 0, y = 0;

            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                fMovement--;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                fMovement++;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (nJump <= 3) {
                    if (nJump == 0) 
                        this.body.setLinearVelocity(this.body.getLinearVelocity().x, 0.1f);
                    nJump++;
                    this.body.applyForceToCenter(0, 35, true);
                }
            } else {
                if (nJump > 0 && nDouble != 1) {
                    nDouble++;
                    nJump = 0;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                this.body.applyForceToCenter(0, 40, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.body.applyForceToCenter(0, -10, true);
            }
            this.body.setLinearVelocity(fMovement * 5, this.body.getLinearVelocity().y);
        }
    }
}
