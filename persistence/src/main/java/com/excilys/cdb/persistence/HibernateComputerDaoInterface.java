package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.core.Computer;

public interface HibernateComputerDaoInterface {
	String SELECT_ALL = "From Computer";
	String SELECT_WHERE_NAME = "From Computer WHERE name LIKE :search";
	String COUNT_ALL = "select count(*) from Computer";
	String COUNT_WHERE_NAME = "select count(*) from Computer WHERE name LIKE :search ";
	
	Computer findById(long id);
	List<Computer> findAll();
	List<Computer> findAll(int offset, int range);
	List<Computer> findAll(int offset, int range, String search);
	int findCount();
	int findCount(String search);
	void add(Computer computer);
	void update(Computer computer);
	void delete(Computer computer);
	
}
