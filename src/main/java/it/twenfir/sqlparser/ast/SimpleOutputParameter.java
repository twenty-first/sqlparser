package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SimpleOutputParameter extends OutputParameter {

	public SimpleOutputParameter(Location location, Parameter parameter) {
		super(location, parameter);
	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSimpleOutputParameter(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
