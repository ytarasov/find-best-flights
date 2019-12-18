package com.projector.edu.findflight.dto;


public class SpotDto implements java.io.Serializable {
	
	private static final long serialVersionUID = -3013820926166118887L;
	private Integer id;
	private String amadeusId;
	private String name;
	private String city;
	private Double lat;
	private Double lon;
	private Integer externalId;
	private String alias;

	private String parentAreaSpotId;

	public SpotDto(String amadeusId) {
		this.amadeusId = amadeusId;
	}

	public SpotDto() {
	}

	public SpotDto(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public SpotDto(Integer id) {
		this.id = id;
	}

	public SpotDto(Integer id, String amadeusId) {
		this.id = id;
		this.amadeusId = amadeusId;
	}

	public SpotDto(Integer id, String amadeusId, String name, String city, Double lat, Double lon, Integer externalId, String alias) {
		this.id = id;
		this.amadeusId = amadeusId;
		this.name = name;
		this.city = city;
		this.lat = lat;
		this.lon = lon;
		this.externalId = externalId;
		this.alias = alias;
	}

	public Integer getId() {
		return this.id;
	}

	public String getParentAreaSpotId() {
		return parentAreaSpotId;
	}

	public void setParentAreaSpotId(String parentAreaSpotId) {
		this.parentAreaSpotId = parentAreaSpotId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAmadeusId() {
		return this.amadeusId;
	}

	public void setAmadeusId(String amadeusId) {
		this.amadeusId = amadeusId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return this.lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Integer getExternalId() {
		return this.externalId;
	}

	public void setExternalId(Integer externalId) {
		this.externalId = externalId;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpotDto other = (SpotDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RouteSpot [id=" + id + ", amadeusId=" + amadeusId + ", name=" + name + ", city=" + city + ", lat=" + lat + ", lon=" + lon + "]";
	}

}
