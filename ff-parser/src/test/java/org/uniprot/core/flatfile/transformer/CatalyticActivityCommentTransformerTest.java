package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import com.google.common.base.Strings;

class CatalyticActivityCommentTransformerTest {
    private static CatalyticActivityCommentTransformer transformer;

    @BeforeAll
    static void setup() {
        transformer = new CatalyticActivityCommentTransformer();
    }

    @Test
    void withTwoPhysiologicalDirection() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " EC=1.1.1.271;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00956, "
                        + "ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971, "
                        + "ECO:0000269|PubMed:9473059};\n"
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n"
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00952};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                "1.1.1.271",
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878,"
                        + " ECO:0000269|PubMed:11021971, ECO:0000269|PubMed:9473059");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(
                pds.get(0), "right-to-left", "Rhea:RHEA:18898", "ECO:0000255|HAMAP-Rule:MF_00957");
        verifyPhysiologicalDirection(
                pds.get(1), "left-to-right", "Rhea:RHEA:18832", "ECO:0000255|HAMAP-Rule:MF_00952");
    }

    @Test
    void withTwoPhysiologicalDirectionWithSpaceSeparator() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " EC=1.1.1.271;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00956, "
                        + "ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971, "
                        + "ECO:0000269|PubMed:9473059}; "
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00957}; "
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00952};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                "1.1.1.271",
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878,"
                        + " ECO:0000269|PubMed:11021971, ECO:0000269|PubMed:9473059");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(
                pds.get(0), "right-to-left", "Rhea:RHEA:18898", "ECO:0000255|HAMAP-Rule:MF_00957");
        verifyPhysiologicalDirection(
                pds.get(1), "left-to-right", "Rhea:RHEA:18832", "ECO:0000255|HAMAP-Rule:MF_00952");
    }

    @Test
    void withTwoPhysiologicalDirectionNoEC() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00956, "
                        + "ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971, "
                        + "ECO:0000269|PubMed:9473059};\n"
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n"
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00952};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                null,
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878,"
                        + " ECO:0000269|PubMed:11021971, ECO:0000269|PubMed:9473059");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(
                pds.get(0), "right-to-left", "Rhea:RHEA:18898", "ECO:0000255|HAMAP-Rule:MF_00957");
        verifyPhysiologicalDirection(
                pds.get(1), "left-to-right", "Rhea:RHEA:18832", "ECO:0000255|HAMAP-Rule:MF_00952");
    }

    @Test
    void withTwoPhysiologicalDirectionNoEvidence() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " EC=1.1.1.271;\n"
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n"
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; ";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                "1.1.1.271",
                "");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(
                pds.get(0), "right-to-left", "Rhea:RHEA:18898", "ECO:0000255|HAMAP-Rule:MF_00957");
        verifyPhysiologicalDirection(pds.get(1), "left-to-right", "Rhea:RHEA:18832", "");
    }

    @Test
    void withTwoPhysiologicalDirectionNoHeaderNoEvidence() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " EC=1.1.1.271; "
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832;";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                "1.1.1.271",
                "");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(pds.get(0), "right-to-left", "Rhea:RHEA:18898", "");
        verifyPhysiologicalDirection(pds.get(1), "left-to-right", "Rhea:RHEA:18832", "");
    }

    @Test
    void withTwoPhysiologicalDirectionNoHheader() {
        String ccLine =
                "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
                        + "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
                        + "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
                        + " EC=1.1.1.271;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00956, "
                        + "ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971, "
                        + "ECO:0000269|PubMed:9473059};\n"
                        + "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n"
                        + "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; "
                        + "Evidence={ECO:0000255|HAMAP-Rule:MF_00952};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964,"
                        + " ChEBI:CHEBI:57783",
                "1.1.1.271",
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878,"
                        + " ECO:0000269|PubMed:11021971, ECO:0000269|PubMed:9473059");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(2, pds.size());
        verifyPhysiologicalDirection(
                pds.get(0), "right-to-left", "Rhea:RHEA:18898", "ECO:0000255|HAMAP-Rule:MF_00957");
        verifyPhysiologicalDirection(
                pds.get(1), "left-to-right", "Rhea:RHEA:18832", "ECO:0000255|HAMAP-Rule:MF_00952");
    }

    @Test
    void withNoPhysiologicalDirectionNoHheader() {
        String ccLine =
                "Reaction=cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-"
                        + "methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in"
                        + " tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine; Xref=Rhea:RHEA:42396,"
                        + " Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,"
                        + " ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,"
                        + " ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856;"
                        + " EC=2.1.1.205; Evidence={ECO:0000255|HAMAP-Rule:MF_03162};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-"
                        + "methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in"
                        + " tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine",
                "Rhea:RHEA:42396, Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,"
                        + " ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,"
                        + " ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856",
                "2.1.1.205",
                "ECO:0000255|HAMAP-Rule:MF_03162");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(0, pds.size());
    }

    @Test
    void caBasedOnEnzyme() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose +"
                        + " H(+) + NADPH; EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,"
                        + " ECO:0000269|PubMed:10480878};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "",
                "1.1.1.271",
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(0, pds.size());
    }

    @Test
    void caBasedOnEnzymen2() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose +"
                        + " H(+) + NADPH; EC=1.1.1.n271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,"
                        + " ECO:0000269|PubMed:10480878};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH",
                "",
                "1.1.1.n271",
                "ECO:0000255|HAMAP-Rule:MF_00956, ECO:0000269|PubMed:10480878");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(0, pds.size());
    }

    @Test
    void caBasedOnEnzymeWithSemicolon() {
        String ccLine =
                "CATALYTIC ACTIVITY:\n"
                        + "Reaction=Endonucleolytic cleavage of DNA to give random double-stranded"
                        + " fragments with terminal 5'-phosphates; ATP is simultaneously hydrolyzed;"
                        + " EC=1.1.1.271; Evidence={ECO:0000256|RuleBase:RU364115};";
        CatalyticActivityComment comment = transformer.transform(ccLine);
        assertNotNull(comment);
        verifyReaction(
                comment.getReaction(),
                "Endonucleolytic cleavage of DNA to give random double-stranded fragments with"
                        + " terminal 5'-phosphates; ATP is simultaneously hydrolyzed",
                "",
                "1.1.1.271",
                "ECO:0000256|RuleBase:RU364115");
        List<PhysiologicalReaction> pds = comment.getPhysiologicalReactions();
        assertEquals(0, pds.size());
    }

    private void verifyReaction(
            Reaction reaction, String name, String xref, String ec, String evidences) {
        assertEquals(name, reaction.getName());
        if (Strings.isNullOrEmpty(ec)) {
            assertNull(reaction.getEcNumber());
        } else assertEquals(ec, reaction.getEcNumber().getValue());
        String xrefRes =
                reaction.getReactionCrossReferences().stream()
                        .map(val -> xrefToString(val))
                        .collect(Collectors.joining(", "));
        assertEquals(xref, xrefRes);

        verifyEvidence(reaction.getEvidences(), evidences);
    }

    private void verifyPhysiologicalDirection(
            PhysiologicalReaction pd, String name, String xref, String evidences) {
        assertEquals(name, pd.getDirectionType().getDisplayName());
        assertEquals(xref, xrefToString(pd.getReactionCrossReference()));
    }

    private String xrefToString(CrossReference<ReactionDatabase> xref) {
        return xref.getDatabase().getName() + ":" + xref.getId();
    }

    private void verifyEvidence(List<Evidence> evIds, String evidences) {

        if (evIds.isEmpty()) {
            assertTrue(Strings.isNullOrEmpty(evidences));
        } else {
            String evStr =
                    evIds.stream().map(val -> val.getValue()).collect(Collectors.joining(", "));
            assertEquals(evidences, evStr);
        }
    }
}
