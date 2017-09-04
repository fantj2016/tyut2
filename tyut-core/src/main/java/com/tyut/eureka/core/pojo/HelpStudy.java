package com.tyut.eureka.core.pojo;

/**
 * Created by SuperJohn on 2017/8/21.
 */
public class HelpStudy {
    private String id;
    private String title;
    private String uptime;
    private String type;
    private String sexlimit;
    private String applyStatus;
    private String stationStatus;
    private String url;

    @Override
    public String toString() {
        return "HelpStudy{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", uptime='" + uptime + '\'' +
                ", type='" + type + '\'' +
                ", sexlimit='" + sexlimit + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                ", stationStatus='" + stationStatus + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexlimit() {
        return sexlimit;
    }

    public void setSexlimit(String sexlimit) {
        this.sexlimit = sexlimit;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
