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
    private String controll_url; //编码器IP',
    private String pelcod_command_url; //'通过串口,发pelcod的ip',
    private String visible_camera_url; //'可见光摄像机地址,模拟请参考controll_url',
    private String visible_rtsp_url; //'可见光RTSP流',
    private String infrared_rtsp_url; //'红外RTSP流',
    private String infrared_camera_url; //'红外摄像机地址',
    private String infrared_circuit_url; //'红外电路板设备地址',
    private float north_migration; //'摄像机0角度与正北的便宜。顺时针为正。',
    private String gis_map_url; //'地图文件存放位置',
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

    @Column(name = "controll_url", length = 100)
    public String getControll_url() {
        return controll_url;
    }

    public void setControll_url(String controll_url) {
        this.controll_url = controll_url;
    }

    @Column(name = "created_at", nullable = false, length = 19)
    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Column(name = "gis_map_url", length = 100)
    public String getGis_map_url() {
        return gis_map_url;
    }

    public void setGis_map_url(String gis_map_url) {
        this.gis_map_url = gis_map_url;
    }

    @Column(name = "infrared_camera_url", length = 100)
    public String getInfrared_camera_url() {
        return infrared_camera_url;
    }

    public void setInfrared_camera_url(String infrared_camera_url) {
        this.infrared_camera_url = infrared_camera_url;
    }

    @Column(name = "infrared_circuit_url", length = 100)
    public String getInfrared_circuit_url() {
        return infrared_circuit_url;
    }

    public void setInfrared_circuit_url(String infrared_circuit_url) {
        this.infrared_circuit_url = infrared_circuit_url;
    }

    @Column(name = "infrared_rtsp_url", length = 100)
    public String getInfrared_rtsp_url() {
        return infrared_rtsp_url;
    }

    public void setInfrared_rtsp_url(String infrared_rtsp_url) {
        this.infrared_rtsp_url = infrared_rtsp_url;
    }

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
    public float getNorth_migration() {
        return north_migration;
    }

    public void setNorth_migration(float north_migration) {
        this.north_migration = north_migration;
    }

    @Column(name = "pelcod_command_url", length = 100)
    public String getPelcod_command_url() {
        return pelcod_command_url;
    }

    public void setPelcod_command_url(String pelcod_command_url) {
        this.pelcod_command_url = pelcod_command_url;
    }

    @Column(name = "updated_at", nullable = false, length = 19)
    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getVisible_camera_url() {
        return visible_camera_url;
    }

    public void setVisible_camera_url(String visible_camera_url) {
        this.visible_camera_url = visible_camera_url;
    }

    @Column(name = "visible_rtsp_url", length = 100)
    public String getVisible_rtsp_url() {
        return visible_rtsp_url;
    }

    public void setVisible_rtsp_url(String visible_rtsp_url) {
        this.visible_rtsp_url = visible_rtsp_url;
    }
}
