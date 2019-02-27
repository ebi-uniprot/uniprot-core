package uk.ac.ebi.uniprot.flatfile.parser.impl.entry;


import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.cv.disease.DiseaseService;
import uk.ac.ebi.uniprot.cv.disease.impl.DiseaseServiceImpl;
import uk.ac.ebi.uniprot.cv.keyword.KeywordService;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordServiceImpl;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationService;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationServiceImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.InternalSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.UniProtAcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.UniProtDrObjects;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.kw.KwLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.og.OgLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ox.OxLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PeLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ss.SsLineConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

public class EntryObjectConverter implements Converter<EntryObject, UniProtEntry> {
    private static final Logger logger = LoggerFactory.getLogger(EntryObjectConverter.class);
    private final AcLineConverter acLineConverter = new AcLineConverter();
    private final DeLineConverter deLineConverter = new DeLineConverter();

    private final DtLineConverter dtLineConverter = new DtLineConverter();
    private final FtLineConverter ftLineConverter = new FtLineConverter();
    private final GnLineConverter gnLineConverter = new GnLineConverter();
    private final IdLineConverter idLineConverter = new IdLineConverter();

    private final OcLineConverter ocLineConverter = new OcLineConverter();
    private final OgLineConverter ogLineConverter = new OgLineConverter();

    private final OhLineConverter ohLineConverter = new OhLineConverter();
    private final OsLineConverter osLineConverter = new OsLineConverter();
    private final OxLineConverter oxLineConverter = new OxLineConverter();
    private final PeLineConverter peLineConverter = new PeLineConverter();

    private final SqLineConverter sqLineConverter = new SqLineConverter();
    private final SsLineConverter ssLineConverter = new SsLineConverter();

    private final ReferenceObjectConverter refObjConverter = new ReferenceObjectConverter();
    private final DrLineConverter drLineConverter;
    private final KwLineConverter kwLineConverter;
    private final CcLineConverter ccLineConverter;
    private Map<String, Map<String, List<Evidence>>> accessionGoEvidences = new HashMap<>();


    public EntryObjectConverter(String keywordFile, String diseaseFile, String goPubmedFile,
    		String subcellularLocationFile,
    		
    		boolean ignoreWrong) {
        drLineConverter = new DrLineConverter(ignoreWrong);
        KeywordService keywordService = new KeywordServiceImpl(keywordFile);
        DiseaseService diseaseService = new DiseaseServiceImpl(diseaseFile);
        SubcellularLocationService subcellularLocationService = new SubcellularLocationServiceImpl(subcellularLocationFile);
        kwLineConverter = new KwLineConverter(keywordService, ignoreWrong);
        ccLineConverter = new CcLineConverter(diseaseService, subcellularLocationService, ignoreWrong);
        initAccessionGoEvidenceMap(goPubmedFile);

    }

    private void initAccessionGoEvidenceMap(String goPubmedFile) {
        if (Strings.isNullOrEmpty(goPubmedFile)) {
            logger.warn("Go pubmed file is not defined");
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(goPubmedFile), StandardCharsets.UTF_8)) {
            for (String line = null; (line = br.readLine()) != null; ) {
                String[] splitedLine = line.split("\t");
                if (splitedLine.length >= 7) {
                    String accession = splitedLine[0];
                    String goId = splitedLine[1];
                    String evidenceValue = splitedLine[6].replace("PMID", "ECO:0000269|PubMed");
                    Evidence evidence = parseEvidenceLine(evidenceValue);
                    addEvidence(accession, goId, evidence);
                } else {
                    logger.warn("unable to parse line: '" + line + "' at file " + goPubmedFile);
                }
            }
        } catch (IOException e) {
            logger.warn("Error while loading Go pubmed file file on path: " + goPubmedFile, e);
        }
    }

    private void addEvidence(String accession, String goTerm, Evidence evidence) {
        if (accessionGoEvidences.containsKey(accession)) {
            Map<String, List<Evidence>> accessionTerms = accessionGoEvidences.get(accession);
            if (accessionTerms.containsKey(goTerm)) {
                List<Evidence> evidenceIds = accessionTerms.get(goTerm);
                evidenceIds.add(evidence);
            } else {
                List<Evidence> evidenceIds = new ArrayList<>();
                evidenceIds.add(evidence);
                accessionTerms.put(goTerm, evidenceIds);
            }
        } else {
            List<Evidence> evidenceIds = new ArrayList<>();
            evidenceIds.add(evidence);

            Map<String, List<Evidence>> term = new HashMap<>();
            term.put(goTerm, evidenceIds);

            accessionGoEvidences.put(accession, term);
        }
    }

    @Override
    public UniProtEntry convert(EntryObject f) {
        clear();
        UniProtEntryBuilder builder = new UniProtEntryBuilder();
        Map.Entry<UniProtId, UniProtEntryType> ids = idLineConverter.convert(f.id);
        UniProtAcLineObject acLineObj = acLineConverter.convert(f.ac);
        UniProtEntryBuilder.ActiveEntryBuilder activeEntryBuilder = builder
                .primaryAccession(acLineObj.getPrimaryAccession())
                .uniProtId(ids.getKey())
                .active()
                .entryType(ids.getValue())
                .secondaryAccessions(acLineObj.getSecondAccessions())
                .entryAudit(dtLineConverter.convert(f.dt));
        if (f.cc != null)
            activeEntryBuilder.comments(ccLineConverter.convert(f.cc));
        activeEntryBuilder.proteinDescription(deLineConverter.convert(f.de));
        UniProtDrObjects drObjects = drLineConverter.convert(f.dr);
        activeEntryBuilder.databaseCrossReferences(addGoEvidence(f.ac.primaryAcc, drObjects.drObjects));

        if (f.ft != null) {
            activeEntryBuilder.features(ftLineConverter.convert(f.ft));
        }
        if (f.gn != null)

            activeEntryBuilder.genes(gnLineConverter.convert(f.gn));
        if (f.kw != null) {
            activeEntryBuilder.keywords(kwLineConverter.convert(f.kw));
        }
        if (f.og != null) {
            activeEntryBuilder.geneLocations(ogLineConverter.convert(f.og));
        }
        if (f.oh != null) {
            activeEntryBuilder.organismHosts(ohLineConverter.convert(f.oh));
        }
        OrganismBuilder organismBuilder = new OrganismBuilder();
        organismBuilder.from(osLineConverter.convert(f.os));
        Organism oxLineOrganism = oxLineConverter.convert(f.ox);
        organismBuilder.taxonId(oxLineOrganism.getTaxonId())
                .evidences(oxLineOrganism.getEvidences())
                .lineage(ocLineConverter.convert(f.oc));
        activeEntryBuilder.organism(organismBuilder.build());
        activeEntryBuilder.proteinExistence(peLineConverter.convert(f.pe));
        activeEntryBuilder.sequence(sqLineConverter.convert(f.sq));
        List<UniProtReference> citations = new ArrayList<>();
        for (EntryObject.ReferenceObject refObj : f.ref) {
            citations.add(refObjConverter.convert(refObj));
        }
        activeEntryBuilder.references(citations);

        InternalSection usl = ssLineConverter.convert(f.ss);

        if (drObjects.ssProsites != null) {
            List<InternalLine> internalLines = new ArrayList<>();
            internalLines.addAll(drObjects.ssProsites);
            if (usl != null)
                internalLines.addAll(usl.getInternalLines());
            if (usl != null)
                activeEntryBuilder.internalSection(new InternalSectionBuilder()
                                                           .evidenceLines(usl.getEvidenceLines())
                                                           .sourceLines(usl.getSourceLines())
                                                           .internalLines(internalLines).build());
            else {
                activeEntryBuilder.internalSection(new InternalSectionBuilder()
                                                           .internalLines(internalLines).build());
            }
        } else {
            activeEntryBuilder.internalSection(usl);
        }


        return activeEntryBuilder.build();

    }

    private List<UniProtDBCrossReference> addGoEvidence(String accession, List<UniProtDBCrossReference> dbRefs) {
        Map<String, List<Evidence>> goEvidenceMap = accessionGoEvidences.get(accession);
        if (goEvidenceMap == null) {
            return dbRefs;
        }
        return dbRefs.stream()
                .map(val -> convert(val, goEvidenceMap))
                .collect(Collectors.toList());

    }

    private UniProtDBCrossReference convert(UniProtDBCrossReference xref, Map<String, List<Evidence>> goEvidenceMap) {
        if (xref.getDatabaseType().getName().equals("GO")) {
            String id = xref.getId();
            List<Evidence> evidences = goEvidenceMap.get(id);
            if ((evidences == null) || (evidences.isEmpty())) {
                return xref;
            }
            return new UniProtDBCrossReferenceBuilder()
                    .databaseType(xref.getDatabaseType())
                    .id(xref.getId())
                    .isoformId(xref.getIsoformId())
                    .evidences(evidences)
                    .properties(xref.getProperties())
                    .build();
        }
        return xref;
    }

    private void clear() {
        ccLineConverter.clear();
        deLineConverter.clear();
        ftLineConverter.clear();
        gnLineConverter.clear();
        kwLineConverter.clear();
        ogLineConverter.clear();
        oxLineConverter.clear();
        refObjConverter.clear();
        drLineConverter.clear();
    }

    private List<Evidence> buildEvidences(List<Evidence> ssEvidences) {
        List<Evidence> evidences = new ArrayList<>();
        Collection<Evidence> evidenceIds = new HashSet<>();
        evidenceIds.addAll(ccLineConverter.getEvidences());
        evidenceIds.addAll(deLineConverter.getEvidences());
        evidenceIds.addAll(ftLineConverter.getEvidences());
        evidenceIds.addAll(gnLineConverter.getEvidences());
        evidenceIds.addAll(kwLineConverter.getEvidences());
        evidenceIds.addAll(ogLineConverter.getEvidences());
        evidenceIds.addAll(oxLineConverter.getEvidences());
        evidenceIds.addAll(refObjConverter.getEvidences());
        evidenceIds.addAll(drLineConverter.getEvidences());
        Collections.sort(new ArrayList<>(evidenceIds));

        if ((ssEvidences == null) || (ssEvidences.isEmpty())) {
            evidences.addAll(evidenceIds);
        } else {
            evidences.addAll(ssEvidences);
        }

        return evidences;
    }
}
