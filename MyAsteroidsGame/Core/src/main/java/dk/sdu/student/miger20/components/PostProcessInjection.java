package dk.sdu.student.miger20.components;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;
import dk.sdu.student.miger20.common.services.IEntityPostProcessingService;
import dk.sdu.student.miger20.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postProcessorInjector")
public class PostProcessInjection implements IProcessor {

	@Override
	public void process(GameData gameData, World world) {
		List<IEntityPostProcessingService> processors = SPILocator.locateAll(IEntityPostProcessingService.class);
		processors.forEach((processor) -> processor.process(gameData, world));
	}
}