import dk.sdu.student.miger20.common.services.IPostEntityProcessingService;

module Collision {
	requires Common;
//	requires Asteroid;
//	requires Bullet;
//	requires Player;
//	requires Enemy;

	provides IPostEntityProcessingService with dk.sdu.student.miger20.collision.CollisionDetector;
}