package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SelectColumn extends AstNode {

	private String outputName;
	
	public SelectColumn(Location location, String outputName) {
		super(location);
		this.outputName = outputName;
	}

	public String getOutputName() {
		return outputName;
	}
	
	public Expression getExpression() {
		return getChild(Expression.class);
	}
	
	public Iterator<FunctionCall> getFunctionCalls() {
		return getDescendants(FunctionCall.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSelectColumn(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
