package dk.sdu.student.miger20.common.services;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;

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
