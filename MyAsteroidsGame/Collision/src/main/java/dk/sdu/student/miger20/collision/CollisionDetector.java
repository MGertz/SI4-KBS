package dk.sdu.student.miger20.collision;

import dk.sdu.student.miger20.asteroidsystem.Asteroid;
import dk.sdu.student.miger20.bullet.Bullet;
import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IPostEntityProcessingService;
import dk.sdu.student.miger20.enemysystem.Enemy;
import dk.sdu.student.miger20.playersystem.Player;

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

                LifePart entity1Life = entity1.getPart(LifePart.class);
                LifePart entity2Life = entity2.getPart(LifePart.class);


                // Player collide with Asteroid
                if ((entity1.getClass() == Player.class && entity2.getClass() == Asteroid.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 1");
                    entity1Life.setIsHit(true);
                }

                // Enemy collide with Asteroid
                if ((entity1.getClass() == Enemy.class && entity2.getClass() == Asteroid.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 2");
                    entity1Life.setIsHit(true);
                }

                // Asteroid shot at
                if ((entity1.getClass() == Asteroid.class && entity2.getClass() == Bullet.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 3");
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }

                // Player and Enemy Collide
                if ((entity1.getClass() == Player.class && entity2.getClass() == Enemy.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 4");
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }

                // Player shot by bullet
                if ((entity1.getClass() == Player.class && entity2.getClass() == Bullet.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 5");
                    entity1Life.setIsHit(true);
                    entity2Life.setIsHit(true);
                }

                // Enemy shot by bullet
                if ((entity1.getClass() == Enemy.class && entity2.getClass() == Bullet.class) && this.collides(entity1, entity2)) {
                    //System.out.println("Collision: 6");
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
