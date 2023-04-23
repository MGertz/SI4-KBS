package dk.sdu.student.miger20.common.services;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;

public interface IBulletCreate {

    /**
     * Start the plugin
     *
     * Pre-conditions: When game is running, An entity wants to spawn a bullet and make it appear.
     * Post-confitions: A Bullet Entity is ready to be drawed into the game.
     *
     * @param entity
     * @param gameData
     * @return Bullet Entity
     */
    Entity create(Entity entity, GameData gameData);
}
