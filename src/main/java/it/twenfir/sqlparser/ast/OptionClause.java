package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OptionClause extends AstNode {

	public OptionClause(Location location) {
		super(location);
	}
	
	public OptionName getOptionName() {
		return getChild(OptionName.class);
	}
	
	public OptionValue getOptionValue() {
		return getChild(OptionValue.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitOptionClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
