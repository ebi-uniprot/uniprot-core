package org.uniprot.core.uniprot.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

public class GeneLocationImpl extends EvidencedValueImpl implements GeneLocation {
    private static final long serialVersionUID = -3156758801647353702L;
    private GeneEncodingType geneEncodingType;

    private GeneLocationImpl() {
        super("", Collections.emptyList());
    }

    public GeneLocationImpl(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences) {
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
        GeneLocationImpl other = (GeneLocationImpl) obj;
        return geneEncodingType == other.geneEncodingType;
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
            case APICOPLAST:
            case CHLOROPLAST:
            case CYANELLE:
            case NON_PHOTOSYNTHETIC_PLASTID:
            case ORGANELLAR_CHROMATOPHORE:
                sb.append("Plastid; ");
                sb.append(getGeneEncodingType().getName());
                break;
            case UNKOWN:
                break;
        }

        return sb.toString();
    }


}
