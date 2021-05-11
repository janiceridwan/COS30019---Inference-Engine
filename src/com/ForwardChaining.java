package com;

import java.util.ArrayList;

public class ForwardChaining extends Method {

    private final KnowledgeBase kb;
    private final ArrayList<String> facts;
    private final String query;
    private ArrayList<Symbol> agenda;
    private final ArrayList<Symbol> outputFacts;

    public ForwardChaining(KnowledgeBase kb) {
        super(kb);
        this.kb = kb;
        this.facts = kb.getFacts();
        this.query = kb.getQuery();
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
            Symbol aFact = agenda.remove(0);

            //add to list of facts used to output
            outputFacts.add(aFact);

            //if the current fact and proposition symbol are the same, return true
            //otherwise loop through each clause and if it finds a match, remove it

            if (aFact.getName().equals(query)) {
                return true;
            }

            //search through each fact until there are none left
            //get the first fact we want to explore and add to list of facts used to output
                for (Symbol c : aFact.getInPremise()) {
                    c.decrementCount();
                    if (c.getCount() == 0) {
                        agenda.add(c);
                    }
                }
            //forward chaining material:
            //https://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases
        }

        return false;
    }

}
