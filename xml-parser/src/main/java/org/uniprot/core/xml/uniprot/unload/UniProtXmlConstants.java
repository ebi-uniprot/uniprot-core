package org.uniprot.core.xml.uniprot.unload;

public interface UniProtXmlConstants {
    public static final String HEADER =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<uniprot xmlns=\"http://uniprot.org/uniprot\"\n"
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                    + " xsi:schemaLocation=\"http://uniprot.org/uniprot"
                    + " http://www.uniprot.org/docs/uniprot.xsd\">\n";
    public static final String FOOTER =
            "<copyright>\n"
                    + "Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms"
                    + " Distributed under the Creative Commons Attribution (CC BY 4.0) License\n"
                    + "</copyright>\n"
                    + "</uniprot>";
}
