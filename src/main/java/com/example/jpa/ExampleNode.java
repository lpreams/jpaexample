package com.example.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tree")
public class ExampleNode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String value;
		
	@ManyToOne
	private ExampleNode parent;
	
	@OneToMany(mappedBy = "parent")
	private List<ExampleNode> children = new ArrayList<>();

	
	// The following empty constructor, getters, and setters are required for JPA persistence
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ExampleNode getParent() {
		return parent;
	}

	public void setParent(ExampleNode parent) {
		this.parent = parent;
	}

	public List<ExampleNode> getChildren() {
		return children;
	}

	public void setChildren(List<ExampleNode> children) {
		this.children = children;
	}
}
