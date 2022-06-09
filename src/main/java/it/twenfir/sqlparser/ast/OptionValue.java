package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OptionValue extends AstNode {

	private String value;
	
	public OptionValue(Location location, String value) {
		super(location);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitOptionValue(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
