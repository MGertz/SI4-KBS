package dk.sdu.student.miger20.asteroid;

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

    // Start Positions
    private float x = -1;
    private float y = -1;

    private int numPoints; // Contains the number of points an asteroid exists of.

    private int type;

    private int life;

    private int spawn; // Contains the number to spawn

    public AsteroidPlugin() {
        this.life = 3;
        this.spawn = 4;

    }

    public AsteroidPlugin(Entity asteroid) {
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);
        this.life = lifePart.getLife()-1;

        this.spawn = 2;

        this.x = positionPart.getX();
        this.y = positionPart.getY();
    }

    @Override
    public void start(GameData gameData, World world) {
        // Check how many to spawn.
        for (int i = 0; i < this.spawn; i++) {

            // Check if life is 3(large)
            // YES: then generate a random x,y coordinate for it.
            // NO: Don't generate a x,y as it will get it from the previously asteroid.
            if (this.life == 3) {
                this.x = (float) Math.random() * gameData.getDisplayWidth();
                this.y = (float) Math.random() * gameData.getDisplayHeight();
            }

            // Add entities to the world
            this.asteroid = createAsteroid(gameData);
            world.addEntity(this.asteroid);

        }

    }

    private Entity createAsteroid(GameData gameData) {
        float deceleration = 10;
        float acceleration = 100;
        float maxSpeed = 0;
        float rotationSpeed = 0;
        float radians = (float) (Math.random() * Math.PI*2);

        Entity asteroid = new Asteroid();

        if (this.life == 3) {
            //System.out.println("Large Generated");
            asteroid.setRadius(15);
            this.numPoints = 12;
            maxSpeed = (float) Math.random() * (20-30) + 20;
        } else if (this.life == 2) {
            //System.out.println("Medium Generated");
            asteroid.setRadius(10);
            this.numPoints=10;
            maxSpeed = (float) Math.random() * (50-60) + 50;
        } else { // SMALL
            //System.out.println("Small Generated");
            asteroid.setRadius(5);
            this.numPoints=8;
            maxSpeed = (float) Math.random() * (70-100) + 70;
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
        asteroid.add(new PositionPart(this.x, this.y, radians));
        asteroid.add(new LifePart(this.life,0));

        float[] dists = new float[this.numPoints];
        for (int i = 0; i < this.numPoints; i++) {
            dists[i] = (float) Math.random() + (asteroid.getRadius()/2 - asteroid.getRadius()) + asteroid.getRadius();
        }

        asteroid.setDists(dists);

        return asteroid;

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(this.asteroid);
    }
}
