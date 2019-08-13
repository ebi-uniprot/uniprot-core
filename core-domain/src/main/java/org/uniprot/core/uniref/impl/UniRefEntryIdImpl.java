package org.uniprot.core.uniref.impl;

import java.util.regex.Pattern;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniref.UniRefEntryId;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class UniRefEntryIdImpl extends ValueImpl implements UniRefEntryId {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713755226228516408L;
	private static final String UNIREF_ACC_REX = "^UniRef100\\_.{6,16}+|^UniRef90\\_.{6,16}+|^UniRef50\\_.{6,16}+";
    private static final Pattern UNIREF_ACC_PATTERN = Pattern.compile(UNIREF_ACC_REX, Pattern.CASE_INSENSITIVE);
	 protected UniRefEntryIdImpl() {
	        super(null);
	    }

	public UniRefEntryIdImpl(String value) {
		super(value);
	}

	@Override
	public boolean isValidId() {
		 return UNIREF_ACC_PATTERN.matcher(this.getValue()).matches();
	}

}

