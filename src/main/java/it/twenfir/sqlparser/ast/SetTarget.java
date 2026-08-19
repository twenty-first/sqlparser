package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SetTarget extends AstNode {

	private String name;

    public SetTarget(Location location, String name) {
        super(location);
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public OutputParameter getParameter() {
		return getChild(OutputParameter.class);
	}
	
	public Expression getExpression() {
		return getChild(Expression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSetTarget(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
