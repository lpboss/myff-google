/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

/**
 *
 * @author jerry
 */
@Entity
@Table(name = "ptzs")
public class PTZ {

    private Long id;
    private String name;
    private String controllUrl; //编码器IP',
    private String pelcodCommandUrl; //'通过串口,发pelcod的ip',
    private String visibleCameraUrl; //'可见光摄像机地址,模拟请参考controll_url',
    private String visibleRTSPUrl; //'可见光RTSP流',
    private String infraredRTSPUrl; //'红外RTSP流',
    private String infraredCameraUrl; //'红外摄像机地址',
    private String infraredCircuitUrl; //'红外电路板设备地址',
    private float northMigration; //'摄像机0角度与正北的便宜。顺时针为正。',
    private String gisMapUrl; //'地图文件存放位置',
    private float visualAngleX;//'红外视角X'
    private float visualAngleY;//'红外视角Y'
    private Integer infraredPixelX;//'红外摄像机X方向像素'
    private Integer infraredPixelY;//'红外摄像机Y方向像素'
    private String brandType; //品牌类型,不同品牌，特性不同，plcod命令拼接方式不同。
    private Integer cruiseStep;//巡航步长
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer version;
    private Long isLocked = new Long(0);// '启用，停用',

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "controll_url", length = 100)
    public String getControllUrl() {
        return controllUrl;
    }

    public void setControllUrl(String controll_url) {
        this.controllUrl = controll_url;
    }

    @Column(name = "created_at", nullable = false, length = 19)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "gis_map_url", length = 100)
    public String getGisMapUrl() {
        return gisMapUrl;
    }

    public void setGisMapUrl(String gisMapUrl) {
        this.gisMapUrl = gisMapUrl;
    }

    @Column(name = "infrared_camera_url", length = 100)
    public String getInfraredCameraUrl() {
        return infraredCameraUrl;
    }

    public void setInfraredCameraUrl(String infraredCameraUrl) {
        this.infraredCameraUrl = infraredCameraUrl;
    }

    @Column(name = "infrared_circuit_url", length = 100)
    public String getInfraredCircuitUrl() {
        return infraredCircuitUrl;
    }

    public void setInfraredCircuitUrl(String infraredCircuitUrl) {
        this.infraredCircuitUrl = infraredCircuitUrl;
    }

    @Column(name = "infrared_rtsp_url", length = 100)
    public String getInfraredRTSPUrl() {
        return infraredRTSPUrl;
    }

    public void setInfraredRTSPUrl(String infrared_rtsp_url) {
        this.infraredRTSPUrl = infraredRTSPUrl;
    }

    @Column(name = "is_locked")
    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "north_migration", length = 7)
    public float getNorthMigration() {
        return northMigration;
    }

    public void setNorthMigration(float northMigration) {
        this.northMigration = northMigration;
    }

    @Column(name = "pelcod_command_url", length = 100)
    public String getPelcodCommandUrl() {
        return pelcodCommandUrl;
    }

    public void setPelcodCommandUrl(String pelcodCommandUrl) {
        this.pelcodCommandUrl = pelcodCommandUrl;
    }

    @Column(name = "updated_at", nullable = false, length = 19)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "visible_camera_url")
    public String getVisibleCameraUrl() {
        return visibleCameraUrl;
    }

    public void setVisibleCameraUrl(String visibleCameraUrl) {
        this.visibleCameraUrl = visibleCameraUrl;
    }

    @Column(name = "visible_rtsp_url", length = 100)
    public String getVisibleRTSPUrl() {
        return visibleRTSPUrl;
    }

    public void setVisibleRTSPUrl(String visibleRTSPUrl) {
        this.visibleRTSPUrl = visibleRTSPUrl;
    }

    @Column(name = "visual_angle_x", length = 100)
    public float getVisualAngleX() {
        return visualAngleX;
    }

    public void setVisualAngleX(float visualAngleX) {
        this.visualAngleX = visualAngleX;
    }

    @Column(name = "visual_angle_y", length = 100)
    public float getVisualAngleY() {
        return visualAngleY;
    }

    public void setVisualAngleY(float visualAngleY) {
        this.visualAngleY = visualAngleY;
    }

    @Column(name = "infrared_pixel_x", length = 100)
    public Integer getInfraredPixelX() {
        return infraredPixelX;
    }

    public void setInfraredPixelX(Integer infraredPixelX) {
        this.infraredPixelX = infraredPixelX;
    }

    @Column(name = "infrared_pixel_y", length = 100)
    public Integer getInfraredPixelY() {
        return infraredPixelY;
    }

    public void setInfraredPixelY(Integer infraredPixelY) {
        this.infraredPixelY = infraredPixelY;
    }

    @Column(name = "brand_type", length = 100)
    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    @Column(name = "cruise_step", length = 100)
    public Integer getCruiseStep() {
        return cruiseStep;
    }

    public void setCruiseStep(Integer cruiseStep) {
        this.cruiseStep = cruiseStep;
    }
}
