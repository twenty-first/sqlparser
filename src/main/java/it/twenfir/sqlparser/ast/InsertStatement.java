package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class InsertStatement extends Statement {

    public InsertStatement(Location location) {
        super(location);
    }
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	if ( visitor instanceof SqlVisitor ) {
    		return ((SqlVisitor<? extends ValueT>) visitor).visitInsertStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
