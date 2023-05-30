package dk.sdu.student.miger20.bullet;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.GameKeys;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;

public class BulletControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);

            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            lifePart.reduceExpiration(gameData.getDelta());
            lifePart.process(gameData, bullet);

            movingPart.setUp(true);

            if (lifePart.getExpiration() <= 0 || lifePart.isIsHit() ) {
                world.removeEntity(bullet);
            }

            updateShape(bullet);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();

        shapex[0] = x-1;
        shapey[0] = y-1;

        shapex[1] = x;
        shapey[1] = y-1;

        shapex[2] = x;
        shapey[2] = y;

        shapex[3] = x-1;
        shapey[3] = y;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

    }
}
