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
        return query;
    }

    /*  Gets passed the line with the horn clauses
        Splits line by ; to get array with individual clauses
        Loops through array and removes whitespaces then sorts them into clauses and facts
    */
    private void parseHornClauses(String line) {
        String[] sentences = line.split(";");
        for (String sentence : sentences) {
            sentence = sentence.replace(" ", ""); // TODO: Look into Java whitespace regex
            System.out.println(sentence); // For testing only, delete line when done

            if (sentence.contains("=>")){
                clauses.add(sentence);
            }
            else {
                facts.add(sentence);
            }
        }
    }
}
