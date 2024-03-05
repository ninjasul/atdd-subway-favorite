package nextstep.subway.application.service;

import nextstep.subway.application.dto.PathResponse;
import nextstep.subway.application.dto.StationResponse;
import nextstep.subway.domain.PathFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PathService {
	private final StationService stationService;
	private final SectionService sectionService;

	public PathService(StationService stationService, SectionService sectionService) {
		this.stationService = stationService;
		this.sectionService = sectionService;
	}

	public PathResponse getPath(Long source, Long target) {
		PathFinder pathFinder = new PathFinder(sectionService.findAll());

		return createPathResponse(pathFinder.getPath(source, target), (int) pathFinder.getDistance(source, target));
	}

	private PathResponse createPathResponse(List<Long> stations, int distance) {
		return new PathResponse(
				stations.stream()
						.map(id -> new StationResponse(id, stationService.findStationById(id).getName()))
						.collect(Collectors.toList())
				, distance);
	}
}