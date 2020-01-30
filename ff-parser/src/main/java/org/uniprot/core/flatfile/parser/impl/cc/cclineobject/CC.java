package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class CC {
    private CCTopicEnum topic;
    private Object object;

    public CCTopicEnum getTopic() {
        return topic;
    }

    public void setTopic(CCTopicEnum topic) {
        this.topic = topic;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public enum CCTopicEnum {
        ALLERGEN,
        BIOTECHNOLOGY,
        CATALYTIC_ACTIVITY,
        CAUTION,
        COFACTOR,
        DEVELOPMENTAL_STAGE,
        DISEASE,
        DISRUPTION_PHENOTYPE,
        DOMAIN,
        ACTIVITY_REGULATION,
        FUNCTION,
        INDUCTION,
        MISCELLANEOUS,
        PATHWAY,
        PHARMACEUTICAL,
        POLYMORPHISM,
        PTM,
        SIMILARITY,
        SUBUNIT,
        TISSUE_SPECIFICITY,
        TOXIC_DOSE,
        ALTERNATIVE_PRODUCTS,
        BIOPHYSICOCHEMICAL_PROPERTIES,
        WEB_RESOURCE,
        INTERACTION,
        SUBCELLULAR_LOCATION,
        SEQUENCE_CAUTION,
        MASS_SPECTROMETRY,

        RNA_EDITING;

        public static CCTopicEnum fromString(String s) {
            String replace = s.replace(' ', '_');
            //            if (replace.equals("ACTIVITY_REGULATION")) {
            //                replace = "ENZYME_REGULATION";
            //            }
            return CCTopicEnum.valueOf(replace);
        }
    }
}
