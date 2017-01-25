package managers;

import newpackage.Box2DMain;
import java.util.Stack;
import states.GameOverState;
import states.GameState;
import states.PlayState;
import states.SplashState;

public class GameStateManager {
    
    // Main Reference
    private final Box2DMain app;
    
    private Stack<GameState> states;
    
    public enum State {
        SPLASH,
        PLAY,
        OVER
    }
    
    public GameStateManager(final Box2DMain app){
        this.app = app;
        this.states = new Stack<GameState>();
        this.setState(State.SPLASH);
    }
    
    public Box2DMain box2dmain() {
        return app;
    }
    
    public void update(float delta){
        states.peek().update(delta);
    }
    
    public void render(){
        states.peek().render();
    }
    
    public void dispose(){
        for(GameState gs : states){
            gs.dispose();
        }
    }
    
    public void resize(int w ,int h){
        states.peek().resize(w,h);
    }
    
    public void setState(State state){
        if(states.size() >= 1) states.pop().dispose();
        states.push(getState(state));
    }
    
    private GameState getState(State state){
        switch(state){
            case SPLASH: return new SplashState(this);
            case PLAY: return new PlayState(this);
            case OVER: return new GameOverState(this);
        }
        return null;
    }
}
