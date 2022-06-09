package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Term extends AstNode {

	public Term(Location location) {
		super(location);
	}
	
	public Factor getFactor() {
		return getChild(Factor.class);
	}
	
	public FunctionCall getFunctionCall() {
		return getChild(FunctionCall.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitTerm(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
