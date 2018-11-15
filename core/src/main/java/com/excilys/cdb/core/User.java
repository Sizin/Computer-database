package com.excilys.cdb.core;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="user")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private long id;

@Column(name = "name")
private String name;

@Column(name="password")
private String password;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "user_role_id")
private Role role;

public User() {

}

public User(long id, String name, String password, Role role) {
this.id = id;
this.name = name;
this.password = password;
this.role = role;
}

public long getId() {
return id;
}

public void setId(long id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public Role getRole() {
return role;
}

public void setRole(Role role) {
this.role = role;
}

@Override
public String toString() {
return "User [id=" + id + ", name=" + name + ", password=" + password + ", role=" + role + "]";
}

}