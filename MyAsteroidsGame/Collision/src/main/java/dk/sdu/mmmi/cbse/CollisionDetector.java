package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

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


                if (entity1Life.getLife() > 0 && this.collides(entity1, entity2)) {
                    //entity1Life.isIsHit(true);
                    world.removeEntity(entity1);
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

        if (distance < (entity1.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }
}