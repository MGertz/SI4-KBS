package dk.sdu.student.miger20.common.services;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;

public interface IGamePluginService {

    /**
     * This interface is responsible for loading plugins when the game is started.
     *
     * Pre-condition:  Starts the game, there is no plugins loaded.
     * Post-condition: The plugins has been loaded.
     *
     * @param GameData gameData
     * @param World world
     */
    void start(GameData gameData, World world);

    /**
     * This interface is responsible for unloaded plugins when the game stops.
     *
     * Pre-condition:  The game is started, and needs to stop, all plugins is loaded
     * Post-condition: All plugins has been unloaded.
     *
     * @param GameData gameData
     * @param World world
     */
    void stop(GameData gameData, World world);
}
