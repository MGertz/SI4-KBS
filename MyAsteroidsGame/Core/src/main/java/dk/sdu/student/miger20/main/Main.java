package dk.sdu.student.miger20.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {

		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("MyAsteroidsGame");
		cfg.setWindowedMode(1920/2, 1080/2);
		cfg.setResizable(false);

		new Lwjgl3Application(new Game(), cfg);
	}
	
}
