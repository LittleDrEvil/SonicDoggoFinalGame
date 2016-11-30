package box2d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import newpackage.Box2DMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = Box2DMain.TITLE;
                config.width = Box2DMain.V_WIDTH;
                config.height = Box2DMain.V_HEIGHT;
                config.backgroundFPS = 60;
                config.foregroundFPS = 60;
		new LwjglApplication(new Box2DMain(), config);
	}
}
