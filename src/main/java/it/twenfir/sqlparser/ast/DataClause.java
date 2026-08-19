package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class DataClause extends AstNode {

	private boolean data;
	
	public DataClause(Location location, boolean data) {
		super(location);
	}
	
	boolean hasData() {
		return data;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitDataClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
