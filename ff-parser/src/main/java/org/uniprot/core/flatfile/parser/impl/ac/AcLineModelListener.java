package org.uniprot.core.flatfile.parser.impl.ac;



import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import org.uniprot.core.flatfile.antlr.AcLineParser;
import org.uniprot.core.flatfile.antlr.AcLineParserBaseListener;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class AcLineModelListener extends AcLineParserBaseListener implements ParseTreeObjectExtractor<AcLineObject> {

    private AcLineObject object;

    @Override
    public void exitAccession(AcLineParser.AccessionContext ctx) {
        String text = ctx.ac().getText();
        if (object.primaryAcc == null) {
            object.primaryAcc = text;
        } else {
            object.secondaryAcc.add(text);
        }
    }

    public AcLineObject getObject() {
        return object;
    }

    @Override
    public void enterAc_ac( AcLineParser.Ac_acContext ctx) {
        object = new AcLineObject();
    }
}
