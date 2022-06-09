package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Factor extends AstNode {

	public Factor(Location location) {
		super(location);
	}
	
	public CombinedInputParameter getCombinedInputParameter() {
		return getChild(CombinedInputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitFactor(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
