package org.hinsteny.bean;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
public class Role implements Serializable{

    private static final long serialVersionUID = -8243145429438016231L;

    private long roleId;
    private String name;
    private String displayName;
    private String description;
    private LocalDateTime created;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return roleId == role.roleId &&
                Objects.equal(name, role.name) &&
                Objects.equal(displayName, role.displayName) &&
                Objects.equal(description, role.description) &&
                Objects.equal(created, role.created);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleId, name, displayName, description, created);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }
}
