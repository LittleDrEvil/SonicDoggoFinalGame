/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import entities.EnemyBody;
import entities.PlayerBody;

public class ContListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(isContact(fa,fb)){
            //16 is the map bit, 32 is the death floor bit, 64 is win bit
            if((fa.getUserData().toString().contains("EnemyBody") && fb.getUserData().toString().contains("PlayerBody"))){
                EnemyBody tba = (EnemyBody) fa.getUserData();
                PlayerBody tbb = (PlayerBody) fb.getUserData();
                tba.hit(fa, fb);
            }
            if((fa.getUserData().toString().contains("16") && fb.getUserData().toString().contains("PlayerBody"))){
//                System.out.println(fa.getUserData().toString());
                PlayerBody tbb = (PlayerBody) fb.getUserData();
                tbb.hitMap();
            }
            if((fa.getUserData().toString().contains("32") && fb.getUserData().toString().contains("PlayerBody"))){
//                System.out.println(fa.getUserData().toString());
                PlayerBody tbb = (PlayerBody) fb.getUserData();
                tbb.hitDeath();
            }
            if((fa.getUserData().toString().contains("64") && !fa.getUserData().toString().contains("EnemyBody") && fb.getUserData().toString().contains("PlayerBody"))){
//                System.out.println(fa.getUserData().toString());
//                System.out.println(fb.getUserData().toString());
                PlayerBody tbb = (PlayerBody) fb.getUserData();
                tbb.Win();
            }
            if((fa.getUserData().toString().contains("32") && fb.getUserData().toString().contains("EnemyBody"))){
//                System.out.println(fa.getUserData().toString());
                EnemyBody tbb = (EnemyBody) fb.getUserData();
                tbb.DeathHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        if(fa == null || fb ==null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
    private boolean isContact(Fixture a, Fixture b){
        return true;
    }
//        return(a.getUserData() instanceof BoxBody && b.getUserData() instanceof BoxBody);

    
}
