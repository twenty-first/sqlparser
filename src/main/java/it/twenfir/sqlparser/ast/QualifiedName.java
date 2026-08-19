package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class QualifiedName extends AstNode {

	private final String name;
	private final String schema;
	private final String library;
	
	public QualifiedName(Location location, String name, String schema, String library) {
		super(location);
		this.name = name;
		this.schema = schema;
		this.library = library;
	}
	
    public String getName() {
		return name;
	}

	public String getSchema() {
		return schema;
	}

	public String getLibrary() {
		return library;
	}

}
