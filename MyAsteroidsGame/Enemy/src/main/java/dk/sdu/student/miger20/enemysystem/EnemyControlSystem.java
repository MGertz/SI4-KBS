package dk.sdu.student.miger20.enemysystem;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.data.entityparts.ShootingPart;
import dk.sdu.student.miger20.common.services.IBulletCreateService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;

import java.util.Collection;

public class EnemyControlSystem implements IEntityProcessingService {

    private int tickCounter = 0;
    private int direction = 0; // 0=left, 1=right

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            ShootingPart shootingPart = enemy.getPart(ShootingPart.class);

            if (this.tickCounter == 30) {
                this.tickCounter = 0;

                if (Math.random() < 0.5) {
                    direction = 0;
                } else {
                    direction = 1;
                }
            }


            if (Math.random() < 0.4) {
                if (direction == 1) {
                    movingPart.setRight(true);
                } else {
                    movingPart.setLeft(true);
                }
            }

            movingPart.setUp(true);


            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            shootingPart.process(gameData, enemy);

            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(false);

            if (lifePart.isIsHit()) {
                lifePart.setLife(lifePart.getLife()-1);
                if( lifePart.getLife() == 0 ) {
                    //System.out.println("Player removed");
                    world.removeEntity(enemy);
                }
            }

            // Craete a random float between 0 and 1.
            // If over 0.99 it will set shootingPart as true.
            //shootingPart.setShooting(MathUtils.random(0f,1f) > 0.99f);
            shootingPart.setShooting(false);

            // Check if shooting part is set to true.
            // If yes. generate a bullet.
            if (shootingPart.getShooting()) {
                Collection<IBulletCreateService> bulletPlugins = SPILocator.locateAll(IBulletCreateService.class);

                for (IBulletCreateService bulletPlugin : bulletPlugins) {
                    //world.addEntity(bulletPlugin.create(enemy, gameData));
                    bulletPlugin.create(gameData, world, enemy);
                }
            }

            updateShape(enemy);

            this.tickCounter++;
        }
    }

    private void updateShape(Entity entity) {
        // This is how to get the original amount of x,y points.
        //float[] shapex = entity.getShapeX();
        //float[] shapey = entity.getShapeY();

        // This is how to overwrite the amount of x,y points
        float[] shapex = new float[6];
        float[] shapey = new float[6];

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();

        //float radians = positionPart.getRadians();

        shapex[0] = x - 10;
        shapey[0] = y;

        shapex[1] = x - 3;
        shapey[1] = y - 5;

        shapex[2] = x + 3;
        shapey[2] = y - 5;

        shapex[3] = x + 10;
        shapey[3] = y;

        shapex[4] = x + 3;
        shapey[4] = y + 5;

        shapex[5] = x - 3;
        shapey[5] = y + 5;


        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
