package com.excilys.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.HibernateComputerDao;
import com.excilys.cdb.model.Computer;

@Service
public class HibernateComputerService {
	
	@Autowired
	private HibernateComputerDao hcomputerDao;
	
	@Transactional(readOnly = true)
	public Computer getComputerById(long id) {
		return hcomputerDao.findById(id);
		
	}
	
	@Transactional(readOnly = true)
	public List<Computer> getAll() {
		return hcomputerDao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Computer> findAll(int offset, int range) {
		return hcomputerDao.findAll(offset, range);
	}
	
	@Transactional(readOnly = true)
	public List<Computer> findAll(int offset, int range, String search) {
		return hcomputerDao.findAll(offset, range, search);
	}
	
	@Transactional(readOnly = true)
	public int getCount() {
		return hcomputerDao.findCount();
	}
	
	@Transactional(readOnly = true)
	public int getCount(String search) {
		return hcomputerDao.findCount(search);
	}
	
	@Transactional
	public void insertComputer(Computer computer) {
		hcomputerDao.add(computer);
	}
	
	@Transactional
	public void updateComputer(Computer computer) {
		hcomputerDao.update(computer);
	}
	
	
	@Transactional
	public void deleteComputer(List<Computer> computers) {
		for(Computer computer : computers) {
			hcomputerDao.delete(computer);
		}
	}
	
	@Transactional
	public void deleteComputer(Computer computer) {
		hcomputerDao.delete(computer);
	}
	
}
