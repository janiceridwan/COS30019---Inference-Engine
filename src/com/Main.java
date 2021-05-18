package com;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            usage();
        } else {
//            String method = args[0].toUpperCase();
            String method = "FC";
            String filename = args[1];

            KnowledgeBase kb = new KnowledgeBase(filename);
            StringBuilder result = null;

            //For the above, could use a switch case like below
            switch (method) {
                case "FC":
                    ForwardChaining FC = new ForwardChaining(kb);
                    result = FC.testAsk();
                    break;
                case "BC":
                	BackwardChaining BC = new BackwardChaining(kb);
                	result = BC.testAsk();
                    break;
                case "TT":
                    break;
                default:
                    throw new IllegalArgumentException("Search method does not exist");
            }


            //Has the method been implemented?
            if (result != null && result.length() != 0) {
                System.out.println(result);
            }
        }
    }


    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  jar -jar project.jar method filename");
    }
}
