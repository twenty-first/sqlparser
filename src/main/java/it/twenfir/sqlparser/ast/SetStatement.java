package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SetStatement extends Statement {

	private String name;

    public SetStatement(Location location, String name) {
        super(location);
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public OutputParameter getParameter() {
		return getChild(OutputParameter.class);
	}
	
	public SelectExpression getSelectExpression() {
		return getDescendant(SelectExpression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSetStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
