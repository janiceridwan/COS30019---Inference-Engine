package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TruthTable extends Method {

	private final ArrayList<Symbol> symbols;
	private final int columns;
	private final int rows;
	private final boolean[][] table;
	private final Symbol querySymbol;
	private int count;
	private final ArrayList<String> facts;
	private final ArrayList<String> clauses;

	public TruthTable(KnowledgeBase kb) {
		super(kb);
		this.kb = kb;
		this.query = kb.getQuery();
		this.facts = kb.getFacts();
		this.clauses = kb.getClauses();
		this.symbols = kb.getSymbols();
		this.querySymbol = kb.getSymbol(query);

		//size of TT is 2^n, where n is the no. of propositional symbols
		rows = (int)Math.pow(2,(symbols.size()));

		// one column for each variable
		columns = symbols.size();

		// table will store the boolean (T/F) value of each variable in a 2d array
		table = new boolean[rows][columns];

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

				table[i][j] = (k == 0);

				// For test printing TT
//				System.out.print(k == 0 ? "T" : "F");
			}
//			System.out.println();
		}

	}

	@Override
	public StringBuilder testAsk() {
		StringBuilder output = new StringBuilder();
		// check if the query is proven or not
		if (checkQuery()) {
			// if proven, output is YES + each fact that is discovered e.g. YES: a, b, p2, p3, p1, d
			output.append("YES: ").append(count);

		} else {
			output.append("NO");
		}

		return output;
	}

	@Override
	public boolean checkQuery() {
		// Checks if the query symbol is in the knowledge base's propositional symbols
		if (!symbols.contains(querySymbol)) {
			throw new IllegalArgumentException("Query must be a propositional symbol");
		}

		boolean validRow;
		boolean queryValid = false;

		// For testing purposes only
//		for (Symbol symbol : symbols) {
//			System.out.print(symbol.getName() + " ");
//		}
//		System.out.println();

		if (facts.isEmpty()) { return false; } // If there are no facts then a query can not be determined

		for (int row = 0; row < table.length; row++) {
			validRow = true;
			// Validate the implications
			for (String clause : clauses) {
				if (validate(clause.split("=>")[0], row) && !validate(clause.split("=>")[1], row)) {
					validRow = false;
				}
			}
			// Validate the facts
			for (String fact : facts) {
				if (!validate(fact, row)) {
					validRow = false;
				}
			}
			// Check if row is valid for given KB and KB |= query
			if (validRow && table[row][findPos(query)]) {
				count++;
				queryValid = true;

				// For testing purposes only
//				for (int x = 0; x < symbols.size(); x++) {
//					System.out.print(table[row][x] + " ");
//				}
//				System.out.println();

			}
		}
		return queryValid;
	}

	// Validates the given horn clause / fact for the current row
	private boolean validate(String s, int row) {
		String[] clauseSymbols = s.split("&");
		Map<String, Integer> clauseSymbolsMap = new HashMap<>();
		for (String clauseSymbol : clauseSymbols) {
			clauseSymbolsMap.put(clauseSymbol, findPos(clauseSymbol));
		}
		for (Map.Entry<String, Integer> entry : clauseSymbolsMap.entrySet()) {
			if (!table[row][entry.getValue()]) { return false; }
		}
		return true;
	}

	// Find the position of a symbol in the table's row
	private Integer findPos(String x) {
		for (int i = 0; i < symbols.size(); i++) {
			if (symbols.get(i).getName().equals(x)) {
				return i;
			}
		}
		return null;
	}
}