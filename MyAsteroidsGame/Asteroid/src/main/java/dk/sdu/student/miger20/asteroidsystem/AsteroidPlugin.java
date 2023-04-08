package dk.sdu.student.miger20.asteroidsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IGamePluginService;


public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    private int numPoints; // Contains the number of points an asteroid exists of.

    private int type;

    private int life;

    public AsteroidPlugin() {
        this(3);
    }

    public AsteroidPlugin(int life) {
        this.life = life;
    }


    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        this.asteroid = createAsteroid(gameData);
        world.addEntity(this.asteroid);
    }

    private Entity createAsteroid(GameData gameData) {
        float deceleration = 10;
        float acceleration = 100;
        float maxSpeed = 0;
        float rotationSpeed = 0;
        float x = MathUtils.random((float)gameData.getDisplayWidth());
        float y = MathUtils.random((float)gameData.getDisplayHeight());
        float radians = MathUtils.random(MathUtils.PI2);

        Entity asteroid = new Asteroid();

        if (this.life == 3) {
            System.out.println("Large Generated");
            asteroid.setRadius(15);
            this.numPoints = 12;
            maxSpeed = MathUtils.random(20,30);
        } else if (this.life == 2) {
            System.out.println("Medium Generated");
            asteroid.setRadius(10);
            this.numPoints=10;
            maxSpeed = MathUtils.random(50,60);
        } else { // SMALL
            System.out.println("Small Generated");
            asteroid.setRadius(5);
            this.numPoints=8;
            maxSpeed = MathUtils.random(70,100);
        }

        int[] colors = new int[4];
        colors[0] = 1;
        colors[1] = 1;
        colors[2] = 1;
        colors[3] = 1;
        asteroid.setColors(colors);

        asteroid.setShapeX(new float[this.numPoints]);
        asteroid.setShapeY(new float[this.numPoints]);

        asteroid.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(this.life,0));

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
