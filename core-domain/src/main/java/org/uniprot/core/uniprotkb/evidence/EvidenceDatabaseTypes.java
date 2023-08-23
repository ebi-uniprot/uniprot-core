package org.uniprot.core.uniprotkb.evidence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public enum EvidenceDatabaseTypes {
    INSTANCE;
    private static final String GOOGLE = "Google";
    private final Map<String, EvidenceDatabaseDetail> typeMap = new HashMap<>();

    EvidenceDatabaseTypes() {
        init();
    }

    public @Nonnull EvidenceDatabaseDetail getType(@Nonnull String typeName) {
        EvidenceDatabaseDetail type = typeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(typeName + " does not exist in Evidence type list");
        }
        return type;
    }

    public List<EvidenceDatabaseDetail> getAllEvidenceDatabases() {
        return new ArrayList<>(typeMap.values());
    }

    private void init() {
        typeMap.put(
                "Araport",
                evd(
                        "Araport",
                        "Araport",
                        "I",
                        "https://apps.araport.org/thalemine/portal.do?externalids=%value"));
        typeMap.put(
                "CGD",
                evd(
                        "CGD",
                        "CGD",
                        "I",
                        "http://www.candidagenome.org/cgi-bin/locus.pl?dbid=%value"));
        typeMap.put("EMBL", evd("EMBL", "EMBL", "I", "https://www.ebi.ac.uk/ena/data/view/%value"));
        typeMap.put(
                "EPD",
                evd(
                        "EPD",
                        "EPD",
                        "C",
                        "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/proteomics_mapping/README"));
        typeMap.put("Ensembl", evd("Ensembl", "Ensembl", "I", "https://www.ensembl.org/id/%value"));
        typeMap.put(
                "EnsemblBacteria",
                evd(
                        "EnsemblBacteria",
                        "EnsemblBacteria",
                        "I",
                        "http://www.ensemblgenomes.org/id/%value"));
        typeMap.put(
                "EnsemblFungi",
                evd(
                        "EnsemblFungi",
                        "EnsemblFungi",
                        "I",
                        "http://www.ensemblgenomes.org/id/%value"));
        typeMap.put(
                "EnsemblMetazoa",
                evd(
                        "EnsemblMetazoa",
                        "EnsemblMetazoa",
                        "I",
                        "http://www.ensemblgenomes.org/id/%value"));
        typeMap.put(
                "EnsemblPlants",
                evd(
                        "EnsemblPlants",
                        "EnsemblPlants",
                        "I",
                        "http://www.ensemblgenomes.org/id/%value"));
        typeMap.put(
                "EnsemblProtists",
                evd(
                        "EnsemblProtists",
                        "EnsemblProtists",
                        "I",
                        "http://www.ensemblgenomes.org/id/%value"));
        typeMap.put(
                "FlyBase",
                evd("FlyBase", "FlyBase", "I", "http://flybase.org/reports/%value.html"));
        typeMap.put(
                "HAMAP-Rule",
                evd("HAMAP-Rule", "HAMAP-Rule", "A", "https://hamap.expasy.org/rule/%value"));
        typeMap.put("ARBA", evd("ARBA", "ARBA", "A", "https://www.uniprot.org/arba/%value"));
        typeMap.put(
                "HGNC",
                evd(
                        "HGNC",
                        "HGNC",
                        "I",
                        "https://www.genenames.org/cgi-bin/gene_symbol_report?hgnc_id=%value"));
        typeMap.put("HPA", evd("HPA", "HPA", "I", "https://www.proteinatlas.org/search/%value"));
        typeMap.put("MGI", evd("MGI", "MGI", "I", "http://www.informatics.jax.org/marker/%value"));
        typeMap.put(
                "MaxQB",
                evd(
                        "MaxQB",
                        "MaxQB",
                        "C",
                        "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/proteomics_mapping/README"));
        typeMap.put(
                "PDB", evd("PDB", "PDB", "I", "https://www.ebi.ac.uk/pdbe-srv/view/entry/%value"));
        typeMap.put(
                "PIR",
                evd("PIR", "PIR", "I", "http://pir.georgetown.edu/cgi-bin/nbrfget?uid=%value"));
        typeMap.put("PIRNR", evd("PIRNR", "PIRNR", "A", "https://www.uniprot.org/unirule/%value"));
        typeMap.put("PIRSR", evd("PIRSR", "PIRSR", "A", "https://www.uniprot.org/unirule/%value"));
        typeMap.put("PROSITE", evd("PROSITE", "PROSITE", "A", "https://prosite.expasy.org/%value"));
        typeMap.put(
                "PROSITE-ProRule",
                evd(
                        "PROSITE-ProRule",
                        "PROSITE-ProRule",
                        "A",
                        "https://prosite.expasy.org/unirule/%value"));
        typeMap.put(
                "PeptideAtlas",
                evd(
                        "PeptideAtlas",
                        "PeptideAtlas",
                        "C",
                        "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/proteomics_mapping/README"));
        typeMap.put("Pfam", evd("Pfam", "Pfam", "A", "http://pfam.xfam.org/family/%value"));
        typeMap.put(
                "PomBase",
                evd("PomBase", "PomBase", "I", "https://www.pombase.org/spombe/result/%value"));
        typeMap.put(
                "Proteomes",
                evd("Proteomes", "Proteomes", "A", "https://www.uniprot.org/proteomes/%value"));
        typeMap.put(
                "PubMed", evd("PubMed", "PubMed", "C", "https://www.uniprot.org/citations/%value"));
        typeMap.put(
                "RGD",
                evd("RGD", "RGD", "I", "http://rgd.mcw.edu/tools/genes/genes_view.cgi?id=%value"));
        typeMap.put(
                "RefSeq",
                evd("RefSeq", "RefSeq", "I", "https://www.ncbi.nlm.nih.gov/protein/%value"));
        typeMap.put(
                "RuleBase",
                evd("RuleBase", "RuleBase", "A", "https://www.uniprot.org/unirule/%value"));
        typeMap.put("SAAS", evd("SAAS", "SAAS", "A", "https://www.uniprot.org/saas/%value"));
        typeMap.put("SAM", evd("SAM", "SAM", "A", "https://www.uniprot.org/help/sam"));
        typeMap.put("SGD", evd("SGD", "SGD", "I", "https://www.yeastgenome.org/locus/%value"));
        typeMap.put(
                "SMART",
                evd(
                        "SMART",
                        "SMART",
                        "A",
                        "http://smart.embl.de/smart/do_annotation.pl?DOMAIN=%value"));
        typeMap.put(
                "TAIR",
                evd(
                        "TAIR",
                        "TAIR",
                        "I",
                        "https://www.arabidopsis.org/servlets/TairObject?accession=%value"));
        typeMap.put(
                "UniRule",
                evd("UniRule", "UniRule", "A", "https://www.uniprot.org/unirule/%value"));
        typeMap.put(
                "VGNC",
                evd(
                        "VGNC",
                        "VGNC",
                        "I",
                        "https://vertebrate.genenames.org/data/gene-symbol-report/#!/vgnc_id/%value"));
        typeMap.put(
                "VectorBase",
                evd("VectorBase", "VectorBase", "I", "https://www.vectorbase.org/id/%value"));
        typeMap.put(
                "WBParaSite",
                evd("WBParaSite", "WBParaSite", "I", "http://parasite.wormbase.org/id/%value"));
        typeMap.put(
                "WormBase",
                evd(
                        "WormBase",
                        "WormBase",
                        "I",
                        "https://wormbase.org/species/c_elegans/cds/%value"));
        typeMap.put(
                "Xenbase",
                evd(
                        "Xenbase",
                        "Xenbase",
                        "I",
                        "http://www.xenbase.org/gene/showgene.do?method=display&geneId=value"));
        typeMap.put(
                "ZFIN",
                evd(
                        "ZFIN",
                        "ZFIN",
                        "I",
                        "http://zfin.org/cgi-bin/webdriver?MIval=aa-markerview.apg&OID=%value"));
        typeMap.put(
                "UniProtKB",
                evd("UniProtKB", "UniProtKB", "C", "https://www.uniprot.org/uniprot/%value"));
        typeMap.put("Reference", evd("Reference", "Reference", "C", ""));
        typeMap.put(
                "dictyBase",
                evd("dictyBase", "dictyBase", "C", "http://dictybase.org/gene/%value"));
        typeMap.put("MIM", evd("MIM", "MIM", "C", "http://www.omim.org/entry/%value"));
        typeMap.put(
                "EcoGene",
                evd("EcoGene", "EcoGene", "C", "http://www.ecogene.org/geneInfo.php?eg_id=%value"));
        typeMap.put(
                "TubercuList",
                evd("TubercuList", "TubercuList", "C", "https://mycobrowser.epfl.ch/genes/%value"));
        typeMap.put(
                "MobiDB-lite",
                evd("MobiDB-lite", "MobiDB-lite", "A", "https://www.uniprot.org/help/MobiDB-lite"));
        typeMap.put(
                "ProteomicsDB",
                evd(
                        "ProteomicsDB",
                        "ProteomicsDB",
                        "C",
                        "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/proteomics_mapping/README"));
        typeMap.put(GOOGLE, evd(GOOGLE, GOOGLE, "A", "https://www.uniprot.org/help/ProtNLM"));
    }

    private EvidenceDatabaseDetail evd(
            String name, String displayName, String category, String uriLink) {
        EvidenceDatabaseCategory etCategory = EvidenceDatabaseCategory.valueOf(category);
        return new EvidenceDatabaseDetail(name, displayName, etCategory, uriLink);
    }
}
