package dk.sdu.student.miger20.components;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.services.IGamePluginService;
import dk.sdu.student.miger20.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IGameEntityPluginServiceInjection")
public class IGameEntityPluginServiceInjection {

	public void startPlugins(GameData gameData, World world) {
		List<IGamePluginService> plugins = SPILocator.locateAll(IGamePluginService.class);
		plugins.forEach((plugin) -> plugin.start(gameData, world));
	}
}




