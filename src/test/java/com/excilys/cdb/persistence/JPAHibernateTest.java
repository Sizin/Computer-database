package com.excilys.cdb.persistence;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.AfterClass;

import org.junit.BeforeClass;

public class JPAHibernateTest {
	//Interface used to interact with the entity manager factory for the persistence unit. 
	protected static EntityManagerFactory emf;
	//Interface used to interact with the persistence context. 
	protected static EntityManager em;
	
	protected static Logger logger = LoggerFactory.getLogger(JPAHibernateTest.class);
	

	@BeforeClass
	// @BeforeClass : Executed only once before the entire test fixture - Only once
	public static void init() throws FileNotFoundException, SQLException {
		//Should be closed
		emf = Persistence.createEntityManagerFactory("mnf-pu-test");
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();		
		
	}
	
	@AfterClass
	public static void close() {
		em.clear();
		em.close();
		emf.close();
	}
	
	
	
}
