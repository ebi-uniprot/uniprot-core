package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UniProtXDbTypesTest {

    @Test
    void testGetAllTypes() {
        List<UniProtXDbTypeDetail> types = UniProtXDbTypes.INSTANCE.getAllDBXRefTypes();

        assertFalse(types.isEmpty());
        System.out.println(types.size());

    }

    @Test
    void testEmblType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("EMBL");
        assertEquals("EMBL", opType.getName());
        assertEquals(DatabaseCategory.SEQUENCE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/ena/data/view/%value", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ProteinId", "protein sequence ID",
                        "https://www.ebi.ac.uk/ena/data/view/%value");
        verifyAttribute(opType.getAttributes().get(1), "Status", "status",
                        null);
        verifyAttribute(opType.getAttributes().get(2), "MoleculeType", "molecule type",
                        null);
    }

    @Test
    void testPDBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("PDB");
        assertEquals("PDB", opType.getName());
        assertEquals("PDB", opType.getDisplayName());
        assertEquals(DatabaseCategory.D3_STRUCTURE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/pdbe/entry/pdb/%value", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Method", "method",
                        null);
        verifyAttribute(opType.getAttributes().get(1), "Resolution", "resolution",
                        null);
        verifyAttribute(opType.getAttributes().get(2), "Chains", "chains",
                        null);
    }

    @Test
    void testComplexPortalType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ComplexPortal");
        assertEquals("ComplexPortal", opType.getName());
        assertEquals("ComplexPortal", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTEIN_PROTEIN_INTERACTION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/complexportal/complex/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name",
                        null);
    }


    @Test
    void testChEMBLType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ChEMBL");
        assertEquals("ChEMBL", opType.getName());
        assertEquals("ChEMBL", opType.getDisplayName());
        assertEquals(DatabaseCategory.CHEMISTRY, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/chembldb/target/inspect/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);

    }

    @Test
    void testMoonDBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("MoonDB");
        assertEquals("MoonDB", opType.getName());
        assertEquals("MoonDB", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTEIN_FAMILY_GROUP_DATABASES, opType.getCategory());
        assertEquals("http://moondb.hb.univ-amu.fr/protein/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Type", "type",
                        null);
    }

    @Test
    void testiPTMnetType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("iPTMnet");
        assertEquals("iPTMnet", opType.getName());
        assertEquals("iPTMnet", opType.getDisplayName());
        assertEquals(DatabaseCategory.PTM_DATABASES, opType.getCategory());
        assertEquals("https://research.bioinformatics.udel.edu/iptmnet/entry/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);
    }

    @Test
    void testdbSNPType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("dbSNP");
        assertEquals("dbSNP", opType.getName());
        assertEquals("dbSNP", opType.getDisplayName());
        assertEquals(DatabaseCategory.POLYMORPHISM_AND_MUTATION_DATABASES, opType.getCategory());
        assertEquals("https://www.ncbi.nlm.nih.gov/snp/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);
    }

    @Test
    void testSwiss2dpageType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("SWISS-2DPAGE");

        assertEquals("SWISS-2DPAGE", opType.getName());
        assertEquals("SWISS-2DPAGE", opType.getDisplayName());
        assertEquals(DatabaseCategory.D2_GEL_DATABASES, opType.getCategory());
        assertEquals("https://world-2dpage.expasy.org/swiss-2dpage/protein/ac=%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);
    }

    @Test
    void testMaxQBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("MaxQB");
        assertEquals("MaxQB", opType.getName());
        assertEquals("MaxQB", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTEOMIC_DATABASES, opType.getCategory());
        assertEquals("http://maxqb.biochem.mpg.de/mxdb/protein/show/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);
    }

    @Test
    void testDNASUType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("DNASU");

        assertEquals("DNASU", opType.getName());
        assertEquals("DNASU", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTOCOLS_AND_MATERIALS_DATABASES, opType.getCategory());
        assertEquals("https://dnasu.org/DNASU/AdvancedSearchOptions.do?geneName=%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description",
                        null);
    }

    @Test
    void testEnsemblType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Ensembl");
        assertEquals("Ensembl", opType.getName());
        assertEquals("Ensembl", opType.getDisplayName());
        assertEquals(DatabaseCategory.GENOME_ANNOTATION_DATABASES, opType.getCategory());
        assertEquals("https://www.ensembl.org/id/%value", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ProteinId", "protein sequence ID",
                        "https://www.ensembl.org/id/%value");
        verifyAttribute(opType.getAttributes().get(1), "GeneId", "gene ID",
                        "https://www.ensembl.org/id/%value");

    }

    @Test
    void testVGNCType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("VGNC");
        assertEquals("VGNC", opType.getName());
        assertEquals("VGNC", opType.getDisplayName());
        assertEquals(DatabaseCategory.ORGANISM_SPECIFIC_DATABASES, opType.getCategory());
        assertEquals("https://vertebrate.genenames.org/data/gene-symbol-report/#!/vgnc_id/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GeneName", "gene designation",
                        null);
    }

    @Test
    void testeggNOGType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("eggNOG");

        assertEquals("eggNOG", opType.getName());
        assertEquals("eggNOG", opType.getDisplayName());
        assertEquals(DatabaseCategory.PHYLOGENOMIC_DATABASES, opType.getCategory());
        assertEquals("http://eggnogdb.embl.de/#/app/results?seqid=%acc&target_nogs=%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ToxonomicScope", "taxonomic scope",
                        null);
    }

    @Test
    void testReactomeType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Reactome");

        assertEquals("Reactome", opType.getName());
        assertEquals("Reactome", opType.getDisplayName());
        assertEquals(DatabaseCategory.ENZYME_AND_PATHWAY_DATABASES, opType.getCategory());
        assertEquals("https://www.reactome.org/PathwayBrowser/#%value&FLG=%acc", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "PathwayName", "pathway name",
                        null);
    }

    @Test
    void testChiTaRSType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ChiTaRS");
        assertEquals("ChiTaRS", opType.getName());
        assertEquals("ChiTaRS", opType.getDisplayName());
        assertEquals(DatabaseCategory.OTHER, opType.getCategory());
        assertEquals("http://chitars.md.biu.ac.il/bin/search.pl?searchtype=gene_name&searchstr=%value", opType
                .getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "OrganismName", "organism name",
                        null);
    }

    @Test
    void testExpressionAtlasType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ExpressionAtlas");
        assertEquals("ExpressionAtlas", opType.getName());
        assertEquals("ExpressionAtlas", opType.getDisplayName());
        assertEquals(DatabaseCategory.GENE_EXPRESSION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/gxa/query?geneQuery=%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ExpressionPatterns", "expression patterns",
                        null);
    }


    @Test
    void testPIRSFType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("PIRSF");
        assertEquals("PIRSF", opType.getName());
        assertEquals("PIRSF", opType.getDisplayName());
        assertEquals(DatabaseCategory.FAMILY_AND_DOMAIN_DATABASES, opType.getCategory());
        assertEquals("https://pir.georgetown.edu/cgi-bin/ipcSF?id=%value", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name",
                        null);
        verifyAttribute(opType.getAttributes().get(1), "MatchStatus", "match status",
                        null);
    }

    @Test
    void testGOType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("GO");
        assertEquals("GO", opType.getName());
        assertEquals("GO", opType.getDisplayName());
        assertEquals(DatabaseCategory.GENE_ONTOLOGY_DATABASES, opType.getCategory());
        assertEquals("https://prosite.expasy.org/doc/%value", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GoTerm", "term",
                        null);
        verifyAttribute(opType.getAttributes().get(1), "GoEvidenceType", "evidence",
                        null);
        verifyAttribute(opType.getAttributes().get(2), "Project", "project",
                        null);
    }

    @Test
    void testProteomesType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Proteomes");
        assertEquals("Proteomes", opType.getName());
        assertEquals("Proteomes", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTEOMES_DATABASES, opType.getCategory());
        assertEquals("https://www.uniprot.org/proteomes/%value", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Component", "component",
                        null);
    }


    void verifyAttribute(DBXRefTypeAttribute attr, String name, String xmlTag, String link) {
        assertEquals(name, attr.getName());
        assertEquals(xmlTag, attr.getXmlTag());
        assertEquals(link, attr.getUriLink());

    }

}
