package com.excilys.cdb.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.Connection;

public class JPAHibernateTest {
	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	
	// @BeforeClass : Executed only once before the entire test fixture - Only once
	@BeforeClass
	public static void init() throws FileNotFoundException, SQLException {
		emf = Persistence.createEntityManagerFactory("mnf-pu-test");
		em = emf.createEntityManager();
	}
	
	
	// @Before : Executed before each test - If 10 test => Executed 10 times
	@Before
	public void initializeDatabase() {
		Session session = em.unwrap(Session.class);
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
					File schemaScript = new File(getClass().getResource("1-SCHEMA.sql").getFile());
					File privilegesScript = new File(getClass().getResource("2-PRIVILEGES.sql").getFile());
					File entriesScript = new File(getClass().getResource("3-ENTRIES.sql").getFile());
					RunScript.execute(connection, new FileReader(schemaScript));
					RunScript.execute(connection, new FileReader(privilegesScript));
					RunScript.execute(connection, new FileReader(entriesScript));
				}catch(FileNotFoundException e) {
					throw new RuntimeException("Could not intinialize with SQL script");
				}
			}
		});
	}
	
	@AfterClass
	public static void close() {
		em.clear();
		em.close();
		emf.close();
	}
	
	
	
}
