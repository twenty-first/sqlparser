package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class FunctionCall extends AstNode {

	private String name;
	
	public FunctionCall(Location location, String name) {
		super(location);
		this.name = name;
	}
	
	public String getName() {
		if ( name == null ) {
			name = getFunction().getName();
		}
		return name;
	}
	
	public Function getFunction() {
		return getChild(Function.class);
	}
	
	public ExprList getExprList() {
		return getChild(ExprList.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
