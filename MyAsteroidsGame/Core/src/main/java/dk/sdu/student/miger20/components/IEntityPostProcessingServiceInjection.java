package dk.sdu.student.miger20.components;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.services.IPostEntityProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IEntityPostProcessingServiceInjection")
public class IEntityPostProcessingServiceInjection implements IProcessor {

	@Override
	public void process(GameData gameData, World world) {
		List<IPostEntityProcessingService> processors = SPILocator.locateAll(IPostEntityProcessingService.class);
		processors.forEach((processor) -> processor.process(gameData, world));
	}
}