import dk.sdu.student.miger20.common.services.IBulletCreationService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;

module Player {
	requires Common;

	uses IBulletCreationService;

	provides IGamePluginService with dk.sdu.student.miger20.playersystem.PlayerPlugin;
	provides IEntityProcessingService with dk.sdu.student.miger20.playersystem.PlayerControlSystem;


}