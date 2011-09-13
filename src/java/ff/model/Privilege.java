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
@Table(name = "privileges")
public class Privilege implements java.io.Serializable {

    private Long id;
    private String name;
    private String params;
    private Long level;
    private Long isMenu;
    private String leaf;
    private String description;
    private Long parentId;
    private Long sortId;
    private Long isLocked;
    private Long version;
    private Timestamp updatedAt;
    private Timestamp createdAt;
    
    private SysController sysController;
    private SysAction sysAction;

    

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "is_locked")
    public Long getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Long isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "is_menu")
    public Long getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Long isMenu) {
        this.isMenu = isMenu;
    }

    @Column(name = "leaf", length = 20)
    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    @Column(name = "level")
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    @Column(name = "version")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "params", length = 200)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "sort_id")
    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    @Column(name = "Description", length = 300)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "updated_at", nullable = false, length = 19)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Column(name = "created_at", nullable = false, length = 19)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    //设置多对一关系：
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sys_action_id")
    public SysAction getSysAction() {
        return sysAction;
    }

    public void setSysAction(SysAction sysAction) {
        this.sysAction = sysAction;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sys_controller_id")
    public SysController getSysController() {
        return sysController;
    }

    public void setSysController(SysController sysController) {
        this.sysController = sysController;
    }
    
    
}
