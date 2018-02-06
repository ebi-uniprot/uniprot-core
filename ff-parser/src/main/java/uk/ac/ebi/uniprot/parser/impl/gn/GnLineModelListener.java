package uk.ac.ebi.uniprot.parser.impl.gn;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import uk.ac.ebi.uniprot.antlr.GnLineParser;
import uk.ac.ebi.uniprot.parser.impl.HasEvidenceInfo;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.GnLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class GnLineModelListener extends GnLineParserBaseListener implements ParseTreeObjectExtractor<GnLineObject> {

	private GnLineObject object;

    @Override
    public void enterGn_gn(@NotNull GnLineParser.Gn_gnContext ctx) {
        this.object = new GnLineObject();
    }

    @Override
	public void exitGn_line_block(@NotNull GnLineParser.Gn_line_blockContext ctx) {
		GnLineObject.GnObject gnObject = new GnLineObject.GnObject();
		List<GnLineParser.One_nameContext> one_nameContexts = ctx.one_name();

		for (GnLineParser.One_nameContext context : one_nameContexts) {
			if (context.gene_name() != null) {
				GnLineParser.Gene_nameContext gene_nameContext = context.gene_name();
				GnLineObject.GnName gnName = new GnLineObject.GnName();
				gnName.type = GnLineObject.GnNameType.GENAME;

				GnLineParser.NameContext name = gene_nameContext.name();
				GnLineParser.EvidenceContext evidence = name.evidence();
				String text = name.words().getText();

				gnName.names.add(text);
				addEvidenceForName(text, evidence, gnName);
				gnObject.names.add(gnName);

			} else if (context.syn_name() != null) {
				GnLineParser.Syn_nameContext syn_nameContext = context.syn_name();
				GnLineObject.GnName gnName = new GnLineObject.GnName();
				gnName.type = GnLineObject.GnNameType.SYNNAME;

				List<GnLineParser.NameContext> names = syn_nameContext.names().name();
				for (GnLineParser.NameContext name : names) {
					GnLineParser.EvidenceContext evidence = name.evidence();
					String text = name.words().getText();
					gnName.names.add(text);
					addEvidenceForName(text, evidence, gnName);
				}
				gnObject.names.add(gnName);
			} else if (context.orf_name() != null) {
				GnLineParser.Orf_nameContext orf_nameContext = context.orf_name();
				GnLineObject.GnName gnName = new GnLineObject.GnName();
				gnName.type = GnLineObject.GnNameType.ORFNAME;

				List<GnLineParser.NameContext> names = orf_nameContext.names().name();
				for (GnLineParser.NameContext name : names) {
					GnLineParser.EvidenceContext evidence = name.evidence();
					String text = name.words().getText();
					gnName.names.add(text);
					addEvidenceForName(text, evidence, gnName);
				}
				gnObject.names.add(gnName);
			} else if (context.ol_name() != null) {
				GnLineParser.Ol_nameContext ol_nameContext = context.ol_name();
				GnLineObject.GnName gnName = new GnLineObject.GnName();
				gnName.type = GnLineObject.GnNameType.OLNAME;

				List<GnLineParser.NameContext> names = ol_nameContext.names().name();
				for (GnLineParser.NameContext name : names) {
					GnLineParser.EvidenceContext evidence = name.evidence();
					String text = name.words().getText();
					gnName.names.add(text);
					addEvidenceForName(text, evidence, gnName);
				}
				gnObject.names.add(gnName);
			}
		}

		object.gnObjects.add(gnObject);
	}

	private void addEvidenceForName(String text, GnLineParser.EvidenceContext evidence, HasEvidenceInfo container) {
		if (evidence == null) return;

		EvidenceInfo evidenceInfo = container.getEvidenceInfo();
		List<TerminalNode> terminalNodes = evidence.EV_TAG();
		List<String> strings = new ArrayList<String>();
		for (TerminalNode terminalNode : terminalNodes) {
			String text1 = terminalNode.getText();
			strings.add(text1);
		}
		evidenceInfo.evidences.put(text, strings);
	}

	public GnLineObject getObject() {
		return object;
	}
}
