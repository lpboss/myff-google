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
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author Joey
 */
@Entity
@Table(name = "roles")
public class Role implements java.io.Serializable {

    private Long id;
    private String name;
    private Set<RolePtz> RolePtzDetails = new HashSet<RolePtz>(0);
    private Set<RolesPrivilegeDetail> rolesPrivilegeDetails = new HashSet<RolesPrivilegeDetail>(0);
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String description;
    private Long isLocked = new Long(0);
    private Long version = new Long(0);

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    @OrderBy("id")
    public Set<RolesPrivilegeDetail> getRolesPrivilegeDetails() {
        return rolesPrivilegeDetails;
    }

    public void setRolesPrivilegeDetails(Set<RolesPrivilegeDetail> rolesPrivilegeDetails) {
        this.rolesPrivilegeDetails = rolesPrivilegeDetails;
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

    public void setVersion(Long version) {
        this.version = version;
    }

    @Version
    @Column(name = "version")
    public Long getVersion() {
        return this.version;
    }

    @Column(name = "is_locked")
    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleId")
    @OrderBy("id")
    public Set<RolePtz> getRolePtzDetails() {
        return RolePtzDetails;
    }

    public void setRolePtzDetails(Set<RolePtz> RolePtzDetails) {
        this.RolePtzDetails = RolePtzDetails;
    }
}
