package com.elftree.mall.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/18.
 */

public class RegionInfo extends CommonModel {

    @Expose private List<Region> province;
    @Expose private List<Region> city;
    @Expose private List<Region> area;

    public List<Region> getProvince() {
        return province;
    }

    public void setProvince(List<Region> province) {
        this.province = province;
    }

    public List<Region> getCity() {
        return city;
    }

    public void setCity(List<Region> city) {
        this.city = city;
    }

    public List<Region> getArea() {
        return area;
    }

    public void setArea(List<Region> area) {
        this.area = area;
    }
}
