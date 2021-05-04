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
            
            
            Method thisMethod = null;
    		
            Method[] methods = {
            		new ForwardChaining(kb)
            };
            
    		//determine which method the user wants to use
    		for(int i = 0; i < methods.length; i++)
    		{
    			//do they want this one?
    			if(methods[i].code.compareTo(method) == 0)
    			{
    				//yes, use this method.
    				thisMethod = methods[i];
    			}
    		}
    		
    		//Has the method been implemented?
    		if(thisMethod == null)
    		{
    			//No, give an error
    			System.out.println("Search method identified by " + method + " not implemented. Methods are case sensitive.");
    			System.exit(1);
    		}

        }
    }

    
	
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  jar -jar project.jar method filename");
    }
}
