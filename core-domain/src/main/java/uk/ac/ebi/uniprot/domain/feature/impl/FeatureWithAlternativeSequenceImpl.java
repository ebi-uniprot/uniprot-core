package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.HasAlternativeSequence;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FeatureWithAlternativeSequenceImpl extends FeatureImpl implements HasAlternativeSequence {
    public static FeatureSequence createFeatureSequence(String value) {
        return new FeatureSequenceImpl(value);
    }

    public static SequenceReport createSequenceReport(List<String> value) {
        return new SequenceReportImpl(value);
    }

    public static SequenceReport createSequenceReport(String value) {
        return new SequenceReportImpl(value);
    }

    
    private final FeatureSequence orginalSequence;
    private final List<FeatureSequence> alternativeSequences;
    private final SequenceReport report;

    
    public FeatureWithAlternativeSequenceImpl(FeatureType type, FeatureLocation location,
            String orginalSequence, List<String> alternativeSequences, List<String> report) {
            super(type, location, "");
            this.orginalSequence = createFeatureSequence(orginalSequence);
            if ((alternativeSequences == null) || alternativeSequences.isEmpty())
                this.alternativeSequences = Collections.emptyList();
            else {
                this.alternativeSequences = 
                        alternativeSequences.stream()
                        .map(val ->createFeatureSequence(val) )
                        .collect(Collectors.toList());
            }
            this.report = createSequenceReport(report);
          
        }
    
    public FeatureWithAlternativeSequenceImpl(FeatureType type, FeatureLocation location,
        FeatureSequence orginalSequence,
        List<FeatureSequence> alternativeSequences, SequenceReport report) {
        super(type, location, "");
        this.orginalSequence = orginalSequence;
        if ((alternativeSequences == null) || alternativeSequences.isEmpty())
            this.alternativeSequences = Collections.emptyList();
        else
            this.alternativeSequences = Collections.unmodifiableList(alternativeSequences);
        this.report = report;
       
    }

    @Override
    public FeatureSequence getOriginalSequence() {
        return orginalSequence;
    }

    @Override
    public List<FeatureSequence> getAlternativeSequences() {
        return alternativeSequences;
    }

    @Override
    public SequenceReport getReport() {
        return report;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((alternativeSequences == null) ? 0 : alternativeSequences.hashCode());
        result = prime * result + ((orginalSequence == null) ? 0 : orginalSequence.hashCode());
        result = prime * result + ((report == null) ? 0 : report.hashCode());
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
        FeatureWithAlternativeSequenceImpl other = (FeatureWithAlternativeSequenceImpl) obj;
        if (alternativeSequences == null) {
            if (other.alternativeSequences != null)
                return false;
        } else if (!alternativeSequences.equals(other.alternativeSequences))
            return false;
        if (orginalSequence == null) {
            if (other.orginalSequence != null)
                return false;
        } else if (!orginalSequence.equals(other.orginalSequence))
            return false;
        if (report == null) {
            if (other.report != null)
                return false;
        } else if (!report.equals(other.report))
            return false;
        return true;
    }


    static class SequenceReportImpl implements SequenceReport {
        private final List<String> report;
        SequenceReportImpl(String val) {
            if ((val == null) || val.isEmpty())
                this.report = Collections.emptyList();
            else
                this.report = Arrays.asList(val);
        }
        
        SequenceReportImpl(List<String> val) {
            if ((val == null) || val.isEmpty())
                this.report = Collections.emptyList();
            else
                this.report = Collections.unmodifiableList(val);
        }

        @Override
        public List<String> getReport() {
            return report;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((report == null) ? 0 : report.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SequenceReportImpl other = (SequenceReportImpl) obj;
            if (report == null) {
                if (other.report != null)
                    return false;
            } else if (!report.equals(other.report))
                return false;
            return true;
        }

    

    }
    
    static class FeatureSequenceImpl implements FeatureSequence {
        private final String value;

        FeatureSequenceImpl(String val) {
            this.value = val;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            FeatureSequenceImpl other = (FeatureSequenceImpl) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

    }

}
