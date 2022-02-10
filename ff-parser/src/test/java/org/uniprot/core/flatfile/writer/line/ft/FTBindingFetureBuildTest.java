package org.uniprot.core.flatfile.writer.line.ft;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;

/**
 * @author jluo
 * @date: 9 Feb 2022
 */
public class FTBindingFetureBuildTest extends FTBuildTestAbstr {

    private UniProtKBFeature createFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            String description,
            Ligand ligand,
            LigandPart ligandPart,
            List<String> evs) {
        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .ligand(ligand)
                .ligandPart(ligandPart)
                .evidencesSet(createEvidence(evs))
                .build();
    }

    private Ligand createLigand(String name, String id, String label, String note) {
        return new LigandBuilder().name(name).id(id).label(label).note(note).build();
    }

    private LigandPart createLigandPart(String name, String id, String label, String note) {
        return new LigandPartBuilder().name(name).id(id).label(label).note(note).build();
    }

    @Test
    public void testBindWithLigand() {
        String ftLine =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";

        String ftLineStringEv =
                "BINDING 152\n"
                        + "/ligand=\"Zn(2+)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "/ligand_label=\"1\"\n"
                        + "/ligand_note=\"structural\"\n"
                        + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";

        FeatureLocation location = new FeatureLocation(152, 152);
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");
        Ligand ligand = createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, null, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandComplete() {
        String ftLine =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /note=\"axial binding residue\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv =
                "BINDING 152\n"
                        + "/ligand=\"Zn(2+)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "/ligand_label=\"1\"\n"
                        + "/ligand_note=\"structural\"\n"
                        + "/note=\"axial binding residue\"\n"
                        + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        FeatureLocation location = new FeatureLocation(152, 152);
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");
        Ligand ligand = createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.BINDING,
                        location,
                        "axial binding residue",
                        ligand,
                        null,
                        evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandNoNote() {
        String ftLine =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv =
                "BINDING 152\n"
                        + "/ligand=\"Zn(2+)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "/ligand_label=\"1\"\n"
                        + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        FeatureLocation location = new FeatureLocation(152, 152);
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");
        Ligand ligand = createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", null);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, null, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandNoLabel() {
        String ftLine =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv =
                "BINDING 152\n"
                        + "/ligand=\"Zn(2+)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "/ligand_note=\"structural\"\n"
                        + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        FeatureLocation location = new FeatureLocation(152, 152);
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");
        Ligand ligand = createLigand("Zn(2+)", "ChEBI:CHEBI:29105", null, "structural");
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, null, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandNoLabelNoNote() {
        String ftLine =
                "FT   BINDING         348..349\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv =
                "BINDING 348..349\n"
                        + "/ligand=\"tRNA(Thr)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        FeatureLocation location = new FeatureLocation(348, 349);
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        Ligand ligand = createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, null, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandOnlyName() {
        String ftLine =
                "FT   BINDING         348..349\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv =
                "BINDING 348..349\n"
                        + "/ligand=\"tRNA(Thr)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        FeatureLocation location = new FeatureLocation(348, 349);
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        Ligand ligand = createLigand("tRNA(Thr)", null, null, null);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, null, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandAndPart() {
        String ftLine =
                "FT   BINDING         692\n"
                        + "FT                   /ligand=\"divinyl chlorophyll-a'\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:?????\"\n"
                        + "FT                   /ligand_label=\"A1\"\n"
                        + "FT                   /ligand_part=\"Mg\"\n"
                        + "FT                   /ligand_part_id=\"ChEBI:CHEBI:12321\"\n"
                        + "FT                   /note=\"axial ligand\"\n"
                        + "FT                   /evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"";
        String ftLineStringEv =
                "BINDING 692\n"
                        + "/ligand=\"divinyl chlorophyll-a'\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:?????\"\n"
                        + "/ligand_label=\"A1\"\n"
                        + "/ligand_part=\"Mg\"\n"
                        + "/ligand_part_id=\"ChEBI:CHEBI:12321\"\n"
                        + "/note=\"axial ligand\"\n"
                        + "/evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"";
        FeatureLocation location = new FeatureLocation(692, 692);
        List<String> evs = List.of("ECO:0000255|HAMAP-Rule:MF_00458");
        Ligand ligand = createLigand("divinyl chlorophyll-a'", "ChEBI:CHEBI:?????", "A1", null);
        LigandPart ligandPart = createLigandPart("Mg", "ChEBI:CHEBI:12321", null, null);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.BINDING,
                        location,
                        "axial ligand",
                        ligand,
                        ligandPart,
                        evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandAndPart2() {
        String ftLine =
                "FT   BINDING         200..219\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /ligand_part=\"tRNA acceptor stem\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv =
                "BINDING 200..219\n"
                        + "/ligand=\"tRNA(Thr)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "/ligand_part=\"tRNA acceptor stem\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        FeatureLocation location = new FeatureLocation(200, 219);
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        Ligand ligand = createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        LigandPart ligandPart = createLigandPart("tRNA acceptor stem", null, null, null);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, ligandPart, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    public void testBindWithLigandAndPart3() {
        String ftLine =
                "FT   BINDING         313..317\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /ligand_part=\"tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-\n"
                        + "FT                   adenosine residue\"\n"
                        + "FT                   /ligand_part_id=\"ChEBI:CHEBI:83071\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv =
                "BINDING 313..317\n"
                        + "/ligand=\"tRNA(Thr)\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "/ligand_part=\"tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue\"\n"
                        + "/ligand_part_id=\"ChEBI:CHEBI:83071\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        FeatureLocation location = new FeatureLocation(313, 317);
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        Ligand ligand = createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        LigandPart ligandPart =
                createLigandPart(
                        "tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue",
                        "ChEBI:CHEBI:83071",
                        null,
                        null);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, location, "", ligand, ligandPart, evs);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
}
