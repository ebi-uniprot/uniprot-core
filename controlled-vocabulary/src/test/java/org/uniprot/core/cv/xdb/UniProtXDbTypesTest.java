package org.uniprot.core.cv.xdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.uniprot.core.cv.xdb.DatabaseCategory.*;

import java.util.List;

import org.junit.jupiter.api.Test;

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
        assertEquals(SEQUENCE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/ena/data/view/%s", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0),
                "ProteinId",
                "protein sequence ID",
                "https://www.ebi.ac.uk/ena/data/view/%s");
        verifyAttribute(opType.getAttributes().get(1), "Status", "status", null);
        verifyAttribute(opType.getAttributes().get(2), "MoleculeType", "molecule type", null);
    }

    @Test
    void testPDBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("PDB");
        assertEquals("PDB", opType.getName());
        assertEquals("PDB", opType.getDisplayName());
        assertEquals(D3_STRUCTURE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/pdbe-srv/view/entry/%s", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Method", "method", null);
        verifyAttribute(opType.getAttributes().get(1), "Resolution", "resolution", null);
        verifyAttribute(opType.getAttributes().get(2), "Chains", "chains", null);
    }

    @Test
    void testComplexPortalType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ComplexPortal");
        assertEquals("ComplexPortal", opType.getName());
        assertEquals("ComplexPortal", opType.getDisplayName());
        assertEquals(PROTEIN_PROTEIN_INTERACTION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/complexportal/complex/%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name", null);
    }

    @Test
    void testChEMBLType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ChEMBL");
        assertEquals("ChEMBL", opType.getName());
        assertEquals("ChEMBL", opType.getDisplayName());
        assertEquals(CHEMISTRY, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/chembldb/target/inspect/%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testMoonDBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("MoonDB");
        assertEquals("MoonDB", opType.getName());
        assertEquals("MoonDB", opType.getDisplayName());
        assertEquals(PROTEIN_FAMILY_GROUP_DATABASES, opType.getCategory());
        assertEquals("http://moondb.hb.univ-amu.fr/protein/%u", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Type", "type", null);
    }

    @Test
    void testiPTMnetType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("iPTMnet");
        assertEquals("iPTMnet", opType.getName());
        assertEquals("iPTMnet", opType.getDisplayName());
        assertEquals(PTM_DATABASES, opType.getCategory());
        assertEquals(
                "http://research.bioinformatics.udel.edu/iptmnet/entry/%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testdbSNPType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("dbSNP");
        assertEquals("dbSNP", opType.getName());
        assertEquals("dbSNP", opType.getDisplayName());
        assertEquals(POLYMORPHISM_AND_MUTATION_DATABASES, opType.getCategory());
        assertEquals(
                "https://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?type=rs&rs=%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testSwiss2dpageType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("SWISS-2DPAGE");

        assertEquals("SWISS-2DPAGE", opType.getName());
        assertEquals("SWISS-2DPAGE", opType.getDisplayName());
        assertEquals(D2_GEL_DATABASES, opType.getCategory());
        assertEquals("https://world-2dpage.expasy.org/swiss-2dpage/%u", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testMaxQBType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("MaxQB");
        assertEquals("MaxQB", opType.getName());
        assertEquals("MaxQB", opType.getDisplayName());
        assertEquals(PROTEOMIC_DATABASES, opType.getCategory());
        assertEquals("http://maxqb.biochem.mpg.de/mxdb/protein/show/%u", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testDNASUType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("DNASU");

        assertEquals("DNASU", opType.getName());
        assertEquals("DNASU", opType.getDisplayName());
        assertEquals(PROTOCOLS_AND_MATERIALS_DATABASES, opType.getCategory());
        assertEquals(
                "https://dnasu.org/DNASU/AdvancedSearchOptions.do?geneName=%s",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testEnsemblType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Ensembl");
        assertEquals("Ensembl", opType.getName());
        assertEquals("Ensembl", opType.getDisplayName());
        assertEquals(GENOME_ANNOTATION_DATABASES, opType.getCategory());
        assertEquals("https://www.ensembl.org/id/%s", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0),
                "ProteinId",
                "protein sequence ID",
                "https://www.ensembl.org/id/%s");
        verifyAttribute(
                opType.getAttributes().get(1),
                "GeneId",
                "gene ID",
                "https://www.ensembl.org/id/%s");
    }

    @Test
    void testVGNCType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("VGNC");
        assertEquals("VGNC", opType.getName());
        assertEquals("VGNC", opType.getDisplayName());
        assertEquals(ORGANISM_SPECIFIC_DATABASES, opType.getCategory());
        assertEquals(
                "https://vertebrate.genenames.org/data/gene-symbol-report/#!/vgnc_id/%s",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GeneName", "gene designation", null);
    }

    @Test
    void testeggNOGType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("eggNOG");

        assertEquals("eggNOG", opType.getName());
        assertEquals("eggNOG", opType.getDisplayName());
        assertEquals(PHYLOGENOMIC_DATABASES, opType.getCategory());
        assertEquals(
                "http://eggnogdb.embl.de/#/app/results?seqid=%u&target_nogs=%s",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ToxonomicScope", "taxonomic scope", null);
    }

    @Test
    void testReactomeType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Reactome");

        assertEquals("Reactome", opType.getName());
        assertEquals("Reactome", opType.getDisplayName());
        assertEquals(ENZYME_AND_PATHWAY_DATABASES, opType.getCategory());
        assertEquals("https://www.reactome.org/PathwayBrowser/#%s&FLG=%u", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "PathwayName", "pathway name", null);
    }

    @Test
    void testChiTaRSType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ChiTaRS");
        assertEquals("ChiTaRS", opType.getName());
        assertEquals("ChiTaRS", opType.getDisplayName());
        assertEquals(OTHER, opType.getCategory());
        assertEquals(
                "http://chitars.md.biu.ac.il/bin/search.pl?searchtype=gene_name&searchstr=%s&%d=1",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "OrganismName", "organism name", null);
    }

    @Test
    void testExpressionAtlasType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ExpressionAtlas");
        assertEquals("ExpressionAtlas", opType.getName());
        assertEquals("ExpressionAtlas", opType.getDisplayName());
        assertEquals(GENE_EXPRESSION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/gxa/query?geneQuery=%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0), "ExpressionPatterns", "expression patterns", null);
    }

    @Test
    void testPIRSFType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("PIRSF");
        assertEquals("PIRSF", opType.getName());
        assertEquals("PIRSF", opType.getDisplayName());
        assertEquals(FAMILY_AND_DOMAIN_DATABASES, opType.getCategory());
        assertEquals(
                "https://proteininformationresource.org/cgi-bin/ipcSF?id=%s", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name", null);
        verifyAttribute(opType.getAttributes().get(1), "MatchStatus", "match status", null);
    }

    @Test
    void testGOType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("GO");
        assertEquals("GO", opType.getName());
        assertEquals("GO", opType.getDisplayName());
        assertEquals(GENE_ONTOLOGY_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/QuickGO/term/%s", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GoTerm", "term", null);
        verifyAttribute(opType.getAttributes().get(1), "GoEvidenceType", "evidence", null);
        verifyAttribute(opType.getAttributes().get(2), "Project", "project", null);
    }

    @Test
    void testProteomesType() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("Proteomes");
        assertEquals("Proteomes", opType.getName());
        assertEquals("Proteomes", opType.getDisplayName());
        assertEquals(DatabaseCategory.PROTEOMES_DATABASES, opType.getCategory());
        assertEquals("https://www.uniprot.org/proteomes/%s", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Component", "component", null);
    }

    @Test
    void testDatabaseFieldSize() {
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(SEQUENCE_DATABASES), 4);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(D3_STRUCTURE_DATABASES), 3);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(
                        PROTEIN_PROTEIN_INTERACTION_DATABASES),
                8);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(CHEMISTRY), 6);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PROTEIN_FAMILY_GROUP_DATABASES), 12);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PTM_DATABASES), 7);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(POLYMORPHISM_AND_MUTATION_DATABASES),
                3);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(D2_GEL_DATABASES), 7);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PROTEOMIC_DATABASES), 11);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PROTOCOLS_AND_MATERIALS_DATABASES),
                2);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(GENOME_ANNOTATION_DATABASES), 14);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(ORGANISM_SPECIFIC_DATABASES), 36);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PHYLOGENOMIC_DATABASES), 9);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(ENZYME_AND_PATHWAY_DATABASES), 8);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(OTHER), 7);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(GENE_EXPRESSION_DATABASES), 5);
        verifyGroupSize(
                UniProtXDbTypes.INSTANCE.getDBTypesByCategory(FAMILY_AND_DOMAIN_DATABASES), 15);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(GENE_ONTOLOGY_DATABASES), 1);
        verifyGroupSize(UniProtXDbTypes.INSTANCE.getDBTypesByCategory(PROTEOMES_DATABASES), 1);
    }

    private void verifyGroupSize(List<UniProtXDbTypeDetail> dbTypesByCategory, int size) {
        assertEquals(size, dbTypesByCategory.size());
    }

    void verifyAttribute(DBXRefTypeAttribute attr, String name, String xmlTag, String link) {
        assertEquals(name, attr.getName());
        assertEquals(xmlTag, attr.getXmlTag());
        assertEquals(link, attr.getUriLink());
    }
}
