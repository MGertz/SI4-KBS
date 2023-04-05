package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;

public class EnemyControlSystem implements IEntityProcessingService {

    private int tickCounter = 0;
    private int direction = 0; // 0=left, 1=right

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

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

            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(false);


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
