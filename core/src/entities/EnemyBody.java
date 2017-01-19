package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import utils.Constants;

public class EnemyBody {

    private String id = "EnemyHead";
    public Body body1, body2;
    private int nWidth;
    private int nJump, nDoubleJump, nAirJump;
    private short MaskBit;
    public World world;
    private Vector2 vPos = new Vector2();
    public boolean bHit = false, bDead = false;
    public int nHit = 0;
    public WeldJointDef jdef = new WeldJointDef();
    public FixtureDef fdef1 = new FixtureDef(), fdef2 = new FixtureDef();
    public Texture tEnemy;

    public EnemyBody(World world, float x, float y, int nWidth, int Height, boolean isStatic, float fRest, short MaskBit, int nFilter) {
        BodyDef bdef = new BodyDef();
        tEnemy = new Texture("Goomba.png");
        PolygonShape shape = new PolygonShape();
        this.world = world;
        this.MaskBit = MaskBit;
        vPos.x = x / Constants.PPM;
        vPos.y = y / Constants.PPM;

        if (isStatic) {
            bdef.type = bdef.type.StaticBody;
        }
        if (!isStatic) {
            bdef.type = bdef.type.DynamicBody;
        }

        bdef.fixedRotation = false;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);

        body1 = world.createBody(bdef);

        jdef.bodyA = body1;

//        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x / Constants.PPM, (y + 6) / Constants.PPM);

        body2 = world.createBody(bdef);

        jdef.bodyB = body2;

        jdef.collideConnected = false;

        shape.setAsBox((nWidth + 5) / Constants.PPM / 2, nWidth / Constants.PPM / 2);

        fdef1.shape = shape;
        fdef1.density = 1f;
        fdef1.friction = 3f;
        fdef1.filter.categoryBits = 8;

        jdef.bodyA.createFixture(fdef1).setUserData(this);

        shape.setAsBox(20 / 2 / Constants.PPM / 2, (nWidth - 3) / 2 / Constants.PPM / 2);
        fdef2.shape = shape;
        fdef2.density = 3f;
        fdef2.friction = 5f;
        fdef2.filter.categoryBits = 8;

        jdef.bodyB.createFixture(fdef2).setUserData(this);

        jdef.initialize(jdef.bodyA, jdef.bodyB, vPos);

        world.createJoint(jdef);

        shape.dispose();
    }

    public void hit(Fixture fa, Fixture fb) {
        if (fa.getBody() == jdef.bodyB) {
            nHit = 1;
        } else  if (fa.getBody() != jdef.bodyB){
            nHit = 2;
        }
    }

    public World Action(World world, PlayerBody pbPlayer) {
        if (nHit == 1) {
            pbPlayer.hitEnemyHead();
            world.destroyBody(body1);
            world.destroyBody(body2);
            bDead = true;
            nHit = 0;
        } else if (nHit == 2) {
            pbPlayer.hitEnemy();
            nHit = 0;
        }
        return world;
    }
}
