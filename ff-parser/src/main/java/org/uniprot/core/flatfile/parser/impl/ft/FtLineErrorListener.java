package org.uniprot.core.flatfile.parser.impl.ft;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.uniprot.core.flatfile.parser.DefaultErrorListener;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 23/10/2013 Time: 12:35 To change this template use
 * File | Settings | File Templates.
 */
public class FtLineErrorListener extends DefaultErrorListener {

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e) {
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
    }
}
