package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                bpcpCommentMap.put("absorption", "BIOPHYSICOCHEMICAL PROPERTIES: ;  " + absorption);
            }
            String kinetics = kineticsToString(bpcpComments);
            if (!kinetics.isEmpty()) {
                bpcpCommentMap.put("kinetics", "BIOPHYSICOCHEMICAL PROPERTIES:  " + kinetics);
            }
            String phDependence = phDependenceToString(bpcpComments);
            if (!phDependence.isEmpty()) {
                bpcpCommentMap.put("ph_dependence", "BIOPHYSICOCHEMICAL PROPERTIES: ;  pH dependence: " + phDependence);
            }
            String redoxPotential = redoxPotentialToString(bpcpComments);
            if (!redoxPotential.isEmpty()) {
                bpcpCommentMap.put("redox_potential", "BIOPHYSICOCHEMICAL PROPERTIES: ;  Redox potential: " + redoxPotential);
            }
            String tempDependence = tempDependenceToString(bpcpComments);
            if (!tempDependence.isEmpty()) {
                bpcpCommentMap.put("temp_dependence", "BIOPHYSICOCHEMICAL PROPERTIES: ;  Temperature dependence: " + tempDependence);
            }
        }
        return bpcpCommentMap;
    }

    private String absorptionToString(List<BPCPComment> bpcpComments) {
        return bpcpComments.stream().map(BPCPComment::getAbsorption).filter(Objects::nonNull)
                .map(absorption -> {
                    String result = "Absorption: Abs(max)="+absorption.getMax();
                    if(absorption.getEvidences() != null && !absorption.getEvidences().isEmpty()){
                        result += " "+ EntryMapUtil.evidencesToString(absorption.getEvidences())+";";
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

}
