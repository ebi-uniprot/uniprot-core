package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.cv.xdb.UniProtDatabaseCategory.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseCategory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

import java.util.List;

class UniProtKBDatabaseTypesTest {

    @Test
    void testGetAllTypes() {
        List<UniProtDatabaseDetail> types = UniProtDatabaseTypes.INSTANCE.getAllDbTypes();

        assertFalse(types.isEmpty());
        System.out.println(types.size());
    }

    @Test
    void testEmblType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("EMBL");
        assertEquals("EMBL", opType.getName());
        assertFalse(opType.isImplicit());
        assertEquals(SEQUENCE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/ena/data/view/%id", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0),
                "ProteinId",
                "protein sequence ID",
                "https://www.ebi.ac.uk/ena/data/view/%ProteinId");
        verifyAttribute(opType.getAttributes().get(1), "Status", "status", null);
        verifyAttribute(opType.getAttributes().get(2), "MoleculeType", "molecule type", null);
    }

    @Test
    void testGeneBankType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("GenBank");
        assertEquals("GenBank", opType.getName());
        assertTrue(opType.isImplicit());
        assertEquals(SEQUENCE_DATABASES, opType.getCategory());
        assertEquals("https://www.ncbi.nlm.nih.gov/protein/%id", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0),
                "ProteinId",
                "protein sequence ID",
                "https://www.ncbi.nlm.nih.gov/nuccore/%ProteinId");
        verifyAttribute(opType.getAttributes().get(1), "Status", "status", null);
        verifyAttribute(opType.getAttributes().get(2), "MoleculeType", "molecule type", null);
    }

    @Test
    void testPDBType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("PDB");
        assertEquals("PDB", opType.getName());
        assertEquals("PDB", opType.getDisplayName());
        assertEquals(D3_STRUCTURE_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/pdbe-srv/view/entry/%id", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Method", "method", null);
        verifyAttribute(opType.getAttributes().get(1), "Resolution", "resolution", null);
        verifyAttribute(opType.getAttributes().get(2), "Chains", "chains", null);
    }

    @Test
    void testComplexPortalType() {
        UniProtDatabaseDetail opType =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName("ComplexPortal");
        assertEquals("ComplexPortal", opType.getName());
        assertEquals("ComplexPortal", opType.getDisplayName());
        assertEquals(PROTEIN_PROTEIN_INTERACTION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/complexportal/complex/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name", null);
    }

    @Test
    void testChEMBLType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("ChEMBL");
        assertEquals("ChEMBL", opType.getName());
        assertEquals("ChEMBL", opType.getDisplayName());
        assertEquals(CHEMISTRY, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/chembldb/target/inspect/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testMoonDBType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("MoonDB");
        assertEquals("MoonDB", opType.getName());
        assertEquals("MoonDB", opType.getDisplayName());
        assertEquals(PROTEIN_FAMILY_GROUP_DATABASES, opType.getCategory());
        assertEquals("http://moondb.hb.univ-amu.fr/protein/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Type", "type", null);
    }

    @Test
    void testiPTMnetType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("iPTMnet");
        assertEquals("iPTMnet", opType.getName());
        assertEquals("iPTMnet", opType.getDisplayName());
        assertEquals(PTM_DATABASES, opType.getCategory());
        assertEquals(
                "http://research.bioinformatics.udel.edu/iptmnet/entry/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testdbSNPType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("dbSNP");
        assertEquals("dbSNP", opType.getName());
        assertEquals("dbSNP", opType.getDisplayName());
        assertEquals(POLYMORPHISM_AND_MUTATION_DATABASES, opType.getCategory());
        assertEquals(
                "https://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?type=rs&rs=%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testSwiss2dpageType() {
        UniProtDatabaseDetail opType =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName("SWISS-2DPAGE");

        assertEquals("SWISS-2DPAGE", opType.getName());
        assertEquals("SWISS-2DPAGE", opType.getDisplayName());
        assertEquals(D2_GEL_DATABASES, opType.getCategory());
        assertEquals("https://world-2dpage.expasy.org/swiss-2dpage/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testMaxQBType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("MaxQB");
        assertEquals("MaxQB", opType.getName());
        assertEquals("MaxQB", opType.getDisplayName());
        assertEquals(PROTEOMIC_DATABASES, opType.getCategory());
        assertEquals("http://maxqb.biochem.mpg.de/mxdb/protein/show/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testDNASUType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("DNASU");

        assertEquals("DNASU", opType.getName());
        assertEquals("DNASU", opType.getDisplayName());
        assertEquals(PROTOCOLS_AND_MATERIALS_DATABASES, opType.getCategory());
        assertEquals(
                "https://dnasu.org/DNASU/AdvancedSearchOptions.do?geneName=%id",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Description", "description", null);
    }

    @Test
    void testEnsemblType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("Ensembl");
        assertEquals("Ensembl", opType.getName());
        assertEquals("Ensembl", opType.getDisplayName());
        assertEquals(GENOME_ANNOTATION_DATABASES, opType.getCategory());
        assertEquals("https://www.ensembl.org/id/%id", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0),
                "ProteinId",
                "protein sequence ID",
                "https://www.ensembl.org/id/%ProteinId");
        verifyAttribute(
                opType.getAttributes().get(1),
                "GeneId",
                "gene ID",
                "https://www.ensembl.org/id/%GeneId");
    }

    @Test
    void testVGNCType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("VGNC");
        assertEquals("VGNC", opType.getName());
        assertEquals("VGNC", opType.getDisplayName());
        assertEquals(ORGANISM_SPECIFIC_DATABASES, opType.getCategory());
        assertEquals(
                "https://vertebrate.genenames.org/data/gene-symbol-report/#!/vgnc_id/%id",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GeneName", "gene designation", null);
    }

    @Test
    void testeggNOGType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("eggNOG");

        assertEquals("eggNOG", opType.getName());
        assertEquals("eggNOG", opType.getDisplayName());
        assertEquals(PHYLOGENOMIC_DATABASES, opType.getCategory());
        assertEquals(
                "http://eggnogdb.embl.de/#/app/results?seqid=%accession&target_nogs=%id",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "ToxonomicScope", "taxonomic scope", null);
    }

    @Test
    void testReactomeType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("Reactome");

        assertEquals("Reactome", opType.getName());
        assertEquals("Reactome", opType.getDisplayName());
        assertEquals(ENZYME_AND_PATHWAY_DATABASES, opType.getCategory());
        assertEquals(
                "https://www.reactome.org/PathwayBrowser/#%id&FLG=%accession", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "PathwayName", "pathway name", null);
    }

    @Test
    void testChiTaRSType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("ChiTaRS");
        assertEquals("ChiTaRS", opType.getName());
        assertEquals("ChiTaRS", opType.getDisplayName());
        assertEquals(OTHER, opType.getCategory());
        assertEquals(
                "http://chitars.md.biu.ac.il/bin/search.pl?searchtype=gene_name&searchstr=%id&%d=1",
                opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "OrganismName", "organism name", null);
    }

    @Test
    void testExpressionAtlasType() {
        UniProtDatabaseDetail opType =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName("ExpressionAtlas");
        assertEquals("ExpressionAtlas", opType.getName());
        assertEquals("ExpressionAtlas", opType.getDisplayName());
        assertEquals(GENE_EXPRESSION_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/gxa/query?geneQuery=%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(
                opType.getAttributes().get(0), "ExpressionPatterns", "expression patterns", null);
    }

    @Test
    void testPIRSFType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("PIRSF");
        assertEquals("PIRSF", opType.getName());
        assertEquals("PIRSF", opType.getDisplayName());
        assertEquals(FAMILY_AND_DOMAIN_DATABASES, opType.getCategory());
        assertEquals(
                "https://proteininformationresource.org/cgi-bin/ipcSF?id=%id", opType.getUriLink());
        assertEquals(2, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "EntryName", "entry name", null);
        verifyAttribute(opType.getAttributes().get(1), "MatchStatus", "match status", null);
    }

    @Test
    void testGOType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("GO");
        assertEquals("GO", opType.getName());
        assertEquals("GO", opType.getDisplayName());
        assertEquals(GENE_ONTOLOGY_DATABASES, opType.getCategory());
        assertEquals("https://www.ebi.ac.uk/QuickGO/term/%id", opType.getUriLink());
        assertEquals(3, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "GoTerm", "term", null);
        verifyAttribute(opType.getAttributes().get(1), "GoEvidenceType", "evidence", null);
        verifyAttribute(opType.getAttributes().get(2), "Project", "project", null);
    }

    @Test
    void testProteomesType() {
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("Proteomes");
        assertEquals("Proteomes", opType.getName());
        assertEquals("Proteomes", opType.getDisplayName());
        Assertions.assertEquals(UniProtDatabaseCategory.PROTEOMES_DATABASES, opType.getCategory());
        assertEquals("https://www.uniprot.org/proteomes/%id", opType.getUriLink());
        assertEquals(1, opType.getAttributes().size());
        verifyAttribute(opType.getAttributes().get(0), "Component", "component", null);
    }

    @Test
    void testDatabaseFieldSize() {
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(SEQUENCE_DATABASES), 6);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(D3_STRUCTURE_DATABASES), 8);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(
                        PROTEIN_PROTEIN_INTERACTION_DATABASES),
                8);
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(CHEMISTRY), 6);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(PROTEIN_FAMILY_GROUP_DATABASES),
                14);
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(PTM_DATABASES), 8);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(
                        POLYMORPHISM_AND_MUTATION_DATABASES),
                3);
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(D2_GEL_DATABASES), 7);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(PROTEOMIC_DATABASES), 11);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(
                        PROTOCOLS_AND_MATERIALS_DATABASES),
                3);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(GENOME_ANNOTATION_DATABASES),
                14);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(ORGANISM_SPECIFIC_DATABASES),
                40);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(PHYLOGENOMIC_DATABASES), 9);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(ENZYME_AND_PATHWAY_DATABASES),
                9);
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(OTHER), 10);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(GENE_EXPRESSION_DATABASES), 5);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(FAMILY_AND_DOMAIN_DATABASES),
                17);
        verifyGroupSize(
                UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(GENE_ONTOLOGY_DATABASES), 1);
        verifyGroupSize(UniProtDatabaseTypes.INSTANCE.getDBTypesByCategory(PROTEOMES_DATABASES), 1);
    }

    private void verifyGroupSize(List<UniProtDatabaseDetail> dbTypesByCategory, int size) {
        assertEquals(size, dbTypesByCategory.size());
    }

    void verifyAttribute(UniProtDatabaseAttribute attr, String name, String xmlTag, String link) {
        assertEquals(name, attr.getName());
        assertEquals(xmlTag, attr.getXmlTag());
        assertEquals(link, attr.getUriLink());
    }
}
