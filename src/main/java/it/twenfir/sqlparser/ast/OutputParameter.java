package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OutputParameter extends Parameter {

	public OutputParameter(Location location, String name, Integer index) {
		super(location, name, index);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitOutputParameter(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
