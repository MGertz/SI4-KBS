package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * Pre-condition:  The game has looped once since last call.
     * Post-condition: process will handle processing of each plugin
     *
     * @param GameData gameData
     * @param World world
     */
    void process(GameData gameData, World world);
}
