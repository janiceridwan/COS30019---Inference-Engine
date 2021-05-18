package com;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
// File format is as follows:
// TELL
// p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;
// ASK
// d

public class KnowledgeBase {
    private final ArrayList<String> facts;
    private final ArrayList<String> clauses;
    private final ArrayList<Symbol> symbols = new ArrayList<>();
    private String query;

    public KnowledgeBase(String filename) throws IOException {
        facts = new ArrayList<>();
        clauses = new ArrayList<>();
        query = readFile(filename);
    }

    private String readFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        LineNumberReader lineReader = new LineNumberReader(reader);
        lineReader.readLine(); // First line is "TELL" and can be ignored
        parseHornClauses(lineReader.readLine());
        lineReader.readLine(); // Third line is "ASK" and can be ignored
        query = lineReader.readLine().trim();
        lineReader.close();
        return query;
    }

    /*
        Gets passed the line with the horn clauses
        Splits line by ; to get array with individual clauses
        Loops through array and removes whitespaces then sorts them into clauses and facts
    */
    private void parseHornClauses(String line) {
        ArrayList<String> tempSymbols = new ArrayList<>();
        String[] sentences = line.split(";");

        for (String sentence : sentences) {
        	//regex from Kevin Bowersox's answer on https://stackoverflow.com/questions/15633228/how-to-remove-all-white-spaces-in-java 
            sentence = sentence.replaceAll("\\s", "");

            String[] symbolList = sentence.split("&|(=>)");
            for (String symbol : symbolList) {
                if (!tempSymbols.contains(symbol)) {
                    tempSymbols.add(symbol);
                    symbols.add(new Symbol(symbol));
                }
                if (sentence.contains("=>") && conclusionContains(sentence, symbol)) {
                    getSymbol(symbol).addCount(premiseCount(sentence));
                }
            }

            if (sentence.contains("=>")){
                clauses.add(sentence);
            }
            else {
                facts.add(sentence);
                getSymbol(sentence).infer();
            }
        }
        for (String sentence : sentences) {
            sentence = sentence.replaceAll("\\s", "");
            if (sentence.contains("=>")){
                for (Symbol s : symbols) {
                    if (premiseContains(sentence, s.getName()) && !s.getInPremise().contains(getSymbol(sentence.split("=>")[1]))) {
                        s.addPremise(getSymbol(sentence.split("=>")[1]));
                    }
                }
            }
        }
        
        for (String sentence : sentences) {
            sentence = sentence.replaceAll("\\s", "");
            if (sentence.contains("=>")){
                for (Symbol s : symbols) {
                    if (conclusionContains(sentence, s.getName())) {
                        String[] premiseStrings = sentence.split("=>")[0].split("&");
                        ArrayList<Symbol> premiseSymbols = new ArrayList();
                        for (String p : premiseStrings){
                            premiseSymbols.add(getSymbol(p));
                        }
                        s.addConclusion(premiseSymbols);
                    }
                }
            }
        }

//        for (Symbol s : symbols) {
//            System.out.println(s.getName());
//            System.out.println(s.getInConclusion() + "\n");
//        }
    }

    private boolean premiseContains(String sentence, String c) {
        boolean contains;
        String premise = sentence.split("=>")[0];
        String[] conjunctions = premise.split("&");
        for (String conjunction : conjunctions) {
            contains = conjunction.equals(c);
            if (contains) {return true;}
        }
        return false;
    }

    public Symbol getSymbol(String c) {
        for (Symbol symbol : symbols) {
            if (c.equals(symbol.getName())) {
                return symbol;
            }
        }
        return null;
    }

    public Integer premiseCount(String sentence) {
        String premise = sentence.split("=>")[0];
        String[] conjunction = premise.split("&");
        return conjunction.length;
    }

    public static boolean conclusionContains(String sentence, String c){
        String conclusion = sentence.split("=>")[1];
        // check if c is the conclusion
        return c.equals(conclusion);
    }
    
    /**
     * @return an ArrayList of the clauses stored in the KnowledgeBase.
     */
    public ArrayList<String> getClauses() {
      return clauses;
    }
    
    /**
     * @return an ArrayList of facts stored in the KnowledgeBase.
     */
    public ArrayList<String> getFacts() {
      return facts;
    }
    
    public String getQuery() {
    	return query;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }
}
