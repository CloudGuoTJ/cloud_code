package com.yunma.vo.mapCount;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 *
 */

public class MapDistrictHotVO implements Serializable{
	private Double lng;
    private Double lat;
    private Integer count;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
