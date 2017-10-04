package com.example.jpa;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class ExampleUtil {
	private static SessionFactory sessionFactory;
	
	// Calls setup when program starts (or at least as soon as anything is this class is accessed) 
	static {
		setup();
	}

	/**
	 * Get the SessionFactory
	 * 
	 * You should only ever have one sessionFactory, as they are expensive to create
	 * 
	 * @return The SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Set up the connection to the database
	 * 
	 * The static block above calls this when the program starts, so you probably never have to
	 */
	public static void setup() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(ExampleNode.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());
	}
	
	/**
	 * Tear down the connection to the database
	 * 
	 * Make sure all Sessions are closed before calling this
	 */
	public static void teardown() {
		sessionFactory.close();
	}
}