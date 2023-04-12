package dk.sdu.student.miger20.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.student.miger20.bullet.BulletControlSystem;
import dk.sdu.student.miger20.bullet.BulletPlugin;
import dk.sdu.student.miger20.collision.CollisionDetector;
import dk.sdu.student.miger20.asteroidsystem.AsteroidControlSystem;
import dk.sdu.student.miger20.asteroidsystem.AsteroidPlugin;
import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.GameKeys;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.ShootingPart;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.services.IPostEntityProcessingService;
import dk.sdu.student.miger20.enemysystem.EnemyControlSystem;
import dk.sdu.student.miger20.enemysystem.EnemyPlugin;
import dk.sdu.student.miger20.managers.GameInputProcessor;
import dk.sdu.student.miger20.playersystem.Player;
import dk.sdu.student.miger20.playersystem.PlayerControlSystem;
import dk.sdu.student.miger20.playersystem.PlayerPlugin;

import java.util.ArrayList;
import java.util.List;

import static dk.sdu.student.miger20.asteroidsystem.AsteroidPlugin.LARGE;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private final List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private final List<IPostEntityProcessingService> entityPostProcessors = new ArrayList<>();
    private final List<IGamePluginService> entityPlugins = new ArrayList<>();
    private final World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );


        IPostEntityProcessingService collisionProcess = new CollisionDetector();
        this.entityPostProcessors.add(collisionProcess);

        /**
         * Bullet ProcessingService
         */
        IEntityProcessingService bulletProcess = new BulletControlSystem();
        this.entityProcessors.add(bulletProcess);



        /**
         * Player Plugin
         */
        IGamePluginService playerPlugin = new PlayerPlugin();
        IEntityProcessingService playerProcess = new PlayerControlSystem();
        entityPlugins.add(playerPlugin);
        entityProcessors.add(playerProcess);


        /**
         * Enemy Plugin
         */
        IGamePluginService enemyPlugin = new EnemyPlugin();
        IEntityProcessingService enemyProcess = new EnemyControlSystem();
        entityPlugins.add(enemyPlugin);
        entityProcessors.add(enemyProcess);

        /**
         * Asteroid ProcessingService
         */
        IEntityProcessingService asteroidProcess = new AsteroidControlSystem();
        entityProcessors.add(asteroidProcess);


        /**
         * Asteroid Plugin
         */
        for (int i = 0; i < 4; i++) {
            IGamePluginService asteroidPlugin = new AsteroidPlugin();
            entityPlugins.add(asteroidPlugin);
        }




        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }


    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {

        /**
         * Detect if some plugin is shooting or not.
         */
        for (Entity entity : world.getEntities()) {
            try {
                ShootingPart shootingPart = entity.getPart(ShootingPart.class);

                if (shootingPart.getShooting()) {
                    IGamePluginService bulletPlugin = new BulletPlugin(entity);
                    this.entityPlugins.add(bulletPlugin);
                    bulletPlugin.start(gameData, world);
                }

            } catch (NullPointerException error) {
            }
        }


        // Update EntityProcessingService
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }

        // Update PostEntityProcessingService
        for (IPostEntityProcessingService postEntityProcessingService : entityPostProcessors) {
            postEntityProcessingService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            int[] colors = entity.getColors();
            sr.setColor(colors[0],colors[1],colors[2],colors[3]);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
