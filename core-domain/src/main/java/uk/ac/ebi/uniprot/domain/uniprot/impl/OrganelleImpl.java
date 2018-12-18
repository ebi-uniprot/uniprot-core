package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrganelleImpl extends EvidencedValueImpl implements Organelle {
    private GeneEncodingType geneEncodingType;

    private OrganelleImpl() {
        super("", Collections.emptyList());
    }

    public OrganelleImpl(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences) {
        super(value, evidences);
        this.geneEncodingType = geneEncodingType;
    }

    @Override
    public GeneEncodingType getGeneEncodingType() {
        return geneEncodingType;
    }

    @Override
    public String getDisplayed(String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(getString());
        if (!this.getEvidences().isEmpty()) {
            sb.append(separator)
                    .append(" {")
                    .append(
                            getEvidences().stream()
                                    .map(val -> val.getValue())
                                    .collect(Collectors.joining(", ")))
                    .append("}");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((geneEncodingType == null) ? 0 : geneEncodingType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrganelleImpl other = (OrganelleImpl) obj;
        if (geneEncodingType != other.geneEncodingType)
            return false;
        return true;
    }

    private String getString() {

        StringBuilder sb = new StringBuilder();
        switch (getGeneEncodingType()) {

            case HYDROGENOSOME:
            case MITOCHONDRION:
            case NUCLEOMORPH:
            case PLASTID:
                sb.append(getGeneEncodingType().getName());
                break;

            case PLASMID:
                sb.append(getGeneEncodingType().getName());
                if ((getValue() != null) && !getValue().isEmpty()) {
                    sb.append(" ");
                    sb.append(getValue());
                }
                break;
            case APICOPLAST_PLASTID:
            case CHLOROPLAST_PLASTID:
            case CYANELLE_PLASTID:
            case NON_PHOTOSYNTHETIC_PLASTID:
            case CHROMATOPHORE_PLASTID:
                sb.append("Plastid; ");
                sb.append(getGeneEncodingType().getName());
                break;
            case UNKOWN:
                break;
        }

        return sb.toString();
    }


}
