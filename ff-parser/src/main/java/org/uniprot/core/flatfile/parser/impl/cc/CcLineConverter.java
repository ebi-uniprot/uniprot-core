package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.exception.ParseDiseaseException;
import org.uniprot.core.flatfile.parser.exception.ParseSubcellularLocationException;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.*;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.Disease;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.FreeText;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.Interaction;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueBuilder;
import org.uniprot.cv.evidence.EvidenceHelper;

import com.google.common.base.Strings;

public class CcLineConverter extends EvidenceCollector
        implements Converter<CcLineObject, List<Comment>> {
    /** */
    private static final long serialVersionUID = 9161643642138549927L;
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
        for (CC cc : f.getCcs()) {
            if (cc.getTopic() == CC.CCTopicEnum.SEQUENCE_CAUTION) {
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
            CC cc, Map<Object, List<Evidence>> evidenceMap) {

        List<SequenceCautionComment> comments = new ArrayList<>();
        if (cc.getTopic() != CC.CCTopicEnum.SEQUENCE_CAUTION) {
            return comments;
        }
        SequenceCaution seqC = (SequenceCaution) cc.getObject();
        for (SequenceCautionObject cObj : seqC.getSequenceCautionObjects()) {
            SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
            builder.sequenceCautionType(convertSequenceCautionType(cObj.getType()));
            if (seqC.getMolecule() != null) {
                builder.molecule(seqC.getMolecule());
            }
            if ((cObj.getNote() != null) && (!cObj.getNote().isEmpty())) {
                builder.note(cObj.getNote());
            }
            if (cObj.getSequence() != null) {
                builder.sequence(cObj.getSequence());
            }
            List<Evidence> evidences = evidenceMap.get(cObj);
            builder.evidencesSet(evidences);
            SequenceCautionComment comment = builder.build();
            comments.add(comment);
        }
        return comments;
    }

    private SequenceCautionType convertSequenceCautionType(
            SequenceCautionObject.SequenceCautionType type) {
        switch (type) {
            case FRAMESHIFT:
                return SequenceCautionType.FRAMESHIFT;
            case ERRONEOUS_INITIATION:
                return SequenceCautionType.ERRONEOUS_INITIATION;
            case ERRONEOUS_TERMINATION:
                return SequenceCautionType.ERRONEOUS_TERMINATION;
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
    private <T extends Comment> T convert(CC cc, Map<Object, List<Evidence>> evidences) {
        CC.CCTopicEnum topic = cc.getTopic();

        CommentType type = convert(topic);
        T comment = null;
        switch (topic) {
            case ALTERNATIVE_PRODUCTS:
                comment =
                        (T)
                                convertAlternativeProduct(
                                        (AlternativeProducts) cc.getObject(), evidences);
                break;
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                comment =
                        (T)
                                convertBiophyChem(
                                        (BiophysicochemicalProperties) cc.getObject(), evidences);
                break;
            case WEB_RESOURCE:
                comment = (T) convertWebResource((WebResource) cc.getObject());
                break;
            case INTERACTION:
                comment = (T) convertInteraction((Interaction) cc.getObject());
                break;
            case DISEASE:
                comment = (T) convertDisease((Disease) cc.getObject(), evidences);
                break;
            case MASS_SPECTROMETRY:
                comment = (T) convertMassSpectrometry((MassSpectrometry) cc.getObject(), evidences);
                break;
            case SUBCELLULAR_LOCATION:
                comment =
                        (T)
                                convertSubcellularLocation(
                                        (SubcullarLocation) cc.getObject(), evidences);
                break;
            case RNA_EDITING:
                comment = (T) convertRNAEditing((RnaEditing) cc.getObject(), evidences);
                break;
            case COFACTOR:
                comment = (T) convertCofactor((StructuredCofactor) cc.getObject(), evidences);
                break;
            case CATALYTIC_ACTIVITY:
                comment =
                        (T) convertCatalyticActivity((CatalyticActivity) cc.getObject(), evidences);
                break;
            default:
                comment = (T) convertTextOnly(type, (FreeText) cc.getObject(), evidences);
        }

        return comment;
    }

    private <T> boolean isNotEmpty(List<T> value) {
        return (value != null) && !value.isEmpty();
    }

    private AlternativeProductsComment convertAlternativeProduct(
            AlternativeProducts cObj, Map<Object, List<Evidence>> evidences) {

        AlternativeProductsCommentBuilder builder = new AlternativeProductsCommentBuilder();
        builder.eventsSet(
                cObj.getEvents().stream().map(APEventType::typeOf).collect(Collectors.toList()));
        if (isNotEmpty(cObj.getComment())) {
            builder.note(new NoteBuilder(convert(cObj.getComment())).build());
        }
        builder.isoformsSet(
                cObj.getNames().stream().map(this::convertAPIsoform).collect(Collectors.toList()));
        return builder.build();
    }

    private APIsoform convertAPIsoform(AlternativeProductName name) {
        APIsoformBuilder builder = new APIsoformBuilder();
        builder.name(
                new IsoformNameBuilder(
                                name.getName().getValue(),
                                parseEvidenceLines(name.getName().getEvidences()))
                        .build());
        if (isNotEmpty(name.getNote())) {
            builder.note(new NoteBuilder(convert(name.getNote())).build());
        }
        builder.synonymsSet(
                        name.getSynNames().stream()
                                .map(
                                        syn ->
                                                new IsoformNameBuilder(
                                                                syn.getValue(),
                                                                parseEvidenceLines(
                                                                        syn.getEvidences()))
                                                        .build())
                                .collect(Collectors.toList()))
                .isoformIdsSet(name.getIsoId())
                .sequenceIdsSet(name.getSequenceFTId())
                .sequenceStatus(convertIsoformSequenceStatus(name.getSequenceEnum()));
        return builder.build();
    }

    private IsoformSequenceStatus convertIsoformSequenceStatus(
            AlternativeProductName.AlternativeNameSequenceEnum type) {
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
            BiophysicochemicalProperties cObj, Map<Object, List<Evidence>> evidences) {

        // has Kinetic parameter
        // CC Kinetic parameters:
        // CC KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
        // CC Celsius and at pH 7.6);
        // CC Vmax=1.9 mmol/min/mg enzyme;
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        if (cObj.getMolecule() != null) {
            builder.molecule(cObj.getMolecule());
        }

        if (isNotEmpty(cObj.getKms())
                || isNotEmpty(cObj.getVmaxs())
                || isNotEmpty(cObj.getKpNote())) {
            List<MichaelisConstant> mcs = new ArrayList<>();
            cObj.getKms().stream()
                    .map(this::convertMichaelisConstant)
                    .forEach(
                            km -> {
                                this.add(km.getEvidences());
                                mcs.add(km);
                            });
            List<MaximumVelocity> mvs = new ArrayList<>();
            cObj.getVmaxs().stream()
                    .map(this::convertMaximumVelocity)
                    .forEach(
                            mv -> {
                                this.add(mv.getEvidences());
                                mvs.add(mv);
                            });
            Note note = null;
            if (isNotEmpty(cObj.getKpNote())) {
                note = new NoteBuilder(convert(cObj.getKpNote())).build();
            }
            builder.kineticParameters(
                    new KineticParametersBuilder()
                            .maximumVelocitiesSet(mvs)
                            .michaelisConstantsSet(mcs)
                            .note(note)
                            .build());
        }
        if (isNotEmpty(cObj.getPhDependence())) {
            builder.phDependence(new PhDependenceBuilder(convert(cObj.getPhDependence())).build());
        }
        if (isNotEmpty(cObj.getRdoxPotential())) {
            builder.redoxPotential(
                    new RedoxPotentialBuilder(convert(cObj.getRdoxPotential())).build());
        }
        if (isNotEmpty(cObj.getTemperatureDependence())) {
            builder.temperatureDependence(
                    new TemperatureDependenceBuilder(convert(cObj.getTemperatureDependence()))
                            .build());
        }
        if (cObj.getBsorptionAbs() != null) {
            String abs = cObj.getBsorptionAbs().getValue();
            int index = abs.indexOf(' ');
            if (index != -1) abs = abs.substring(0, index).trim();
            Note note = null;
            if (isNotEmpty(cObj.getBsorptionNote())) {
                note = new NoteBuilder(convert(cObj.getBsorptionNote())).build();
            }
            Absorption absorption =
                    new AbsorptionBuilder()
                            .max(Integer.parseInt(abs))
                            .note(note)
                            .approximate(cObj.isBsorptionAbsApproximate())
                            .evidencesSet(parseEvidenceLines(cObj.getBsorptionAbs().getEvidences()))
                            .build();

            builder.absorption(absorption);
        }
        return builder.build();
    }

    private MichaelisConstant convertMichaelisConstant(EvidencedString kmEvStr) {
        String kmStr = kmEvStr.getValue();
        // MichaelisConstant km = factory.buildMichaelisConstant();
        int index = kmStr.indexOf(' ');
        String val = kmStr.substring(0, index).trim();
        kmStr = kmStr.substring(index + 1).trim();
        index = kmStr.indexOf(' ');
        String unit = kmStr.substring(0, index).trim();
        kmStr = kmStr.substring(index + 5).trim();
        double value = Double.parseDouble(val);
        return new MichaelisConstantBuilder()
                .evidencesSet(parseEvidenceLines(kmEvStr.getEvidences()))
                .constant(value)
                .unit(MichaelisConstantUnit.convert(unit))
                .substrate(kmStr)
                .build();
    }

    private MaximumVelocity convertMaximumVelocity(EvidencedString vmaxEvStr) {
        String vmaxStr = vmaxEvStr.getValue();
        int index = vmaxStr.indexOf(' ');
        String val = vmaxStr.substring(0, index).trim();

        vmaxStr = vmaxStr.substring(index + 1).trim();
        index = vmaxStr.indexOf(' ');
        String unit = vmaxStr.substring(0, index).trim();
        vmaxStr = vmaxStr.substring(index + 1).trim();
        double value = Double.parseDouble(val);
        return new MaximumVelocityBuilder()
                .enzyme(vmaxStr)
                .evidencesSet(parseEvidenceLines(vmaxEvStr.getEvidences()))
                .unit(unit)
                .velocity(value)
                .build();
    }

    private WebResourceComment convertWebResource(WebResource cObj) {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        builder.resourceName(cObj.getName())
                .note(cObj.getNote())
                .resourceUrl(cObj.getUrl())
                .molecule(cObj.getMolecule());
        if (cObj.getUrl() != null) builder.isFtp(cObj.getUrl().startsWith("ftp"));
        return builder.build();
    }

    private InteractionComment convertInteraction(Interaction cObj) {

        List<org.uniprot.core.uniprot.comment.Interaction> interactions = new ArrayList<>();
        for (InteractionObject io : cObj.getInteractions()) {
            InteractionBuilder builder = new InteractionBuilder();

            if (!io.isSelf()) {
                builder.uniProtAccession(io.getSpAc());
            }
            if (!Strings.isNullOrEmpty(io.getGene())) {
                builder.geneName(io.getGene());
            }
            if (io.isXeno()) builder.interactionType(InteractionType.XENO);
            else if (io.isSelf()) {
                builder.interactionType(InteractionType.SELF);
            } else {
                builder.interactionType(InteractionType.BINARY);
            }
            builder.firstInteractor(io.getFirstId());
            if (!Strings.isNullOrEmpty(io.getSecondId())) {
                builder.secondInteractor(io.getSecondId());
            }
            builder.numberOfExperiments(io.getNbexp());

            interactions.add(builder.build());
        }
        return new InteractionCommentBuilder().interactionsSet(interactions).build();
    }

    private DiseaseComment convertDisease(Disease cObj, Map<Object, List<Evidence>> evidences) {
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();
        if (!Strings.isNullOrEmpty(cObj.getMolecule())) {
            commentBuilder.molecule(cObj.getMolecule());
        }

        DiseaseBuilder builder = new DiseaseBuilder();

        if (!Strings.isNullOrEmpty(cObj.getName())) {
            builder.diseaseId(cObj.getName());
            String disease = diseaseMap.getOrDefault(cObj.getName(), "");
            if (!ignoreWrong && disease.isEmpty()) {
                throw new ParseDiseaseException(
                        cObj.getName() + " does not match any humdisease entry");
            }
            builder.diseaseAc(disease);
            if (!Strings.isNullOrEmpty(cObj.getAbbr())) {
                builder.acronym(cObj.getAbbr());
            }
            if (!Strings.isNullOrEmpty(cObj.getMim())) {
                builder.diseaseCrossReference(
                        new CrossReferenceBuilder<DiseaseDatabase>()
                                .database(DiseaseDatabase.MIM)
                                .id(cObj.getMim())
                                .build());
            }
            if (!Strings.isNullOrEmpty(cObj.getDescription())) {
                String descr = cObj.getDescription();
                if (!descr.endsWith(".")) descr += ".";
                builder.description(descr).evidencesSet(evidences.get(cObj.getDescription()));
            }
            commentBuilder.disease(builder.build());
        }
        if (isNotEmpty(cObj.getNote())) {
            commentBuilder.note(new NoteBuilder(convert(cObj.getNote())).build());
        }
        return commentBuilder.build();
    }

    private SubcellularLocationComment convertSubcellularLocation(
            SubcullarLocation cObj, Map<Object, List<Evidence>> evidences) {
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        if (cObj.getMolecule() != null) {
            builder.molecule(cObj.getMolecule());
        }
        List<SubcellularLocation> locations = new ArrayList<SubcellularLocation>();
        for (LocationObject lo : cObj.getLocations()) {
            locations.add(
                    new SubcellularLocationBuilder()
                            .location(
                                    createSubcellularLocationValue(
                                            lo, lo.getSubcellularLocation(), evidences))
                            .topology(
                                    createSubcellularLocationValue(lo, lo.getTopology(), evidences))
                            .orientation(
                                    createSubcellularLocationValue(
                                            lo, lo.getOrientation(), evidences))
                            .build());
        }
        builder.subcellularLocationsSet(locations);

        if (cObj.getNote() != null && !cObj.getNote().isEmpty()) {
            builder.note(new NoteBuilder(convert(cObj.getNote())).build());
        }
        return builder.build();
    }

    private SubcellularLocationValue createSubcellularLocationValue(
            LocationObject lo,
            LocationValue locationValue,
            Map<Object, List<Evidence>> evidenceMap) {
        if ((locationValue == null) || locationValue.getValue().isEmpty()) {
            return null;
        }

        String subcellLocation = subcellularLocationMap.getOrDefault(locationValue.getValue(), "");
        if (!ignoreWrong && subcellLocation.isEmpty()) {
            throw new ParseSubcellularLocationException(
                    locationValue.getValue() + " does not match any subcellular Location entry");
        }
        List<Evidence> evidences = evidenceMap.get(locationValue);
        if (evidences == null) {
            evidences = evidenceMap.get(lo);
        }
        return new SubcellularLocationValueBuilder()
                .id(subcellLocation)
                .value(locationValue.getValue())
                .evidencesSet(evidenceMap.get(locationValue))
                .build();
    }

    private MassSpectrometryComment convertMassSpectrometry(
            MassSpectrometry cObj, Map<Object, List<Evidence>> evidenceMap) {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        if (cObj.getMolecule() != null) {
            builder.molecule(cObj.getMolecule());
        }

        Float mass = cObj.getMass();
        Float massError = cObj.getMassError();
        builder.method(MassSpectrometryMethod.toType(cObj.getMethod()))
                .molWeight(mass)
                .molWeightError(massError);

        if (!Strings.isNullOrEmpty(cObj.getNote())) {
            builder.note(cObj.getNote());
        }

        builder.evidencesSet(
                cObj.getSources().stream()
                        .map(EvidenceHelper::parseEvidenceLine)
                        .collect(Collectors.toList()));

        return builder.build();
    }

    private RnaEditingComment convertRNAEditing(
            RnaEditing cObj, Map<Object, List<Evidence>> evidences) {
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        if (cObj.getMolecule() != null) {
            builder.molecule(cObj.getMolecule());
        }

        if (cObj.getLocations().isEmpty()) {
            if (cObj.getLocationEnum() == RnaEditing.RnaEditingLocationEnum.UNDETERMINED) {
                builder.locationType(RnaEditingLocationType.Undetermined);

            } else if (cObj.getLocationEnum() == RnaEditing.RnaEditingLocationEnum.NOT_APPLICABLE) {
                builder.locationType(RnaEditingLocationType.Not_applicable);
            }
        } else {
            builder.locationType(RnaEditingLocationType.Known);
            builder.positionsSet(
                    cObj.getLocations().stream()
                            .map(pos -> convertRNAEditingPosition(pos, evidences))
                            .collect(Collectors.toList()));
        }
        if (isNotEmpty(cObj.getNote())) {
            builder.note(new NoteBuilder(convert(cObj.getNote(), true)).build());
        }
        return builder.build();
    }

    private RnaEdPosition convertRNAEditingPosition(
            int pos, Map<Object, List<Evidence>> evidences) {
        String spos = "" + pos;
        return new RnaEditingPositionBuilder()
                .position(spos)
                .evidencesSet(evidences.get(spos))
                .build();
    }

    private FreeTextComment convertTextOnly(
            CommentType commentType, FreeText cObj, Map<Object, List<Evidence>> evidences) {
        List<EvidencedValue> cTexts = new ArrayList<>();
        String molecule = null;
        boolean first = true;
        for (EvidencedString val : cObj.getTexts()) {
            String value = val.getValue();
            if (first) {
                first = false;
                List<String> result = CcLineUtils.parseFreeText(value);
                value = result.get(1);
                molecule = result.get(0);
            }
            List<Evidence> txtEvidences = parseEvidenceLines(val.getEvidences());
            cTexts.add(new EvidencedValueBuilder(value, txtEvidences).build());
        }
        return new FreeTextCommentBuilder()
                .commentType(commentType)
                .textsSet(cTexts)
                .molecule(molecule)
                .build();
    }

    private CofactorComment convertCofactor(
            StructuredCofactor cobj, Map<Object, List<Evidence>> evidences) {
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        if (cobj.getMolecule() != null) {
            builder.molecule(cobj.getMolecule());
        }
        if ((cobj.getNote() != null) && (!cobj.getNote().isEmpty())) {
            builder.note(new NoteBuilder(convert(cobj.getNote())).build());
        }
        if (cobj.getCofactors() != null) {
            builder.cofactorsSet(
                    cobj.getCofactors().stream()
                            .map(item -> convertCofactor(item, evidences))
                            .collect(Collectors.toList()));
        }
        return builder.build();
    }

    private Cofactor convertCofactor(CofactorItem item, Map<Object, List<Evidence>> evidenceMap) {
        List<Evidence> evidences = evidenceMap.get(item);
        return new CofactorBuilder()
                .name(item.getName())
                .cofactorCrossReference(createCofactorReference(item.getXref()))
                .evidencesSet(evidences)
                .build();
    }

    private CrossReference<CofactorDatabase> createCofactorReference(String val) {
        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        return new CrossReferenceBuilder<CofactorDatabase>()
                .id(id)
                .database(CofactorDatabase.typeOf(type))
                .build();
    }

    private List<EvidencedValue> convert(List<EvidencedString> val, boolean trimTrailStop) {
        return val.stream()
                .map(evStr -> convertEvidencedValue(evStr, trimTrailStop))
                .collect(Collectors.toList());
    }

    private EvidencedValue convertEvidencedValue(EvidencedString evStr, boolean trimTrailStop) {
        String evVal = evStr.getValue().trim();
        if (trimTrailStop) {
            evVal = stripTrailing(evStr.getValue().trim(), STOP);
        }
        List<Evidence> evidences = parseEvidenceLines(evStr.getEvidences());
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
        Reaction reaction = convertReaction(object.getReaction(), evidences);
        List<PhysiologicalReaction> physiologicalReactions =
                object.getPhysiologicalDirections().stream()
                        .map(val -> convertPhysiologicalDirection(val, evidences))
                        .collect(Collectors.toList());

        return new CatalyticActivityCommentBuilder()
                .molecule(object.getMolecule())
                .reaction(reaction)
                .physiologicalReactionsSet(physiologicalReactions)
                .build();
    }

    private PhysiologicalReaction convertPhysiologicalDirection(
            CAPhysioDirection capd, Map<Object, List<Evidence>> evidences) {
        CrossReference<ReactionDatabase> reactionReference = null;
        if (capd.getXref() != null) reactionReference = convertReactionReference(capd.getXref());

        return new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.typeOf(capd.getName()))
                .reactionCrossReference(reactionReference)
                .evidencesSet(evidences.get(capd))
                .build();
    }

    private Reaction convertReaction(CAReaction caReaction, Map<Object, List<Evidence>> evidences) {
        List<CrossReference<ReactionDatabase>> xrefs = null;
        ECNumber ecNumber = null;
        if (!Strings.isNullOrEmpty(caReaction.getXref())) {
            xrefs =
                    Arrays.stream(caReaction.getXref().split(", "))
                            .map(this::convertReactionReference)
                            .collect(Collectors.toList());
        }
        if (caReaction.getEc() != null) {
            ecNumber = new ECNumberBuilder(caReaction.getEc()).build();
        }
        return new ReactionBuilder()
                .name(caReaction.getName())
                .reactionCrossReferencesSet(xrefs)
                .ecNumber(ecNumber)
                .evidencesSet(evidences.get(caReaction))
                .build();
    }

    private CrossReference<ReactionDatabase> convertReactionReference(String val) {

        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        return new CrossReferenceBuilder<ReactionDatabase>()
                .database(ReactionDatabase.typeOf(type))
                .id(id)
                .build();
    }

    private CommentType convert(CC.CCTopicEnum topic) {
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
