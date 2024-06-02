package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class FunctionCall extends AstNode {
	
	public FunctionCall(Location location) {
		super(location);
	}
	
	public Function getFunction() {
		return getChild(Function.class);
	}
	
	public ExprList getExprList() {
		return getChild(ExprList.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitFunctionCall(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
