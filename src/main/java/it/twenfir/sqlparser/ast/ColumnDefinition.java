package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class ColumnDefinition extends AstNode {

	public ColumnDefinition(Location location) {
		super(location);
	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitColumnDefinition(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
