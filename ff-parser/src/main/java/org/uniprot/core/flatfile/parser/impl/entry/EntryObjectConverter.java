package org.uniprot.core.flatfile.parser.impl.entry;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineConverter;
import org.uniprot.core.flatfile.parser.impl.ac.UniProtAcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineConverter;
import org.uniprot.core.flatfile.parser.impl.dr.UniProtDrObjects;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineConverter;
import org.uniprot.core.flatfile.parser.impl.id.IdLineConverter;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineConverter;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineConverter;
import org.uniprot.core.flatfile.parser.impl.og.OgLineConverter;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineConverter;
import org.uniprot.core.flatfile.parser.impl.os.OsLineConverter;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineConverter;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineConverter;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineConverter;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineConverter;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.builder.InternalSectionBuilder;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class EntryObjectConverter implements Converter<EntryObject, UniProtEntry>{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1548724347898352705L;
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
    private final Map<String, Map<String, List<Evidence>>> accessionGoEvidences;

    public EntryObjectConverter(SupportingDataMap supportingDataMap, boolean ignoreWrong) {
        drLineConverter = new DrLineConverter(ignoreWrong);
        kwLineConverter = new KwLineConverter(supportingDataMap.getKeywordMap(), ignoreWrong);
        ccLineConverter = new CcLineConverter(supportingDataMap.getDiseaseMap(), supportingDataMap.getSubcellularLocationMap(), ignoreWrong);
        this.accessionGoEvidences = supportingDataMap.getGoEvidencesMap();
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