package dk.sdu.student.miger20.common.services;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;

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
