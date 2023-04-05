package dk.sdu.mmmi.cbse.asteroidsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;


public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    private int numPoints; // Contains the number of points an asteroid exists of.

    private int type;

    public AsteroidPlugin() {
        this(LARGE);
    }

    public AsteroidPlugin(int type) {
        this.type = type;
    }


    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        this.asteroid = createAsteroid(gameData);
        world.addEntity(this.asteroid);
    }

    private Entity createAsteroid(GameData gameData) {
        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 0;
        float rotationSpeed = 0;
        float x = MathUtils.random((float)gameData.getDisplayWidth());
        float y = MathUtils.random((float)gameData.getDisplayHeight());
        float radians = MathUtils.random(MathUtils.PI2);

        Entity asteroid = new Asteroid();

        if (this.type == LARGE) {
            System.out.println("Large Generated");
            asteroid.setRadius(25);
            this.numPoints = 12;
            maxSpeed = MathUtils.random(20,30);
        } else if (this.type == MEDIUM) {
            System.out.println("Medium Generated");
            asteroid.setRadius(15);
            this.numPoints=10;
            maxSpeed = MathUtils.random(50,60);
        } else { // SMALL
            System.out.println("Small Generated");
            asteroid.setRadius(10);
            this.numPoints=8;
            maxSpeed = MathUtils.random(70,100);
        }

        asteroid.setShapeX(new float[this.numPoints]);
        asteroid.setShapeY(new float[this.numPoints]);

        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));

        float[] dists = new float[this.numPoints];
        for (int i = 0; i < this.numPoints; i++) {
            dists[i] = MathUtils.random(asteroid.getRadius()/2, asteroid.getRadius());
        }

        asteroid.setDists(dists);

        return asteroid;

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(this.asteroid);
    }
}