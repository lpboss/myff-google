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
    private Integer ptzId;// '云台ID',
    private Timestamp actionDate; //` datetime DEFAULT NULL COMMENT '火警时间',
    private float ptzHAngle;//` float DEFAULT NULL COMMENT '水平角度',
    private float ptzVAngle;//` float DEFAULT NULL COMMENT '垂直角度',
    private Integer heatMax; // ' 最高热值',
    private Integer heatMin;
    private Integer heatAvg;//'平均热值',
    private String description;//` text,
    private Integer userId;
    private Timestamp dealDate;
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

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDealDate() {
        return dealDate;
    }

    public void setDealDate(Timestamp dealDate) {
        this.dealDate = dealDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeatAvg() {
        return heatAvg;
    }

    public void setHeatAvg(Integer heatAvg) {
        this.heatAvg = heatAvg;
    }

    public Integer getHeatMax() {
        return heatMax;
    }

    public void setHeatMax(Integer heatMax) {
        this.heatMax = heatMax;
    }

    public Integer getHeatMin() {
        return heatMin;
    }

    public void setHeatMin(Integer heatMin) {
        this.heatMin = heatMin;
    }

    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    public float getPTZHAngle() {
        return ptzHAngle;
    }

    public void setPTZHAngle(float ptzHAngle) {
        this.ptzHAngle = ptzHAngle;
    }

    public Integer getPTZId() {
        return ptzId;
    }

    public void setPTZId(Integer ptzId) {
        this.ptzId = ptzId;
    }

    public float getPTZVAngle() {
        return ptzVAngle;
    }

    public void setPTZVAngle(float ptzVAngle) {
        this.ptzVAngle = ptzVAngle;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
