package dk.sdu.student.miger20.components;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;

public interface IProcessor {

	/**
	 * Process System
	 *
	 * @param gameData
	 * @param world
	 */
	void process(GameData gameData, World world);
}
