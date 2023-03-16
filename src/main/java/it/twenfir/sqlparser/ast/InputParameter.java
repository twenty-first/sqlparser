package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class InputParameter extends AstNode {

	private Parameter parameter;

	public InputParameter(Location location, Parameter parameter) {
        super(location);
		this.parameter = parameter;
    }
    
	public Parameter getParameter() {
		return parameter;
	}
}
