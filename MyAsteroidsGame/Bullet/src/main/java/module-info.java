import dk.sdu.student.miger20.common.services.IBulletCreateService;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.services.IGamePluginService;

module Bullet {
	requires Common;

	provides IBulletCreateService with dk.sdu.student.miger20.bullet.BulletPlugin;
	provides IGamePluginService with dk.sdu.student.miger20.bullet.BulletPlugin;
	provides IEntityProcessingService with dk.sdu.student.miger20.bullet.BulletControlSystem;
}