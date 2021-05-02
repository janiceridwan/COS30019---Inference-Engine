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
    public static ArrayList<String> facts = null;
    public static ArrayList<String> clauses = null;

    public KnowledgeBase() {
    }

    public static KnowledgeBase readFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        LineNumberReader lineReader = new LineNumberReader(reader);
        lineReader.readLine(); // First line is "TELL" and not needed
        String[] hornClauses = parseHornClauses(lineReader.readLine());
        return null;
    }

    private static String[] parseHornClauses(String line) {
        System.out.println(line);
        String[] sentences = line.split(";");
        for (String sentence : sentences) {
            sentence = sentence.trim();
            sentence = sentence.replace(" ", "");
            System.out.println(sentence); // TODO: fix extra null string at end of array

            if (sentence.contains("=>")){
                clauses.add(sentence); // TODO: fix null pointer
            }
            else {
                facts.add(sentence);
            }
        }
        return sentences;
    }
}
