package org.uniprot.core.xml;

import java.util.List;

public interface XmlBuilder {

    XmlBuildStats build(List<String> dataFile, String xmlFile);
}
