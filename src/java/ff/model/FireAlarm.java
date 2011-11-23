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
@Table(name = "fire_alarms")
public class FireAlarm {

    private Long id;
    private PTZ ptz;
    private Timestamp actionDate; //` datetime DEFAULT NULL COMMENT '火警时间',
    private float ptzAngleX;//` float DEFAULT NULL COMMENT '水平角度',
    private float ptzAngleY;//` float DEFAULT NULL COMMENT '垂直角度',
    private Integer heatMax; // ' 最高热值',
    private Integer heatMin;
    private Integer heatAvg;//'平均热值',
    private String description;//` text,
    private User userId;
    private Timestamp dealDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer version;
    private Long isLocked = new Long(0);// '启用，停用',
    private Short is_alarming;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "action_date", length = 100)
    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    @Column(name = "created_at", length = 100)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "deal_date", length = 100)
    public Timestamp getDealDate() {
        return dealDate;
    }

    public void setDealDate(Timestamp dealDate) {
        this.dealDate = dealDate;
    }

    @Column(name = "description", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "heat_avg", length = 100)
    public Integer getHeatAvg() {
        return heatAvg;
    }

    public void setHeatAvg(Integer heatAvg) {
        this.heatAvg = heatAvg;
    }

    @Column(name = "heat_max", length = 100)
    public Integer getHeatMax() {
        return heatMax;
    }

    public void setHeatMax(Integer heatMax) {
        this.heatMax = heatMax;
    }

    @Column(name = "heat_min", length = 100)
    public Integer getHeatMin() {
        return heatMin;
    }

    public void setHeatMin(Integer heatMin) {
        this.heatMin = heatMin;
    }

    @Column(name = "is_locked", length = 100)
    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "is_alarming", length = 100)
    public Short getIs_alarming() {
        return is_alarming;
    }

    public void setIs_alarming(Short is_alarming) {
        this.is_alarming = is_alarming;
    }

    @Column(name = "ptz_angle_x", length = 100)
    public float getPtzAngleX() {
        return ptzAngleX;
    }

    public void setPtzAngleX(float ptzAngleX) {
        this.ptzAngleX = ptzAngleX;
    }

    @Column(name = "ptz_angle_y", length = 100)
    public float getPtzAngleY() {
        return ptzAngleY;
    }

    public void setPtzAngleY(float ptzAngleY) {
        this.ptzAngleY = ptzAngleY;
    }

    @Column(name = "updated_at", length = 100)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Column(name = "version", length = 100)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ptz_id")    
    public PTZ getPtz() {
        return ptz;
    }

    public void setPtz(PTZ ptz) {
        this.ptz = ptz;
    }
}
