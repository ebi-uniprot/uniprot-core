package org.uniprot.core.flatfile.parser.impl.kw;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

import org.uniprot.core.flatfile.antlr.KwLineParser;
import org.uniprot.core.flatfile.antlr.KwLineParserBaseListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class KwLineModelListener extends KwLineParserBaseListener implements ParseTreeObjectExtractor<KwLineObject> {

	private KwLineObject object;

    @Override
    public void enterKw_kw(@NotNull KwLineParser.Kw_kwContext ctx) {
        object = new KwLineObject();
    }

    @Override
	public void exitKeyword(@NotNull KwLineParser.KeywordContext ctx) {
		String text = ctx.keyword_v().getText();
		object.keywords.add(text);

		if (ctx.evidence() != null) {
			List<TerminalNode> terminalNodes = ctx.evidence().EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
		}
	}

	public KwLineObject getObject() {
		return object;
	}
}
