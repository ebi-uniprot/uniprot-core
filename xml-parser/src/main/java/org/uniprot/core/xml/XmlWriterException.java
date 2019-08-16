package org.uniprot.core.xml;

/**
 *
 * @author jluo
 * @date: 15 Aug 2019
 *
*/

public class XmlWriterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5565439459161616710L;

	public XmlWriterException(Throwable throwable) {
        super(throwable);
    }
	public XmlWriterException(String message) {
        super(message);
    }

	public XmlWriterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

