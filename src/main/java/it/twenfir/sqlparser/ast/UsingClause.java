package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class UsingClause extends AstNode {

	public UsingClause(Location location) {
		super(location);
	}

	Iterator<CombinedInputParameter> getParameters() {
		return getChildren(CombinedInputParameter.class);
	}

	Iterator<Parameter> getDescriptors() {
		return getChildren(Parameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitUsingClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }
}
