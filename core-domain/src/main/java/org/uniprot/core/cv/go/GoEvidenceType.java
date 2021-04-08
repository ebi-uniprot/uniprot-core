package org.uniprot.core.cv.go;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 8 Apr 2021
 *
*/

public enum GoEvidenceType implements EnumDisplay {
	EXP("Inferred from experiment"),
	
	HDA("high throughput direct assay evidence used in manual assertion"),
	HEP("high throughput expression pattern evidence used in manual assertion"),
	HGI("high throughput genetic interaction evidence used in manual assertion"),
	HMP("high throughput mutant phenotype evidence used in manual assertion"),	
	HTP("high throughput evidence used in manual assertion"),
		
	IBA("inferred from Biological aspect of Ancestor"),
	IBD("inferred from Biological aspect of Descendant"),
	IC("inferred by curator"),
	IDA("inferred from direct assay"),
	IEA("inferred from electronic annotation"),
	IEP("inferred from expression pattern"),
	IGC("inferred from Genomic Context"),
	IGI("inferred from genetic interaction"),
	IKR("inferred from Key Residues"),
	IMP("inferred from mutant phenotype"),	
	IPI("inferred from physical interaction"),
	IRD("inferred from Rapid Divergence"),	
	ISA("inferred from Sequence Alignment"),
	ISM("inferred from Sequence Model"),
	ISO("inferred from Sequence Orthology"),
	ISS("inferred from sequence or structural similarity"),
	
	
	NAS( "non-traceable author statement"),
	ND("no biological data available"),
	RCA("inferred from reviewed computational analysis"),
	TAS("traceable author statement");

	private final String name;
	GoEvidenceType(String name){
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
    public static @Nonnull GoEvidenceType typeOf(@Nonnull String value) {
        for (GoEvidenceType featureType : GoEvidenceType.values()) {
            if (featureType.name().equalsIgnoreCase(value)) {
                return featureType;
            }
          
        }
        throw new IllegalArgumentException(
                "the Go Evidence: " + value + " doesn't exist");
    }
}

