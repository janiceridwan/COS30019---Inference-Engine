package com;

import java.util.ArrayList;

public class ForwardChaining extends Method {

	private ArrayList<String> clauses;
	private ArrayList<String> facts;
	private String query;
	
	private ArrayList<String> outputFacts;
	
	public ForwardChaining() {
		
	}
	
	public ForwardChaining (KnowledgeBase kb) {
		
		super(kb);
		code = "FC";
		longName = "Forward Chaining";
		clauses = kb.getClauses();
		facts = kb.getFacts();
		query = kb.getQuery();
	}
	

	@Override
	public String testAsk()
	{
		String output = "";
		// check if the query is proven or not 
		if ( checkQuery() )
		{
			// if proven, output is YES + each fact that is discovered e.g. YES: a, b, p2, p3, p1, d
			
			output = "YES: ";
			
			for (int i = 0; i < outputFacts.size(); i++)
			{
				output += ( outputFacts.get(i) );
				
				if (i < outputFacts.size() - 1)
				{
					output += ", ";
				}
			}
		}
		else
		{
			output = "NO";
		}
		
		return output;
	}
	
	@Override
	public boolean checkQuery(){
		
		while ( !facts.isEmpty() ){
			
			// remove off the first fact we want to explore
			String aFact = facts.remove(0);
			
			//add to list of facts used to output
			outputFacts.add(aFact);
			
			//if the current fact and proposition symbol are the same, return true	
			//otherwise loop through each clause and if it finds a match, remove it
			
			if (aFact.equals(query))
			{
				return true;
			}
			
			//search through each fact until there are none left
			//remove the first fact we want to explore and add to list of facts used to output

			//forward chaining material:
			//https://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases
		}
		
		return false;
	}
}
