package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class LabelStatement extends Statement {

	public LabelStatement(Location location) {
		super(location);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	if ( visitor instanceof SqlVisitor ) {
    		return ((SqlVisitor<? extends ValueT>) visitor).visitLabelStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
