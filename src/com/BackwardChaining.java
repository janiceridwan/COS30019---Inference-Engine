package com;

// While agenda not empty
//      get next symbol off agenda
//      if curSymbol is not inferred
//          add previous symbols to agenda

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BackwardChaining extends Method {

    private final KnowledgeBase kb;
    private final ArrayList<String> facts;
    private final String query;
    public static ArrayList<String> clauses;
    private ArrayList<Symbol> agenda = new ArrayList<>();
    private final Queue<Symbol> outputFacts;

    public BackwardChaining(KnowledgeBase kb) {
        super(kb);
        this.kb = kb;
        this.facts = kb.getFacts();
        this.query = kb.getQuery();
        this.clauses = kb.getClauses();
        this.outputFacts = new LinkedList<>();
        agenda.add(kb.getSymbol(query));
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
        Symbol aFact = agenda.remove(0);

        if (aFact.isInferred(facts)){
//            if (!outputFacts.contains(aFact)) {outputFacts.add(aFact);}
            outputFacts.add(aFact);
            return true;
        }

        int i = aFact.getInConclusion().size();
        for (Symbol s : aFact.getInConclusion()) {
            agenda.add(s);
            if (checkQuery()) {
//                if (!outputFacts.contains(aFact)) {outputFacts.add(aFact);}
                outputFacts.add(aFact);
                i--;
                if (i==0) {return true;}
            }
        }

        return false;
    }



 
}
