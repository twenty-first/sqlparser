package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OutputParameter extends AstNode {

	public OutputParameter(Location location) {
		super(location);
	}

	public Parameter getParameter() {
		return getChild(Parameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
