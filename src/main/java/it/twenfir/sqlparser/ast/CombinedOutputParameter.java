package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CombinedOutputParameter extends AstNode {

	public CombinedOutputParameter(Location location) {
		super(location);
	}

	public OutputParameter getOutputParameter() {
		return getChild(OutputParameter.class);
	}

	public Indicator getIndicator() {
		return getChild(Indicator.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
