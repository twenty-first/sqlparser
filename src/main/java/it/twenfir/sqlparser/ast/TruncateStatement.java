package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class TruncateStatement extends Statement {
	
	public TruncateStatement(Location location) {
		super(location);
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitTruncateStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
