package dk.sdu.student.miger20.asteroid;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;

public class AsteroidControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            if (lifePart.isIsHit()) {
                // Remove asteroids from world
                world.removeEntity(asteroid);

                // Check if the asteroid which were killed, has a life of 1, which means the smallest, if not, spawn 2 new asteroids.
                if (lifePart.getLife() != 1 ) {
                    IGamePluginService asteroidPlugin = new AsteroidPlugin(asteroid);
                    asteroidPlugin.start(gameData, world);
                }

            }

            updateShape(asteroid);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();

        float radians = positionPart.getRadians();

        float angle = 0;
        float radius = entity.getRadius();

        float[] dists = entity.getDists();

        for (int i=0; i < shapex.length ; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * dists[i];
            shapey[i] = y + (float) Math.sin(angle + radians) * dists[i];

            angle += Math.PI*2 / shapex.length;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
