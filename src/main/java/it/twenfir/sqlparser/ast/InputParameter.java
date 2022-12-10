package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class InputParameter extends AstNode {

    public InputParameter(Location location) {
        super(location);
    }
    
    public abstract Parameter getParameter();
}
