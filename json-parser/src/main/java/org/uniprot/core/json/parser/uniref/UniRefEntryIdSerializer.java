package org.uniprot.core.json.parser.uniref;

import java.io.IOException;

import org.uniprot.core.uniparc.impl.UniParcIdImpl;
import org.uniprot.core.uniref.impl.UniRefEntryIdImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class UniRefEntryIdSerializer extends StdSerializer<UniRefEntryIdImpl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3741474487836075645L;
	 public UniRefEntryIdSerializer() {
	        super(UniRefEntryIdImpl.class);
	    }

	
	@Override
	public void serialize(UniRefEntryIdImpl value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException {
		 jsonGenerator.writeString(value.getValue());
		
	}

}

