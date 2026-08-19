package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Index extends QualifiedName {

	public Index(Location location, String... identifiers) {
		super(location, identifiers[0], identifiers[1], identifiers[2]);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitIndex(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
