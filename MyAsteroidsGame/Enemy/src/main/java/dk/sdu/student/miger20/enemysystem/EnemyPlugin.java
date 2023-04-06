package dk.sdu.student.miger20.enemysystem;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.MovingPart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import dk.sdu.student.miger20.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        this.enemy = createEnemyShip(gameData);
        world.addEntity(this.enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 150;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        int[] colors = new int[4];
        colors[0] = 1;
        colors[1] = 0;
        colors[2] = 0;
        colors[3] = 1;

        Entity enemyShip = new Enemy();
        enemyShip.setColors(colors);
        enemyShip.setRadius(10);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1,0));

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(this.enemy);
    }
}