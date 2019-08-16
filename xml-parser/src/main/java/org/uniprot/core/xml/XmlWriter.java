package org.uniprot.core.xml;

import java.util.stream.Stream;

/**
 *
 * @author jluo
 * @date: 15 Aug 2019
 *
 */

public interface XmlWriter<T> {
	void write(T entry);

	void writeHeader(String header);

	void init();

	int write(Stream<T> entries);

	void writeFooter(String footer);
}
