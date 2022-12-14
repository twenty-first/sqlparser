package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class DeclareTempTableStatement extends Statement {

	public DeclareTempTableStatement(Location location) {
		super(location);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitDeclareTempTableStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
