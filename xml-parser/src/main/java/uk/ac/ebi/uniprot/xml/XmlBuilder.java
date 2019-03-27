package uk.ac.ebi.uniprot.xml;

import java.util.List;

public interface XmlBuilder {
    
    XmlBuildStats build(List<String> dataFile, String xmlFile);
}
