package com.isoft.igis.edit.domain;

public class EditDataInfo {

	private String poiDataUrl;

	private String regionDataUrl;
	
	private boolean isHasData;

	public boolean isHasData() {
		return isHasData;
	}

	public void setHasData(boolean isHasData) {
		this.isHasData = isHasData;
	}

	public String getPoiDataUrl() {
		return poiDataUrl;
	}

	public void setPoiDataUrl(String poiDataUrl) {
		this.poiDataUrl = poiDataUrl;
	}

	public String getRegionDataUrl() {
		return regionDataUrl;
	}

	public void setRegionDataUrl(String regionDataUrl) {
		this.regionDataUrl = regionDataUrl;
	}

}
