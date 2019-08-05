package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.impl.cc.CCBioPhyChemCommentLineBuilder;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprot.comment.Absorption;
import org.uniprot.core.uniprot.comment.BPCPComment;
import org.uniprot.core.uniprot.comment.KineticParameters;

public class BPCPMap implements NamedValueMap {

	private final List<BPCPComment> bpcpComments;
	private final CCBioPhyChemCommentLineBuilder lineBuilder = new CCBioPhyChemCommentLineBuilder();

	public BPCPMap(List<BPCPComment> bpcpComments) {
		this.bpcpComments = bpcpComments;
	}

	@Override
	public Map<String, String> attributeValues() {
		return getBioPhyChemPropComments(this.bpcpComments);
	}

	private Map<String, String> getBioPhyChemPropComments(List<BPCPComment> bpcpComments) {
		Map<String, String> bpcpCommentMap = new HashMap<>();
		if ((bpcpComments != null)) {
			String absorption = absorptionToString(bpcpComments);
			if (!absorption.isEmpty()) {
				bpcpCommentMap.put("absorption", "BIOPHYSICOCHEMICAL PROPERTIES:  " + absorption);
			}
			String kinetics = kineticsToString(bpcpComments);
			if (!kinetics.isEmpty()) {
				bpcpCommentMap.put("kinetics", "BIOPHYSICOCHEMICAL PROPERTIES:  " + kinetics);
			}
			String phDependence = phDependenceToString(bpcpComments);
			if (!phDependence.isEmpty()) {
				bpcpCommentMap.put("ph_dependence", "BIOPHYSICOCHEMICAL PROPERTIES:  " + phDependence);
			}
			String redoxPotential = redoxPotentialToString(bpcpComments);
			if (!redoxPotential.isEmpty()) {
				bpcpCommentMap.put("redox_potential", "BIOPHYSICOCHEMICAL PROPERTIES:  " + redoxPotential);
			}
			String tempDependence = tempDependenceToString(bpcpComments);
			if (!tempDependence.isEmpty()) {
				bpcpCommentMap.put("temp_dependence", "BIOPHYSICOCHEMICAL PROPERTIES:  " + tempDependence);
			}
		}
		return bpcpCommentMap;
	}

	private String absorptionToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().filter(BPCPComment::hasAbsorption).map(BPCPComment::getAbsorption)
				.map(this::absortionParameterToString).collect(Collectors.joining(" "));

	}

	private String absortionParameterToString(Absorption absorption) {
		return lineBuilder.buildAbsorptionLine(absorption, false, true).stream().collect(Collectors.joining(" "));
	}

	private String kineticsToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().filter(BPCPComment::hasKineticParameters).map(BPCPComment::getKineticParameters)
				.map(this::kinectParameterToString).collect(Collectors.joining(" "));
	}

	private String kinectParameterToString(KineticParameters kineticParameters) {
		return lineBuilder.buildKineticLine(kineticParameters, false, true).stream().collect(Collectors.joining(" "));
	}

	private String phDependenceToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().filter(BPCPComment::hasPhDependence).map(BPCPComment::getPhDependence)
				.map(phDependence -> lineBuilder.buildPHDepLine(phDependence, false, true).stream()
						.collect(Collectors.joining(" ")))
				.collect(Collectors.joining(" "));
	}

	private String redoxPotentialToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().filter(BPCPComment::hasRedoxPotential).map(BPCPComment::getRedoxPotential)
				.map(redoxPotential -> lineBuilder.buildRedoxLine(redoxPotential, false, true).stream()
						.collect(Collectors.joining(" ")))
				.collect(Collectors.joining(" "));
	}

	private String tempDependenceToString(List<BPCPComment> bpcpComments) {
		return bpcpComments.stream().filter(BPCPComment::hasTemperatureDependence)
				.map(BPCPComment::getTemperatureDependence).map(temperatureDependence -> lineBuilder
						.buildTempLine(temperatureDependence, false, true).stream().collect(Collectors.joining(" ")))
				.collect(Collectors.joining(" "));
	}

}
