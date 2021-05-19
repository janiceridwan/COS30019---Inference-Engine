package com;

import java.util.ArrayList;

public class TruthTable extends Method {

    private ArrayList<Symbol> symbols;
	private int columns;
	private int rows;
	private boolean[][] table;
	private boolean[] rowResult;
	private boolean[] queryResult;
	private int queryIndex;
	private int count;
	private ArrayList<String> facts;
	private int[] factIndex;

	public TruthTable(KnowledgeBase kb) {
		super(kb);
		this.kb = kb;
        this.query = kb.getQuery();
        this.facts = kb.getFacts();
        
        symbols = kb.getSymbols();

		//size of TT is 2^n, where n is the no. of propositional symbols 
		rows = (int)Math.pow(2,(symbols.size()));

		// one column for each variable
		columns = symbols.size();
		
		// table will store the boolean (T/F) value of each variable in a 2d array
		table = new boolean[rows][columns];

		// a final column that determines the result of each row
		rowResult = new boolean[rows];	
		
		factIndex = new int[facts.size()];
		
		//query index is 0 as the q value will be on top of the symbols array
		queryIndex = 0;
		queryResult = new boolean[rows];
		
		count = 0;

		populateTable();
	}
	
	public void populateTable( )
	{
		//Dhass' answer on https://careercup.com/question?id=17632666 to populate a TT 
		for ( int i = 0; i < rows; i++ )
		{
			for ( int j = 0; j < columns; j++ )
			{
				int k = i & 1 << columns - 1 - j;

				table[i][j] = ( k == 0 ? true : false );
				//test print TT 
				System.out.print(k == 0 ? "T" : "F");
			}
			System.out.println();
		}
		
	}

	@Override
    public StringBuilder testAsk() {
        StringBuilder output = new StringBuilder();
        // check if the query is proven or not
        if (checkQuery()) {
            // if proven, output is YES + each fact that is discovered e.g. YES: a, b, p2, p3, p1, d
            output.append("YES: " + count);

        } else {
            output.append("NO");
        }

        return output;
    }
	
	@Override
    public boolean checkQuery() {
				
		for ( int i = 0; i < rows; i++ )
		{
			rowResult[i] = true;
			
	        //check state of facts in each row
			//if one fact is false, whole row result will be false
			for ( int j = 0; j < factIndex.length; j++ ) {
				if(rowResult[i]) {				
					// if the query is false, the whole row is false
					if (!table[i][queryIndex] )
					{
						queryResult[i] = false;
						rowResult[i] = false;						

					}
					else
					{
						queryResult[i] = true;
					}				
				}
				rowResult[i] = table[i][factIndex[j]];
			}
			//then check state of other literals
			//check against the clauses, for example:
			//a=>p1, if a is T and p1 is F then row result is F
			//a&c=>e if a&c are T but e is F, then row result is F						
			
			if ( queryResult[i] == false && rowResult[i] == true )
			{
				return false;
			}
			
			// count the number of rows that are true
			if (rowResult[i]==true)
			{
				count++;
			}
		}

        return true;
    }
	

}
