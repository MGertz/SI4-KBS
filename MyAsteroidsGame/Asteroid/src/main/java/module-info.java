import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;

module Asteroid {
	requires Common;

	provides IGamePluginService with dk.sdu.student.miger20.asteroid.AsteroidPlugin;
	provides IEntityProcessingService with dk.sdu.student.miger20.asteroid.AsteroidControlSystem;
}
