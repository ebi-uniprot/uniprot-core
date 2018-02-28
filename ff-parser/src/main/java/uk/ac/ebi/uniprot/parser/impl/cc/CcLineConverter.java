package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AbsorptionNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comments.KPNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CofactorItem;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.LocationValue;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.RnaEditingLocationEnum;

public class CcLineConverter extends EvidenceCollector implements Converter<CcLineObject, List<Comment>> {
	// private final DefaultCommentFactory factory = DefaultCommentFactory
	// .getInstance();
	private final static String STOP = ".";

	@Override
	public List<Comment> convert(CcLineObject f) {
		Map<Object, List<Evidence>> evidences = EvidenceHelper.convert(f.getEvidenceInfo());
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

	private List<SequenceCautionComment> convertSequenceCaution(CcLineObject.CC cc,
			Map<Object, List<Evidence>> evidenceMap) {

		List<SequenceCautionComment> comments = new ArrayList<>();
		if (cc.topic != CcLineObject.CCTopicEnum.SEQUENCE_CAUTION) {
			return comments;
		}
		CcLineObject.SequenceCaution seqC = (CcLineObject.SequenceCaution) cc.object;
		for (CcLineObject.SequenceCautionObject cObj : seqC.sequenceCautionObjects) {
			SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
			builder.sequenceCautionType(convertSequenceCautionType(cObj.type));
			if ((cObj.note != null) && (!cObj.note.isEmpty())) {
				builder.note(cObj.note);
			}
			List<String> positions = cObj.positions.stream().map(val -> val.toString()).collect(Collectors.toList());

			if ((cObj.positions.size() == 0) && (cObj.positionValue != null)) {
				positions.addAll(cObj.positions.stream().map(val -> val.toString()).collect(Collectors.toList()));
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
		case Frameshift:
			return SequenceCautionType.FRAMESHIFT;
		case Erroneous_initiation:
			return SequenceCautionType.ERRONEOUS_INITIATION;
		case Erroneous_termination:
			return SequenceCautionType.ERRONEOUS_TERMIINATION;
		case Erroneous_gene_model_prediction:
			return SequenceCautionType.ERRONEOUS_PREDICTION;
		case Erroneous_translation:
			return SequenceCautionType.ERRONEOUS_TRANSLATION;
		case Miscellaneous_discrepancy:
			return SequenceCautionType.MISCELLANEOUS_DISCREPANCY;
		}
		return SequenceCautionType.UNKNOWN;
	}

	@SuppressWarnings("unchecked")
	private <T extends Comment> T convert(CcLineObject.CC cc, Map<Object, List<Evidence>> evidences) {
		CcLineObject.CCTopicEnum topic = cc.topic;

		CommentType type = convert(topic);
		T comment = null;
		switch (topic) {
		case ALTERNATIVE_PRODUCTS:
			comment = (T) convertAlternativeProduct((CcLineObject.AlternativeProducts) cc.object, evidences);
			break;
		case BIOPHYSICOCHEMICAL_PROPERTIES:
			comment = (T) convertBiophyChem((CcLineObject.BiophysicochemicalProperties) cc.object, evidences);
			break;
		case WEB_RESOURCE:
			comment = (T) convertWebResource((CcLineObject.WebResource) cc.object);
			break;
		case INTERACTION:
			comment = (T) convertInteraction((CcLineObject.Interaction) cc.object);
			break;
		case DISEASE:
			comment = (T)convertDisease( (CcLineObject.Disease) cc.object, evidences);
			break;
		case MASS_SPECTROMETRY:
			comment =(T)convertMassSpectrometry( (CcLineObject.MassSpectrometry) cc.object,
					evidences);
			break;
		case SUBCELLULAR_LOCATION:
			comment =(T)convertSubcellularLocation( (CcLineObject.SubcullarLocation) cc.object,
					evidences);
			break;
		case RNA_EDITING:
			comment = (T) convertRNAEditing((CcLineObject.RnaEditing) cc.object, evidences);
			break;
		case COFACTOR:
			comment = (T) convertCofactor((CcLineObject.StructuredCofactor) cc.object, evidences);
			break;
		default:
			comment = (T) convertTextOnly(type, (CcLineObject.FreeText) cc.object, evidences);
		}

		return comment;

	}

	private <T> boolean isNotEmpty(List<T> value) {
		return (value != null) && !value.isEmpty();
	}

	private AlternativeProductsComment convertAlternativeProduct(CcLineObject.AlternativeProducts cObj,
			Map<Object, List<Evidence>> evidences) {

		APCommentBuilder builder = APCommentBuilder.newInstance();
		builder.events(cObj.events.stream().map(val -> APCommentBuilder.createEvent(val)).collect(Collectors.toList()));
		if (isNotEmpty(cObj.comment)) {
			builder.note(CommentFactory.INSTANCE.createCommentNote(convert(cObj.comment)));
		}
		builder.isoforms(cObj.names.stream().map(name -> convertAPIsoform(name)).collect(Collectors.toList()));
		return builder.build();

	}

	private APIsoform convertAPIsoform(CcLineObject.AlternativeProductName name) {
		APCommentBuilder.APIsoformBuilder builder = APCommentBuilder.APIsoformBuilder.newInstance();
		builder.isoformName(
				APCommentBuilder.createIsoformName(name.name.value, EvidenceHelper.convert(name.name.evidences)));
		if (isNotEmpty(name.note)) {
			builder.note(APCommentBuilder.createIsoformNote(convert(name.note)));
		}
		builder.isoformSynonyms(name.synNames.stream()
				.map(syn -> APCommentBuilder.createIsoformSynonym(syn.value, EvidenceHelper.convert(syn.evidences))

				).collect(Collectors.toList()));
		builder.isoformIds(
				name.isoId.stream().map(val -> APCommentBuilder.createIsoformId(val)).collect(Collectors.toList()));
		builder.sequenceIds(name.sequence_FTId);
		builder.isoformSequenceStatus(convertIsoformSequenceStatus(name.sequence_enum));
		return builder.build();

	}

	private IsoformSequenceStatus convertIsoformSequenceStatus(CcLineObject.AlternativeNameSequenceEnum type) {
		if (type == null)
			return IsoformSequenceStatus.DESCRIBED;
		switch (type) {
		case Displayed:
			return IsoformSequenceStatus.DISPLAYED;
		case External:
			return IsoformSequenceStatus.EXTERNAL;
		case Not_described:
			return IsoformSequenceStatus.NOT_DESCRIBED;
		default:
			return IsoformSequenceStatus.DISPLAYED;
		}
	}

	private BioPhysicoChemicalPropertiesComment convertBiophyChem(CcLineObject.BiophysicochemicalProperties cObj,
			Map<Object, List<Evidence>> evidences) {

		// has Kinetic parameter
		// CC Kinetic parameters:
		// CC KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
		// CC Celsius and at pH 7.6);
		// CC Vmax=1.9 mmol/min/mg enzyme;
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		if (isNotEmpty(cObj.kms) || isNotEmpty(cObj.vmaxs) || isNotEmpty(cObj.kp_note)) {
			List<MichaelisConstant> mcs = new ArrayList<>();
			cObj.kms.stream().map(kmEvStr -> convertMichaelisConstant(kmEvStr)).forEach(km -> {
				this.add(km.getEvidences());
				mcs.add(km);
			});
			List<MaximumVelocity> mvs = new ArrayList<>();
			cObj.vmaxs.stream().map(vmaxEvStr -> convertMaximumVelocity(vmaxEvStr)).forEach(mv -> {
				this.add(mv.getEvidences());
				mvs.add(mv);
			});
			KPNote note = null;
			if (isNotEmpty(cObj.kp_note)) {
				note = BPCPCommentBuilder.createKPNote(convert(cObj.kp_note));
			}
			builder.kineticParameters(BPCPCommentBuilder.createKineticParameters(mvs, mcs, note));

		}
		if (isNotEmpty(cObj.ph_dependence)) {
			builder.pHDependence(BPCPCommentBuilder.createPHDependence(convert(cObj.ph_dependence)));

		}
		if (isNotEmpty(cObj.rdox_potential)) {
			builder.redoxPotential(BPCPCommentBuilder.createRedoxPotential(convert(cObj.rdox_potential)));
		}
		if (isNotEmpty(cObj.temperature_dependence)) {
			builder.temperatureDependence(
					BPCPCommentBuilder.createTemperatureDependence(convert(cObj.temperature_dependence)));
		}
		if (cObj.bsorption_abs != null) {
			String abs = cObj.bsorption_abs.value;
			int index = abs.indexOf(' ');
			if (index != -1)
				abs = abs.substring(0, index).trim();
			AbsorptionNote note = null;
			if (isNotEmpty(cObj.bsorption_note)) {
				note = BPCPCommentBuilder.createAbsorptionNote(convert(cObj.bsorption_note));
			}
			Absorption absorption = BPCPCommentBuilder.createAbsorption(Integer.parseInt(abs),
					cObj.bsorption_abs_approximate, note, EvidenceHelper.convert(cObj.bsorption_abs.evidences));

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
		return BPCPCommentBuilder.createMichaelisConstant((float) value, MichaelisConstantUnit.convert(unit), kmStr,
				EvidenceHelper.convert(kmEvStr.evidences));
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
		return BPCPCommentBuilder.createMaximumVelocity((float) value, unit, vmaxStr,
				EvidenceHelper.convert(vmaxEvStr.evidences));

	}

	private WebResourceComment convertWebResource(CcLineObject.WebResource cObj) {
		WebResourceCommentBuilder builder = WebResourceCommentBuilder.newInstance();
	//	String databaseName = "someDbName";
	//	String databaseUrl = "some url";
	//	String note = "some note";
		builder.resourceName(cObj.name).note(cObj.note)
		.resourceUrl(cObj.url);
		if(cObj.url !=null)
		builder.isFtp(cObj.url.startsWith("ftp"));
		return builder.build();
	}

	private InteractionComment convertInteraction(CcLineObject.Interaction cObj) {

		List<Interaction> interactions = new ArrayList<>();
		for (CcLineObject.InteractionObject io : cObj.interactions) {
			InteractionBuilder builder = InteractionCommentBuilder.newInteractionBuilder();

			if (!io.isSelf) {
				builder.uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession(io.spAc));
			}
			if (!Strings.isNullOrEmpty(io.gene)) {
				builder.geneName(io.gene);
			}
			if (io.xeno)
				builder.interactionType(InteractionType.XENO);
			else if (io.isSelf) {
				builder.interactionType(InteractionType.SELF);
			} else {
				builder.interactionType(InteractionType.BINARY);
			}
			builder.firstInteractor(InteractionBuilder.createInteractorAccession(io.firstId));
			if (!Strings.isNullOrEmpty(io.secondId)) {
				builder.secondInteractor(InteractionBuilder.createInteractorAccession(io.secondId));
			}
			builder.numberOfExperiments(io.nbexp);

			interactions.add(builder.build());
		}
		InteractionCommentBuilder commentBuilder = InteractionCommentBuilder.newInstance();
		return commentBuilder.interactions(interactions).build();
	}

	private DiseaseComment convertDisease(CcLineObject.Disease cObj,
			Map<Object, List<Evidence>> evidences) {
		 DiseaseCommentBuilder commentBuilder = DiseaseCommentBuilder.newInstance();
		 DiseaseBuilder builder = DiseaseCommentBuilder.newDiseaseBuilder();
		if (!Strings.isNullOrEmpty(cObj.name)) {
			builder.diseaseId(DiseaseBuilder.createDiseaseId(cObj.name));

			if (!Strings.isNullOrEmpty(cObj.abbr)) {
				builder.acronym(cObj.abbr);

			}
			if (!Strings.isNullOrEmpty(cObj.mim)) {
				builder.reference(
						DiseaseBuilder.createDiseaseReference(DiseaseReferenceType.MIM, cObj.mim));
			
			}
			if (!Strings.isNullOrEmpty(cObj.description)) {
				String descr = cObj.description;
				if (!descr.endsWith("."))
					descr += ".";
				builder.description(
				DiseaseBuilder.createDiseaseDescription(descr, evidences.get(cObj.description)));

			}
			commentBuilder.disease(builder.build());
		}
		if (isNotEmpty(cObj.note)) {
			commentBuilder.note(CommentFactory.INSTANCE.createCommentNote(convert(cObj.note)));
		}
		return commentBuilder.build();
	}
	
	private SubcellularLocationComment convertSubcellularLocation( CcLineObject.SubcullarLocation cObj,
			Map<Object, List<Evidence>> evidences) {
		 SubcellularLocationCommentBuilder builder =SubcellularLocationCommentBuilder.newInstance();
		if (cObj.molecule != null) {
			builder.molecule(cObj.molecule);
		}
		List<SubcellularLocation> locations = new ArrayList<SubcellularLocation>();
		for (CcLineObject.LocationObject lo : cObj.locations) {
			locations.add(
					SubcellularLocationCommentBuilder.createSubcellularLocation( createSubcellularLocationValue(lo.subcellular_location, evidences)
							, createSubcellularLocationValue(lo.topology, evidences), createSubcellularLocationValue(lo.orientation, evidences)));
			
		}
		builder.subcellularLocations(locations);

		if (cObj.note != null && !cObj.note.isEmpty()) {
			builder.note(CommentFactory.INSTANCE.createCommentNote(convert(cObj.note)));
		}
		return builder.build();
	}

	
	private SubcellularLocationValue createSubcellularLocationValue(LocationValue locationValue,
			Map<Object, List<Evidence>> evidenceMap) {
		if ((locationValue == null) || locationValue.value.isEmpty()) {
			return null;
		}
		return SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationValue.value, evidenceMap.get(locationValue));

	}

	private MassSpectrometryComment convertMassSpectrometry(CcLineObject.MassSpectrometry cObj,
			Map<Object, List<Evidence>> evidenceMap) {
		MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
		builder.massSpectrometryMethod(MassSpectrometryMethod.toType(cObj.method)).molWeight((double) cObj.mass)
				.molWeightError((double) cObj.mass_error);

		if (!Strings.isNullOrEmpty(cObj.note)) {
			builder.note(cObj.note);
		}

		builder.evidences(cObj.sources.stream().map(val -> EvidenceFactory.INSTANCE.createFromEvidenceLine(val))
				.collect(Collectors.toList()));

		builder.massSpectrometryRanges(
				cObj.ranges.stream().map(mrange -> convertMassSpectrometryRange(mrange)).collect(Collectors.toList()));
		return builder.build();
	}

	private MassSpectrometryRange convertMassSpectrometryRange(CcLineObject.MassSpectrometryRange mrange) {
		int start;
		int end;
		if (mrange.start_unknown) {
			start = -1;
		} else
			start = mrange.start;
		if (mrange.end_unknown) {
			end = -1;
		} else
			end = mrange.end;
		return MassSpectrometryCommentBuilder.createMassSpectrometryRange(start, end, mrange.range_isoform);

	}

	private RnaEditingComment convertRNAEditing(CcLineObject.RnaEditing cObj, Map<Object, List<Evidence>> evidences) {
		RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
		if (cObj.locations.isEmpty()) {
			if (cObj.locationEnum == RnaEditingLocationEnum.Undetermined) {
				builder.rnaEditingLocationType(RnaEditingLocationType.Undetermined);

			} else if (cObj.locationEnum == RnaEditingLocationEnum.Not_applicable) {
				builder.rnaEditingLocationType(RnaEditingLocationType.Not_applicable);
			}
		} else {
			builder.rnaEditingLocationType(RnaEditingLocationType.Known);
			builder.locations(cObj.locations.stream().map(pos -> convertRNAEditingPosition(pos, evidences))
					.collect(Collectors.toList()));
		}
		if (isNotEmpty(cObj.note)) {
			builder.note(CommentFactory.INSTANCE.createCommentNote(convert(cObj.note, true)));
		}
		return builder.build();
	}

	private Position convertRNAEditingPosition(int pos, Map<Object, List<Evidence>> evidences) {
		String spos = "" + pos;
		return RnaEditingCommentBuilder.createPosition(spos, evidences.get(spos));

	}

	private FreeTextComment convertTextOnly(CommentType commentType, CcLineObject.FreeText cObj,
			Map<Object, List<Evidence>> evidences) {
		List<EvidencedValue> texts = convert(cObj.texts);
		return FreeTextCommentBuilder.buildFreeTextComment(commentType, texts);

	}

	private CofactorComment convertCofactor(CcLineObject.StructuredCofactor cobj,
			Map<Object, List<Evidence>> evidences) {
		CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
		if (cobj.molecule != null) {
			builder.molecule(cobj.molecule);
		}
		if ((cobj.note != null) && (!cobj.note.isEmpty())) {
			builder.note(CommentFactory.INSTANCE.createCommentNote(convert(cobj.note)));

		}
		if (cobj.cofactors != null) {
			builder.cofactors(
					cobj.cofactors.stream().map(item -> convertCofactor(item, evidences)).collect(Collectors.toList()));

		}
		return builder.build();
	}

	private Cofactor convertCofactor(CofactorItem item, Map<Object, List<Evidence>> evidenceMap) {
		List<Evidence> evidences = evidenceMap.get(item);
		return CofactorCommentBuilder.createCofactor(item.name, evidences, createCofactorReference(item.xref));
	}

	private CofactorReference createCofactorReference(String val) {
		int index = val.indexOf(':');
		String type = val.substring(0, index);
		String id = val.substring(index + 1);
		return CofactorCommentBuilder.createCofactorReference(CofactorReferenceType.typeOf(type), id);

	}

	

	private List<EvidencedValue> convert(List<EvidencedString> val, boolean trimTrailStop) {
		return val.stream().map(evStr -> convertEvidencedValue(evStr, trimTrailStop)).collect(Collectors.toList());
	}

	private EvidencedValue convertEvidencedValue(EvidencedString evStr, boolean trimTrailStop) {
		String evVal = evStr.value.trim();
		if(trimTrailStop) {
			evVal = stripTrailing(evStr.value.trim(), STOP);
		}
		List<Evidence> evidences = EvidenceHelper.convert(evStr.evidences);
		return UniProtFactory.INSTANCE.createEvidencedValue(evVal, evidences);
		
	}

	private String stripTrailing(String val, String trail){
		if (val.endsWith(trail))
			return val.substring(0, val.length()-trail.length());
		else
			return val;
	}
	private List<EvidencedValue> convert(List<EvidencedString> val) {
		return convert(val, false);

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
		case ENZYME_REGULATION:
			type = CommentType.ENZYME_REGULATION;
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
