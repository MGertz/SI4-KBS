import dk.sdu.student.miger20.common.services.IEntityPostProcessingService;

module Collision {
	requires Common;
//	requires Asteroid;
//	requires Bullet;
//	requires Player;
//	requires Enemy;

	provides IEntityPostProcessingService with dk.sdu.student.miger20.collision.CollisionDetector;
}