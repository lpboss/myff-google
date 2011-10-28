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
    private Integer ptz_id;// '云台ID',
    private Timestamp action_date; //` datetime DEFAULT NULL COMMENT '火警时间',
    private float ptz_h_angle;//` float DEFAULT NULL COMMENT '水平角度',
    private float ptz_v_angle;//` float DEFAULT NULL COMMENT '垂直角度',
    private Integer heat_max; // ' 最高热值',
    private Integer heat_min;
    private Integer heat_avg;//'平均热值',
    private String description;//` text,
    private Integer user_id;
    private Timestamp deal_date;
    private Timestamp created_at;
    private Timestamp updated_at;
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

    public Timestamp getAction_date() {
        return action_date;
    }

    public void setAction_date(Timestamp action_date) {
        this.action_date = action_date;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(Timestamp deal_date) {
        this.deal_date = deal_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeat_avg() {
        return heat_avg;
    }

    public void setHeat_avg(Integer heat_avg) {
        this.heat_avg = heat_avg;
    }

    public Integer getHeat_max() {
        return heat_max;
    }

    public void setHeat_max(Integer heat_max) {
        this.heat_max = heat_max;
    }

    public Integer getHeat_min() {
        return heat_min;
    }

    public void setHeat_min(Integer heat_min) {
        this.heat_min = heat_min;
    }

    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    public float getPtz_h_angle() {
        return ptz_h_angle;
    }

    public void setPtz_h_angle(float ptz_h_angle) {
        this.ptz_h_angle = ptz_h_angle;
    }

    public Integer getPtz_id() {
        return ptz_id;
    }

    public void setPtz_id(Integer ptz_id) {
        this.ptz_id = ptz_id;
    }

    public float getPtz_v_angle() {
        return ptz_v_angle;
    }

    public void setPtz_v_angle(float ptz_v_angle) {
        this.ptz_v_angle = ptz_v_angle;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
