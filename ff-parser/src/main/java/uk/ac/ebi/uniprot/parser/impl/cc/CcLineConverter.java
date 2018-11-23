package uk.ac.ebi.uniprot.parser.impl.cc;

import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceIdCollector;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CAPhysioDirection;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CAReaction;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CatalyticActivity;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CofactorItem;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.LocationFlagEnum;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.LocationValue;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.RnaEditingLocationEnum;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import uk.ac.ebi.kraken.interfaces.uniprot.CommentStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.EvidencedValue;
import uk.ac.ebi.kraken.interfaces.uniprot.HasCommentStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Absorption;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AbsorptionNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsCommentComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CatalyticActivityCommentStructured;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Cofactor;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CofactorCommentStructured;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CofactorNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CofactorReference;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CofactorReferenceType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Comment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentText;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Disease;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseAcronym;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseCommentStructured;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseReference;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseReferenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Interaction;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformName;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.KineticParameterNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.KineticParameters;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryCommentSource;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.PHDependence;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.PhysiologicalReaction;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.PhysiologicalDirectionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Position;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Reaction;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.ReactionReference;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.ReactionReferenceType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RedoxPotential;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingLocationType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionPosition;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocation;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocationComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocationNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocationValue;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularMolecule;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.TemperatureDependence;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.TextOnlyComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.WebResourceComment;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultCommentFactory;
import uk.ac.ebi.kraken.model.factories.DefaultEvidenceFactory;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.kraken.parser.translator.CommentTranslatorHelper;

public class CcLineConverter extends EvidenceIdCollector implements Converter<CcLineObject, List<Comment>> {
	private final DefaultCommentFactory factory = DefaultCommentFactory.getInstance();
	private static final String STOP = ".";

	@Override
	public List<Comment> convert(CcLineObject f) {
		Map<Object, List<EvidenceId>> evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll(evidences.values());
		List<Comment> comments = new ArrayList<>();
		for (CcLineObject.CC cc : f.ccs) {
			if (cc.topic == CcLineObject.CCTopicEnum.SEQUENCE_CAUTION) {
				List<SequenceCautionComment> scComments = convertSequenceCaution(cc, evidences);
				for (SequenceCautionComment comment : scComments) {
					EvidenceHelper.setEvidences(comment, evidences, cc);
				}
				comments.addAll(scComments);
			} else {
				Comment gcomment = convert(cc, evidences);
				EvidenceHelper.setEvidences(gcomment, evidences, cc);
				comments.add(gcomment);
			}
		}
		return comments;
	}

	private List<SequenceCautionComment> convertSequenceCaution(CcLineObject.CC cc,
			Map<Object, List<EvidenceId>> evidences) {
		List<SequenceCautionComment> comments = new ArrayList<>();
		if (cc.topic != CcLineObject.CCTopicEnum.SEQUENCE_CAUTION) {
			return comments;
		}
		CcLineObject.SequenceCaution seqC = (CcLineObject.SequenceCaution) cc.object;
		for (CcLineObject.SequenceCautionObject cObj : seqC.sequenceCautionObjects) {
			SequenceCautionComment comment = factory.buildComment(CommentType.SEQUENCE_CAUTION);
			if ((cObj.note != null) && (!cObj.note.isEmpty())) {
				comment.setNote(factory.buildSequenceCautionCommentNote(cObj.note));
			}
			// position could be multiple
			List<SequenceCautionPosition> positions = new ArrayList<>();
			for (Integer pos : cObj.positions) {
				positions.add(factory.buildSequenceCautionPosition(pos.toString()));
			}
			if (cObj.positions.isEmpty() && cObj.positionValue != null) {
				positions.add(factory.buildSequenceCautionPosition(cObj.positionValue));
			}
			comment.setPositions(positions);
			if (cObj.sequence != null) {
				comment.setSequence(cObj.sequence);
			}
			EvidenceHelper.setEvidences(comment, evidences, cObj);
			comment.setType(convertSequenceCautionType(cObj.type));

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

	private <T extends Comment> T convert(CcLineObject.CC cc, Map<Object, List<EvidenceId>> evidences) {
		CcLineObject.CCTopicEnum topic = cc.topic;

		CommentType type = convert(topic);
		T comment = factory.buildComment(type);
		switch (topic) {
		case ALTERNATIVE_PRODUCTS:
			updateAlternativeProduct((AlternativeProductsComment) comment,
					(CcLineObject.AlternativeProducts) cc.object);
			break;
		case BIOPHYSICOCHEMICAL_PROPERTIES:
			updateBiophyChem((BioPhysicoChemicalPropertiesComment) comment,
					(CcLineObject.BiophysicochemicalProperties) cc.object);
			break;
		case WEB_RESOURCE:
			updateWebResource((WebResourceComment) comment, (CcLineObject.WebResource) cc.object);
			break;
		case INTERACTION:
			updateInteraction((InteractionComment) comment, (CcLineObject.Interaction) cc.object);
			break;
		case DISEASE:
			updateDisease((DiseaseCommentStructured) comment, (CcLineObject.Disease) cc.object, evidences);
			break;
		case MASS_SPECTROMETRY:
			updateMassSpectrometry((MassSpectrometryComment) comment, (CcLineObject.MassSpectrometry) cc.object);
			break;
		case SUBCELLULAR_LOCATION:
			updateSubcellularLocation((SubcellularLocationComment) comment, (CcLineObject.SubcullarLocation) cc.object,
					evidences);
			break;
		case RNA_EDITING:
			updateRNAEditing((RnaEditingComment) comment, (CcLineObject.RnaEditing) cc.object, evidences);
			break;
		case COFACTOR:
			updateCofactor((CofactorCommentStructured) comment, (CcLineObject.StructuredCofactor) cc.object, evidences);
			break;
		case CATALYTIC_ACTIVITY:
			updateCatalyticActivity((CatalyticActivityCommentStructured) comment,
					(CcLineObject.CatalyticActivity) cc.object, evidences);
			break;
		default:
			updateTextOnly((TextOnlyComment) comment, (CcLineObject.FreeText) cc.object);
		}

		return comment;

	}

	private <T> boolean isNotEmpty(List<T> value) {
		return (value != null) && !value.isEmpty();
	}

	private void updateAlternativeProduct(AlternativeProductsComment comment, CcLineObject.AlternativeProducts cObj) {
		cObj.events.forEach(event -> comment.getEvents().add(factory.buildAlternativeProductsEvent(event)));

		if (isNotEmpty(cObj.comment)) {
			AlternativeProductsCommentComment commentComment = factory.buildAlternativeProductsCommentComment();
			commentComment.setTexts(convert(cObj.comment));
			comment.setComment(commentComment);
		}
		comment.setIsoforms(
				cObj.names.stream().map(name -> convertAlternativeProductsIsoform(name)).collect(Collectors.toList()));

	}

	private AlternativeProductsIsoform convertAlternativeProductsIsoform(CcLineObject.AlternativeProductName name) {
		AlternativeProductsIsoform aPIsoform = factory.buildAlternativeProductsIsoform();
		IsoformName isoformName = factory.buildIsoformName(name.name.value);
		isoformName.setEvidenceIds(EvidenceHelper.convert(name.name.evidences));
		this.add(isoformName.getEvidenceIds());
		aPIsoform.setName(isoformName);

		if (isNotEmpty(name.note)) {
			IsoformNote note = factory.buildIsoformNote();
			note.setTexts(convert(name.note));
			aPIsoform.setNote(note);
		}
		List<IsoformId> isoIds = new ArrayList<>();
		for (String isoId : name.isoId) {
			isoIds.add(factory.buildIsoformId(isoId));
		}
		aPIsoform.setIds(isoIds);
		aPIsoform.setSynonyms(name.synNames.stream().map(syn -> {
			IsoformSynonym isoformSyn = factory.buildIsoformSynonym(syn.value);
			isoformSyn.setEvidenceIds(EvidenceHelper.convert(syn.evidences));
			this.add(isoformSyn.getEvidenceIds());
			return isoformSyn;
		}).collect(Collectors.toList()));

		List<IsoformSequenceId> isoSeqIds = new ArrayList<>();
		for (String featId : name.sequenceFTId) {
			isoSeqIds.add(factory.buildIsoformSequenceId(featId));
		}
		aPIsoform.setSequenceIds(isoSeqIds);
		aPIsoform.setIsoformSequenceStatus(convertIsoformSequenceStatus(name.sequenceEnum));
		return aPIsoform;
	}

	private IsoformSequenceStatus convertIsoformSequenceStatus(CcLineObject.AlternativeNameSequenceEnum type) {
		if (type == null)
			return IsoformSequenceStatus.DESCRIBED;
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

	private MichaelisConstant convertMichaelisConstant(EvidencedString kmEvStr) {
		String kmStr = kmEvStr.value;
		MichaelisConstant km = factory.buildMichaelisConstant();
		int index = kmStr.indexOf(' ');
		String val = kmStr.substring(0, index).trim();
		kmStr = kmStr.substring(index + 1).trim();
		index = kmStr.indexOf(' ');
		String unit = kmStr.substring(0, index).trim();
		kmStr = kmStr.substring(index + 5).trim();
		double value = Double.parseDouble(val);
		km.setConstant((float) value);
		km.setUnit(MichaelisConstantUnit.convert(unit));
		km.setSubstrate(factory.buildSubstrate(kmStr));
		km.setEvidenceIds(EvidenceHelper.convert(kmEvStr.evidences));
		return km;
	}

	private MaximumVelocity convertMaximumVelocity(EvidencedString vmaxEvStr) {
		String vmaxStr = vmaxEvStr.value;
		MaximumVelocity mv = factory.buildMaximumVelocity();
		int index = vmaxStr.indexOf(' ');
		String val = vmaxStr.substring(0, index).trim();

		vmaxStr = vmaxStr.substring(index + 1).trim();
		index = vmaxStr.indexOf(' ');
		String unit = vmaxStr.substring(0, index).trim();
		vmaxStr = vmaxStr.substring(index + 1).trim();
		double value = Double.parseDouble(val);
		mv.setMaxVelocityUnit(factory.buildMaxVelocityUnit(unit));
		mv.setVelocity((float) value);
		mv.setEnzyme(factory.buildEnzyme(vmaxStr));
		mv.setEvidenceIds(EvidenceHelper.convert(vmaxEvStr.evidences));
		return mv;
	}

	private void updateBiophyChem(BioPhysicoChemicalPropertiesComment comment,
			CcLineObject.BiophysicochemicalProperties cObj) {

		// has Kinetic parameter
		// CC Kinetic parameters:
		// CC KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
		// CC Celsius and at pH 7.6);
		// CC Vmax=1.9 mmol/min/mg enzyme;
		if (isNotEmpty(cObj.kms) || isNotEmpty(cObj.vmaxs) || isNotEmpty(cObj.kpNote)) {
			KineticParameters kp = factory.buildKineticParameters();
			cObj.kms.stream().map(kmEvStr -> convertMichaelisConstant(kmEvStr)).forEach(km -> {
				this.add(km.getEvidenceIds());
				kp.getMichaelisConstants().add(km);
			});

			cObj.vmaxs.stream().map(vmaxEvStr -> convertMaximumVelocity(vmaxEvStr)).forEach(mv -> {
				this.add(mv.getEvidenceIds());
				kp.getMaximumVelocities().add(mv);
			});
			if (isNotEmpty(cObj.kpNote)) {
				KineticParameterNote note = factory.buildKineticParameterNote();
				note.setTexts(convert(cObj.kpNote));
				kp.setNote(note);
			}
			comment.setKineticParameters(kp);
		}
		if (isNotEmpty(cObj.phDependence)) {
			PHDependence phDependence = factory.buildPHDependence();
			phDependence.setTexts(convert(cObj.phDependence));
			comment.setPHDepencence(phDependence);
		}
		if (isNotEmpty(cObj.rdoxPotential)) {
			RedoxPotential redoxPotential = factory.buildRedoxPotential();
			redoxPotential.setTexts(convert(cObj.rdoxPotential));
			comment.setRedoxPotential(redoxPotential);
		}
		if (isNotEmpty(cObj.temperatureDependence)) {
			TemperatureDependence temperatureDependence = factory.buildTemperatureDependence();
			temperatureDependence.setTexts(convert(cObj.temperatureDependence));
			comment.setTemperatureDependence(temperatureDependence);
		}
		if (cObj.bsorptionAbs != null) {
			Absorption absorption = factory.buildAbsorption();
			String abs = cObj.bsorptionAbs.value;
			int index = abs.indexOf(' ');
			if (index != -1)
				abs = abs.substring(0, index).trim();
			absorption.setMax(Integer.parseInt(abs));
			absorption.setEvidenceIds(EvidenceHelper.convert(cObj.bsorptionAbs.evidences));
			this.add(absorption.getEvidenceIds());
			if (isNotEmpty(cObj.bsorptionNote)) {
				AbsorptionNote note = factory.buildAbsorptionNote();
				note.setTexts(convert(cObj.bsorptionNote));
				absorption.setNote(note);
			}

			absorption.setApproximation(cObj.bsorptionAbsApproximate);

			comment.setAbsorption(absorption);
		}

	}

	private void updateWebResource(WebResourceComment comment, CcLineObject.WebResource cObj) {
		comment.setDatabaseName(factory.buildDatabaseName(cObj.name));
		if (!Strings.isNullOrEmpty(cObj.note)) {
			comment.setDatabaseNote(factory.buildDatabaseNote(cObj.note));
		}
		if (cObj.url.startsWith("ftp")) {
			comment.setDatabaseFTP(factory.buildDatabaseFTP(cObj.url));
		} else
			comment.setDatabaseURL(factory.buildDatabaseURL(cObj.url));
	}

	private void updateInteraction(InteractionComment comment, CcLineObject.Interaction cObj) {
		List<Interaction> interactions = new ArrayList<>();
		for (CcLineObject.InteractionObject io : cObj.interactions) {
			Interaction interaction = factory.buildInteraction();

			if (!io.isSelf)
				interaction.setInteractorUniProtAccession(factory.buildInteractorUniProtAccession(io.spAc));
			if (!Strings.isNullOrEmpty(io.gene)) {
				interaction.setInteractionGeneName(factory.buildInteractionGeneName(io.gene));
			}
			if (io.xeno)
				interaction.setInteractionType(InteractionType.XENO);
			else if (io.isSelf) {
				interaction.setInteractionType(InteractionType.SELF);
			} else
				interaction.setInteractionType(InteractionType.BINARY);
			interaction.setFirstInteractor(factory.buildIntActAccession(io.firstId));
			if (!Strings.isNullOrEmpty(io.secondId)) {
				interaction.setSecondInteractor(factory.buildIntActAccession(io.secondId));
			}
			interaction.setNumberOfExperiments(io.nbexp);
			interactions.add(interaction);
		}
		comment.setInteractions(interactions);
	}

	private void updateDisease(DiseaseCommentStructured comment, CcLineObject.Disease cObj,
			Map<Object, List<EvidenceId>> evidences) {
		if (!Strings.isNullOrEmpty(cObj.name)) {
			Disease disease = factory.buildDisease();
			DiseaseId id = factory.buildDiseaseId();
			id.setValue(cObj.name);
			disease.setDiseaseId(id);
			if (!Strings.isNullOrEmpty(cObj.abbr)) {
				DiseaseAcronym da = factory.buildDiseaseAcronym();
				da.setValue(cObj.abbr);
				disease.setDiseaseAcronym(da);
			}
			if (!Strings.isNullOrEmpty(cObj.mim)) {
				DiseaseReference dr = factory.buildDiseaseReference();
				dr.setDiseaseReferenceType(DiseaseReferenceType.MIM);
				DiseaseReferenceId drId = factory.buildDiseaseReferenceId();
				drId.setValue(cObj.mim);
				dr.setDiseaseReferenceId(drId);
				disease.setDiseaseReference(dr);
			}
			if (!Strings.isNullOrEmpty(cObj.description)) {
				DiseaseDescription diseaseDescr = factory.buildDiseaseDescription();
				String descr = cObj.description;
				if (!descr.endsWith("."))
					descr += ".";
				diseaseDescr.setValue(descr);

				EvidenceHelper.setEvidences(diseaseDescr, evidences, cObj.description);
				disease.setDiseaseDescription(diseaseDescr);
			}
			comment.setDisease(disease);
		}
		if (isNotEmpty(cObj.note)) {
			DiseaseNote diseaseNote = factory.buildDiseaseNote();
			diseaseNote.setTexts(convert(cObj.note));
			comment.setNote(diseaseNote);
		}
	}

	private void updateSubcellularLocation(SubcellularLocationComment comment, CcLineObject.SubcullarLocation cObj,
			Map<Object, List<EvidenceId>> evidences) {
		// to be implemented
		if (cObj.molecule != null) {
			SubcellularMolecule molecule = factory.buildSubcellularMolecule();
			molecule.setValue(cObj.molecule);
			comment.setSubcellularMolecule(molecule);
		}
		List<SubcellularLocation> locations = new ArrayList<>();
		for (CcLineObject.LocationObject lo : cObj.locations) {

			SubcellularLocation location = factory.buildSubcellularLocation();
			locations.add(location);
			SubcellularLocationValue locationVal = createSubcellularLocationValue(lo.subcellularLocation, evidences);
			if (locationVal != null) {
				location.setLocation(locationVal);
			}

			SubcellularLocationValue orientationVal = createSubcellularLocationValue(lo.orientation, evidences);
			if (orientationVal != null) {
				location.setOrientation(orientationVal);
			}

			SubcellularLocationValue topologyVal = createSubcellularLocationValue(lo.topology, evidences);
			if (topologyVal != null) {
				location.setTopology(topologyVal);
			}

			List<EvidenceId> evids2 = evidences.get(lo);
			if ((evids2 != null) && (!evids2.isEmpty())) {
				EvidenceHelper.setEvidences(comment, evidences, lo);
			}

		}
		comment.setSubcellularLocations(locations);

		if (cObj.note != null && !cObj.note.isEmpty()) {
			SubcellularLocationNote note = factory.buildSubcellularLocationNote();
			note.setTexts(convert(cObj.note));
			comment.setSubcellularLocationNote(note);
		}

	}

	private SubcellularLocationValue createSubcellularLocationValue(LocationValue locationValue,
			Map<Object, List<EvidenceId>> evidences) {
		if ((locationValue == null) || locationValue.value.isEmpty()) {
			return null;
		}
		SubcellularLocationValue subLocationValue = factory.buildSubcellularLocationValue();
		subLocationValue.setValue(locationValue.value);
		setStatus(subLocationValue, locationValue.flag);
		EvidenceHelper.setEvidences(subLocationValue, evidences, locationValue);
		return subLocationValue;
	}

	private void setStatus(HasCommentStatus hasStatus, LocationFlagEnum locationflag) {
		if (locationflag == null) {
			hasStatus.setCommentStatus(CommentStatus.EXPERIMENTAL);
			return;
		}
		switch (locationflag) {
		case BY_SIMILARITY:
			hasStatus.setCommentStatus(CommentStatus.BY_SIMILARITY);
			break;
		case PROBABLE:
			hasStatus.setCommentStatus(CommentStatus.PROBABLE);
			break;
		case POTENTIAL:
			hasStatus.setCommentStatus(CommentStatus.POTENTIAL);
			break;
		default:
			hasStatus.setCommentStatus(CommentStatus.EXPERIMENTAL);
		}
	}

	private void updateMassSpectrometry(MassSpectrometryComment comment, CcLineObject.MassSpectrometry cObj) {
		comment.setMethod(MassSpectrometryMethod.toType(cObj.method));
		comment.setMolWeight(cObj.mass);
		comment.setMolWeightError(cObj.massError);
		if (!Strings.isNullOrEmpty(cObj.note)) {
			comment.setNote(factory.buildMassSpectrometryCommentNote(cObj.note));
		}
		List<MassSpectrometryCommentSource> sources = new ArrayList<>();
		for (String source : cObj.sources) {
			sources.add(factory.buildMassSpectrometryCommentSource(source));
			final EvidenceId evId = DefaultEvidenceFactory.getInstance().buildEvidenceId(source);
			comment.getEvidenceIds().add(evId);
		}
		comment.setSources(sources);
		comment.setRanges(cObj.ranges.stream().map(this::convertMassSpectrometryRange).collect(Collectors.toList()));
	}

	private MassSpectrometryRange convertMassSpectrometryRange(CcLineObject.MassSpectrometryRange mrange) {
		MassSpectrometryRange range = factory.buildMassSpectrometryRange();
		if (mrange.startUnknown) {
			range.setStart(-1);
		} else
			range.setStart(mrange.start);
		if (mrange.endUnknown) {
			range.setEnd(-1);
		} else
			range.setEnd(mrange.end);
		if (!Strings.isNullOrEmpty(mrange.rangeIsoform)) {
			range.setIsoformId(factory.buildMassSpectrometryIsoformId(mrange.rangeIsoform));
		}
		return range;
	}

	private void updateRNAEditing(RnaEditingComment comment, CcLineObject.RnaEditing cObj,
			Map<Object, List<EvidenceId>> evidences) {
		if (cObj.locations.isEmpty()) {
			if (cObj.locationEnum == RnaEditingLocationEnum.UNDETERMINED) {
				comment.setLocationType(RnaEditingLocationType.Undetermined);
			} else if (cObj.locationEnum == RnaEditingLocationEnum.NOT_APPLICABLE) {
				comment.setLocationType(RnaEditingLocationType.Not_applicable);
			}
		} else {
			comment.setLocationType(RnaEditingLocationType.Known);
			comment.setPositions(cObj.locations.stream().map(pos -> convertRNAEditingPosition(pos, evidences))
					.collect(Collectors.toList()));
		}
		if (isNotEmpty(cObj.note)) {
			RnaEditingNote note = factory.buildRnaEditingNote();
			note.setTexts(convert(cObj.note, true));
			comment.setRnaEditingNote(note);
		}
	}

	private Position convertRNAEditingPosition(int pos, Map<Object, List<EvidenceId>> evidences) {
		Position position = factory.buildRnaEditingPosition();
		String spos = "" + pos;
		position.setPosition(spos);
		EvidenceHelper.setEvidences(position, evidences, spos);
		return position;
	}

	private void updateTextOnly(TextOnlyComment comment, CcLineObject.FreeText cObj) {
		List<CommentText> cTexts = new ArrayList<>();
		for (EvidencedString val : cObj.texts) {
			CommentText text = factory.buildCommentText();
			String value = val.value;
			value = setCommentStatus(value, text);
			text.setValue(value);
			text.setEvidenceIds(EvidenceHelper.convert(val.evidences));
			this.add(text.getEvidenceIds());
			cTexts.add(text);
		}
		comment.setTexts(cTexts);
	}

	private void updateCofactor(CofactorCommentStructured comment, CcLineObject.StructuredCofactor cobj,
			Map<Object, List<EvidenceId>> evidences) {
		if (cobj.molecule != null) {
			comment.setMolecule(cobj.molecule);
		}
		if ((cobj.note != null) && (!cobj.note.isEmpty())) {
			CofactorNote note = factory.buildCofactorNote();
			note.setTexts(convert(cobj.note, true));
			comment.setNote(note);
		}
		if (cobj.cofactors != null) {
			comment.setCofactors(
					cobj.cofactors.stream().map(item -> convertCofactor(item, evidences)).collect(Collectors.toList()));

		}
	}

	private Cofactor convertCofactor(CofactorItem item, Map<Object, List<EvidenceId>> evidences) {
		Cofactor cofactor = factory.buildCofactor();
		cofactor.setName(item.name);
		cofactor.setCofactorReference(createCofactorReference(item.xref));
		EvidenceHelper.setEvidences(cofactor, evidences, item);
		return cofactor;
	}

	private CofactorReference createCofactorReference(String val) {
		CofactorReference ref = factory.buildCofactorReference();
		int index = val.indexOf(':');
		String type = val.substring(0, index);
		String id = val.substring(index + 1);
		ref.setCofactorReferenceType(CofactorReferenceType.typeOf(type));
		ref.setReferenceId(id);
		return ref;
	}

	private void updateCatalyticActivity(CatalyticActivityCommentStructured comment, CatalyticActivity object,
			Map<Object, List<EvidenceId>> evidences) {
		comment.setReaction(convertReaction(object.reaction, evidences));
		comment.setPhysiologicalReactions(object.physiologicalDirections.stream()
				.map(val -> convertPhysiologicalDirection(val, evidences)).collect(Collectors.toList()));
	}

	private PhysiologicalReaction convertPhysiologicalDirection(CAPhysioDirection capd,
			Map<Object, List<EvidenceId>> evidences) {
		PhysiologicalReaction direction = factory.buildPhysiologicalReaction();
		if (capd.xref != null)
			direction.setReactionReference(convertReactionReference(capd.xref));
		direction.setDirectionType(PhysiologicalDirectionType.typeOf(capd.name));
		EvidenceHelper.setEvidences(direction, evidences, capd);
		return direction;
	}

	private Reaction convertReaction(CAReaction caReaction, Map<Object, List<EvidenceId>> evidences) {
		Reaction reaction = factory.buildReaction();
		reaction.setName(caReaction.name);
		if (caReaction.ec != null) {
			reaction.setECNumber(caReaction.ec);
		}
		if(!Strings.isNullOrEmpty(caReaction.xref )) {
		reaction.setReactionReferences(Arrays.stream(caReaction.xref.split(", ")).map(this::convertReactionReference)
				.collect(Collectors.toList()));
		}

		EvidenceHelper.setEvidences(reaction, evidences, caReaction);
		return reaction;
	}

	private ReactionReference convertReactionReference(String val) {
		ReactionReference ref = factory.buildReactionReference();
		int index = val.indexOf(':');
		String type = val.substring(0, index);
		String id = val.substring(index + 1);
		ref.setType(ReactionReferenceType.typeOf(type));
		ref.setId(id);
		return ref;
	}

	private String setCommentStatus(String annotation, HasCommentStatus comment) {

		if (annotation.endsWith(" (Potential)") || annotation.endsWith(" (potential)")) {
			comment.setCommentStatus(CommentStatus.POTENTIAL);
			annotation = annotation.substring(0, annotation.length() - 11);
		} else if (annotation.endsWith(" (Probable)") || annotation.endsWith(" (probable)")) {
			comment.setCommentStatus(CommentStatus.PROBABLE);
			annotation = annotation.substring(0, annotation.length() - 11);
		} else if (annotation.endsWith(" (By similarity)") || annotation.endsWith(" (by similarity)")) {
			comment.setCommentStatus(CommentStatus.BY_SIMILARITY);
			annotation = annotation.substring(0, annotation.length() - 16);
		} else {
			comment.setCommentStatus(CommentStatus.EXPERIMENTAL);
		}
		return annotation;
	}

	private List<EvidencedValue> convert(List<EvidencedString> val, boolean trimTrailStop) {
		return val.stream().map(evStr -> convertEvidencedValue(evStr, trimTrailStop)).collect(Collectors.toList());
	}

	private EvidencedValue convertEvidencedValue(EvidencedString evStr, boolean trimTrailStop) {
		EvidencedValue evVal = DefaultUniProtFactory.getInstance().buildEvidencedValue();
		if (trimTrailStop) {
			evVal.setValue(CommentTranslatorHelper.stripTrailing(evStr.value.trim(), STOP));
		} else
			evVal.setValue(evStr.value.trim());
		evVal.setEvidenceIds(EvidenceHelper.convert(evStr.evidences));
		this.add(evVal.getEvidenceIds());
		return evVal;
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
