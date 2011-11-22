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
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ignore_areas") //alarm_ignore_areas
public class IgnoreAreas {

    private Integer id;
    private PTZ ptz;   //云台的外键
    private Integer ptzAngelX; //火警时云台的水平角度
    private Integer ptzAngelY; //火警时云台的Y角度
    private Integer ccdArea; //热成像起火面积值
    private Integer heatMax; //最大热值
    private Timestamp beginDate;//火警时间范围(开始)
    private Timestamp endDate; //火警时间范围(结束)
    private Long isLocked = new Long(0);// 状态 '启用，停用',
    private Integer version;//版本
    private Timestamp createdAt;
    private Timestamp updatedAt;
    

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "begin_date")
    public Timestamp getBeginDate() {
        return beginDate;
    }

    @Column(name = "ccd_area", length = 6)
    public Integer getCcdArea() {
        return ccdArea;
    }

    @Column(name = "created_at", nullable = false, length = 19)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    @Column(name = "heat_max", length = 6)
    public Integer getHeatMax() {
        return heatMax;
    }

    @Column(name = "is_locked")
    public Long getIsLocked() {
        return isLocked;
    }

    @Column(name = "ptz_angel_x", length = 6)
    public Integer getPtzAngelX() {
        return ptzAngelX;
    }

    @Column(name = "ptz_angel_y", length = 6)
    public Integer getPtzAngelY() {
        return ptzAngelY;
    }

    @Column(name = "updated_at", nullable = false, length = 19)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public void setCcdArea(Integer ccdArea) {
        this.ccdArea = ccdArea;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public void setHeatMax(Integer heatMax) {
        this.heatMax = heatMax;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    public void setPtzAngelX(Integer ptzAngelX) {
        this.ptzAngelX = ptzAngelX;
    }

    public void setPtzAngelY(Integer ptzAngelY) {
        this.ptzAngelY = ptzAngelY;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    //设置多对一关系：
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ptz_id")
    public PTZ getPtz() {
        return ptz;
    }

    public void setPtz(PTZ ptz) {
        this.ptz = ptz;
    }
    
    
    
    
}
