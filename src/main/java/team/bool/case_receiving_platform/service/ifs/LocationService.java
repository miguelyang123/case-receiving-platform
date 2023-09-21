package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.vo.LocationListRes;

public interface LocationService {

	public LocationListRes getOnsiteLocation();
	
	public LocationListRes getRemoteLocation();
	
}
