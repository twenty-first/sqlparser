package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Function extends AstNode {

	private String name;

	public Function(Location location, String name) {
		super(location);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitFunction(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
