package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SimpleOutputParameter extends InputParameter {

	public SimpleOutputParameter(Location location) {
		super(location);
	}

	public Parameter getParameter() {
		return getChild(Parameter.class);
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
