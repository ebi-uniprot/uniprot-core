package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntryCommentsMap implements NamedValueMap {
	public static final List<String> FIELDS = Arrays.asList("cc:alternative_products", "cc:mass_spectrometry",
			"cc:polymorphism", "cc:rna_editing", "cc:sequence_caution", "cc:catalytic_activity", "cc:cofactor",
			"cc:enzyme_regulation", "cc:function", "cc:pathway", "cc:miscellaneous", "cc:interaction", "cc:subunit",
			"cc:developmental_stage", "cc:induction", "cc:tissue_specificity", "cc:allergen", "cc:biotechnology",
			"cc:disruption_phenotype", "cc:disease", "cc:pharmaceutical", "cc:toxic_dose", "cc:subcellular_location",
			"cc:ptm", "cc:domain", "cc:similarity", "cc:caution", "absorption", "kinetics", "ph_dependence",
			"redox_potential", "temp_dependence", "error_gmodel_pred", "protein_families");

	private final List<Comment> comments;
	private static final Pattern PATTERN_FAMILY =Pattern.compile("(?:In the .+? section; )?[Bb]elongs to the (.+?family)\\.(?: (.+?family)\\.)?(?: (.+?family)\\.)?(?: Highly divergent\\.)?");

	public EntryCommentsMap(List<Comment> comments) {
		if (comments == null) {
			this.comments = Collections.emptyList();
		} else
			this.comments = Collections.unmodifiableList(comments);
	}

	@Override
	public Map<String, String> attributeValues() {
		if (comments.isEmpty())
			return Collections.emptyMap();

		Map<String, String> map = new HashMap<>();
		for (CommentType type : CommentType.values()) {
			switch (type) {
			case ALTERNATIVE_PRODUCTS:
				List<AlternativeProductsComment> apComments = getComments(type);
				updateAPComments(map, type, apComments);
				break;
			case BIOPHYSICOCHEMICAL_PROPERTIES:
				List<BPCPComment> bpcpComments = getComments(type);
				updateBioPhyChemPropComments(map, type, bpcpComments);
				break;
			case CATALYTIC_ACTIVITY:
				//TODO: Jie can you please help us with this?
				break;
			case COFACTOR:
				List<CofactorComment> cfComments = getComments(type);
				updateCofactorComments(map, type, cfComments);
				break;
			case DISEASE:
				List<DiseaseComment> dsComments = getComments(type);
				updateDiseaseComments(map, type, dsComments);
				break;
			case INTERACTION:
				List<InteractionComment> iaComments = getComments(type);
				updateInterActComments(map, type, iaComments);
				break;
			case MASS_SPECTROMETRY:
				List<MassSpectrometryComment> msComments = getComments(type);
				updateMassSpecComments(map, type, msComments);
				break;
			case RNA_EDITING:
				List<RnaEditingComment> reComments = getComments(type);
				updateRnaEdComments(map, type, reComments);
				break;
			case SEQUENCE_CAUTION:
				List<SequenceCautionComment> scComments = getComments(type);
				updateSeqCautionComments(map, type, scComments);
				break;
			case SUBCELLULAR_LOCATION:
				List<SubcellularLocationComment> sclComments = getComments(type);
				updateSubCellLocComments(map, type, sclComments);
				break;
			case WEBRESOURCE:
				// List<WRComment> wrComments = getComments(type);
				break;
			case SIMILARITY:
				List<FreeTextComment> simiComments = getComments(type);
				updateTextComments(map, type, simiComments);
				updateProteinFamility(map, type, simiComments);
				
			default:
				List<FreeTextComment> txtComments = getComments(type);
				updateTextComments(map, type, txtComments);
				
			}

		}
		return map;
	}

	private void updateAPComments(Map<String, String> map, CommentType type, List<AlternativeProductsComment> apComments) {
		if ((apComments == null) || apComments.isEmpty())
			return;
		String result = apComments.stream().map(alternativeProductsComment -> {
			String alternativeProductsStr = "";
			if(alternativeProductsComment.getEvents() != null && !alternativeProductsComment.getEvents().isEmpty()){
				alternativeProductsStr = "Event="+alternativeProductsComment.getEvents().stream()
						.map(APEventType::getName)
						.collect(Collectors.joining(", "))+";";
			}

			if(alternativeProductsComment.getIsoforms() != null && !alternativeProductsComment.getIsoforms().isEmpty()){
				alternativeProductsStr += " Named isoforms="+alternativeProductsComment.getIsoforms().size()+";";
				if(alternativeProductsComment.getNote() != null) {
					alternativeProductsStr += "Comment=" + alternativeProductsComment.getNote().getTexts()
							.stream().map(EntryMapUtil::evidencedValueToString)
							.collect(Collectors.joining("; "))+";";
				}
				alternativeProductsStr += " "+alternativeProductsComment.getIsoforms().stream()
						.map(apIsoform -> {
							String apIsoformStr = "";
							if(apIsoform.getName() != null){
								apIsoformStr += " Name="+EntryMapUtil.evidencedValueToString(apIsoform.getName())+";";
							}
							if(apIsoform.getIsoformIds() != null && !apIsoform.getIsoformIds().isEmpty()){
								apIsoformStr += " IsoId="+apIsoform.getIsoformIds().stream()
										.map(IsoformId::getValue)
										.collect(Collectors.joining(", "))+";";
							}
							if(apIsoform.getSequenceIds() != null && !apIsoform.getSequenceIds().isEmpty()){
								apIsoformStr += " Sequence="+String.join(", ", apIsoform.getSequenceIds()) +";";
							}else{
								apIsoformStr += " Sequence="+apIsoform.getIsoformSequenceStatus().getValue()+";";
							}
							if(apIsoform.getNote() != null) {
								apIsoformStr += EntryMapUtil.getNoteString(apIsoform.getNote())+";";
							}

							return apIsoformStr;
						})
						.collect(Collectors.joining(""));
			}
			return alternativeProductsStr;
		}).collect(Collectors.joining("; "));
		map.put("cc:alternative_products", "ALTERNATIVE PRODUCTS:  " +result);
	}

	private void updateBioPhyChemPropComments(Map<String, String> map, CommentType type,
			List<BPCPComment> bpcpComments) {
		if ((bpcpComments == null) || bpcpComments.isEmpty())
			return;
		String absorption = absorptionToString(bpcpComments);
		if (!absorption.isEmpty()) {
			map.put("absorption", "BIOPHYSICOCHEMICAL PROPERTIES: ;  " + absorption);
		}
		String kinetics = kineticsToString(bpcpComments);
		if (!kinetics.isEmpty()) {
			map.put("kinetics", "BIOPHYSICOCHEMICAL PROPERTIES:  " + kinetics);
		}
		String phDependence = phDependenceToString(bpcpComments);
		if (!phDependence.isEmpty()) {
			map.put("ph_dependence", "BIOPHYSICOCHEMICAL PROPERTIES: ;  pH dependence: " + phDependence);
		}
		String redoxPotential = redoxPotentialToString(bpcpComments);
		if (!redoxPotential.isEmpty()) {
			map.put("redox_potential", "BIOPHYSICOCHEMICAL PROPERTIES: ;  Redox potential: " + redoxPotential);
		}
		String tempDependence = tempDependenceToString(bpcpComments);
		if (!tempDependence.isEmpty()) {
			map.put("temp_dependence", "BIOPHYSICOCHEMICAL PROPERTIES: ;  Temperature dependence: " + tempDependence);
		}
	}

	private String absorptionToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().map(BPCPComment::getAbsorption).filter(Objects::nonNull)
				.map(absorption -> {
					String result = "Absorption: Abs(max)="+absorption.getMax();
					if(absorption.getEvidences() != null && !absorption.getEvidences().isEmpty()){
						result += " "+EntryMapUtil.evidencesToString(absorption.getEvidences())+";";
					}
					if(absorption.getNote() != null) {
						result += EntryMapUtil.getNoteString(absorption.getNote())+";";
					}
					return result;
				}).collect(Collectors.joining("; "));

	}

	private String kineticsToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().map(BPCPComment::getKineticParameters).filter(Objects::nonNull)
				.map(kineticParameters -> {
					String result = "Kinetic parameters:";
					if(kineticParameters.getMichaelisConstants() != null && !kineticParameters.getMichaelisConstants().isEmpty()){
						result += kineticParameters.getMichaelisConstants().stream()
								.map(this::getMichaelConstantsString)
								.collect(Collectors.joining(";"))+";";
					}
					if(kineticParameters.getMaximumVelocities() != null && !kineticParameters.getMaximumVelocities().isEmpty()) {
						result += kineticParameters.getMaximumVelocities().stream()
								.map(this::getMaximumVelocitiesString)
								.collect(Collectors.joining(";"))+";";
					}
					if(kineticParameters.getNote() != null ) {
						result += EntryMapUtil.getNoteString(kineticParameters.getNote());
					}
					return result;
				})
				.collect(Collectors.joining("; "));
	}

	private String getMaximumVelocitiesString(MaximumVelocity maximumVelocity) {
		String result = " Vmax="+maximumVelocity.getVelocity()+" "
				+maximumVelocity.getUnit()+ " "
				+maximumVelocity.getEnzyme();
		if(maximumVelocity.getEvidences() != null && !maximumVelocity.getEvidences().isEmpty()) {
			result += " "+EntryMapUtil.evidencesToString(maximumVelocity.getEvidences());
		}
		return result;
	}

	private String getMichaelConstantsString(MichaelisConstant michaelisConstant) {
		String result = " KM="+michaelisConstant.getConstant()+" "
				+michaelisConstant.getUnit().getName()+" for "
				+michaelisConstant.getSubstrate();
		if(michaelisConstant.getEvidences() != null && !michaelisConstant.getEvidences().isEmpty()) {
			result += " "+EntryMapUtil.evidencesToString(michaelisConstant.getEvidences());
		}
		return result;
	}

	private String phDependenceToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().map(BPCPComment::getPhDependence).filter(Objects::nonNull)
				.map(phDependence ->
						phDependence.getTexts().stream()
								.map(EntryMapUtil::evidencedValueToString)
								.collect(Collectors.joining("; "))
				).collect(Collectors.joining("; "));
	}

	private String redoxPotentialToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().map(BPCPComment::getRedoxPotential).filter(Objects::nonNull)
				.map(redoxPotential ->
						redoxPotential.getTexts().stream()
								.map(EntryMapUtil::evidencedValueToString)
								.collect(Collectors.joining("; "))
				).collect(Collectors.joining("; "));
	}

	private String tempDependenceToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().map(BPCPComment::getTemperatureDependence).filter(Objects::nonNull)
				.map(temperatureDependence ->
						temperatureDependence.getTexts().stream()
								.map(EntryMapUtil::evidencedValueToString)
								.collect(Collectors.joining("; "))
				).collect(Collectors.joining("; "));
	}


	private void updateCofactorComments(Map<String, String> map, CommentType type, List<CofactorComment> cfComments) {
		if ((cfComments == null) || cfComments.isEmpty())
			return;
		String result = cfComments.stream().map(this::mapCofactorCommentToString).collect(Collectors.joining("; "));
		map.put("cc:cofactor", result);
	}

	private String mapCofactorCommentToString(CofactorComment cofactorComment) {
		String result = "";
		if(cofactorComment.getMolecule() != null && !cofactorComment.getMolecule().isEmpty()) {
			result += cofactorComment.getMolecule();
		}
		if(cofactorComment.getCofactors() != null && !cofactorComment.getCofactors().isEmpty()) {
			result += cofactorComment.getCofactors().stream().map(cofactor -> {
				String cofactorString = "";
				if(cofactor.getName() != null && !cofactor.getName().isEmpty()){
					cofactorString += " "+cofactor.getName();
				}
				if(cofactor.getCofactorReference() != null){
					cofactorString +=" "+cofactor.getCofactorReference().getDatabaseType().getName();
					cofactorString +=" "+cofactor.getCofactorReference().getId();
				}
				return cofactorString;
			}).collect(Collectors.joining("; "));
		}
		if(cofactorComment.getNote() != null) {
			result += EntryMapUtil.getNoteString(cofactorComment.getNote());
		}
		return result;
	}

	private void updateDiseaseComments(Map<String, String> map, CommentType type, List<DiseaseComment> dsComments) {
		if ((dsComments == null) || dsComments.isEmpty())
			return;
		String result = dsComments.stream().map(this::mapDiseaseCommentToString).collect(Collectors.joining("; "));
		map.put("cc:disease", result);
	}

	private  String mapDiseaseCommentToString(DiseaseComment diseaseComment) {
		String result = "";
		if(diseaseComment.getDisease() != null){
			Disease disease = diseaseComment.getDisease();
			if(disease.getDiseaseId() != null && !disease.getDiseaseId().isEmpty()){
				result += " "+disease.getDiseaseId();
			}
			if(disease.getDiseaseAccession() != null && !disease.getDiseaseAccession().isEmpty()){
				result += " "+disease.getDiseaseAccession();
			}
			if(disease.getAcronym() != null && !disease.getAcronym().isEmpty()){
				result += " "+disease.getAcronym();
			}
			if(disease.getDescription() != null && !disease.getDescription().isEmpty()){
				result += " "+disease.getDescription();
			}
			if(disease.getReference() != null){
				DBCrossReference<DiseaseReferenceType> reference = disease.getReference();
				if(reference.getDatabaseType() != null) {
					result += " " + reference.getDatabaseType().getName();
				}
				if(reference.getId() != null && !reference.getId().isEmpty()) {
					result += " " + reference.getId();
				}
			}
		}
		if(diseaseComment.getNote() != null) {
			result += EntryMapUtil.getNoteString(diseaseComment.getNote());
		}
		return result;
	}

	private void updateInterActComments(Map<String, String> map, CommentType type, List<InteractionComment> iaComments) {
		if ((iaComments == null) || iaComments.isEmpty())
			return;
		String result = iaComments.stream().flatMap(val -> val.getInteractions().stream()).map(this::getInterAct)
				.collect(Collectors.joining("; "));
		map.put("cc:interaction", result);
	}

	private String getInterAct(Interaction interAct) {
		if (InteractionType.SELF.equals(interAct.getType())) {
			return "Self";
		} else
			return interAct.getUniProtAccession().getValue();
	}

	private void updateMassSpecComments(Map<String, String> map, CommentType type, List<MassSpectrometryComment> msComments) {
		if ((msComments == null) || msComments.isEmpty())
			return;
		String result = msComments.stream().map(this::mapMassSpectrometryCommentToString).collect(Collectors.joining(";  "));
		map.put("cc:mass_spectrometry", result);
	}

	private String mapMassSpectrometryCommentToString(MassSpectrometryComment massSpectrometryComment) {
		String result = "";
		if(massSpectrometryComment.getMolWeight() != null){
			result += " "+massSpectrometryComment.getMolWeight();
		}
		if(massSpectrometryComment.getMolWeightError() != null){
			result += " "+massSpectrometryComment.getMolWeightError();
		}
		if(massSpectrometryComment.getMethod() != null){
			result += " "+massSpectrometryComment.getMethod().getValue();
		}
		if(massSpectrometryComment.getRanges() != null && !massSpectrometryComment.getRanges().isEmpty()){
			result += " "+massSpectrometryComment.getRanges().stream().map(this::mapMassSpectrometryRangeToString)
					.collect(Collectors.joining("; "));
		}
		if(massSpectrometryComment.getNote() != null && !massSpectrometryComment.getNote().isEmpty()){
			result += " "+massSpectrometryComment.getNote();
		}
		if(massSpectrometryComment.getEvidences() != null && !massSpectrometryComment.getEvidences().isEmpty()){
			result += " "+EntryMapUtil.evidencesToString(massSpectrometryComment.getEvidences());
		}
		return result;
	}

	private String mapMassSpectrometryRangeToString(MassSpectrometryRange massSpectrometryRange) {
		String range = "";
		if(massSpectrometryRange.getRange() != null){
			Position start = massSpectrometryRange.getRange().getStart();
			if(start != null){
				range += start.getValue();
			}
			Position end = massSpectrometryRange.getRange().getEnd();
			if(end != null){
				range += end.getValue();
			}
		}
		return range;
	}

	private void updateRnaEdComments(Map<String, String> map, CommentType type, List<RnaEditingComment> reComments) {
		if ((reComments == null) || reComments.isEmpty())
			return;
		String result = reComments.stream()
				.map(this::mapRnaEditingCommentToString)
				.collect(Collectors.joining(";  "));
		map.put("cc:rna_editing", result);
	}

	private  String mapRnaEditingCommentToString(RnaEditingComment rnaEditingComment) {
		String result = "";
		if(rnaEditingComment.getLocationType() != null){
			result += " "+rnaEditingComment.getLocationType().name();
		}
		if(rnaEditingComment.getPositions() != null){
			result += " "+rnaEditingComment.getPositions().stream()
					.map(this::mapRnaEdPositionToString)
					.collect(Collectors.joining("; "));
		}

		if(rnaEditingComment.getNote() != null){
			result += " "+EntryMapUtil.getNoteString(rnaEditingComment.getNote());
		}
		return result;
	}

	private String mapRnaEdPositionToString(RnaEdPosition rnaEdPosition) {
		String rnaPositionString = rnaEdPosition.getPosition();
		if(rnaEdPosition.getPosition() != null && !rnaEdPosition.getPosition().isEmpty()){
			rnaPositionString += " "+EntryMapUtil.evidencesToString(rnaEdPosition.getEvidences());
		}
		return rnaPositionString;
	}

	private void updateSeqCautionComments(Map<String, String> map, CommentType type,
			List<SequenceCautionComment> scComments) {
		if ((scComments == null) || scComments.isEmpty())
			return;
		String nonErroneousPrediction = scComments.stream()
				.filter(val -> !SequenceCautionType.ERRONEOUS_PREDICTION.equals(val.getSequenceCautionType()))
				.map(this::sequenceCautionToString)
				.collect(Collectors.joining(";  "));
		if(!nonErroneousPrediction.isEmpty()) {
			map.put("cc:sequence_caution", "SEQUENCE CAUTION:  "+nonErroneousPrediction);
		}
		String erroneousPrediction= scComments.stream()
				.filter(val -> SequenceCautionType.ERRONEOUS_PREDICTION.equals(val.getSequenceCautionType()))
				.map(this::sequenceCautionToString)
				.collect(Collectors.joining(";  "));
		if(!erroneousPrediction.isEmpty()) {
			map.put("error_gmodel_pred", "SEQUENCE CAUTION:  "+erroneousPrediction);
		}
	}

	private String sequenceCautionToString(SequenceCautionComment sequenceCautionComment) {
		String sequenceCautionStr = "";
		if(sequenceCautionComment.getSequence() != null){
			sequenceCautionStr+= "Sequence="+sequenceCautionComment.getSequence()+";";
		}
		if(sequenceCautionComment.getSequenceCautionType()!= null){
			sequenceCautionStr+= " Type="+sequenceCautionComment.getSequenceCautionType().name()+";";
		}
		if(sequenceCautionComment.getPositions()!= null && !sequenceCautionComment.getPositions().isEmpty()){
			sequenceCautionStr+= " Positions="+String.join(", ", sequenceCautionComment.getPositions())+";";
		}
		if(sequenceCautionComment.getNote()!= null){
			sequenceCautionStr+= " Note="+sequenceCautionComment.getNote()+";";
		}
		if(sequenceCautionComment.getEvidences()!= null){
			sequenceCautionStr+= " Evidence="+EntryMapUtil.evidencesToString(sequenceCautionComment.getEvidences()).trim()+";";
		}
		return sequenceCautionStr;
	}

	private void updateSubCellLocComments(Map<String, String> map, CommentType type, List<SubcellularLocationComment> sclComments) {
		if ((sclComments == null) || sclComments.isEmpty())
			return;

		String result = sclComments.stream().map(subcellularLocationComment -> {
			String subcellularLocationStr = "";
			if(subcellularLocationComment.getMolecule() != null && !subcellularLocationComment.getMolecule().isEmpty()){
				subcellularLocationStr += subcellularLocationComment.getMolecule();
			}
			if(subcellularLocationComment.getNote() != null){
				subcellularLocationStr += EntryMapUtil.getNoteString(subcellularLocationComment.getNote());
			}
			if(subcellularLocationComment.getSubcellularLocations() != null && !subcellularLocationComment.getSubcellularLocations().isEmpty()) {
				subcellularLocationStr += subcellularLocationComment.getSubcellularLocations().stream()
						.map(subcellularLocation -> {
							String locationStr = "";
							if(subcellularLocation.getLocation() != null){
								locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getLocation());
							}
							if(subcellularLocation.getOrientation() != null){
								locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getOrientation());
							}
							if(subcellularLocation.getTopology() != null){
								locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getTopology());
							}
							return locationStr;
						}).collect(Collectors.joining(". "))+".";
			}
			return subcellularLocationStr;
		}).collect(Collectors.joining(";  "));
		map.put("cc:subcellular_location", type.toDisplayName()+": "+result);

	}

	private void updateTextComments(Map<String, String> map, CommentType type, List<FreeTextComment> txtComments) {
		if ((txtComments == null) || txtComments.isEmpty())
			return;
		String value = txtComments.stream().map(freeTextComment ->
				type.name()+": "+freeTextComment.getTexts().stream()
					.map(EntryMapUtil::evidencedValueToString)
					.collect(Collectors.joining("; "))
		).collect(Collectors.joining(".; "));
		String field = "cc:" + type.name().toLowerCase();
		map.put(field, value+".");
	}

	private void updateProteinFamility(Map<String, String> map, CommentType type, List<FreeTextComment> txtComments) {
		if ((txtComments == null) || txtComments.isEmpty() || type != CommentType.SIMILARITY)
			return;
		
		String value = txtComments.stream().flatMap(val->val.getTexts().stream())
				.map(val -> convertToProteinFamily(val.getValue()))
				.filter(val -> val != null && !val.isEmpty())
				.collect(Collectors.joining("; "));
		String field = "protein_families";
		map.put(field, value);
	}

	private String convertToProteinFamily(String text) {
		String val = text;
		if(!val.endsWith(".")) {
			val +=".";
		}
		Matcher m = PATTERN_FAMILY.matcher(val);
		if (m.matches()){
			StringBuilder line = new StringBuilder();
			line.append(m.group(1));
			if (m.group(2) != null)
				line.append(", ").append(m.group(2));
			if (m.group(3) != null)
				line.append(", ").append(m.group(3));
			String result = line.toString();
			return result.substring(0, 1).toUpperCase() + result.substring(1);
		}
		return null;
	}
	public static List<String> getSequenceCautionTypes(List<Comment> comments) {
		if (comments == null)
			return Collections.emptyList();
		Map<String, Long> values = comments.stream()
				.filter(comment -> (comment.getCommentType().equals(CommentType.SEQUENCE_CAUTION)))
				.map(val -> (SequenceCautionComment) val).map(val -> val.getSequenceCautionType().name())
				.collect(Collectors.groupingBy(val -> val, TreeMap::new, Collectors.counting()));
		return values.entrySet().stream().map(val -> (val.getKey() + " (" + val.getValue().toString() + ")"))
				.collect(Collectors.toList());

	}

	@SuppressWarnings("unchecked")
	private <T extends Comment> List<T> getComments(CommentType type) {
		return comments.stream().filter(comment -> (comment.getCommentType().equals(type))).map(val -> (T) val)
				.collect(Collectors.toList());
	}

	public static boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
		
	}
}
