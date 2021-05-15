package com;

import java.util.ArrayList;

public class BackwardChaining extends Method {

    private final KnowledgeBase kb;
    private final ArrayList<String> facts;
    private final String query;
    public static ArrayList<String> clauses;
    private ArrayList<Symbol> agenda;
    private final ArrayList<Symbol> outputFacts;

    public BackwardChaining(KnowledgeBase kb) {
        super(kb);
        this.kb = kb;
        this.facts = kb.getFacts();
        this.query = kb.getQuery();
        this.clauses = kb.getClauses();
        this.outputFacts = new ArrayList<>();
        createAgenda();
    }

    private void createAgenda() {
        agenda = new ArrayList<>();
        for (Symbol symbol : kb.getSymbols()) {
            if (symbol.isInferred(facts)) {
                agenda.add(symbol);
            }
        }
    }

    @Override
    public StringBuilder testAsk() {
        StringBuilder output = new StringBuilder();
        // check if the query is proven or not
        if (checkQuery()) {
            // if proven, output is YES + each fact that is discovered e.g. YES: a, b, p2, p3, p1, d
            output.append("YES: ");
            return output.append(String.join(", ", outputFacts + "\n"));

        } else {
            output.append("NO");
        }

        return output;
    }

    @Override
    public boolean checkQuery() {
        while (!agenda.isEmpty()) {
            // remove off the first fact we want to explore
            Symbol aFact = agenda.remove(agenda.size()-1);

            //add to list of facts used to output
            outputFacts.add(aFact);

         // if this element is a fact then we dont need to go further
    		if (!facts.contains(aFact)){
    			// create array to hold new symbols to be processed 
    			ArrayList<Symbol> p = new ArrayList<Symbol>();
    			
    			// for each clause that contains the symbol as its conclusion  					
				for (Symbol c: aFact.getInConclusion()){
					
					//add symbols to be processed
					for (Symbol d: aFact.getInPremise()) {
						p.add(d);
					}
				}								
					
															    			
    			// as it is not a fact and no symbols were generated, we cannot prove that ASK is implied by TELL 
    			if (p.size()==0){
    				return false;
    			}
    			else{
    					// there are symbols so check for previously processed ones and add to agenda
					for(int i=0;i<p.size();i++){
							if (!outputFacts.contains(p.get(i)))
									agenda.add(p.get(i));
							}        
    			}
    		}
     
    	}//while end

        return true;
    }



 
}
