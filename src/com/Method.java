package com;

public abstract class Method {
	  public String code; //code used to identify the algorithm in command line
	  public String longName; //name of the algorithm
	  public KnowledgeBase kb; 
	  
	  public String query;
	  
	  public Method() {
		  
	  }
	  
	  public Method(KnowledgeBase kb) {
		  
	  }

	abstract public String testAsk();
	  
	  abstract public boolean checkQuery();

	}