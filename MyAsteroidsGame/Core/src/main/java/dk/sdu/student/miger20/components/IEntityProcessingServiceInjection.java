package dk.sdu.student.miger20.components;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.services.IEntityProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IEntityProcessingServiceInjection")
public class IEntityProcessingServiceInjection implements IProcessor {

	@Override
	public void process(GameData gameData, World world) {
		List<IEntityProcessingService> processors = SPILocator.locateAll(IEntityProcessingService.class);
		processors.forEach((processor) -> processor.process(gameData, world));
	}
}