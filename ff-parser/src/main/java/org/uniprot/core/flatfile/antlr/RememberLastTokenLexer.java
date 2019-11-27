package org.uniprot.core.flatfile.antlr;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

/**
 * Individual LineLexers can use the superClass option use this class as their super class, thus
 * gain the abilitity to remember the last token emitted.
 *
 * <p><code>
 * options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }
 * </code>
 *
 * <p>This is mainly used to deal with the change of line. <br>
 * This class cannot be put under the uk.ac.ebi.uniprot.parser package cause parser is a keyword in
 * the Antlr grammar file.
 */
public abstract class RememberLastTokenLexer extends AbstractUniProtLexer {

    /** */
    private static final long serialVersionUID = 1L;

    private Token lastToken;
    private Token nextToken;
    private static final List<String> END_TOKENS = Arrays.asList(new String[] {"-", "/"});

    protected RememberLastTokenLexer(CharStream input) {
        super(input);
    }

    @Override
    public Token emit() {
        lastToken = super.emit();
        nextToken = super.getToken();
        return lastToken;
    }

    public void replaceDoubleQuote() {

        String text3 = this.getText();
        System.out.println(text3);
        this.setText("''");
        if (nextToken != null) {
            String text2 = nextToken.getText();
            System.out.println("nextToken=" + text2);
        }
    }

    public void replaceChangeOfLine() {
        if (lastToken != null) {
            String text1 = lastToken.getText();
            if (text1.endsWith("-")) {
                int index = this._input.index();
                String text = this._input.getText(Interval.of(index, index + 3));

                // Do not add a blank after a hyphen that is not preceded by a
                // blank and not followed by and/or.
                //
                if ((text.startsWith("and ") || text.startsWith("or ")) || text1.endsWith(" -")) {
                    this.setText(" ");
                } else {
                    this.setText("");
                }
            } else {
                this.setText(" ");
            }
        }
        //		this.setText(" ");
    }

    public void replaceChangeOfLineForFT() {
        if (lastToken != null) {
            String text1 = lastToken.getText();
            if (text1.endsWith("/")) {
                this.setText("");
            } else if (text1.endsWith("-")) {
                int index = this._input.index();
                String text = this._input.getText(Interval.of(index, index + 3));

                // Do not add a blank after a hyphen that is not preceded by a
                // blank and not followed by and/or.
                //
                if ((text.startsWith("and ") || text.startsWith("or ")) || text1.endsWith(" -")) {
                    this.setText(" ");
                } else {
                    this.setText("");
                }
            } else {
                this.setText(" ");
            }
        }
        //		this.setText(" ");
    }

    public void replaceChangeOfLine(boolean seq) {
        if (!seq) {
            replaceChangeOfLineForFT();
        } else {
            if (isSequenceLetter(lastToken.getText())) {
                this.setText("");
            } else {
                replaceChangeOfLineForFT();
            }
        }
    }

    public boolean isSequenceLetter(String se) {
        for (int i = 0; i < se.length(); i++) {
            if (se.charAt(i) > 'Z' || se.charAt(i) < 'A') return false;
        }
        return true;
    }
}
