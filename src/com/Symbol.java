package com;

import java.util.ArrayList;

public class Symbol {
    private final String name;
    private int count = 0;
    private Boolean inferred = false;
    private final ArrayList<Symbol> inPremise = new ArrayList<>();
    private final ArrayList<Symbol> inConclusion = new ArrayList<>();

    public Symbol(String name) {
        this.name = name;
    }

    public void addCount(Integer premises) {
        for (int i = 0; i < premises; i++) {
                count++;
        }
    }

    public void decrementCount() {
        count--;
    }

    public Boolean isInferred(ArrayList<String> facts) {
        if (count == 0 && facts.contains(this.name)) {
            inferred = true;
        }
        return inferred;
    }

    public void infer() {
        inferred = true;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Symbol> getInPremise() {
        return inPremise;
    }

    public void addPremise(Symbol symbol) {
        inPremise.add(symbol);
    }

    public ArrayList<Symbol> getInConclusion() {
        return inConclusion;
    }

    public void addConclusion(ArrayList<Symbol> symbols) {
        inConclusion.addAll(symbols);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
