package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CombinedInputParameter extends CompositeInputParameter {

	public CombinedInputParameter(Location location) {
		super(location);
	}

	public InputParameter getInputParameter() {
		return getChild(InputParameter.class);
	}

	public Indicator getIndicator() {
		return getChild(Indicator.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitCombinedInputParameter(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
