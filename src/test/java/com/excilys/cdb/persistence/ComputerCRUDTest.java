package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

//import com.excilys.cdb.config.SpringJdbcConfig;
import com.excilys.cdb.model.Computer;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SpringJdbcConfig.class)
public class ComputerCRUDTest{

//	private static Logger logger = LoggerFactory.getLogger(ComputerCRUDTest.class);
//
//	@Autowired
//	private ComputerDao computerDao;
//
//	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//	@PostConstruct
//	public void init() {
//		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//	}
//
//	@Test
//	public void testGetListCompanies() {
//		List<Computer> computers = computerDao.getAllComputers(0, 0, "null");
//	}

	
	
//	@Test
//	public void testGetAll() {
//
//		// Get nb computer in the db
//		Query query = em.createQuery("SELECT count(*) FROM Computer");
//		long nbComputersExpected = (long) query.getSingleResult();
//
//		// Getting all computers
//		List<Computer> computers = computerDao.getAllComputers(0, 0, null);
//		int nbComputerGet = 0;
//		for (Computer computer : computers) {
//			nbComputerGet += 1;
//		}
//
//		assertEquals(nbComputerGet, nbComputersExpected);
//
//	}

//	@Test
//	public void testFindComputerById() {
//		Computer computer = computerDao.getOne(12);
//
//		assertEquals(12, computer.getId());
//		assertEquals("Apple III", computer.getName());
//		assertEquals("1980-05-01", computer.getIntroduced().toString());
//		assertEquals("1984-04-01", computer.getDiscontinued().toString());
//		assertEquals(1, computer.getCompanyId());
//		assertEquals("Apple Inc.", computer.getCompanyName());
//	}

//	@Test
//	public void testInsertComputer() {
//		Query getComputerCountBeforeInsert = em.createQuery("SELECT count(*) FROM Computer");
//
//		long nbComputersGet = (long) getComputerCountBeforeInsert.getSingleResult();
//
//		// Test 1 : Computer(id, name, introduced : null, discontinued : null, company :
//		// null)
//		Computer expectedComputer = new Computer();
//		expectedComputer.setId(nbComputersGet + 1);
//		expectedComputer.setName("SinanTest");
//
//		// Inserting computer
//		computerDao.add(expectedComputer);
//
//		int retrievedComputerId = (int) nbComputersGet + 1;
//		Computer retrievedComputer = computerDao.getOne(retrievedComputerId);
//
//		Query getComputerCountAfterInsertQuery = em.createQuery("SELECT count(*) FROM Computer");
//		long getComputerCountAfterInsert = (long) getComputerCountAfterInsertQuery.getSingleResult();
//
//	}

}
