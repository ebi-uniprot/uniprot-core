package org.uniprot.core.flatfile.antlr;

import java.io.Serializable;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;

/**
 *
 * @author jluo
 * @date: 2 Jul 2019
 *
*/

public abstract class AbstractUniProtLexer extends Lexer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1009632171803230607L;



	protected AbstractUniProtLexer(CharStream input) {
		super(input);

	}


	

}

