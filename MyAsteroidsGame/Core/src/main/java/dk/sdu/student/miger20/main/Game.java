package dk.sdu.student.miger20.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IEntityPostProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;
import dk.sdu.student.miger20.components.IProcessor;
import dk.sdu.student.miger20.components.PluginInjection;
import dk.sdu.student.miger20.managers.GameInputProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("Game")
public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private final World world = new World();

    private AnnotationConfigApplicationContext components;

    public Game() {
        this.components = new AnnotationConfigApplicationContext();
        this.components.scan("dk.sdu.student.miger20.components");
        this.components.refresh();
    }

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        PluginInjection pluginInjection = components.getBean(PluginInjection.class);
        pluginInjection.startPlugins(gameData, world);

//        PluginInjection pluginInjection = (PluginInjection) components.getBean("pluginInjector");
//        pluginInjection.startPlugins(gameData, world);

//        ((PluginInjection) components.getBean("pluginInjector")).startPlugins(gameData, world);
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
        ((IProcessor) components.getBean("processorInjector")).process(gameData, world);
        ((IProcessor) components.getBean("postProcessorInjector")).process(gameData, world);
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

    /**
     * Find all IEntityProcessingServices
     * @return Collection
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    /**
     * Find all IEntityPostProcessingServices
     * @return Collection
     */
    private Collection<? extends IEntityPostProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IEntityPostProcessingService.class);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
