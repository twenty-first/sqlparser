package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class DropAliasStatement extends Statement {
	
	public DropAliasStatement(Location location) {
		super(location);
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitDropAliasStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
