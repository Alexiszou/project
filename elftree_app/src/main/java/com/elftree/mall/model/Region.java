package com.elftree.mall.model;

import android.support.annotation.XmlRes;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zouhongzhi on 2017/10/18.
 */
@Entity
public class Region extends CommonModel {

    /**
     * region_id : 2
     * parent_id : 1
     * region_name : 北京
     * region_type : 1
     * agency_id : 0
     */
    @Id
    private Long id;
    @Expose private String region_id;
    @Expose private String parent_id;
    @Expose private String region_name;
    @Expose private String region_type;
    @Expose private String agency_id;


    @Generated(hash = 1677366590)
    public Region(Long id, String region_id, String parent_id, String region_name,
            String region_type, String agency_id) {
        this.id = id;
        this.region_id = region_id;
        this.parent_id = parent_id;
        this.region_name = region_name;
        this.region_type = region_type;
        this.agency_id = agency_id;
    }

    @Generated(hash = 600106640)
    public Region() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_type() {
        return region_type;
    }

    public void setRegion_type(String region_type) {
        this.region_type = region_type;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", region_id='" + region_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", region_type='" + region_type + '\'' +
                ", agency_id='" + agency_id + '\'' +
                '}';
    }

}
