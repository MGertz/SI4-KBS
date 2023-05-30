package dk.sdu.student.miger20.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	public static void main(String[] args) {

		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("MyAsteroidsGame");
		cfg.setWindowedMode(1920/2, 1080/2);
		cfg.setResizable(false);

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.scan("dk.sdu.student.miger20.main");
		applicationContext.refresh();

		new Lwjgl3Application((ApplicationListener)( applicationContext.getBean("Game", cfg)));
	}
}