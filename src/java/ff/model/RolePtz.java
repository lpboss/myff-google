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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "role_ptzs")
public class RolePtz implements java.io.Serializable {

    private Long id;
    private Integer roleId;
    private Integer ptzId;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ptz_id", length = 100)
    public Integer getPtzId() {
        return ptzId;
    }

    public void setPtzId(Integer ptzId) {
        this.ptzId = ptzId;
    }

    @Column(name = "role_id", length = 100)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
