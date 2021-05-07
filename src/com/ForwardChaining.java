package com;

import java.util.ArrayList;

public class ForwardChaining extends Method {

    private KnowledgeBase kb;
    private ArrayList<String> clauses;
    private ArrayList<String> facts;
    private String query;
    private ArrayList<Symbol> agenda;
    private ArrayList<Symbol> outputFacts;

    public ForwardChaining() {

    }

    public ForwardChaining(KnowledgeBase kb) {
        super(kb);
//        code = "FC";
//        longName = "Forward Chaining";
        this.kb = kb;
        this.clauses = kb.getClauses();
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
    public String testAsk() {
        StringBuilder output = new StringBuilder();
        // check if the query is proven or not
        if (checkQuery()) {
            // if proven, output is YES + each fact that is discovered e.g. YES: a, b, p2, p3, p1, d

            output.append("YES: ");

//            for (int i = 0; i < outputFacts.size(); i++) {
//                output.append(outputFacts.get(i));
//
//                if (i < outputFacts.size() - 1) {
//                    output.append(", ");
//                }
//            }

            // Simpler option to the above commented out code
            System.out.println(output.append(String.join(", ", outputFacts + "\n")));

        } else {
            output.append("NO");
        }

        return output.toString();
    }

    @Override
    public boolean checkQuery() {
        while (!agenda.isEmpty()) {
            // remove off the first fact we want to explore
//            String aFact = facts.remove(0);
            Symbol aFact = agenda.get(0);

            //add to list of facts used to output
            outputFacts.add(aFact);

            //if the current fact and proposition symbol are the same, return true
            //otherwise loop through each clause and if it finds a match, remove it

            if (aFact.getName().equals(query)) {
                return true;
            }

            //search through each fact until there are none left
            //get the first fact we want to explore and add to list of facts used to output
//            if (!aFact.isInferred(facts)) {
//                while (!aFact.isInferred(facts)) {
//                    for ()
//                }
//            }

            //forward chaining material:
            //https://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases
        }

        return false;
    }

}
