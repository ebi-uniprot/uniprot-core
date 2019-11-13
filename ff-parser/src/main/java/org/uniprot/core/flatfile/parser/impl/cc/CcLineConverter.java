package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.Range;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.exception.ParseDiseaseException;
import org.uniprot.core.flatfile.parser.exception.ParseSubcellularLocationException;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.*;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.*;
import org.uniprot.core.uniprot.comment.impl.CatalyticActivityCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.uniprot.evidence.impl.EvidenceHelper;

import com.google.common.base.Strings;

public class CcLineConverter extends EvidenceCollector
        implements Converter<CcLineObject, List<Comment>> {
    // private final DefaultCommentFactory factory = DefaultCommentFactory
    // .getInstance();
    private final Map<String, String> diseaseMap;
    private final Map<String, String> subcellularLocationMap;
    private final boolean ignoreWrong;

    public CcLineConverter(
            Map<String, String> diseaseService, Map<String, String> subcellularLocationService) {
        this(diseaseService, subcellularLocationService, true);
    }

    public CcLineConverter(
            Map<String, String> diseaseMap,
            Map<String, String> subcellularLocationMap,
            boolean ignoreWrong) {
        this.diseaseMap = diseaseMap;
        this.subcellularLocationMap = subcellularLocationMap;
        this.ignoreWrong = ignoreWrong;
    }

    private static final String STOP = ".";

    @Override
    public List<Comment> convert(CcLineObject f) {
        Map<Object, List<Evidence>> evidences =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        List<Comment> comments = new ArrayList<>();
        for (CcLineObject.CC cc : f.ccs) {
            if (cc.topic == CcLineObject.CCTopicEnum.SEQUENCE_CAUTION) {
                List<SequenceCautionComment> scComments = convertSequenceCaution(cc, evidences);
                comments.addAll(scComments);
            } else {
                Comment gcomment = convert(cc, evidences);
                comments.add(gcomment);
            }
        }
        return comments;
        //
    }

    private List<SequenceCautionComment> convertSequenceCaution(
            CcLineObject.CC cc, Map<Object, List<Evidence>> evidenceMap) {

        List<SequenceCautionComment> comments = new ArrayList<>();
        if (cc.topic != CcLineObject.CCTopicEnum.SEQUENCE_CAUTION) {
            return comments;
        }
        CcLineObject.SequenceCaution seqC = (CcLineObject.SequenceCaution) cc.object;
        for (CcLineObject.SequenceCautionObject cObj : seqC.sequenceCautionObjects) {
            SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
            builder.sequenceCautionType(convertSequenceCautionType(cObj.type));
            if ((cObj.note != null) && (!cObj.note.isEmpty())) {
                builder.note(cObj.note);
            }
            List<String> positions =
                    cObj.positions.stream().map(Object::toString).collect(Collectors.toList());

            if ((cObj.positions.size() == 0) && (cObj.positionValue != null)) {
                positions.add(cObj.positionValue);
            }
            builder.positions(positions);
            if (cObj.sequence != null) {
                builder.sequence(cObj.sequence);
            }
            List<Evidence> evidences = evidenceMap.get(cObj);
            builder.evidences(evidences);
            SequenceCautionComment comment = builder.build();
            comments.add(comment);
        }
        return comments;
    }

    private SequenceCautionType convertSequenceCautionType(CcLineObject.SequenceCautionType type) {
        switch (type) {
            case FRAMESHIFT:
                return SequenceCautionType.FRAMESHIFT;
            case ERRONEOUS_INITIATION:
                return SequenceCautionType.ERRONEOUS_INITIATION;
            case ERRONEOUS_TERMINATION:
                return SequenceCautionType.ERRONEOUS_TERMIINATION;
            case ERRONEOUS_GENE_MODEL_PREDICTION:
                return SequenceCautionType.ERRONEOUS_PREDICTION;
            case ERRONEOUS_TRANSLATION:
                return SequenceCautionType.ERRONEOUS_TRANSLATION;
            case MISCELLANEOUS_DISCREPANCY:
                return SequenceCautionType.MISCELLANEOUS_DISCREPANCY;
        }
        return SequenceCautionType.UNKNOWN;
    }

    @SuppressWarnings("unchecked")
    private <T extends Comment> T convert(
            CcLineObject.CC cc, Map<Object, List<Evidence>> evidences) {
        CcLineObject.CCTopicEnum topic = cc.topic;

        CommentType type = convert(topic);
        T comment = null;
        switch (topic) {
            case ALTERNATIVE_PRODUCTS:
                comment =
                        (T)
                                convertAlternativeProduct(
                                        (CcLineObject.AlternativeProducts) cc.object, evidences);
                break;
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                comment =
                        (T)
                                convertBiophyChem(
                                        (CcLineObject.BiophysicochemicalProperties) cc.object,
                                        evidences);
                break;
            case WEB_RESOURCE:
                comment = (T) convertWebResource((CcLineObject.WebResource) cc.object);
                break;
            case INTERACTION:
                comment = (T) convertInteraction((CcLineObject.Interaction) cc.object);
                break;
            case DISEASE:
                comment = (T) convertDisease((CcLineObject.Disease) cc.object, evidences);
                break;
            case MASS_SPECTROMETRY:
                comment =
                        (T)
                                convertMassSpectrometry(
                                        (CcLineObject.MassSpectrometry) cc.object, evidences);
                break;
            case SUBCELLULAR_LOCATION:
                comment =
                        (T)
                                convertSubcellularLocation(
                                        (CcLineObject.SubcullarLocation) cc.object, evidences);
                break;
            case RNA_EDITING:
                comment = (T) convertRNAEditing((CcLineObject.RnaEditing) cc.object, evidences);
                break;
            case COFACTOR:
                comment =
                        (T) convertCofactor((CcLineObject.StructuredCofactor) cc.object, evidences);
                break;
            case CATALYTIC_ACTIVITY:
                comment =
                        (T)
                                convertCatalyticActivity(
                                        (CcLineObject.CatalyticActivity) cc.object, evidences);
                break;
            default:
                comment = (T) convertTextOnly(type, (CcLineObject.FreeText) cc.object, evidences);
        }

        return comment;
    }

    private <T> boolean isNotEmpty(List<T> value) {
        return (value != null) && !value.isEmpty();
    }

    private AlternativeProductsComment convertAlternativeProduct(
            CcLineObject.AlternativeProducts cObj, Map<Object, List<Evidence>> evidences) {

        APCommentBuilder builder = new APCommentBuilder();
        builder.events(cObj.events.stream().map(APEventType::typeOf).collect(Collectors.toList()));
        if (isNotEmpty(cObj.comment)) {
            builder.note(new NoteBuilder(convert(cObj.comment)).build());
        }
        builder.isoforms(
                cObj.names.stream().map(this::convertAPIsoform).collect(Collectors.toList()));
        return builder.build();
    }

    private APIsoform convertAPIsoform(CcLineObject.AlternativeProductName name) {
        APIsoformBuilder builder = new APIsoformBuilder();
        builder.name(
                new IsoformNameBuilder(name.name.value, parseEvidenceLines(name.name.evidences))
                        .build());
        if (isNotEmpty(name.note)) {
            builder.note(new NoteBuilder(convert(name.note)).build());
        }
        builder.synonyms(
                        name.synNames.stream()
                                .map(
                                        syn ->
                                                new IsoformNameBuilder(
                                                                syn.value,
                                                                parseEvidenceLines(syn.evidences))
                                                        .build())
                                .collect(Collectors.toList()))
                .ids(name.isoId)
                .sequenceIds(name.sequenceFTId)
                .sequenceStatus(convertIsoformSequenceStatus(name.sequenceEnum));
        return builder.build();
    }

    private IsoformSequenceStatus convertIsoformSequenceStatus(
            CcLineObject.AlternativeNameSequenceEnum type) {
        if (type == null) return IsoformSequenceStatus.DESCRIBED;
        switch (type) {
            case DISPLAYED:
                return IsoformSequenceStatus.DISPLAYED;
            case EXTERNAL:
                return IsoformSequenceStatus.EXTERNAL;
            case NOT_DESCRIBED:
                return IsoformSequenceStatus.NOT_DESCRIBED;
            default:
                return IsoformSequenceStatus.DISPLAYED;
        }
    }

    private BPCPComment convertBiophyChem(
            CcLineObject.BiophysicochemicalProperties cObj, Map<Object, List<Evidence>> evidences) {

        // has Kinetic parameter
        // CC Kinetic parameters:
        // CC KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
        // CC Celsius and at pH 7.6);
        // CC Vmax=1.9 mmol/min/mg enzyme;
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        if (isNotEmpty(cObj.kms) || isNotEmpty(cObj.vmaxs) || isNotEmpty(cObj.kpNote)) {
            List<MichaelisConstant> mcs = new ArrayList<>();
            cObj.kms.stream()
                    .map(this::convertMichaelisConstant)
                    .forEach(
                            km -> {
                                this.add(km.getEvidences());
                                mcs.add(km);
                            });
            List<MaximumVelocity> mvs = new ArrayList<>();
            cObj.vmaxs.stream()
                    .map(this::convertMaximumVelocity)
                    .forEach(
                            mv -> {
                                this.add(mv.getEvidences());
                                mvs.add(mv);
                            });
            Note note = null;
            if (isNotEmpty(cObj.kpNote)) {
                note = new NoteBuilder(convert(cObj.kpNote)).build();
            }
            builder.kineticParameters(
                    new KineticParametersBuilder()
                            .maximumVelocities(mvs)
                            .michaelisConstants(mcs)
                            .note(note)
                            .build());
        }
        if (isNotEmpty(cObj.phDependence)) {
            builder.phDependence(new PhDependenceBuilder(convert(cObj.phDependence)).build());
        }
        if (isNotEmpty(cObj.rdoxPotential)) {
            builder.redoxPotential(new RedoxPotentialBuilder(convert(cObj.rdoxPotential)).build());
        }
        if (isNotEmpty(cObj.temperatureDependence)) {
            builder.temperatureDependence(
                    new TemperatureDependenceBuilder(convert(cObj.temperatureDependence)).build());
        }
        if (cObj.bsorptionAbs != null) {
            String abs = cObj.bsorptionAbs.value;
            int index = abs.indexOf(' ');
            if (index != -1) abs = abs.substring(0, index).trim();
            Note note = null;
            if (isNotEmpty(cObj.bsorptionNote)) {
                note = new NoteBuilder(convert(cObj.bsorptionNote)).build();
            }
            Absorption absorption =
                    new AbsorptionBuilder()
                            .max(Integer.parseInt(abs))
                            .note(note)
                            .approximate(cObj.bsorptionAbsApproximate)
                            .evidences(parseEvidenceLines(cObj.bsorptionAbs.evidences))
                            .build();

            builder.absorption(absorption);
        }
        return builder.build();
    }

    private MichaelisConstant convertMichaelisConstant(EvidencedString kmEvStr) {
        String kmStr = kmEvStr.value;
        // MichaelisConstant km = factory.buildMichaelisConstant();
        int index = kmStr.indexOf(' ');
        String val = kmStr.substring(0, index).trim();
        kmStr = kmStr.substring(index + 1).trim();
        index = kmStr.indexOf(' ');
        String unit = kmStr.substring(0, index).trim();
        kmStr = kmStr.substring(index + 5).trim();
        double value = Double.parseDouble(val);
        return new MichaelisConstantBuilder()
                .evidences(parseEvidenceLines(kmEvStr.evidences))
                .constant(value)
                .unit(MichaelisConstantUnit.convert(unit))
                .substrate(kmStr)
                .build();
    }

    private MaximumVelocity convertMaximumVelocity(EvidencedString vmaxEvStr) {
        String vmaxStr = vmaxEvStr.value;
        int index = vmaxStr.indexOf(' ');
        String val = vmaxStr.substring(0, index).trim();

        vmaxStr = vmaxStr.substring(index + 1).trim();
        index = vmaxStr.indexOf(' ');
        String unit = vmaxStr.substring(0, index).trim();
        vmaxStr = vmaxStr.substring(index + 1).trim();
        double value = Double.parseDouble(val);
        return new MaximumVelocityBuilder()
                .enzyme(vmaxStr)
                .evidences(parseEvidenceLines(vmaxEvStr.evidences))
                .unit(unit)
                .velocity(value)
                .build();
    }

    private WebResourceComment convertWebResource(CcLineObject.WebResource cObj) {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        builder.resourceName(cObj.name).note(cObj.note).resourceUrl(cObj.url);
        if (cObj.url != null) builder.isFtp(cObj.url.startsWith("ftp"));
        return builder.build();
    }

    private InteractionComment convertInteraction(CcLineObject.Interaction cObj) {

        List<Interaction> interactions = new ArrayList<>();
        for (CcLineObject.InteractionObject io : cObj.interactions) {
            InteractionBuilder builder = new InteractionBuilder();

            if (!io.isSelf) {
                builder.uniProtAccession(io.spAc);
            }
            if (!Strings.isNullOrEmpty(io.gene)) {
                builder.geneName(io.gene);
            }
            if (io.xeno) builder.interactionType(InteractionType.XENO);
            else if (io.isSelf) {
                builder.interactionType(InteractionType.SELF);
            } else {
                builder.interactionType(InteractionType.BINARY);
            }
            builder.firstInteractor(io.firstId);
            if (!Strings.isNullOrEmpty(io.secondId)) {
                builder.secondInteractor(io.secondId);
            }
            builder.numberOfExperiments(io.nbexp);

            interactions.add(builder.build());
        }
        return new InteractionCommentBuilder().interactions(interactions).build();
    }

    private DiseaseComment convertDisease(
            CcLineObject.Disease cObj, Map<Object, List<Evidence>> evidences) {
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();
        DiseaseBuilder builder = new DiseaseBuilder();
        if (!Strings.isNullOrEmpty(cObj.name)) {
            builder.diseaseId(cObj.name);
            String disease = diseaseMap.getOrDefault(cObj.name, "");
            if (!ignoreWrong && disease.isEmpty()) {
                throw new ParseDiseaseException(cObj.name + " does not match any humdisease entry");
            }
            builder.diseaseAc(disease);
            if (!Strings.isNullOrEmpty(cObj.abbr)) {
                builder.acronym(cObj.abbr);
            }
            if (!Strings.isNullOrEmpty(cObj.mim)) {
                builder.reference(
                        new DBCrossReferenceBuilder<DiseaseReferenceType>()
                                .databaseType(DiseaseReferenceType.MIM)
                                .id(cObj.mim)
                                .build());
            }
            if (!Strings.isNullOrEmpty(cObj.description)) {
                String descr = cObj.description;
                if (!descr.endsWith(".")) descr += ".";
                builder.description(descr).evidences(evidences.get(cObj.description));
            }
            commentBuilder.disease(builder.build());
        }
        if (isNotEmpty(cObj.note)) {
            commentBuilder.note(new NoteBuilder(convert(cObj.note)).build());
        }
        return commentBuilder.build();
    }

    private SubcellularLocationComment convertSubcellularLocation(
            CcLineObject.SubcullarLocation cObj, Map<Object, List<Evidence>> evidences) {
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        if (cObj.molecule != null) {
            builder.molecule(cObj.molecule);
        }
        List<SubcellularLocation> locations = new ArrayList<SubcellularLocation>();
        for (CcLineObject.LocationObject lo : cObj.locations) {
            locations.add(
                    new SubcellularLocationBuilder()
                            .location(
                                    createSubcellularLocationValue(
                                            lo, lo.subcellularLocation, evidences))
                            .topology(createSubcellularLocationValue(lo, lo.topology, evidences))
                            .orientation(
                                    createSubcellularLocationValue(lo, lo.orientation, evidences))
                            .build());
        }
        builder.subcellularLocations(locations);

        if (cObj.note != null && !cObj.note.isEmpty()) {
            builder.note(new NoteBuilder(convert(cObj.note)).build());
        }
        return builder.build();
    }

    private SubcellularLocationValue createSubcellularLocationValue(
            LocationObject lo,
            LocationValue locationValue,
            Map<Object, List<Evidence>> evidenceMap) {
        if ((locationValue == null) || locationValue.value.isEmpty()) {
            return null;
        }

        String subcellLocation = subcellularLocationMap.getOrDefault(locationValue.value, "");
        if (!ignoreWrong && subcellLocation.isEmpty()) {
            throw new ParseSubcellularLocationException(
                    locationValue.value + " does not match any subcellular Location entry");
        }
        List<Evidence> evidences = evidenceMap.get(locationValue);
        if (evidences == null) {
            evidences = evidenceMap.get(lo);
        }
        return new SubcellularLocationValueBuilder(
                        subcellLocation, locationValue.value, evidenceMap.get(locationValue))
                .build();
    }

    private MassSpectrometryComment convertMassSpectrometry(
            CcLineObject.MassSpectrometry cObj, Map<Object, List<Evidence>> evidenceMap) {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        Float mass = cObj.mass;
        Float massError = cObj.massError;
        builder.method(MassSpectrometryMethod.toType(cObj.method))
                .molWeight(mass)
                .molWeightError(massError);

        if (!Strings.isNullOrEmpty(cObj.note)) {
            builder.note(cObj.note);
        }

        builder.evidences(
                cObj.sources.stream()
                        .map(EvidenceHelper::parseEvidenceLine)
                        .collect(Collectors.toList()));

        builder.ranges(
                cObj.ranges.stream()
                        .map(this::convertMassSpectrometryRange)
                        .collect(Collectors.toList()));
        return builder.build();
    }

    private MassSpectrometryRange convertMassSpectrometryRange(
            CcLineObject.MassSpectrometryRange mrange) {
        int start;
        int end;
        if (mrange.startUnknown) {
            start = -1;
        } else start = mrange.start;
        if (mrange.endUnknown) {
            end = -1;
        } else end = mrange.end;
        return new MassSpectrometryRangeBuilder()
                .range(new Range(start, end))
                .isoformId(mrange.rangeIsoform)
                .build();
    }

    private RnaEditingComment convertRNAEditing(
            CcLineObject.RnaEditing cObj, Map<Object, List<Evidence>> evidences) {
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        if (cObj.locations.isEmpty()) {
            if (cObj.locationEnum == RnaEditingLocationEnum.UNDETERMINED) {
                builder.locationType(RnaEditingLocationType.Undetermined);

            } else if (cObj.locationEnum == RnaEditingLocationEnum.NOT_APPLICABLE) {
                builder.locationType(RnaEditingLocationType.Not_applicable);
            }
        } else {
            builder.locationType(RnaEditingLocationType.Known);
            builder.positions(
                    cObj.locations.stream()
                            .map(pos -> convertRNAEditingPosition(pos, evidences))
                            .collect(Collectors.toList()));
        }
        if (isNotEmpty(cObj.note)) {
            builder.note(new NoteBuilder(convert(cObj.note, true)).build());
        }
        return builder.build();
    }

    private RnaEdPosition convertRNAEditingPosition(
            int pos, Map<Object, List<Evidence>> evidences) {
        String spos = "" + pos;
        return new RnaEditingPositionBuilder().position(spos).evidences(evidences.get(spos)).build();
    }

    private FreeTextComment convertTextOnly(
            CommentType commentType,
            CcLineObject.FreeText cObj,
            Map<Object, List<Evidence>> evidences) {
        List<EvidencedValue> texts = convert(cObj.texts);
        return new FreeTextCommentBuilder().commentType(commentType).texts(texts).build();
    }

    private CofactorComment convertCofactor(
            CcLineObject.StructuredCofactor cobj, Map<Object, List<Evidence>> evidences) {
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        if (cobj.molecule != null) {
            builder.molecule(cobj.molecule);
        }
        if ((cobj.note != null) && (!cobj.note.isEmpty())) {
            builder.note(new NoteBuilder(convert(cobj.note)).build());
        }
        if (cobj.cofactors != null) {
            builder.cofactors(
                    cobj.cofactors.stream()
                            .map(item -> convertCofactor(item, evidences))
                            .collect(Collectors.toList()));
        }
        return builder.build();
    }

    private Cofactor convertCofactor(CofactorItem item, Map<Object, List<Evidence>> evidenceMap) {
        List<Evidence> evidences = evidenceMap.get(item);
        return new CofactorBuilder()
                .name(item.name)
                .reference(createCofactorReference(item.xref))
                .evidences(evidences)
                .build();
    }

    private DBCrossReference<CofactorReferenceType> createCofactorReference(String val) {
        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        return new DBCrossReferenceBuilder<CofactorReferenceType>()
                .id(id)
                .databaseType(CofactorReferenceType.typeOf(type))
                .build();
    }

    private List<EvidencedValue> convert(List<EvidencedString> val, boolean trimTrailStop) {
        return val.stream()
                .map(evStr -> convertEvidencedValue(evStr, trimTrailStop))
                .collect(Collectors.toList());
    }

    private EvidencedValue convertEvidencedValue(EvidencedString evStr, boolean trimTrailStop) {
        String evVal = evStr.value.trim();
        if (trimTrailStop) {
            evVal = stripTrailing(evStr.value.trim(), STOP);
        }
        List<Evidence> evidences = parseEvidenceLines(evStr.evidences);
        return new EvidencedValueBuilder(evVal, evidences).build();
    }

    private String stripTrailing(String val, String trail) {
        if (val.endsWith(trail)) return val.substring(0, val.length() - trail.length());
        else return val;
    }

    private List<EvidencedValue> convert(List<EvidencedString> val) {
        return convert(val, false);
    }

    private CatalyticActivityComment convertCatalyticActivity(
            CatalyticActivity object, Map<Object, List<Evidence>> evidences) {
        Reaction reaction = convertReaction(object.reaction, evidences);
        List<PhysiologicalReaction> physiologicalReactions =
                object.physiologicalDirections.stream()
                        .map(val -> convertPhysiologicalDirection(val, evidences))
                        .collect(Collectors.toList());

        return new CatalyticActivityCommentImpl(reaction, physiologicalReactions);
    }

    private PhysiologicalReaction convertPhysiologicalDirection(
            CAPhysioDirection capd, Map<Object, List<Evidence>> evidences) {
        DBCrossReference<ReactionReferenceType> reactionReference = null;
        if (capd.xref != null) reactionReference = convertReactionReference(capd.xref);

        return new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.typeOf(capd.name))
                .reactionReference(reactionReference)
                .evidences(evidences.get(capd))
                .build();
    }

    private Reaction convertReaction(CAReaction caReaction, Map<Object, List<Evidence>> evidences) {
        List<DBCrossReference<ReactionReferenceType>> xrefs = null;
        ECNumber ecNumber = null;
        if (!Strings.isNullOrEmpty(caReaction.xref)) {
            xrefs =
                    Arrays.stream(caReaction.xref.split(", "))
                            .map(this::convertReactionReference)
                            .collect(Collectors.toList());
        }
        if (caReaction.ec != null) {
            ecNumber = new ECNumberImpl(caReaction.ec);
        }
        return new ReactionBuilder()
                .name(caReaction.name)
                .references(xrefs)
                .ecNumber(ecNumber)
                .evidences(evidences.get(caReaction))
                .build();
    }

    private DBCrossReference<ReactionReferenceType> convertReactionReference(String val) {

        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        return new DBCrossReferenceImpl<>(ReactionReferenceType.typeOf(type), id);
    }

    private CommentType convert(CcLineObject.CCTopicEnum topic) {
        CommentType type = CommentType.UNKNOWN;
        switch (topic) {
            case ALLERGEN:
                type = CommentType.ALLERGEN;
                break;
            case BIOTECHNOLOGY:
                type = CommentType.BIOTECHNOLOGY;
                break;
            case CATALYTIC_ACTIVITY:
                type = CommentType.CATALYTIC_ACTIVITY;
                break;
            case CAUTION:
                type = CommentType.CAUTION;
                break;
            case COFACTOR:
                type = CommentType.COFACTOR;
                break;
            case DEVELOPMENTAL_STAGE:
                type = CommentType.DEVELOPMENTAL_STAGE;
                break;
            case DISEASE:
                type = CommentType.DISEASE;
                break;
            case DISRUPTION_PHENOTYPE:
                type = CommentType.DISRUPTION_PHENOTYPE;
                break;
            case DOMAIN:
                type = CommentType.DOMAIN;
                break;
            case ACTIVITY_REGULATION:
                type = CommentType.ACTIVITY_REGULATION;
                break;
            case FUNCTION:
                type = CommentType.FUNCTION;
                break;
            case INDUCTION:
                type = CommentType.INDUCTION;
                break;
            case MISCELLANEOUS:
                type = CommentType.MISCELLANEOUS;
                break;
            case PATHWAY:
                type = CommentType.PATHWAY;
                break;
            case PHARMACEUTICAL:
                type = CommentType.PHARMACEUTICAL;
                break;
            case POLYMORPHISM:
                type = CommentType.POLYMORPHISM;
                break;
            case PTM:
                type = CommentType.PTM;
                break;
            case SIMILARITY:
                type = CommentType.SIMILARITY;
                break;
            case SUBUNIT:
                type = CommentType.SUBUNIT;
                break;
            case TISSUE_SPECIFICITY:
                type = CommentType.TISSUE_SPECIFICITY;
                break;
            case TOXIC_DOSE:
                type = CommentType.TOXIC_DOSE;
                break;
            case ALTERNATIVE_PRODUCTS:
                type = CommentType.ALTERNATIVE_PRODUCTS;
                break;
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                type = CommentType.BIOPHYSICOCHEMICAL_PROPERTIES;
                break;
            case WEB_RESOURCE:
                type = CommentType.WEBRESOURCE;
                break;
            case INTERACTION:
                type = CommentType.INTERACTION;
                break;
            case SUBCELLULAR_LOCATION:
                type = CommentType.SUBCELLULAR_LOCATION;
                break;
            case MASS_SPECTROMETRY:
                type = CommentType.MASS_SPECTROMETRY;
                break;
            case RNA_EDITING:
                type = CommentType.RNA_EDITING;
                break;
            default:
                type = CommentType.UNKNOWN;
        }
        return type;
    }
}
