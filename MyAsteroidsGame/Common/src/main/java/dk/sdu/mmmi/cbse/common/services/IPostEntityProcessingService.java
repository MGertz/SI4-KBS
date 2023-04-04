package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IPostEntityProcessingService  {

    /**
     * Pre-condition: The game has looped once since last call. All entities has been processed.
     * Post-condition: Entity is processed and updated.
     *
     * @param GameData gameData
     * @param World world
     */
    void process(GameData gameData, World world);
}
