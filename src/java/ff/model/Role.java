/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author Joey
 */
@Entity
@Table(name = "roles")
public class Role implements java.io.Serializable {

    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer version;

    public Role() {
    }

    // Property accessors
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "created_at", nullable = false, length = 19)
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "updated_at", nullable = false, length = 19)
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return this.version;
    }
}
