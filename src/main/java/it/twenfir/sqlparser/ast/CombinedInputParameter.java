package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CombinedInputParameter extends AstNode {

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
    	return visitor.visit(this);
    }

}
