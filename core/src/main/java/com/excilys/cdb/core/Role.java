package com.excilys.cdb.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="role")
public class Role {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private long id;

@Column(name = "name")
private String name;

public Role() {

}
public Role(String name) {
	this.name=name;
}


public Role(long id, String name) {
this.id = id;
this.name = name;
}

public long getId() {
return id;
}

public String getName() {
return name;
}


@Override
public String toString() {
return "Role [id=" + id + ", name=" + name + "]";
}

}