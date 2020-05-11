package org.uniprot.core.flatfile.parser.impl.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineConverter;
import org.uniprot.core.flatfile.parser.impl.ac.UniProtKBAcLineObject;
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
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.InternalSectionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class EntryObjectConverter implements Converter<EntryObject, UniProtKBEntry> {

    /** */
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
        ccLineConverter =
                new CcLineConverter(
                        supportingDataMap.getDiseaseMap(),
                        supportingDataMap.getSubcellularLocationMap(),
                        ignoreWrong);
        this.accessionGoEvidences = supportingDataMap.getGoEvidencesMap();
    }

    @Override
    public UniProtKBEntry convert(EntryObject f) {
        clear();
        Map.Entry<UniProtKBId, UniProtKBEntryType> ids = idLineConverter.convert(f.id);
        UniProtKBAcLineObject acLineObj = acLineConverter.convert(f.ac);
        UniProtKBEntryBuilder activeEntryBuilder =
                new UniProtKBEntryBuilder(
                                acLineObj.getPrimaryAccession(), ids.getKey(), ids.getValue())
                        .secondaryAccessionsSet(acLineObj.getSecondAccessions())
                        .entryAudit(dtLineConverter.convert(f.dt));
        if (f.cc != null) activeEntryBuilder.commentsSet(ccLineConverter.convert(f.cc));
        activeEntryBuilder.proteinDescription(deLineConverter.convert(f.de));
        UniProtDrObjects drObjects = drLineConverter.convert(f.dr);
        activeEntryBuilder.uniProtCrossReferencesSet(
                addGoEvidence(f.ac.primaryAcc, drObjects.drObjects));

        if (f.ft != null) {
            activeEntryBuilder.featuresSet(ftLineConverter.convert(f.ft));
        }
        if (f.gn != null) activeEntryBuilder.genesSet(gnLineConverter.convert(f.gn));

        if (f.kw != null) {
            activeEntryBuilder.keywordsSet(kwLineConverter.convert(f.kw));
        }
        if (f.og != null) {
            activeEntryBuilder.geneLocationsSet(ogLineConverter.convert(f.og));
        }
        if (f.oh != null) {
            activeEntryBuilder.organismHostsSet(ohLineConverter.convert(f.oh));
        }
        OrganismName orgName = osLineConverter.convert(f.os);
        Organism oxLineOrganism = oxLineConverter.convert(f.ox);
        Organism organism =
                new OrganismBuilder()
                        .taxonId(oxLineOrganism.getTaxonId())
                        .evidencesSet(oxLineOrganism.getEvidences())
                        .lineagesSet(ocLineConverter.convert(f.oc))
                        .scientificName(orgName.getScientificName())
                        .commonName(orgName.getCommonName())
                        .synonymsSet(orgName.getSynonyms())
                        .build();
        activeEntryBuilder.organism(organism);
        activeEntryBuilder.proteinExistence(peLineConverter.convert(f.pe));
        activeEntryBuilder.sequence(sqLineConverter.convert(f.sq));
        List<UniProtKBReference> citations = new ArrayList<>();
        for (EntryObject.ReferenceObject refObj : f.ref) {
            citations.add(refObjConverter.convert(refObj));
        }
        activeEntryBuilder.referencesSet(citations);

        InternalSection usl = ssLineConverter.convert(f.ss);

        if (drObjects.ssProsites != null) {
            List<InternalLine> internalLines = new ArrayList<>(drObjects.ssProsites);
            if (usl != null) internalLines.addAll(usl.getInternalLines());
            if (usl != null)
                activeEntryBuilder.internalSection(
                        new InternalSectionBuilder()
                                .evidenceLinesSet(usl.getEvidenceLines())
                                .sourceLinesSet(usl.getSourceLines())
                                .internalLinesSet(internalLines)
                                .build());
            else {
                activeEntryBuilder.internalSection(
                        new InternalSectionBuilder().internalLinesSet(internalLines).build());
            }
        } else {
            activeEntryBuilder.internalSection(usl);
        }

        return activeEntryBuilder.build();
    }

    private List<UniProtKBCrossReference> addGoEvidence(
            String accession, List<UniProtKBCrossReference> dbRefs) {
        Map<String, List<Evidence>> goEvidenceMap = accessionGoEvidences.get(accession);
        if (goEvidenceMap == null) {
            return dbRefs;
        }
        return dbRefs.stream().map(val -> convert(val, goEvidenceMap)).collect(Collectors.toList());
    }

    private UniProtKBCrossReference convert(
            UniProtKBCrossReference xref, Map<String, List<Evidence>> goEvidenceMap) {
        if ("GO".equals(xref.getDatabase().getName())) {
            String id = xref.getId();
            List<Evidence> evidences = goEvidenceMap.get(id);
            if ((evidences == null) || (evidences.isEmpty())) {
                return xref;
            }
            return new UniProtCrossReferenceBuilder()
                    .database(xref.getDatabase())
                    .id(xref.getId())
                    .isoformId(xref.getIsoformId())
                    .evidencesSet(evidences)
                    .propertiesSet(xref.getProperties())
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
