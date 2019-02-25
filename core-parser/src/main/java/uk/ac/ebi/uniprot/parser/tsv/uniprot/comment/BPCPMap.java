package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BPCPMap implements NamedValueMap {

    private final List<BPCPComment> bpcpComments;

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
                bpcpCommentMap.put("kinetics", "BIOPHYSICOCHEMICAL PROPERTIES:  " + kinetics+";");
            }
            String phDependence = phDependenceToString(bpcpComments);
            if (!phDependence.isEmpty()) {
                bpcpCommentMap.put("ph_dependence", "BIOPHYSICOCHEMICAL PROPERTIES:  pH dependence: " + phDependence+";");
            }
            String redoxPotential = redoxPotentialToString(bpcpComments);
            if (!redoxPotential.isEmpty()) {
                bpcpCommentMap.put("redox_potential", "BIOPHYSICOCHEMICAL PROPERTIES:  Redox potential: " + redoxPotential+";");
            }
            String tempDependence = tempDependenceToString(bpcpComments);
            if (!tempDependence.isEmpty()) {
                bpcpCommentMap.put("temp_dependence", "BIOPHYSICOCHEMICAL PROPERTIES:  Temperature dependence: " + tempDependence+";");
            }
        }
        return bpcpCommentMap;
    }

    private String absorptionToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream()
                .filter(BPCPComment::hasAbsorption)
                .map(BPCPComment::getAbsorption)
                .map(this::absortionParameterToString)
                .collect(Collectors.joining(" "));

    }

    private String absortionParameterToString(Absorption absorption) {
        List<String> result = new ArrayList<>();
        if(absorption.hasMax()) {
            String prefix = "Abs(max)=";
            if(absorption.isApproximate()) {
                prefix += "~";
            }
            result.add(prefix + absorption.getMax() + " nm");

        }
        if(absorption.hasEvidences()){
            result.add(EntryMapUtil.evidencesToString(absorption.getEvidences())+";");
        }
        if(absorption.hasNote()) {
            result.add(EntryMapUtil.getNoteStringWithoutDot(absorption.getNote())+";");
        }
        return "Absorption: "+String.join(" ",result);
    }

    private String kineticsToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream()
                .filter(BPCPComment::hasKineticParameters)
                .map(BPCPComment::getKineticParameters)
                .map(this::kinectParameterToString)
                .collect(Collectors.joining("; "));
    }

    private  String kinectParameterToString(KineticParameters kineticParameters) {
        List<String> result = new ArrayList<>();
        if(kineticParameters.hasMichaelisConstants()){
            result.add(kineticParameters.getMichaelisConstants().stream()
                    .map(this::getMichaelConstantsString)
                    .collect(Collectors.joining("; ")));
        }
        if(kineticParameters.hasMaximumVelocities()) {
            result.add(kineticParameters.getMaximumVelocities().stream()
                    .map(this::getMaximumVelocitiesString)
                    .collect(Collectors.joining("; ")));
        }
        if(kineticParameters.hasNote()) {
            result.add(EntryMapUtil.getNoteStringWithoutDot(kineticParameters.getNote()));
        }
        return "Kinetic parameters: "+String.join("; ",result);
    }

    private String getMaximumVelocitiesString(MaximumVelocity maximumVelocity) {
        String result = "Vmax="+EntryMapUtil.formatDouble(maximumVelocity.getVelocity())+" "
                +maximumVelocity.getUnit()+ " "
                +maximumVelocity.getEnzyme();
        if(maximumVelocity.hasEvidences()) {
            result += " "+EntryMapUtil.evidencesToString(maximumVelocity.getEvidences());
        }
        return result;
    }

    private String getMichaelConstantsString(MichaelisConstant michaelisConstant) {
        String result = "KM="+EntryMapUtil.formatDouble(michaelisConstant.getConstant())+" "
                +michaelisConstant.getUnit().getName()+" for "
                +michaelisConstant.getSubstrate();
        if(michaelisConstant.hasEvidences()) {
            result += " "+EntryMapUtil.evidencesToString(michaelisConstant.getEvidences());
        }
        return result;
    }

    private String phDependenceToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream()
                .filter(BPCPComment::hasPhDependence)
                .map(BPCPComment::getPhDependence)
                .map(phDependence ->
                        phDependence.getTexts().stream()
                                .map(EntryMapUtil::evidencedValueToString)
                                .collect(Collectors.joining("; "))
                ).collect(Collectors.joining("; "));
    }

    private String redoxPotentialToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream()
                .filter(BPCPComment::hasRedoxPotential)
                .map(BPCPComment::getRedoxPotential)
                .map(redoxPotential ->
                        redoxPotential.getTexts().stream()
                                .map(EntryMapUtil::evidencedValueToString)
                                .collect(Collectors.joining("; "))
                ).collect(Collectors.joining("; "));
    }

    private String tempDependenceToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream()
                .filter(BPCPComment::hasTemperatureDependence)
                .map(BPCPComment::getTemperatureDependence)
                .map(temperatureDependence ->
                        temperatureDependence.getTexts().stream()
                                .map(EntryMapUtil::evidencedValueToString)
                                .collect(Collectors.joining("; "))
                ).collect(Collectors.joining("; "));
    }

}
