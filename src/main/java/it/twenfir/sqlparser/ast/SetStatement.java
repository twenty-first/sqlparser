package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SetStatement extends AstNode {

	private String name;

    public SetStatement(Location location, String name) {
        super(location);
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public OutputParameter getParameter() {
		return getChild(OutputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSetStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
