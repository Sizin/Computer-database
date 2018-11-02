package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface HibernateComputerDaoInterface {
	Computer findById(long id);
	List<Computer> findAll();
	List<Computer> findAll(int offset, int range);
	List<Computer> findAll(int offset, int range, String search);
	int findCount();
	int findCount(String search);
	void add(Computer computer);
	void update(Computer computer);
	
}
