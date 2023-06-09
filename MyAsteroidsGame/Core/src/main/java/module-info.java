import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IEntityPostProcessingService;

module Core {
	requires java.desktop;
	requires com.badlogic.gdx;
	requires Common;
	requires spring.context;

	uses IGamePluginService;
	uses IEntityProcessingService;
	uses IEntityPostProcessingService;

	exports dk.sdu.student.miger20.components;
	exports dk.sdu.student.miger20.main;
}