package dk.sdu.student.miger20.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MyAsteroidsGame";
		cfg.width = (1920/3);
		cfg.height = (1080/3);
		cfg.useGL30 = false;
		cfg.resizable = true;
		//cfg.fullscreen = true;
		
		new LwjglApplication(new Game(), cfg);
		
	}
	
}
