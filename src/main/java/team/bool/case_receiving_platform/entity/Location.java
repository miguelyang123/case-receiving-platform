package team.bool.case_receiving_platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

	// 地點的流水號(PK)
	@Id
	@Column(name = "location_id")
	private String locationId;

	// 地點(線上/現場)
	@Column(name = "location_name")
	private String locationName;

	
	// 建構方法
	public Location() {
		super();
	}

	public Location(String locationId, String locationName) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
	}

	
	// Getters and setters
	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
