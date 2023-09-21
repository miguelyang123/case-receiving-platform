package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.service.ifs.LocationService;
import team.bool.case_receiving_platform.vo.LocationListRes;

@RestController
@RequestMapping("location_api")
public class LocationServiceController {

	@Autowired
	private LocationService lService;
	
	@GetMapping("get_onsite")
	public LocationListRes getOnsiteLocation() {
		return lService.getOnsiteLocation();
	}
	
	@GetMapping("get_remote")
	public LocationListRes getRemoteLocation() {
		return lService.getRemoteLocation();
	}
	
}
