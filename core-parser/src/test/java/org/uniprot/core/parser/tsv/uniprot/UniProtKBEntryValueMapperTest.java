package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprotkb.EntryInactiveReason;
import org.uniprot.core.uniprotkb.InactiveReasonType;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.impl.EntryInactiveReasonBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;

class UniProtKBEntryValueMapperTest {
    private static UniProtKBEntry entryQ15758;
    private static UniProtKBEntry entryP03431;
    private static UniProtKBEntry entryQ84MC7;
    private static UniProtKBEntry entryQ70KY3;

    @BeforeAll
    static void setup() throws Exception {
        URL url = UniProtKBEntryValueMapperTest.class.getResource("/uniprot/keywlist.txt");
        UniProtParser parser =
                new DefaultUniProtParser(
                        new SupportingDataMapImpl(url.getPath(), "", "", ""), true);

        InputStream is =
                UniProtKBEntryValueMapperTest.class.getResourceAsStream("/uniprot/Q15758.dat");
        entryQ15758 = parser.parse(inputStreamToString(is));
        is.close();

        is = UniProtKBEntryValueMapperTest.class.getResourceAsStream("/uniprot/P03431.dat");
        entryP03431 = parser.parse(inputStreamToString(is));
        // UniParc is aggregated after convertion, so I am adding manually
        entryP03431 =
                UniProtKBEntryBuilder.from(entryP03431)
                        .extraAttributesAdd(UniProtKBEntryBuilder.UNIPARC_ID_ATTRIB, "UP1234567890")
                        .build();
        is.close();

        is = UniProtKBEntryValueMapperTest.class.getResourceAsStream("/uniprot/Q84MC7.dat");
        entryQ84MC7 = parser.parse(inputStreamToString(is));
        is.close();

        is = UniProtKBEntryValueMapperTest.class.getResourceAsStream("/uniprot/Q70KY3.dat");
        entryQ70KY3 = parser.parse(inputStreamToString(is));
        is.close();
    }

    private static String inputStreamToString(InputStream is) {
        String result = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            result = outputStream.toString(Charset.defaultCharset().name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    void testInactiveEntries() {
        List<String> fields = List.of("accession", "id", "protein_name");
        EntryInactiveReason inactiveReason =
                new EntryInactiveReasonBuilder().type(InactiveReasonType.DELETED).build();
        UniProtKBEntry inactiveEntry =
                new UniProtKBEntryBuilder("Q15758", "AAAT_HUMAN", inactiveReason).build();
        Map<String, String> result =
                new UniProtKBEntryValueMapper().mapEntity(inactiveEntry, fields);

        verify("Q15758", "accession", result);
        verify("AAAT_HUMAN", "id", result);
        verify("deleted", "protein_name", result);
    }

    @Test
    void testIdAccession() {
        List<String> fields = List.of("accession", "id");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        verify("AAAT_HUMAN", "id", result);
    }

    @Test
    void testInfo() {
        List<String> fields = List.of("reviewed", "version", "protein_existence");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("reviewed", "reviewed", result);
        verify("195", "version", result);
        verify("Evidence at protein level", "protein_existence", result);
    }

    @Test
    void testSequence() {
        List<String> fields = List.of("length", "mass", "sequence_version", "sequence");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("541", "length", result);
        verify("56598", "mass", result);
        verify("2", "sequence_version", result);
        String seq =
                "MVADPPRDSKGLAAAEPTANGGLALASIEDQGAAAGGYCGSRDQVRRCLRANLLVLLTVV"
                        + "AVVAGVALGLGVSGAGGALALGPERLSAFVFPGELLLRLLRMIILPLVVCSLIGGAASLD"
                        + "PGALGRLGAWALLFFLVTTLLASALGVGLALALQPGAASAAINASVGAAGSAENAPSKEV"
                        + "LDSFLDLARNIFPSNLVSAAFRSYSTTYEERNITGTRVKVPVGQEVEGMNILGLVVFAIV"
                        + "FGVALRKLGPEGELLIRFFNSFNEATMVLVSWIMWYAPVGIMFLVAGKIVEMEDVGLLFA"
                        + "RLGKYILCCLLGHAIHGLLVLPLIYFLFTRKNPYRFLWGIVTPLATAFGTSSSSATLPLM"
                        + "MKCVEENNGVAKHISRFILPIGATVNMDGAALFQCVAAVFIAQLSQQSLDFVKIITILVT"
                        + "ATASSVGAAGIPAGGVLTLAIILEAVNLPVDHISLILAVDWLVDRSCTVLNVEGDALGAG"
                        + "LLQNYVDRTESRSTEPELIQVKSELPLDPLPVPTEEGNPLLKHYRGPAGDATVASEKESV"
                        + "M";
        verify(seq, "sequence", result);
    }

    @Test
    void testDefault() {
        List<String> fields =
                List.of("accession", "id", "protein_name", "gene_names", "organism_name");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        verify("AAAT_HUMAN", "id", result);
        String proteinName =
                "Neutral amino acid transporter B(0) (ATB(0)) (Baboon M7 virus receptor) (RD114/simian type D retrovirus receptor) (Sodium-dependent neutral amino acid transporter type 2) (Solute carrier family 1 member 5)";
        verify(proteinName, "protein_name", result);
        verify("SLC1A5 ASCT2 M7V1 RDR RDRC", "gene_names", result);
        verify("Homo sapiens (Human)", "organism_name", result);
    }

    @Test
    void testECnumber() {
        List<String> fields = List.of("accession", "protein_name", "ec");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        String proteinName =
                "RNA-directed RNA polymerase catalytic subunit (EC 2.7.7.48) (Polymerase basic protein 1) (PB1) (RNA-directed RNA polymerase subunit P1)";
        verify("P03431", "accession", result);
        verify(proteinName, "protein_name", result);
        verify("2.7.7.48", "ec", result);
    }

    @Test
    void testGene() {
        List<String> fields =
                List.of("gene_names", "gene_primary", "gene_synonym", "gene_oln", "gene_orf");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("SLC1A5 ASCT2 M7V1 RDR RDRC", "gene_names", result);
        verify("SLC1A5", "gene_primary", result);
        verify("ASCT2 M7V1 RDR RDRC", "gene_synonym", result);
        verify("", "gene_oln", result);
        verify("", "gene_orf", result);
    }

    @Test
    void testOrganism() {
        List<String> fields = List.of("organism_name", "organism_id");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Homo sapiens (Human)", "organism_name", result);
        verify("9606", "organism_id", result);
    }

    @Test
    void testVirusHosts() {
        List<String> fields = List.of("accession", "organism_name", "virus_hosts", "lineage");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        verify("Influenza A virus (strain A/Puerto Rico/8/1934 H1N1)", "organism_name", result);
        verify(
                "Aves (birds) [TaxID: 8782]; Homo sapiens (Human) [TaxID: 9606]; Sus scrofa (Pig)"
                        + " [TaxID: 9823]",
                "virus_hosts",
                result);
    }

    @Test
    void testAlterProduct() {
        List<String> fields = List.of("accession", "cc_alternative_products");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);

        String altProd =
                "ALTERNATIVE PRODUCTS:  Event=Alternative splicing, Alternative initiation; Named"
                        + " isoforms=3; Comment=A number of isoforms are produced by alternative"
                        + " initiation. Isoforms start at multiple alternative CUG and GUG codons."
                        + " {ECO:0000269|PubMed:11350958}; Name=1; IsoId=Q15758-1; Sequence=Displayed;"
                        + " Name=2; IsoId=Q15758-2; Sequence=VSP_046354; Name=3; IsoId=Q15758-3;"
                        + " Sequence=VSP_046851;";
        verify(altProd, "cc_alternative_products", result);
    }

    @Test
    void testComments() {
        List<String> fields =
                List.of("accession", "cc_function", "cc_domain", "cc_subunit", "cc_interaction");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String cfunction =
                "FUNCTION: Sodium-dependent amino acids transporter that has a broad substrate"
                        + " specificity, with a preference for zwitterionic amino acids. It accepts as"
                        + " substrates all neutral amino acids, including glutamine, asparagine, and"
                        + " branched-chain and aromatic amino acids, and excludes methylated, anionic,"
                        + " and cationic amino acids (PubMed:8702519, PubMed:29872227). Through"
                        + " binding of the fusogenic protein syncytin-1/ERVW-1 may mediate"
                        + " trophoblasts syncytialization, the spontaneous fusion of their plasma"
                        + " membranes, an essential process in placental development (PubMed:10708449,"
                        + " PubMed:23492904). {ECO:0000269|PubMed:10708449,"
                        + " ECO:0000269|PubMed:23492904, ECO:0000269|PubMed:29872227,"
                        + " ECO:0000269|PubMed:8702519}.; FUNCTION: (Microbial infection) Acts as a"
                        + " cell surface receptor for Feline endogenous virus RD114."
                        + " {ECO:0000269|PubMed:10051606, ECO:0000269|PubMed:10196349}.; FUNCTION:"
                        + " (Microbial infection) Acts as a cell surface receptor for Baboon M7"
                        + " endogenous virus. {ECO:0000269|PubMed:10196349}.; FUNCTION: (Microbial"
                        + " infection) Acts as a cell surface receptor for type D simian retroviruses."
                        + " {ECO:0000269|PubMed:10196349}.";

        String csubunit =
                "SUBUNIT: Homotrimer (Probable) (PubMed:29872227). Interacts with"
                        + " ERVH48-1/suppressyn; may negatively regulate syncytialization"
                        + " (PubMed:23492904). {ECO:0000269|PubMed:23492904,"
                        + " ECO:0000269|PubMed:29872227, ECO:0000305|PubMed:28424515}.";

        String cinteraction = "Q99942";
        verify(cfunction, "cc_function", result);
        verify(null, "cc_domain", result);
        verify(csubunit, "cc_subunit", result);
        verify(cinteraction, "cc_interaction", result);
    }

    @Test
    void testComments2() {
        List<String> fields =
                List.of(
                        "accession",
                        "cc_interaction",
                        "cc_subcellular_location",
                        "cc_ptm",
                        "cc_similarity");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        String interaction = "Q14318; P03466; P03433; P03428; Q99959";
        String subcell =
                "SUBCELLULAR LOCATION: Host nucleus {ECO:0000255|HAMAP-Rule:MF_04065,"
                        + " ECO:0000269|PubMed:19906916}. Host cytoplasm"
                        + " {ECO:0000255|HAMAP-Rule:MF_04065, ECO:0000269|PubMed:19906916}.";
        String ptm =
                "PTM: Phosphorylated by host PRKCA. {ECO:0000255|HAMAP-Rule:MF_04065,"
                        + " ECO:0000269|PubMed:19264651}.";
        String similarity =
                "SIMILARITY: Belongs to the influenza viruses polymerase PB1 family."
                        + " {ECO:0000255|HAMAP-Rule:MF_04065}.";
        verify(interaction, "cc_interaction", result);
        verify(subcell, "cc_subcellular_location", result);
        verify(ptm, "cc_ptm", result);
        verify(similarity, "cc_similarity", result);
    }

    @Test
    void testProteinFamily() {
        List<String> fields = List.of("accession", "protein_families", "cc_similarity");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        String proteinFamily = "Influenza viruses polymerase PB1 family";
        String similarity =
                "SIMILARITY: Belongs to the influenza viruses polymerase PB1 family."
                        + " {ECO:0000255|HAMAP-Rule:MF_04065}.";
        verify(proteinFamily, "protein_families", result);
        verify(similarity, "cc_similarity", result);
    }

    @Test
    void testSequenceCaution() {
        List<String> fields = List.of("accession", "cc_sequence_caution", "cc_sc_epred");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ84MC7, fields);

        verify("Q84MC7", "accession", result);
        String seqCaution =
                "SEQUENCE CAUTION:  Sequence=AAF97339.1; Type=Erroneous initiation; Note=Truncated"
                        + " N-terminus.; Evidence={ECO:0000305};  Sequence=AAM65514.1; Type=Erroneous"
                        + " initiation; Note=Truncated N-terminus.; Evidence={ECO:0000305};";
        verify(seqCaution, "cc_sequence_caution", result);
    }

    @Test
    void testBPCP() {
        List<String> fields =
                List.of(
                        "accession",
                        "absorption",
                        "kinetics",
                        "ph_dependence",
                        "redox_potential",
                        "temp_dependence");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ70KY3, fields);

        verify("Q70KY3", "accession", result);
        String absorption =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Absorption: Abs(max)=280 nm"
                        + " {ECO:0000269|PubMed:12111146, ECO:0000269|PubMed:12118243}; Note=Exhibits"
                        + " a shoulder at 360 nm, a smaller absorption peak at 450 nm, and a second,"
                        + " larger peak at 590 nm. {ECO:0000269|PubMed:12118243};";
        //        String kinetic =
        //                "BIOPHYSICOCHEMICAL PROPERTIES:  Kinetic parameters: KM=5.61 mM for
        // ethanol {ECO:0000269|PubMed:10320337,"
        //                        + " ECO:0000269|PubMed:16061256, ECO:0000269|PubMed:7730276};
        // KM=0.105 mM for butane-1-ol {ECO:0000269|PubMed:10320337,"
        //                        + " ECO:0000269|PubMed:16061256, ECO:0000269|PubMed:7730276};
        // Vmax=45.5 umol/min/mg enzyme toward potassium"
        //                        + " ferricyanide (in the presence of 30 mM Tris-HCl pH 8.0)
        // {ECO:0000269|PubMed:10320337, ECO:0000269|PubMed:16061256,"
        //                        + " ECO:0000269|PubMed:7730276};";
        String phDep =
                "BIOPHYSICOCHEMICAL PROPERTIES:  pH dependence: Optimum pH is 3.5 with"
                        + " 2,2'-azinobis-(3-ethylbenzthiazoline-6-sulphonate) as substrate, 5.0-7.5"
                        + " with guiacol as substrate, and 6.0-7.0 with syringaldazine as substrate."
                        + " {ECO:0000269|PubMed:12111146, ECO:0000269|PubMed:12118243};";
        //        String redox =
        //                "BIOPHYSICOCHEMICAL PROPERTIES:  Redox potential: E(0) is +185 mV for heme
        // c at pH 7.0, +188 mV "
        //                        + "for heme c at pH 8.0, +172 mV for heme c at pH 8.0 and 0.3 M
        // KCl and +189 mV for ADH IIB-Azurin complex."
        //                        + " {ECO:0000269|PubMed:10320337, ECO:0000269|PubMed:16061256,
        // ECO:0000269|PubMed:7730276};";
        String tempDep =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Temperature dependence: Optimum temperature is"
                        + " 60-70 degrees Celsius. {ECO:0000269|PubMed:12111146,"
                        + " ECO:0000269|PubMed:12118243};";
        verify(absorption, "absorption", result);
        verify(null, "kinetics", result);
        verify(phDep, "ph_dependence", result);
        verify(null, "redox_potential", result);
        verify(tempDep, "temp_dependence", result);
    }

    @Test
    void testFeatures() {
        List<String> fields =
                List.of(
                        "accession",
                        "ft_chain",
                        "ft_topo_dom",
                        "ft_transmem",
                        "ft_mod_res",
                        "ft_carbohyd",
                        "ft_var_seq",
                        "ft_variant",
                        "ft_conflict",
                        "ft_domain");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);

        String chain =
                "CHAIN 1..541; /note=\"Neutral amino acid transporter B(0)\"; /id=\"PRO_0000202082\"";
        String topoDom =
                "TOPO_DOM 1..51; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 82..94; /note=\"Extracellular\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 117..130; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 154..224; /note=\"Extracellular\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 249..257; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 286..306; /note=\"Extracellular\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 329..333; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 365..373; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 401..413; /note=\"Extracellular\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 448..460; /note=\"Extracellular\"; /evidence=\"ECO:0000305\";"
                        + " TOPO_DOM 483..541; /note=\"Cytoplasmic\"; /evidence=\"ECO:0000305\"";
        String transmem =
                "TRANSMEM 52..81; /note=\"Helical; Name=1\""
                        + "; /evidence=\"ECO:0000305|PubMed:29872227\"; TRANSMEM 95..116;"
                        + " /note=\"Helical; Name=2\"; /evidence=\"ECO:0000305|PubMed:29872227\";"
                        + " TRANSMEM 131..153; /note=\"Helical; Name=3\""
                        + "; /evidence=\"ECO:0000305|PubMed:29872227\"; TRANSMEM 225..248;"
                        + " /note=\"Helical; Name=4\"; /evidence=\"ECO:0000305|PubMed:29872227\";"
                        + " TRANSMEM 258..285; /note=\"Helical; Name=5\""
                        + "; /evidence=\"ECO:0000305|PubMed:29872227\"; TRANSMEM 307..328;"
                        + " /note=\"Helical; Name=6\"; /evidence=\"ECO:0000305|PubMed:29872227\";"
                        + " TRANSMEM 374..400; /note=\"Helical; Name=7\""
                        + "; /evidence=\"ECO:0000305|PubMed:29872227\"; TRANSMEM 461..482;"
                        + " /note=\"Helical; Name=8\"; /evidence=\"ECO:0000305|PubMed:29872227\"";
        String modRes =
                "MOD_RES 1; /note=\"N-acetylmethionine\"; /evidence=\"ECO:0000244|PubMed:19413330,"
                        + " ECO:0000244|PubMed:22814378\"; MOD_RES 493; /note=\"Phosphoserine\""
                        + "; /evidence=\"ECO:0000244|PubMed:21406692, ECO:0000244|PubMed:23186163\";"
                        + " MOD_RES 494; /note=\"Phosphothreonine\""
                        + "; /evidence=\"ECO:0000244|PubMed:23186163\"; MOD_RES 503;"
                        + " /note=\"Phosphoserine\"; /evidence=\"ECO:0000244|PubMed:19690332\"; MOD_RES"
                        + " 535; /note=\"Phosphoserine\"; /evidence=\"ECO:0000244|PubMed:17081983,"
                        + " ECO:0000244|PubMed:18669648, ECO:0000244|PubMed:19690332\"; MOD_RES 539;"
                        + " /note=\"Phosphoserine\"; /evidence=\"ECO:0000244|PubMed:23186163\"";
        String carbohyd =
                "CARBOHYD 163; /note=\"N-linked (GlcNAc...) asparagine\"; /evidence=\"ECO:0000255\";"
                        + " CARBOHYD 212; /note=\"N-linked (GlcNAc...) asparagine\""
                        + "; /evidence=\"ECO:0000269|PubMed:19349973\"";
        String varSeq =
                "VAR_SEQ 1..228; /note=\"Missing (in isoform 3)\""
                        + "; /evidence=\"ECO:0000303|PubMed:14702039\"; /id=\"VSP_046851\"; VAR_SEQ"
                        + " 1..203;"
                        + " /note=\"MVADPPRDSKGLAAAEPTANGGLALASIEDQGAAAGGYCGSRDQVRRCLRANLLVLLTVVAVVAGVALGLG"
                        + "VSGAGGALALGPERLSAFVFPGELLLRLLRMIILPLVVCSLIGGAASLDPGALGRLGAWALLFFLVTTLLASALGVGLALALQPGAASAAINASVGAAGSAENAPSKEVLDSFLDLARNIFPSNLVSAAFRS"
                        + " -> M (in isoform 2)\"; /evidence=\"ECO:0000303|PubMed:14702039\""
                        + "; /id=\"VSP_046354\"";
        String variant =
                "VARIANT 17; /note=\"P -> A (in dbSNP:rs3027956)\"; /id=\"VAR_020439\"; VARIANT 512"
                        + "; /note=\"V -> L (in dbSNP:rs3027961)\""
                        + "; /evidence=\"ECO:0000244|PubMed:19690332, ECO:0000269|PubMed:14702039\""
                        + "; /id=\"VAR_013517\"";
        String conflict =
                "CONFLICT 18..24; /note=\"TANGGLA -> PPTGAWQ (in Ref. 1; AAC50629)\""
                        + "; /evidence=\"ECO:0000305\"; CONFLICT 44; /note=\"Q -> L (in Ref. 1;"
                        + " AAC50629)\"; /evidence=\"ECO:0000305\"; CONFLICT 84..87; /note=\"ERLS ->"
                        + " GALE (in Ref. 1; AAC50629)\"; /evidence=\"ECO:0000305\"; CONFLICT 341;"
                        + " /note=\"V -> A (in Ref. 5; BAH14917)\"; /evidence=\"ECO:0000305\"; CONFLICT"
                        + " 453; /note=\"I -> V (in Ref. 2; AAD09812)\"; /evidence=\"ECO:0000305\";"
                        + " CONFLICT 460; /note=\"D -> G (in Ref. 2; AAD09812)\""
                        + "; /evidence=\"ECO:0000305\"; CONFLICT 463; /note=\"V -> A (in Ref. 2;"
                        + " AAD09812)\"; /evidence=\"ECO:0000305\"; CONFLICT 508; /note=\"D -> G (in"
                        + " Ref. 2; AAD09812)\"; /evidence=\"ECO:0000305\"";

        verify(chain, "ft_chain", result);
        verify(topoDom, "ft_topo_dom", result);
        verify(transmem, "ft_transmem", result);
        verify(modRes, "ft_mod_res", result);
        verify(carbohyd, "ft_carbohyd", result);
        verify(varSeq, "ft_var_seq", result);
        verify(variant, "ft_variant", result);
        verify(conflict, "ft_conflict", result);
        verify(null, "ft_domain", result);
    }

    @Test
    void testNumberOfFeatures() {
        List<String> fields = List.of("accession", "feature");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String numOfFeature =
                "Alternative sequence (2); Beta strand (2); Chain (1); Glycosylation (2);"
                        + " Helix (11); Intramembrane (2); Metal binding (5);"
                        + " Modified residue (6); Natural variant (2); Sequence conflict (8);"
                        + " Topological domain (11); Transmembrane (8)";
        verify(numOfFeature, "feature", result);
    }

    @Test
    void testReferences() {
        List<String> fields = List.of("accession", "lit_pubmed_id");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String pmids =
                "8702519; 10051606; 10196349; 14702039; 15057824; 15489334; 11350958; 10708449;"
                        + " 17081983; 17081065; 18669648; 19413330; 19349973; 19690332; 20068231;"
                        + " 21269460; 21406692; 22814378; 23186163; 25944712; 28424515; 29872227";
        verify(pmids, "lit_pubmed_id", result);
    }

    @Test
    void testGOTerm() {
        List<String> fields = List.of("accession", "go", "go_c", "go_f", "go_p", "go_id");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String go =
                "extracellular exosome [GO:0070062];"
                        + " integral component of membrane [GO:0016021];"
                        + " integral component of plasma membrane [GO:0005887];"
                        + " melanosome [GO:0042470];"
                        + " membrane [GO:0016020];"
                        + " plasma membrane [GO:0005886];"
                        + " amino acid transmembrane transporter activity [GO:0015171];"
                        + " L-glutamine transmembrane transporter activity [GO:0015186];"
                        + " L-serine transmembrane transporter activity [GO:0015194];"
                        + " metal ion binding [GO:0046872];"
                        + " neutral amino acid transmembrane transporter activity [GO:0015175];"
                        + " signaling receptor activity [GO:0038023];"
                        + " symporter activity [GO:0015293];"
                        + " virus receptor activity [GO:0001618];"
                        + " amino acid transport [GO:0006865];"
                        + " glutamine secretion [GO:0010585];"
                        + " glutamine transport [GO:0006868];"
                        + " L-glutamine import across plasma membrane [GO:1903803];"
                        + " neutral amino acid transport [GO:0015804];"
                        + " protein homotrimerization [GO:0070207]";

        String goC =
                "extracellular exosome [GO:0070062];"
                        + " integral component of membrane [GO:0016021];"
                        + " integral component of plasma membrane [GO:0005887];"
                        + " melanosome [GO:0042470];"
                        + " membrane [GO:0016020];"
                        + " plasma membrane [GO:0005886]";

        String goF =
                "amino acid transmembrane transporter activity [GO:0015171];"
                        + " L-glutamine transmembrane transporter activity [GO:0015186];"
                        + " L-serine transmembrane transporter activity [GO:0015194];"
                        + " metal ion binding [GO:0046872];"
                        + " neutral amino acid transmembrane transporter activity [GO:0015175];"
                        + " signaling receptor activity [GO:0038023];"
                        + " symporter activity [GO:0015293];"
                        + " virus receptor activity [GO:0001618]";

        String goP =
                "amino acid transport [GO:0006865];"
                        + " glutamine secretion [GO:0010585];"
                        + " glutamine transport [GO:0006868];"
                        + " L-glutamine import across plasma membrane [GO:1903803];"
                        + " neutral amino acid transport [GO:0015804];"
                        + " protein homotrimerization [GO:0070207]";

        String goId =
                "GO:0001618; GO:0005886; GO:0005887; GO:0006865; GO:0006868; GO:0010585;"
                        + " GO:0015171; GO:0015175; GO:0015186; GO:0015194; GO:0015293; GO:0015804;"
                        + " GO:0016020; GO:0016021; GO:0038023; GO:0042470; GO:0046872; GO:0070062;"
                        + " GO:0070207; GO:1903803";
        verify(go, "go", result);
        verify(goC, "go_c", result);
        verify(goF, "go_f", result);
        verify(goP, "go_p", result);
        verify(goId, "go_id", result);
    }

    @Test
    void testXRefs1() {
        List<String> fields =
                List.of("accession", "xref_embl", "xref_ccds", "xref_refseq", "xref_smr");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String embl =
                "U53347;AF102826;AF105423;GQ919058;"
                        + "AK292690;AK299137;AK301661;AK316546;"
                        + "AC008622;CH471126;BC000062;AF334818;";
        String ccds = "CCDS12692.1 [Q15758-1];CCDS46125.1 [Q15758-2];CCDS46126.1 [Q15758-3];";
        String refseq =
                "NP_001138616.1 [Q15758-3];NP_001138617.1 [Q15758-2];NP_005619.1 [Q15758-1];";
        String smr = "Q15758;";
        verify(embl, "xref_embl", result);
        verify(ccds, "xref_ccds", result);
        verify(refseq, "xref_refseq", result);
        verify(smr, "xref_smr", result);
    }

    @Test
    void testXRefs1Full() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_embl_full",
                        "xref_ccds_full",
                        "xref_refseq_full",
                        "xref_smr_full");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);

        String emblFull =
                "\"U53347; AAC50629.1; -; mRNA.\";"
                        + "\"AF102826; AAD09812.1; -; mRNA.\";"
                        + "\"AF105423; AAD27806.1; -; mRNA.\";"
                        + "\"GQ919058; ACX53626.1; -; mRNA.\";"
                        + "\"AK292690; BAF85379.1; -; mRNA.\";"
                        + "\"AK299137; BAG61189.1; -; mRNA.\";"
                        + "\"AK301661; BAG63136.1; -; mRNA.\";"
                        + "\"AK316546; BAH14917.1; -; mRNA.\";"
                        + "\"AC008622; -; NOT_ANNOTATED_CDS; Genomic_DNA.\";"
                        + "\"CH471126; EAW57446.1; -; Genomic_DNA.\";"
                        + "\"BC000062; AAH00062.1; -; mRNA.\";"
                        + "\"AF334818; AAK77026.1; -; mRNA.\";";
        verify(emblFull, "xref_embl_full", result);
        assertNull(result.get("xref_ccds_full"));

        String refseqFull =
                "\"NP_001138616.1; NM_001145144.1. [Q15758-3]\";"
                        + "\"NP_001138617.1; NM_001145145.1. [Q15758-2]\";"
                        + "\"NP_005619.1; NM_005628.2. [Q15758-1]\";";
        verify(refseqFull, "xref_refseq_full", result);

        assertNull(result.get("xref_smr_full"));
    }

    @Test
    void testXRefs2() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_smr",
                        "xref_biogrid",
                        "xref_intact",
                        "xref_mint",
                        "xref_string");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String smr = "Q15758;";
        String biogrid = "112401;";
        String intact = "Q15758;";
        String mint = "Q15758;";
        String string = "9606.ENSP00000444408;";
        verify(smr, "xref_smr", result);
        verify(biogrid, "xref_biogrid", result);
        verify(intact, "xref_intact", result);
        verify(mint, "xref_mint", result);
        verify(string, "xref_string", result);
    }

    @Test
    void testXRefs2FullXrefs() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_smr_full",
                        "xref_biogrid_full",
                        "xref_intact_full",
                        "xref_mint_full",
                        "xref_string_full");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);

        String biogrid = "\"112401; 92.\";";
        String intact = "\"Q15758; 73.\";";
        assertNull(result.get("xref_smr_full"));
        verify(biogrid, "xref_biogrid_full", result);
        verify(intact, "xref_intact_full", result);
        assertNull(result.get("xref_mint_full"));
        assertNull(result.get("xref_string_full"));
    }

    @Test
    void testXRefs3() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_drugbank",
                        "xref_guidetopharmacology",
                        "xref_tcdb",
                        "xref_dmdm");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String drugbank = "DB00174;DB13146;DB00130;";
        String guidetopharmacology = "874;";
        String tcdb = "2.A.23.3.3;";
        String dmdm = "21542389;";
        verify(drugbank, "xref_drugbank", result);
        verify(guidetopharmacology, "xref_guidetopharmacology", result);
        verify(tcdb, "xref_tcdb", result);
        verify(dmdm, "xref_dmdm", result);
    }

    @Test
    void testXRefs3Full() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_drugbank_full",
                        "xref_guidetopharmacology_full",
                        "xref_tcdb_full",
                        "xref_dmdm_full");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String drugbank =
                "\"DB00174; Asparagine.\";\"DB13146; Fluciclovine (18F).\";\"DB00130; L-Glutamine.\";";
        String tcdb =
                "\"2.A.23.3.3; the dicarboxylate/amino acid:cation (na(+) or h(+)) symporter (daacs) family.\";";

        verify(drugbank, "xref_drugbank_full", result);
        assertNull(result.get("xref_guidetopharmacology_full"));
        verify(tcdb, "xref_tcdb_full", result);
        assertNull(result.get("xref_dmdm_full"));
    }

    @Test
    void testXRefs4() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_ensembl",
                        "xref_reactome",
                        "xref_interpro",
                        "xref_prosite",
                        "xref_pfam");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);
        String ensembl =
                "ENST00000412532 [Q15758-3];ENST00000434726 [Q15758-2];ENST00000542575 [Q15758-1];";
        String reactome = "R-HSA-352230;";
        String interpro = "IPR001991;IPR018107;IPR036458;";
        String prosite = "PS00713;PS00714;";
        String pfam = "PF00375;";
        verify(ensembl, "xref_ensembl", result);
        verify(reactome, "xref_reactome", result);
        verify(interpro, "xref_interpro", result);
        verify(prosite, "xref_prosite", result);
        verify(pfam, "xref_pfam", result);
    }

    @Test
    void testXRefs4Full() {
        List<String> fields =
                List.of(
                        "accession",
                        "xref_ensembl_full",
                        "xref_reactome_full",
                        "xref_interpro_full",
                        "xref_prosite_full",
                        "xref_pfam_full");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        verify("Q15758", "accession", result);

        String ensemblFull =
                "\"ENST00000412532; ENSP00000397924; ENSG00000105281. [Q15758-3]\";"
                        + "\"ENST00000434726; ENSP00000406532; ENSG00000105281. [Q15758-2]\";"
                        + "\"ENST00000542575; ENSP00000444408; ENSG00000105281. [Q15758-1]\";";
        String reactomeFull = "\"R-HSA-352230; Amino acid transport across the plasma membrane.\";";
        String interproFull =
                "\"IPR001991; Na-dicarboxylate_symporter.\";\"IPR018107; Na-dicarboxylate_symporter_CS.\";\"IPR036458; Na:dicarbo_symporter_sf.\";";
        String prositeFull =
                "\"PS00713; NA_DICARBOXYL_SYMP_1; 1.\";\"PS00714; NA_DICARBOXYL_SYMP_2; 1.\";";
        String pfamFull = "\"PF00375; SDF; 1.\";";
        verify(ensemblFull, "xref_ensembl_full", result);
        verify(reactomeFull, "xref_reactome_full", result);
        verify(interproFull, "xref_interpro_full", result);
        verify(prositeFull, "xref_prosite_full", result);
        verify(pfamFull, "xref_pfam_full", result);
    }

    @Test
    void testProteome() {
        List<String> fields = List.of("accession", "xref_proteomes");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        String proteome = "UP000009255: Genome; UP000116373: Genome; UP000170967: Genome";
        verify(proteome, "xref_proteomes", result);
    }

    @Test
    void testPdb() {
        List<String> fields = List.of("accession", "xref_pdb", "structure_3d");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        String pdb = "2ZNL;2ZTT;3A1G;";
        String pdbFull =
                "\"2ZNL; X-ray; 2.30 A; B=1-81.\";\"2ZTT; X-ray; 2.10 A; A/C=679-757.\";\"3A1G; X-ray; 1.70 A; A/C=678-757.\";";
        String d3d = "X-ray crystallography (3)";
        verify(pdb, "xref_pdb", result);
        verify(pdbFull, "xref_pdb_full", result);
        verify(d3d, "structure_3d", result);
    }

    @Test
    void testKeyword() {
        List<String> fields = List.of("accession", "keyword", "keywordid");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("P03431", "accession", result);
        String keyword =
                "3D-structure;Eukaryotic host gene expression shutoff by virus;Eukaryotic host"
                        + " transcription shutoff by virus;Host cytoplasm;Host gene expression shutoff"
                        + " by virus;Host nucleus;Host-virus interaction;Inhibition of host RNA"
                        + " polymerase II by"
                        + " virus;Nucleotide-binding;Nucleotidyltransferase;Phosphoprotein;Reference"
                        + " proteome;RNA-directed RNA polymerase;Transferase;Viral RNA"
                        + " replication;Viral transcription";
        String keywordid =
                "KW-0002; KW-1262; KW-1191; KW-1035; KW-1190; KW-1048; KW-0945; KW-1104; KW-0547;"
                        + " KW-0548; KW-0597; KW-1185; KW-0696; KW-0808; KW-0693; KW-1195";
        verify(keyword, "keyword", result);
        verify(keywordid, "keywordid", result);
    }

    @Test
    void testExtraAttributeCommentCountForQ15758() {
        List<String> fields = List.of("comment_count");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        String expectedCommentCount =
                "ALTERNATIVE PRODUCTS (3); FUNCTION (4); INTERACTION (1); SIMILARITY (1);"
                        + " SUBCELLULAR LOCATION (1); SUBUNIT (1); TISSUE SPECIFICITY (1)";

        verify(expectedCommentCount, "comment_count", result);
    }

    @Test
    void testExtraAttributeCommentCountForP03431() {
        List<String> fields = List.of("comment_count");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        String expectedCommentCount =
                "CATALYTIC ACTIVITY (1); FUNCTION (1); INTERACTION (5); "
                        + "PTM (1); SIMILARITY (1); SUBCELLULAR LOCATION (1); SUBUNIT (1)";

        verify(expectedCommentCount, "comment_count", result);
    }

    @Test
    void testExtraAttributeFeatureCount() {
        List<String> fields = List.of("feature_count");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryQ15758, fields);

        String expectedCommentCount =
                "Alternative sequence (2); Beta strand (2); Chain (1); Glycosylation (2); Helix (11); Intramembrane (2); Metal binding (5); Modified residue (6); Natural variant (2); Sequence conflict (8); Topological domain (11); Transmembrane (8)";

        verify(expectedCommentCount, "feature_count", result);
    }

    @Test
    void testExtraAttributeUniParcId() {
        List<String> fields = List.of("uniparc_id");
        Map<String, String> result = new UniProtKBEntryValueMapper().mapEntity(entryP03431, fields);

        verify("UP1234567890", "uniparc_id", result);
    }

    private void verify(String expected, String fieldName, Map<String, String> result) {
        assertEquals(expected, result.get(fieldName));
    }
}
