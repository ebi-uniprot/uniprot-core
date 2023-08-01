package org.uniprot.core.xml;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.impl.EntryAuditBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Uniprot;
import org.uniprot.core.xml.uniprot.UniProtEntryConverter;

class ConverterXMLToFFTest {

    @Test
    void testLeo() throws Exception {

        String xmlInput =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<uniprot xmlns=\"http://uniprot.org/uniprot\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://uniprot.org/uniprot http://www.uniprot.org/docs/uniprot.xsd\">\n"
                        + "<entry dataset=\"TrEMBL\" created=\"2018-01-31\" modified=\"2023-02-22\" version=\"11\">\n"
                        + "<accession>A0A2E0WTX0</accession>\n"
                        + "<name>TEMPLATE_VALUE</name>\n"
                        + "<protein>\n"
                        + "<submittedName>\n"
                        + "<fullName>NAD(P)H-dependent oxidoreductase</fullName>\n"
                        + "</submittedName>\n"
                        + "</protein>\n"
                        + "<proteinExistence type=\"UNKNOWN\"/>\n"
                        + "<comment >\n"
                        + "<type>similarity</type>\n"
                        + "<text>Belongs to the nitroreductase family.</text>\n"
                        + "</comment>\n"
                        + "<sequence length=\"1\" mass=\"1\" checksum=\"1\" modified=\"2018-01-31\" version=\"1\">A</sequence>\n"
                        + "</entry>"
                        + "<copyright> Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms Distributed under the Creative Commons Attribution (CC BY 4.0) License </copyright>"
                        + "</uniprot>";

        String xmlInput2 =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<uniprot xmlns=\"http://uniprot.org/uniprot\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://uniprot.org/uniprot http://www.uniprot.org/docs/uniprot.xsd\">\n"
                        + "<entry xmlns=\"http://uniprot.org/uniprot\" dataset=\"TrEMBL\" created=\"2018-01-31\" modified=\"2023-02-22\" version=\"11\">\n"
                        + "<accession>A0A2E0WTX0</accession>\n"
                        + "<name>A0A2E0WTX0_9FLAO</name>\n"
                        + "<protein>\n"
                        + "<submittedName>\n"
                        + "<fullName evidence=\"3\">NAD(P)H-dependent oxidoreductase</fullName>\n"
                        + "</submittedName>\n"
                        + "</protein>\n"
                        + "<gene>\n"
                        + "<name evidence=\"3\" type=\"ORF\">CL830_02265</name>\n"
                        + "</gene>\n"
                        + "<organism evidence=\"3 4\">\n"
                        + "<name type=\"scientific\">Crocinitomicaceae bacterium</name>\n"
                        + "<dbReference type=\"NCBI Taxonomy\" id=\"2026728\"/>\n"
                        + "<lineage>\n"
                        + "<taxon>Bacteria</taxon>\n"
                        + "<taxon>Bacteroidota</taxon>\n"
                        + "<taxon>Flavobacteriia</taxon>\n"
                        + "<taxon>Flavobacteriales</taxon>\n"
                        + "<taxon>Crocinitomicaceae</taxon>\n"
                        + "</lineage>\n"
                        + "</organism>\n"
                        + "<reference evidence=\"4\" key=\"1\">\n"
                        + "<citation type=\"submission\" date=\"2017-09\" db=\"EMBL/GenBank/DDBJ databases\">\n"
                        + "<title>The Reconstruction of 2,631 Draft Metagenome-Assembled Genomes from the Global Oceans.</title>\n"
                        + "<authorList>\n"
                        + "<person name=\"Tully B.J.\"/>\n"
                        + "<person name=\"Graham E.D.\"/>\n"
                        + "<person name=\"Heidelberg J.F.\"/>\n"
                        + "</authorList>\n"
                        + "</citation>\n"
                        + "<scope>NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]</scope>\n"
                        + "</reference>\n"
                        + "<comment type=\"similarity\">\n"
                        + "<text evidence=\"1\">Belongs to the nitroreductase family.</text>\n"
                        + "</comment>\n"
                        + "<comment type=\"caution\">\n"
                        + "<text evidence=\"3\">The sequence shown here is derived from an EMBL/GenBank/DDBJ whole genome shotgun (WGS) entry which is preliminary data.</text>\n"
                        + "</comment>\n"
                        + "<dbReference type=\"EMBL\" id=\"PACI01000017\">\n"
                        + "<property type=\"protein sequence ID\" value=\"MAT67205.1\"/>\n"
                        + "<property type=\"molecule type\" value=\"Genomic_DNA\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"AlphaFoldDB\" id=\"A0A2E0WTX0\"/>\n"
                        + "<dbReference type=\"Proteomes\" id=\"UP000236247\">\n"
                        + "<property type=\"component\" value=\"Unassembled WGS sequence\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"GO\" id=\"GO:0016491\">\n"
                        + "<property type=\"term\" value=\"F:oxidoreductase activity\"/>\n"
                        + "<property type=\"evidence\" value=\"ECO:0007669\"/>\n"
                        + "<property type=\"project\" value=\"InterPro\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"CDD\" id=\"cd02149\">\n"
                        + "<property type=\"entry name\" value=\"NfsB-like\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"Gene3D\" id=\"3.40.109.10\">\n"
                        + "<property type=\"entry name\" value=\"NADH Oxidase\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"InterPro\" id=\"IPR033878\">\n"
                        + "<property type=\"entry name\" value=\"NfsB-like\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"InterPro\" id=\"IPR029479\">\n"
                        + "<property type=\"entry name\" value=\"Nitroreductase\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"InterPro\" id=\"IPR000415\">\n"
                        + "<property type=\"entry name\" value=\"Nitroreductase-like\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"PANTHER\" id=\"PTHR43673\">\n"
                        + "<property type=\"entry name\" value=\"NAD(P)H NITROREDUCTASE YDGI-RELATED\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"PANTHER\" id=\"PTHR43673:SF2\">\n"
                        + "<property type=\"entry name\" value=\"NITROREDUCTASE FAMILY PROTEIN\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"Pfam\" id=\"PF00881\">\n"
                        + "<property type=\"entry name\" value=\"Nitroreductase\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<dbReference type=\"SUPFAM\" id=\"SSF55469\">\n"
                        + "<property type=\"entry name\" value=\"FMN-dependent nitroreductase-like\"/>\n"
                        + "<property type=\"match status\" value=\"1\"/>\n"
                        + "</dbReference>\n"
                        + "<proteinExistence type=\"inferred from homology\"/>\n"
                        + "<feature type=\"domain\" description=\"Nitroreductase\" evidence=\"2\">\n"
                        + "<location>\n"
                        + "<begin position=\"8\"/>\n"
                        + "<end position=\"185\"/>\n"
                        + "</location>\n"
                        + "</feature>\n"
                        + "<evidence type=\"ECO:0000256\" key=\"1\">\n"
                        + "<source>\n"
                        + "<dbReference type=\"ARBA\" id=\"ARBA00007118\"/>\n"
                        + "</source>\n"
                        + "</evidence>\n"
                        + "<evidence type=\"ECO:0000259\" key=\"2\">\n"
                        + "<source>\n"
                        + "<dbReference type=\"Pfam\" id=\"PF00881\"/>\n"
                        + "</source>\n"
                        + "</evidence>\n"
                        + "<evidence type=\"ECO:0000313\" key=\"3\">\n"
                        + "<source>\n"
                        + "<dbReference type=\"EMBL\" id=\"MAT67205.1\"/>\n"
                        + "</source>\n"
                        + "</evidence>\n"
                        + "<evidence type=\"ECO:0000313\" key=\"4\">\n"
                        + "<source>\n"
                        + "<dbReference type=\"Proteomes\" id=\"UP000236247\"/>\n"
                        + "</source>\n"
                        + "</evidence>\n"
                        + "<sequence length=\"209\" mass=\"24103\" checksum=\"492CC4863943E9ED\" modified=\"2018-01-31\" version=\"1\">MDIIDYYKWRYATKKFNPNKKIPISDIEIIKESIRLAPTSYGLQLFKVIIIENQLKKEALRKFSYNQSQVSDASHLFIFCNSTKVFEKDIDSYIENKSLSQEIPIEKNKGYGDFLKKTLLNKSSEEISEWTKNQLYIALTHLMTACASLKIDSCPIEGFDTSKYNDFLDIDKKSLSAGVVAAIGYRSETDNSQYDKKVRKATKDIFEVD</sequence>\n"
                        + "</entry>\n"
                        + "<copyright> Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms Distributed under the Creative Commons Attribution (CC BY 4.0) License </copyright>\n"
                        + "</uniprot>";

        String inputAddHeader =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<uniprot xmlns=\"http://uniprot.org/uniprot\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://uniprot.org/uniprot http://www.uniprot.org/docs/uniprot.xsd\">\n"
                        + "<entry>\n"
                        + "<accession>A0A2E0WTX0</accession>\n"
                        + "<name>TEMPLATE_VALUE</name>\n"
                        + "<protein>\n"
                        + "<submittedName>\n"
                        + "<fullName>NAD(P)H-dependent oxidoreductase</fullName>\n"
                        + "</submittedName>\n"
                        + "</protein>\n"
                        + "<comment type=\"similarity\">\n"
                        + "<text>Belongs to the nitroreductase family.</text>\n"
                        + "</comment>\n"
                        + "</entry>"
                        + "</uniprot>";

        InputStream targetStream = new ByteArrayInputStream(inputAddHeader.getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance("org.uniprot.core.xml.jaxb.uniprot");

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Uniprot xmlEntry = (Uniprot) jaxbUnmarshaller.unmarshal(targetStream);
        assertNotNull(xmlEntry);

        UniProtEntryConverter converter = new UniProtEntryConverter();
        UniProtKBEntry uniprotEntry =
                xmlEntry.getEntry().stream().map(converter::fromXml).findFirst().orElse(null);
        assertNotNull(uniprotEntry);

        UniProtKBEntry auditedEntry =
                UniProtKBEntryBuilder.from(uniprotEntry)
                        .entryAudit(
                                new EntryAuditBuilder()
                                        .firstPublic(LocalDate.now())
                                        .lastAnnotationUpdate(LocalDate.now())
                                        .lastSequenceUpdate(LocalDate.now())
                                        .build())
                        .build();

        // "XML FILE" --> "FF PARTIAL" (Curator can use to validate)

        String ffResult = UniProtFlatfileWriter.write(auditedEntry);
        assertNotNull(ffResult);
        System.out.println(ffResult);
    }

    protected Marshaller createMarshaller(JAXBContext jaxbContext) {
        try {
            Marshaller contextMarshaller = jaxbContext.createMarshaller();
            contextMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            contextMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            return contextMarshaller;
        } catch (Exception e) {
            throw new RuntimeException("JAXB marshaller creation failed", e);
        }
    }
}
