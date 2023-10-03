package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class WithUrClause extends AstNode {

	public WithUrClause(Location location) {
		super(location);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitWithUrClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
