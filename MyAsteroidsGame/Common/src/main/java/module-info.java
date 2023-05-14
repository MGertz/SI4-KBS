import dk.sdu.student.miger20.common.services.IBulletCreate;
import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IEntityPostProcessingService;

module Common {
	exports dk.sdu.student.miger20.common.services;
	exports dk.sdu.student.miger20.common.data;
	exports dk.sdu.student.miger20.common.data.entityparts;
	exports dk.sdu.student.miger20.common.util;

	uses IBulletCreate;
	uses IGamePluginService;
	uses IEntityProcessingService;
	uses IEntityPostProcessingService;
}