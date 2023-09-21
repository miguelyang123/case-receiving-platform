package team.bool.case_receiving_platform.vo;

import java.util.List;

import team.bool.case_receiving_platform.entity.Location;

public class LocationListRes {

	private String code;

	private String message;

	private List<Location> locationList;

	public LocationListRes() {
		super();
	}

	public LocationListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public LocationListRes(String code, String message, List<Location> locationList) {
		super();
		this.code = code;
		this.message = message;
		this.locationList = locationList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

}
