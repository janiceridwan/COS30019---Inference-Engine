package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BackwardChaining extends Method {

    private final ArrayList<String> facts;
    public static ArrayList<String> clauses;
    private final ArrayList<Symbol> agenda = new ArrayList<>();
    private final Queue<Symbol> outputFacts;
    private final ArrayList<Symbol> checkedSymbols = new ArrayList<>();
    private final ArrayList<Symbol> symbols;
    private final Symbol querySymbol;

    public BackwardChaining(KnowledgeBase kb) {
        super(kb);
        this.facts = kb.getFacts();
        String query = kb.getQuery();
        clauses = kb.getClauses();
        this.outputFacts = new LinkedList<>();
        agenda.add(kb.getSymbol(query));
        this.symbols = kb.getSymbols();
        this.querySymbol = kb.getSymbol(query);
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

    // Depth-first search until a fact is reached then backtrack
    @Override
    public boolean checkQuery() {
        // Checks if the query symbol is in the knowledge base's propositional symbols
        if (!symbols.contains(querySymbol)) {
            throw new IllegalArgumentException("Query must be a propositional symbol");
        }

        // remove the first symbol on the agenda
        Symbol aSymbol = agenda.remove(0);
        checkedSymbols.add(aSymbol);

        // check if aSymbol is a fact
        if (aSymbol.isInferred(facts)){
            if (!outputFacts.contains(aSymbol)) {outputFacts.add(aSymbol);}
            if (!facts.contains(aSymbol.getName())) {facts.add(aSymbol.getName());}
            return true;
        }

        int i = aSymbol.getInConclusion().size();
        for (Symbol s : aSymbol.getInConclusion()) {
            boolean x = !checkedSymbols.contains(s);
            if (!checkedSymbols.isEmpty() && x) {
                agenda.add(s);
            }
            if (!agenda.isEmpty() && checkQuery()) {
                i--;
                if (i==0) {
                    if (!facts.contains(aSymbol.getName())) {facts.add(aSymbol.getName());}
                    if (!outputFacts.contains(aSymbol)) {outputFacts.add(aSymbol);}
                    return true;
                }
            }
        }
        for (Symbol s : aSymbol.getInConclusion()) {
            if (agenda.isEmpty() && facts.contains(s.getName())) {
                if (!facts.contains(aSymbol.getName())) {facts.add(aSymbol.getName());}
                if (!outputFacts.contains(aSymbol)) {outputFacts.add(aSymbol);}
                return true;
            }
        }
        return false;
    }
}
