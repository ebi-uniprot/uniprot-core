package org.uniprot.core.flatfile.parser.impl.aaentry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
/**
 *
 * @author jluo
 * @date: 30 Jan 2026
 *
*/

class AAUniProtParserTest {

	@Test
	void testParse() {
		SupportingDataMap supportingDataMap =new SupportingDataMapImpl();
		AAUniProtParser parser = new AAUniProtParser(supportingDataMap, true);
		
        String aaEntryLines = """
AC   UPI000000000B-1032;
DE   RecName: Full=Angiotensin-converting enzyme {ECO:0000256|RuleBase:RU361144};
DE            EC=3.4.-.- {ECO:0000256|RuleBase:RU361144};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=[Pyr1]apelin-13 + H2O = [Pyr1]apelin-12 + L-phenylalanine;
CC         Xref=Rhea:RHEA:63604, ChEBI:CHEBI:15377, ChEBI:CHEBI:58095,
CC         ChEBI:CHEBI:147415, ChEBI:CHEBI:147416;
CC         Evidence={ECO:0000256|ARBA:ARBA00051552};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63605;
CC         Evidence={ECO:0000256|ARBA:ARBA00051552};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=angiotensin I + H2O = angiotensin-(1-9) + L-leucine;
CC         Xref=Rhea:RHEA:63532, ChEBI:CHEBI:15377, ChEBI:CHEBI:57427,
CC         ChEBI:CHEBI:147350, ChEBI:CHEBI:147351;
CC         Evidence={ECO:0000256|ARBA:ARBA00000796};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63533;
CC         Evidence={ECO:0000256|ARBA:ARBA00000796};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=angiotensin II + H2O = angiotensin-(1-7) + L-phenylalanine;
CC         Xref=Rhea:RHEA:26554, ChEBI:CHEBI:15377, ChEBI:CHEBI:58095,
CC         ChEBI:CHEBI:58506, ChEBI:CHEBI:58922; EC=3.4.17.23;
CC         Evidence={ECO:0000256|ARBA:ARBA00001502};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:26555;
CC         Evidence={ECO:0000256|ARBA:ARBA00001502};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=apelin-13 + H2O = apelin-12 + L-phenylalanine;
CC         Xref=Rhea:RHEA:63564, ChEBI:CHEBI:15377, ChEBI:CHEBI:58095,
CC         ChEBI:CHEBI:147395, ChEBI:CHEBI:147396;
CC         Evidence={ECO:0000256|ARBA:ARBA00050676};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63565;
CC         Evidence={ECO:0000256|ARBA:ARBA00050676};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=apelin-17 + H2O = apelin-16 + L-phenylalanine;
CC         Xref=Rhea:RHEA:63608, ChEBI:CHEBI:15377, ChEBI:CHEBI:58095,
CC         ChEBI:CHEBI:147421, ChEBI:CHEBI:147422;
CC         Evidence={ECO:0000256|ARBA:ARBA00050287};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63609;
CC         Evidence={ECO:0000256|ARBA:ARBA00050287};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=bradykinin(1-8) + H2O = bradykinin(1-7) + L-phenylalanine;
CC         Xref=Rhea:RHEA:63536, ChEBI:CHEBI:15377, ChEBI:CHEBI:58095,
CC         ChEBI:CHEBI:133069, ChEBI:CHEBI:147352;
CC         Evidence={ECO:0000256|ARBA:ARBA00052474};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63537;
CC         Evidence={ECO:0000256|ARBA:ARBA00052474};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=dynorphin A-(1-13) + H2O = dynorphin A-(1-12) + L-lysine;
CC         Xref=Rhea:RHEA:63556, ChEBI:CHEBI:15377, ChEBI:CHEBI:32551,
CC         ChEBI:CHEBI:147381, ChEBI:CHEBI:147383;
CC         Evidence={ECO:0000256|ARBA:ARBA00050289};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63557;
CC         Evidence={ECO:0000256|ARBA:ARBA00050289};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=kinetensin + H2O = kinetensin-(1-8) + L-leucine;
CC         Xref=Rhea:RHEA:63544, ChEBI:CHEBI:15377, ChEBI:CHEBI:57427,
CC         ChEBI:CHEBI:147364, ChEBI:CHEBI:147365;
CC         Evidence={ECO:0000256|ARBA:ARBA00050348};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63545;
CC         Evidence={ECO:0000256|ARBA:ARBA00050348};
CC   -!- CATALYTIC ACTIVITY:
CC       Reaction=neurotensin + H2O = neurotensin-(1-12) + L-leucine;
CC         Xref=Rhea:RHEA:63540, ChEBI:CHEBI:15377, ChEBI:CHEBI:57427,
CC         ChEBI:CHEBI:147362, ChEBI:CHEBI:147363;
CC         Evidence={ECO:0000256|ARBA:ARBA00052322};
CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:63541;
CC         Evidence={ECO:0000256|ARBA:ARBA00052322};
CC   -!- COFACTOR:
CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;
CC         Evidence={ECO:0000256|RuleBase:RU361144};
CC       Note=Binds 1 zinc ion per subunit. {ECO:0000256|RuleBase:RU361144};
CC   -!- COFACTOR:
CC       Name=chloride; Xref=ChEBI:CHEBI:17996;
CC         Evidence={ECO:0000256|ARBA:ARBA00001923};
CC   -!- SUBUNIT: Homodimer. Interacts with the catalytically active form of
CC       TMPRSS2. Interacts with SLC6A19; this interaction is essential for
CC       expression and function of SLC6A19 in intestine. Interacts with
CC       ITGA5:ITGB1. Probably interacts (via endocytic sorting signal motif)
CC       with AP2M1; the interaction is inhibited by phosphorylation of Tyr-781.
CC       Interacts (via PDZ-binding motif) with NHERF1 (via PDZ domains); the
CC       interaction may enhance ACE2 membrane residence.
CC       {ECO:0000256|ARBA:ARBA00063223}.
CC   -!- SUBCELLULAR LOCATION: Apical cell membrane
CC       {ECO:0000256|ARBA:ARBA00004221}. Cell membrane
CC       {ECO:0000256|ARBA:ARBA00004251, ECO:0000256|PROSITE-ProRule:PRU01354};
CC       Single-pass type I membrane protein {ECO:0000256|ARBA:ARBA00004251,
CC       ECO:0000256|PROSITE-ProRule:PRU01354}. Cell projection, cilium
CC       {ECO:0000256|ARBA:ARBA00004138}. Cytoplasm
CC       {ECO:0000256|ARBA:ARBA00004496}. Secreted
CC       {ECO:0000256|ARBA:ARBA00004613}.
CC   -!- SIMILARITY: Belongs to the peptidase M2 family.
CC       {ECO:0000256|ARBA:ARBA00008139, ECO:0000256|PROSITE-ProRule:PRU01355,
CC       ECO:0000256|RuleBase:RU361144}.
CC   -!- CAUTION: Lacks conserved residue(s) required for the propagation of
CC       feature annotation. {ECO:0000256|PROSITE-ProRule:PRU01355}.
KW   Carboxypeptidase {ECO:0000256|ARBA:ARBA00022645,
KW   ECO:0000256|RuleBase:RU361144};
KW   Cell membrane {ECO:0000256|ARBA:ARBA00022475, ECO:0000256|PROSITE-
KW   ProRule:PRU01354}; Cell projection {ECO:0000256|ARBA:ARBA00023273};
KW   Chloride {ECO:0000256|ARBA:ARBA00023214};
KW   Cytoplasm {ECO:0000256|ARBA:ARBA00022490};
KW   Disulfide bond {ECO:0000256|ARBA:ARBA00023157,
KW   ECO:0000256|PIRSR:PIRSR601548-4};
KW   Glycoprotein {ECO:0000256|ARBA:ARBA00023180, ECO:0000256|PIRSR:PIRSR601548-
KW   10};
KW   Hydrolase {ECO:0000256|ARBA:ARBA00022801, ECO:0000256|RuleBase:RU361144};
KW   Membrane {ECO:0000256|ARBA:ARBA00023136, ECO:0000256|PROSITE-
KW   ProRule:PRU01354};
KW   Metal-binding {ECO:0000256|ARBA:ARBA00022723,
KW   ECO:0000256|RuleBase:RU361144};
KW   Metalloprotease {ECO:0000256|ARBA:ARBA00023049,
KW   ECO:0000256|RuleBase:RU361144};
KW   Phosphoprotein {ECO:0000256|ARBA:ARBA00022553};
KW   Protease {ECO:0000256|ARBA:ARBA00022670, ECO:0000256|RuleBase:RU361144};
KW   Secreted {ECO:0000256|ARBA:ARBA00022525};
KW   Signal {ECO:0000256|ARBA:ARBA00022729, ECO:0000256|SAM:SignalP};
KW   Transmembrane {ECO:0000256|ARBA:ARBA00022692, ECO:0000256|PROSITE-
KW   ProRule:PRU01354};
KW   Transmembrane helix {ECO:0000256|ARBA:ARBA00022989, ECO:0000256|PROSITE-
KW   ProRule:PRU01354};
KW   Zinc {ECO:0000256|ARBA:ARBA00022833, ECO:0000256|RuleBase:RU361144}.
FT   CHAIN           18..805
FT                   /note="Angiotensin-converting enzyme"
FT                   /evidence="ECO:0000256|SAM:SignalP"
FT                   /id="PRO_5035841938"
FT   TRANSMEM        741..765
FT                   /note="Helical"
FT                   /evidence="ECO:0000256|SAM:Phobius"
FT   DOMAIN          614..805
FT                   /note="Collectrin-like"
FT                   /evidence="ECO:0000259|PROSITE:PS52010"
FT   REGION          772..805
FT                   /note="Disordered"
FT                   /evidence="ECO:0000256|SAM:MobiDB-lite"
FT   COMPBIAS        789..805
FT                   /note="Polar residues"
FT                   /evidence="ECO:0000256|SAM:MobiDB-lite"
//                        
                        """;
		UniProtKBEntry entry =parser.parse(aaEntryLines);
		assertNotNull(entry);
		assertEquals("UPI000000000B-1032", entry.getPrimaryAccession().getValue());
		
		ProteinDescription pd = entry.getProteinDescription();
		assertEquals("Angiotensin-converting enzyme", pd.getRecommendedName()
				.getFullName().getValue());
		assertFalse(pd.hasAlternativeNames());
		assertEquals(15, entry.getComments().size());
		List<CatalyticActivityComment> caComments =
				entry.getCommentsByType(CommentType.CATALYTIC_ACTIVITY);
		
		assertEquals(9, caComments.size());
		List<FreeTextComment> coComments =
				entry.getCommentsByType(CommentType.CAUTION);
		assertEquals(1, coComments.size());
		FreeTextComment ctComment = coComments.get(0);
		assertEquals(1, ctComment.getTexts().size());
		EvidencedValue ev = ctComment.getTexts().get(0);
		
		String expectedEv ="Lacks conserved residue(s) required for the propagation of feature annotation {ECO:0000256|PROSITE-ProRule:PRU01355}";
		assertEquals(expectedEv, ev.toString());
		assertEquals(5, entry.getFeatures().size());
		 List<UniProtKBFeature> domainFts =entry.getFeaturesByType(UniprotKBFeatureType.DOMAIN);
		assertEquals(1, domainFts.size());
		
		assertEquals(18, entry.getKeywords().size());
	     
	}

}

