package org.uniprot.core.scorer.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.UniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.uniprotkb.description.ProteinDescription;

/** Tests if the DE line is parsed correctly for SwissProt and TrEMBL entries. */
class ProteinDescriptionScoredTest {

    private static final UniprotKBLineParserFactory PARSER_FACTORY =
            new DefaultUniprotKBLineParserFactory();

    @Test
    void shouldSubNameScore6() {

        String description =
                "DE   SubName: Full=Type IIS restriction enzyme Eco57I;\n"
                        + "DE            EC=3.1.21.4;\n"
                        + "DE            EC=2.1.1.72;\n";
        testDescription(description, 6.0, Consensus.COMPLEX);
    }

    @Test
    void shouldSubnameScore0() {
        String description =
                "DE   SubName: Full=Putative uncharacterized protein {ECO:0000269|PubMed:10433554};\n"
                        + "DE   Flags: Precursor {ECO:0000269|PubMed:10433554};\n";

        testDescription(description, 0.0, Consensus.COMPLEX);
    }

    @Test
    void shouldSubnameScore3() {
        String description =
                "DE   SubName: Full=Glutamate synthase large chain. {ECO:0000269|PubMed:10433554};\n"
                        + "DE            EC=1.4.1.13 {ECO:0000269|PubMed:10433554};\n";
        testDescription(description, 3.0, Consensus.COMPLEX);
    }

    @Test
    void shouldSubName2Score3() {
        String description =
                "DE   SubName: Full=Catalytic activity: beta-D-glucuronoside + H2O = D-glucuronate + "
                        + "alcohol. {ECO:0000269|PubMed:10433554};\n"
                        + "DE            EC=3.2.1.31 {ECO:0000269|PubMed:10433554};\n";

        testDescription(description, 3.0, Consensus.COMPLEX);
    }

    @Test
    void shouldRecNameScore9() {

        String description =
                "DE   RecName: Full=Interleukin-2;\n"
                        + "DE            Short=IL-2;\n"
                        + "DE   AltName: Full=T-cell growth factor;\n"
                        + "DE            Short=TCGF;\n"
                        + "DE   AltName: INN=Aldesleukin;\n";

        testDescription(description, 9.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDEwithFlagsScore14() {

        String description =
                "DE   RecName: Full=A disintegrin and metalloproteinase domain 10;\n"
                        + "DE            Short=ADAM 10;\n"
                        + "DE            EC=3.4.24.81;\n"
                        + "DE   AltName: Full=Mammalian disintegrin-metalloprotease;\n"
                        + "DE   AltName: Full=Kuzbanian protein homolog;\n"
                        + "DE   AltName: CD_antigen=CD156c;\n"
                        + "DE   Flags: Precursor; Fragment;\n";

        testDescription(description, 14.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDEwithFlagsScore16() {

        String description =
                "DE   RecName: Full=A disintegrin and metalloproteinase domain 10;\n"
                        + "DE            Short=ADAM 10;\n"
                        + "DE            EC=3.4.24.81;\n"
                        + "DE   AltName: Full=Mammalian disintegrin-metalloprotease;\n"
                        + "DE   AltName: Full=Kuzbanian protein homolog;\n"
                        + "DE   AltName: Allergen=Some value;\n"
                        + "DE   AltName: CD_antigen=CD156c;\n"
                        + "DE   Flags: Precursor; Fragment;\n";

        testDescription(description, 16.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDEwithIncludesScore11() {

        String description =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n";
        testDescription(description, 11.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDEwithDuplicateECsScore14() {

        String description =
                "DE   RecName: Full=Multifunctional CCA protein;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=CCA-adding enzyme;\n"
                        + "DE              EC=2.7.7.72;\n"
                        + "DE     AltName: Full=CCA tRNA nucleotidyltransferase;\n"
                        + "DE     AltName: Full=tRNA CCA-pyrophosphorylase;\n"
                        + "DE     AltName: Full=tRNA adenylyl-/cytidylyl-transferase;\n"
                        + "DE     AltName: Full=tRNA nucleotidyltransferase;\n"
                        + "DE     AltName: Full=tRNA-NT;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=2'-nucleotidase;\n"
                        + "DE              EC=3.1.3.-;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=2',3'-cyclic phosphodiesterase;\n"
                        + "DE              EC=3.1.4.-;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Phosphatase;\n"
                        + "DE              EC=3.1.3.-;\n";
        testDescription(description, 14.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDEwithContainsScore11() {

        String description =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;\n";
        testDescription(description, 11.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDescriptionScore9() {

        String description =
                "DE   RecName: Full=Interleukin-2;\n"
                        + "DE            Short=IL-2;\n"
                        + "DE   AltName: Full=T-cell growth factor;\n"
                        + "DE            Short=TCGF;\n"
                        + "DE   AltName: INN=Aldesleukin;\n";
        testDescription(description, 9.0, Consensus.COMPLEX);
    }

    @Test
    void testIncludeScore3() {

        String description =
                "DE   Includes:\n"
                        + "DE     RecName: Full=Phosphoribosylaminoimidazolecarboxamide formyltransferase;\n"
                        + "DE              EC=2.1.2.3;\n"
                        + "DE     AltName: Full=5-aminoimidazole-4-carboxamide ribonucleotide formyltransferase;\n"
                        + "DE     AltName: Full=AICAR transformylase;\n";
        testDescription(description, 3.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDescriptionScore11() {

        String description =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain;\n";

        testDescription(description, 11.0, Consensus.COMPLEX);
    }

    @Test
    void shouldRecName2Score9() {
        String description =
                "DE   RecName: Full=Interleukin-2;\n"
                        + "DE            Short=IL-2;\n"
                        + "DE   AltName: Full=T-cell growth factor;\n"
                        + "DE            Short=TCGF;\n"
                        + "DE   AltName: INN=Aldesleukin;\n";
        testDescription(description, 9.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDeWithECScore17() {
        String description =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.4;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;\n"
                        + "DE              EC=2.3.1.56;\n";

        testDescription(description, 17.0, Consensus.COMPLEX);
    }

    @Test
    void testSpDescriptionScore11() {

        String description =
                "DE   RecName: Full=Granulocyte colony-stimulating factor;\n"
                        + "DE            Short=G-CSF;\n"
                        + "DE   AltName: Full=Pluripoietin;\n"
                        + "DE   AltName: INN=Filgrastim;\n"
                        + "DE   AltName: INN=Lenograstim;\n"
                        + "DE   Flags: Precursor;\n";
        testDescription(description, 11.0, Consensus.COMPLEX);
    }

    @Test
    void shouldBigDescriptionScore11() {

        String description =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain;\n";

        testDescription(description, 11.0, Consensus.COMPLEX);
    }

    @Test
    void shouldDescriptionScore14() {

        String description =
                "DE   RecName: Full=4-coumarate--CoA ligase-like 5;\n"
                        + "DE            EC=6.2.1.-;\n"
                        + "DE   AltName: Full=Peroxisomal OPC-8:0-CoA ligase 1;\n"
                        + "DE   AltName: Full=4-coumarate--CoA ligase isoform 9;\n"
                        + "DE   AltName: Full=At4CL9;\n";

        testDescription(description, 14.0, Consensus.COMPLEX);
    }

    @Test
    void shouldSubNameScore0() {

        String description = "DE   SubName: Full=transposase{EI1};\n";
        testDescription(description, 0.0, Consensus.COMPLEX);
    }

    @Test
    void shouldRecName2Score5() {

        String description =
                "DE   RecName: Full=Ammonium transporter 1 member 1;\n"
                        + "DE            Short=AtAMT1;1;\n"
                        + "DE   Flags: Precursor;\n";

        testDescription(description, 5.0, Consensus.COMPLEX);
    }

    @Test
    void shouldRecNameScore5() {

        String description =
                "DE   RecName: Full=9.5 days embryo parthenogenote cDNA, RIKEN full-length enriched"
                        + " library, clone:B130014N10 product:LIM homeobox transcription factor 1 alpha, full insert sequence;\n"
                        + "DE            Short=LIM homeobox transcription factor 1 alpha;\n";
        testDescription(description, 5.0, Consensus.COMPLEX);
    }

    private void testDescription(String description, double score, Consensus consensus) {
        ProteinDescription proteinDescription = parseLines(description);
        ProteinDescriptionScored scored = new ProteinDescriptionScored(proteinDescription);
        assertEquals(score, scored.score(), 0.0001);
        assertEquals(consensus, scored.consensus());
    }

    private ProteinDescription parseLines(String lines) {
        UniprotKBLineParser<DeLineObject> parser = PARSER_FACTORY.createDeLineParser();
        DeLineObject obj = parser.parse(lines);
        DeLineConverter converter = new DeLineConverter();
        return converter.convert(obj);
    }
}
