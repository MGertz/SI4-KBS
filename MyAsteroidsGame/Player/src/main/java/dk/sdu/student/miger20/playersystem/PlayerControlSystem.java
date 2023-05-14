package dk.sdu.student.miger20.playersystem;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.GameKeys;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.data.entityparts.ShootingPart;
import dk.sdu.student.miger20.common.services.IBulletCreationService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;

import java.util.Collection;

public class PlayerControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {

            MovingPart movingPart = player.getPart(MovingPart.class);
            PositionPart positionPart = player.getPart(PositionPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            shootingPart.process(gameData, player);

            // Set shooting part. Taking whether the spacebar is pressed or not.
            shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));

            // Check if shooting part is set to true.
            // If yes. generate a bullet.
            if (shootingPart.getShooting()) {
                Collection<IBulletCreationService> bulletPlugins = SPILocator.locateAll(IBulletCreationService.class);

                for (IBulletCreationService bulletPlugin : bulletPlugins) {
                    bulletPlugin.create(gameData, world, player);
                }
            }

            if (lifePart.isIsHit()) {
                lifePart.setLife(lifePart.getLife()-1);
                if( lifePart.getLife() == 0 ) {
                    //System.out.println("Player removed");
                    world.removeEntity(player);
                }
            }

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
