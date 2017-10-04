package com.example.jpa;


import org.hibernate.Session;

public class ExampleAccess {
	
	public static void main(String[] args) {
		ExampleAccess ex = new ExampleAccess();
		
		Long rootID = ex.createRoot("root node value");
		System.out.println("Created root node with ID " + rootID);
		
		Long childID = ex.createChild(rootID, "child node value");
		System.out.println("Created child node with ID " + childID);
		
		ExampleUtil.getSessionFactory().close();
	}
	
	/**
	 * Create a new root node in the tree and return its generated ID
	 * 
	 * @param value Desired value of new root node
	 * @return generated ID of new root node
	 */
	public Long createRoot(String value) {
		// Create the new node
		ExampleNode root = new ExampleNode();
		root.setValue(value);
		
		// Create DB transaction
		Session session = ExampleUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// Save the new node to the DB
		Long rootID = (Long) session.save(root); // returns generated ID
		
		// Commit this transaction to the DB
		session.getTransaction().commit();
		session.close();
		
		return rootID;
	}
	
	/**
	 * Create a new child node of a given parent
	 * 
	 * @param parentID ID of parent node
	 * @param childValue Desired value of new child node
	 * @return generated ID of new child node
	 */
	public Long createChild(Long parentID, String childValue) {
		ExampleNode child = new ExampleNode();
		child.setValue(childValue);
		
		Session session = ExampleUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		ExampleNode parent = session.get(ExampleNode.class, parentID);
		child.setParent(parent);
		parent.getChildren().add(child); // You have to update both sides of the relationship manually
		
		Long childID = (Long) session.save(child);
		
		// You only need one of the following, depending on which behavior you want
		session.merge(parent); // creates new default node if parent.getId() is not a key in the table (meaning parent isn't present in the table)
		session.update(parent); // throws an exeption if parent.getId() is not a key in the table 
		
		session.getTransaction().commit();
		session.close();
		
		return childID;
	}
	
	/**
	 * Get a node from the database
	 * 
	 * @param id ID of desired node
	 * @return the node
	 */
	public ExampleNode get(Long id) {
		Session session = ExampleUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		ExampleNode node = session.get(ExampleNode.class, id);
		
		session.getTransaction().commit(); // probably not necessary since we didn't change anything
		session.close();
		
		return node;
	}
}
