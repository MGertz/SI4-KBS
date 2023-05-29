package dk.sdu.student.miger20.collision;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {

        // Loop through all entities.
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                // Check the entities are the same, if yes, skip this loop.
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                String entity1ClassName = entity1.getClass().getName();
                String entity2ClassName = entity2.getClass().getName();

                String[] entity1ClassNameArr = entity1ClassName.split("\\.");
                String[] entity2ClassNameArr = entity2ClassName.split("\\.");

                String entity1Name = entity1ClassNameArr[entity1ClassNameArr.length - 1];
                String entity2Name = entity2ClassNameArr[entity2ClassNameArr.length - 1];

                LifePart entity1Life = entity1.getPart(LifePart.class);
                LifePart entity2Life = entity2.getPart(LifePart.class);

                // Player collide with Asteroid
                if (entity1Name.equals("Player") && entity2Name.equals("Asteroid") && collides(entity1, entity2) ) {
                    entity1Life.setIsHit(true);
                }

                // Enemy collided with Asteroid
                if (entity1Name.equals("Enemy") && entity2Name.equals("Asteroid") && collides(entity1, entity2) ) {
                    entity1Life.setIsHit(true);
                }

                // if ASteroid shot at
                if (entity1Name.equals("Asteroid") && entity2Name.equals("Bullet") && collides(entity1, entity2) ) {
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }

                // Player and Enemy collide
                if (entity1Name.equals("Player") && entity2Name.equals("Enemy") && collides(entity1, entity2) ) {
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }

                // Player shot by bullet
                if (entity1Name.equals("Player") && entity2Name.equals("Bullet") && collides(entity1, entity2) ) {
//                    entity1Life.setIsHit(true);
//                    entity2Life.setIsHit(true);
                }

                // Enemy shot by bullet
                if (entity1Name.equals("Enemy") && entity2Name.equals("Bullet") && collides(entity1, entity2) ) {
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        PositionPart pP1 = entity1.getPart(PositionPart.class);
        PositionPart pP2 = entity2.getPart(PositionPart.class);

        float dx = pP1.getX() - pP2.getX();
        float dy = pP1.getY() - pP2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float totalRadius = entity1.getRadius() + entity2.getRadius();

        if (distance < totalRadius) {
            return true;
        }
        return false;
    }
}
