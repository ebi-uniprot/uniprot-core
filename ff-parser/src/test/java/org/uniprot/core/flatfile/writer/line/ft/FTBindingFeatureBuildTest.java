package org.uniprot.core.flatfile.writer.line.ft;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;

public class FTBindingFeatureBuildTest extends FTBuildTestAbstr {
	private Ligand createLigand(String name, String id, String label, String note) {
		return new LigandBuilder()
        		.name(name)
        		.id(id)
        		.label(label)
        		.note(note)
        		.build();
    }
    
    private LigandPart createLigandPart(String name, String id, String label, String note) {
        return new LigandPartBuilder()
        		.name(name)
        		.id(id)
        		.label(label)
        		.note(note)
        		.build();
      
    }
    
    @Test
    public void testBindWithLigand() {
        String ftLine = "FT   BINDING         152\n"
                + "FT                   /ligand=\"Zn(2+)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "FT                   /ligand_label=\"1\"\n"
                + "FT                   /ligand_note=\"structural\"\n"
                + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
       
        String ftLineStringEv = "BINDING 152\n"
                + "/ligand=\"Zn(2+)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "/ligand_label=\"1\"\n"
                + "/ligand_note=\"structural\"\n"
                + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");

        Ligand ligand =createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 152, 152, null, null, evs, ligand, null);
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
    }
    @Test
    public void testBindWithLigandComplete() {
        String ftLine = "FT   BINDING         152\n"
                + "FT                   /ligand=\"Zn(2+)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "FT                   /ligand_label=\"1\"\n"
                + "FT                   /ligand_note=\"structural\"\n"
                + "FT                   /note=\"axial binding residue\"\n"
                + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv = "BINDING 152\n"
                + "/ligand=\"Zn(2+)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "/ligand_label=\"1\"\n"
                + "/ligand_note=\"structural\"\n"
                + "/note=\"axial binding residue\"\n"
                + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");

        Ligand ligand =createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 152, 152, "axial binding residue", null, evs, ligand, null);
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
    }
    @Test
    public void testBindWithLigandNoNote() {
        String ftLine = "FT   BINDING         152\n"
                + "FT                   /ligand=\"Zn(2+)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "FT                   /ligand_label=\"1\"\n"
                + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv = "BINDING 152\n"
                + "/ligand=\"Zn(2+)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "/ligand_label=\"1\"\n"
                + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");

        Ligand ligand =createLigand("Zn(2+)", "ChEBI:CHEBI:29105", "1", null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 152, 152, null, null, evs, ligand, null);
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
        
    }

    @Test
    public void testBindWithLigandNoLabel() {
        String ftLine = "FT   BINDING         152\n"
                + "FT                   /ligand=\"Zn(2+)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "FT                   /ligand_note=\"structural\"\n"
                + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        String ftLineStringEv = "BINDING 152\n"
                + "/ligand=\"Zn(2+)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29105\"\n"
                + "/ligand_note=\"structural\"\n"
                + "/evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"";
        
        List<String> evs = List.of("ECO:0000255|PROSITE-ProRule:PRU01239");

        Ligand ligand =createLigand("Zn(2+)", "ChEBI:CHEBI:29105", null, "structural");
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 152, 152, null, null, evs, ligand, null);
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
        
    }
    
    @Test
    public void testBindWithLigandNoLabelNoNote() {
        String ftLine = "FT   BINDING         348..349\n"
                + "FT                   /ligand=\"tRNA(Thr)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv = "BINDING 348..349\n"
                + "/ligand=\"tRNA(Thr)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");

        Ligand ligand =createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 348, 349, null, null, evs, ligand, null);
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
        
    }
    @Test
    public void testBindWithLigandOnlyName() {
        String ftLine = "FT   BINDING         348..349\n"
                + "FT                   /ligand=\"Substrate\"\n"
                + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv = "BINDING 348..349\n"
                + "/ligand=\"Substrate\"\n"
                + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");

        Ligand ligand =createLigand("Substrate",null, null, null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 348, 349, null, null, evs, ligand, null);
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
        
    }
    
    @Test
    public void testBindWithLigandAndPart() {
        String ftLine = "FT   BINDING         692\n"
                + "FT                   /ligand=\"divinyl chlorophyll-a'\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:?????\"\n"
                + "FT                   /ligand_label=\"A1\"\n"
                + "FT                   /ligand_part=\"Mg\"\n"
                + "FT                   /ligand_part_id=\"ChEBI:CHEBI:?????\"\n"
                + "FT                   /note=\"axial ligand\"\n"
                + "FT                   /evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"";
        String ftLineStringEv = "BINDING 692\n"
                + "/ligand=\"divinyl chlorophyll-a'\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:?????\"\n"
                + "/ligand_label=\"A1\"\n"
                + "/ligand_part=\"Mg\"\n"
                + "/ligand_part_id=\"ChEBI:CHEBI:?????\"\n"
                + "/note=\"axial ligand\"\n"
                + "/evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"";
        
        List<String> evs = List.of("ECO:0000255|HAMAP-Rule:MF_00458");
        LigandPart ligandPart =createLigandPart("Mg","ChEBI:CHEBI:?????", null, null);
        
        Ligand ligand =createLigand("divinyl chlorophyll-a'", "ChEBI:CHEBI:?????", "A1", null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 692, 692, "axial ligand", null, evs, ligand, ligandPart);
        
        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
        
    }
    
    @Test
    public void testBindWithLigandAndPart2() {
        String ftLine = "FT   BINDING         200..219\n"
                + "FT                   /ligand=\"tRNA(Thr)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "FT                   /ligand_part=\"tRNA acceptor stem\"\n"
                + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv = "BINDING 200..219\n"
                + "/ligand=\"tRNA(Thr)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "/ligand_part=\"tRNA acceptor stem\"\n"
                + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        LigandPart ligandPart =createLigandPart("tRNA acceptor stem",null, null, null);
        
        Ligand ligand =createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 200, 219, null, null, evs, ligand, ligandPart);

        
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);    
        
    }
    @Test
    public void testBindWithLigandAndPart3() {
        String ftLine = "FT   BINDING         313..317\n"
                + "FT                   /ligand=\"tRNA(Thr)\"\n"
                + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "FT                   /ligand_part=\"tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-\n"
                + "FT                   adenosine residue\"\n"
                + "FT                   /ligand_part_id=\"ChEBI:CHEBI:83071\"\n"
                + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"";
        String ftLineStringEv = "BINDING 313..317\n"
                + "/ligand=\"tRNA(Thr)\"\n"
                + "/ligand_id=\"ChEBI:CHEBI:29180\"\n"
                + "/ligand_part=\"tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue\"\n"
                + "/ligand_part_id=\"ChEBI:CHEBI:83071\"\n"
                + "/evidence=\"ECO:0000269|PubMed:10319817\"";
        
        List<String> evs = List.of("ECO:0000269|PubMed:10319817");
        LigandPart ligandPart =createLigandPart("tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue","ChEBI:CHEBI:83071", null, null);
        
        
        Ligand ligand =createLigand("tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 313, 317, null, null, evs, ligand, ligandPart);
          
        doTest(ftLine, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
        
}
