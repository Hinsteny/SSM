package org.hinsteny.bean;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @date 2016/7/23
 * @copyright: 2016 All rights reserved.
 */
public class Actor implements Serializable{

    private static final long serialVersionUID = -8243145429438016231L;

    private long actorId;
    private String name;
    private String password;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime created;
    private LocalDateTime lastLogin;

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return actorId == actor.actorId &&
                enabled == actor.enabled &&
                Objects.equal(name, actor.name) &&
                Objects.equal(password, actor.password) &&
                Objects.equal(firstName, actor.firstName) &&
                Objects.equal(lastName, actor.lastName) &&
                Objects.equal(email, actor.email) &&
                Objects.equal(created, actor.created) &&
                Objects.equal(lastLogin, actor.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actorId, name, password, enabled, firstName, lastName, email, created, lastLogin);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
