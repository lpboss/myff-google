/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author jerry
 * 2011
 */
@Entity
@Table(name = "ptzs")
public class PTZ {

    private Long id;
    private String name;
    private Set<RolePtz> RolePtz = new HashSet<RolePtz>(0); //RolePtz表外键
    private Set<FireAlarm> fireAlarm = new HashSet<FireAlarm>(0); //FireAlarm表外键
    private String controllUrl; //编码器IP',
    private String pelcodCommandUrl; //'通过串口,发pelcod的ip',
    private String visibleCameraUrl; //'可见光摄像机地址.模拟请参考controll_url',
    private String visibleRTSPUrl; //'可见光RTSP流�,
    private String infraredRTSPUrl; //'红外RTSP流�,
    private String infraredCameraUrl; //'红外摄像机地址',
    private String infraredCircuitUrl; //'红外电路板设备地址',
    private float northMigration; //'云台0角度与正北的偏移。顺时针为正�,
    private String gisMapUrl; //'地图文件存放位置',
    private float visualAngleX;//'红外视角X'
    private float visualAngleY;//'红外视角Y'
    private Integer infraredPixelX;//'红外摄像机X方向像素'
    private Integer infraredPixelY;//'红外摄像机Y方向像素'
    private String brandType; //品牌类型.不同品牌，特性不同，plcod命令拼接方式不同�
    private Integer cruiseStep;//巡航步长
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer isAlarm;// 是否正在报警
    private Integer alarmHeatValue;// 报警最高热值(报警警戒热值)�
    private Integer cruiseRightLimit; //巡航右边�
    private Integer cruiseLeftLimit; //巡航左边�
    private Integer cruiseUpLimit; //巡航时最大仰角�
    private Integer cruiseDownLimit; //巡航时最大俯角�
    private String cruiseFromTo; //巡航方向。�
    private Integer cruiseAngleYStep;//巡航时抬头步长(巡航上扬角度步长)
    private Long isLocked = new Long(0);// 状�'启用，停�,
    private Integer shiftStep; //云台非巡航状态下默认移动步长

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

    public void setInfraredRTSPUrl(String infraredRTSPUrl) {
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

    @Column(name = "shift_step")
    public Integer getShiftStep() {
        return shiftStep;
    }

    public void setShiftStep(Integer shiftStep) {
        this.shiftStep = shiftStep;
    }

    @Column(name = "cruise_down_limit", length = 6)
    public Integer getCruiseDownLimit() {
        return cruiseDownLimit;
    }

    public void setCruiseDownLimit(Integer cruiseDownLimit) {
        this.cruiseDownLimit = cruiseDownLimit;
    }

    @Column(name = "cruise_left_limit", length = 6)
    public Integer getCruiseLeftLimit() {
        return cruiseLeftLimit;
    }

    public void setCruiseLeftLimit(Integer cruiseLeftLimit) {
        this.cruiseLeftLimit = cruiseLeftLimit;
    }

    @Column(name = "cruise_right_limit", length = 6)
    public Integer getCruiseRightLimit() {
        return cruiseRightLimit;
    }

    public void setCruiseRightLimit(Integer cruiseRightLimit) {
        this.cruiseRightLimit = cruiseRightLimit;
    }

    @Column(name = "cruise_up_limit", length = 6)
    public Integer getCruiseUpLimit() {
        return cruiseUpLimit;
    }

    public void setCruiseUpLimit(Integer cruiseUpLimit) {
        this.cruiseUpLimit = cruiseUpLimit;
    }

    @Column(name = "alarm_heat_value")
    public Integer getAlarmHeatValue() {
        return alarmHeatValue;
    }

    public void setAlarmHeatValue(Integer alarmHeatValue) {
        this.alarmHeatValue = alarmHeatValue;
    }

    @Column(name = "is_alarm")
    public Integer getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Integer isAlarm) {
        this.isAlarm = isAlarm;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ptz")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) //只是加这一步才可以实现。
    @OrderBy("id")
    public Set<FireAlarm> getfireAlarm() {
        return fireAlarm;
    }

    @Column(name = "cruise_angle_y_step")
    public Integer getCruiseAngleYStep() {
        return cruiseAngleYStep;

    }

    public void setfireAlarm(Set<FireAlarm> fireAlarm) {
        this.fireAlarm = fireAlarm;
    }

    public void setCruiseAngleYStep(Integer cruiseAngleYStep) {
        this.cruiseAngleYStep = cruiseAngleYStep;

    }   
    
    @Column(name = "cruise_from_to")
    public String getCruiseFromTo() {
        return cruiseFromTo;
    }

    public void setCruiseFromTo(String cruiseFromTo) {
        this.cruiseFromTo = cruiseFromTo;
    }  

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ptz")
    @OrderBy("id")
    public Set<RolePtz> getRolePtz() {
        return RolePtz;
    }

    public void setRolePtz(Set<RolePtz> RolePtz) {
        this.RolePtz = RolePtz;
    }    
    
}
