package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Parameter extends AstNode {

	private String name;
	private Integer index;
	
	public Parameter(Location location, String name, Integer index) {
		super(location);
		this.name = name;
		this.index = index;
	}
	
    public String getName() {
		return name;
	}

	public Integer getIndex() {
		return index;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
