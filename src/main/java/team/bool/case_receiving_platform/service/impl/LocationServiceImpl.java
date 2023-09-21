package team.bool.case_receiving_platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.Location;
import team.bool.case_receiving_platform.repository.LocationDao;
import team.bool.case_receiving_platform.service.ifs.LocationService;
import team.bool.case_receiving_platform.vo.LocationListRes;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;

	@Override
	public LocationListRes getOnsiteLocation() {

		//get DB "On site" Date
		List<Location> onsiteList = locationDao.selectOnsite();

		// not find data
		if (CollectionUtils.isEmpty(onsiteList)) {
			return new LocationListRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMessage());
		}

		return new LocationListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),onsiteList);
	}

	@Override
	public LocationListRes getRemoteLocation() {

		//get DB "Remote" Date
		List<Location> remoteList = locationDao.selectRemote();

		// not find data
		if (CollectionUtils.isEmpty(remoteList)) {
			return new LocationListRes(RtnCode.DATA_NOT_FOUND.getCode(), RtnCode.DATA_NOT_FOUND.getMessage());
		}
		
		return new LocationListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),remoteList);
	}

}
