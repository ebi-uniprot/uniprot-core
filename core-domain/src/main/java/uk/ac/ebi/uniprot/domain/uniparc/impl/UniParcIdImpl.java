package uk.ac.ebi.uniprot.domain.uniparc.impl;

import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcId;

/**
 *
 * @author jluo
 * @date: 22 May 2019
 *
*/

public class UniParcIdImpl extends ValueImpl  implements UniParcId {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6122456799951248540L;
    private static final String UNIPARC_ACC_REX = "^UPI\\w{10}";
    private static final Pattern UNIPARC_ACC_PATTERN = Pattern.compile(UNIPARC_ACC_REX, Pattern.CASE_INSENSITIVE);
    
	protected UniParcIdImpl() {
		super(null);
	}
	public UniParcIdImpl(String value) {
		super(value);
	}


	@Override
	public boolean isValidId() {
		 return UNIPARC_ACC_PATTERN.matcher(this.getValue()).matches();
	}

	
}

