package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SimpleInputParameter extends InputParameter {

	public SimpleInputParameter(Location location) {
		super(location);
	}

	public Parameter getParameter() {
		return getChild(Parameter.class);
	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSimpleInputParameter(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
