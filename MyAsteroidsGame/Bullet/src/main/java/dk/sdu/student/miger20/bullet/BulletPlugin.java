package dk.sdu.student.miger20.bullet;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;
    private Entity owner;

    }

    @Override
    public void start(GameData gameData, World world) {
        bullet = createBullet(gameData);
        world.addEntity(bullet);
    public BulletPlugin(Entity owner) {
        this.owner = owner;
    }

    /**
     * Create bullet entity with default parameters based on shooter
     * <br />
     * Pre-condition: New bullet entity has to be created for the game from a shooter <br />
     * Post-condition: Bullet entity, that has parameters, such that it is shot from shooter
     *
     * @param gameData Data for the game
     * @return Bullet entity with initial data from shooter
     */
    private Entity createBullet(GameData gameData) {
        PositionPart shooterPosition = this.owner.getPart(PositionPart.class);
        MovingPart shooterMovement = this.owner.getPart(MovingPart.class);

        float deceleration = 0;
        float acceleration = 0;
        float maxSpeed = 1000;
        float rotationSpeed = 0;
        float radians = shooterPosition.getRadians();

        Entity bullet = new Bullet();

        bullet.setRadius(1);

        float bx = (float) MathUtils.cos(radians) * this.owner.getRadius() * bullet.getRadius();
        float x = bx + shooterPosition.getX();
        float by = (float) MathUtils.sin(radians) * this.owner.getRadius() * bullet.getRadius();
        float y = by + shooterPosition.getY();

        float shootSpeed = 350 + shooterMovement.getSpeed();

        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        int[] colors = new int[4];
        colors[0] = 1;
        colors[1] = 0;
        colors[2] = 0;
        colors[3] = 1;
        bullet.setColors(colors);
        bullet.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed, shootSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 1));

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(bullet);
    }
}



