import dk.sdu.student.miger20.common.services.IBulletCreationService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.util.SPILocator;

module Enemy {
	requires Common;

	uses SPILocator;
	uses IBulletCreationService;

	provides IGamePluginService with dk.sdu.student.miger20.enemysystem.EnemyPlugin;
	provides IEntityProcessingService with dk.sdu.student.miger20.enemysystem.EnemyControlSystem;
}