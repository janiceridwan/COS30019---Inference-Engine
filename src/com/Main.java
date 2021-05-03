package com;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
        } else {
            String method = args[0].toUpperCase();
            String filename = args[1];

            KnowledgeBase kb = new KnowledgeBase(filename);
        }
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  jar -jar project.jar method filename");
    }
}
