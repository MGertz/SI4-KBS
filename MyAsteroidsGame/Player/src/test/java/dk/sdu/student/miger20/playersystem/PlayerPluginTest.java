package dk.sdu.student.miger20.playersystem;

import dk.sdu.student.miger20.common.data.Entity;
import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.data.entityparts.LifePart;
import dk.sdu.student.miger20.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerPluginTest {

	@Test
	void verifyExistence() {
		Entity player = mock(Player.class);
		GameData gameData = mock(GameData.class);
		World world = mock(World.class);

		LifePart lifePart = mock(LifePart.class);

		when(player.getPart(LifePart.class)).thenReturn(lifePart);
		when(lifePart.getLife()).thenReturn(3);

		PlayerPlugin playerPlugin = new PlayerPlugin();
		playerPlugin.start(gameData, world);

		// Test that the entity is added to the world
		verify(world).addEntity(any(Player.class));

		// Check that the entity has one life
		assertEquals(3, lifePart.getLife());
	}

		//playerPlugin.start(gameData, world);







		//when(world.getEntity(String)).thenReturn((Entity) entityMap);

		//GameEntities gameEntities = mock(GameEntities.class);

		//when(gameData.getGameEntities()).thenReturn(gameEntities);



	//}

	@Disabled
	void stop() {
	}
}