package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CombinedOutputParameter extends OutputParameter {

	private Parameter indicator;

	public CombinedOutputParameter(Location location, Parameter parameter, Parameter indicator) {
		super(location, parameter);
		this.indicator = indicator;
	}

	public Parameter getIndicator() {
		return indicator;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitCombinedOutputParameter(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
