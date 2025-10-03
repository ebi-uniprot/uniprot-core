package org.uniprot.core.xml.uniprot;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Random;

public class SplitUniProtXML {
    public static void main(String[] args) {
        try {
            File outputDir = new File("/Users/sahmad/work/protnlm-data/google/");
            // Input UniProt XML file
            File inputFile = new File("/Users/sahmad/work/protnlm-data/post-processed_10k_Aug29.xml");

            // Parse XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Random generator
            Random random = new Random();

            // Get all entry nodes
            NodeList entryList = doc.getElementsByTagNameNS("http://uniprot.org/uniprot", "entry");

            for (int i = 0; i < entryList.getLength(); i++) {
                Node entryNode = entryList.item(i);

                if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryElement = (Element) entryNode;

                    // 🔹 Update <organism>/<dbReference>
                    NodeList organisms = entryElement.getElementsByTagNameNS("http://uniprot.org/uniprot", "organism");
                    for (int j = 0; j < organisms.getLength(); j++) {
                        Element organism = (Element) organisms.item(j);

                        // Update dbReference id with random int
                        NodeList dbRefs = organism.getElementsByTagNameNS("http://uniprot.org/uniprot", "dbReference");
                        for (int k = 0; k < dbRefs.getLength(); k++) {
                            Element dbRef = (Element) dbRefs.item(k);
                            if ("PLACEHOLDER".equals(dbRef.getAttribute("id"))) {
                                dbRef.setAttribute("id", String.valueOf(random.nextInt(1000000)));
                            }
                        }

                        // 🔹 Add <lineage><taxon>PLACEHOLDER</taxon></lineage>
                        NodeList lineageNodes = organism.getElementsByTagNameNS("http://uniprot.org/uniprot", "lineage");
                        if (lineageNodes.getLength() == 0) {
                            Element lineage = doc.createElementNS("http://uniprot.org/uniprot", "lineage");
                            Element taxon = doc.createElementNS("http://uniprot.org/uniprot", "taxon");
                            taxon.setTextContent("PLACEHOLDER");
                            lineage.appendChild(taxon);
                            organism.appendChild(lineage);
                        }
                    }

                    // 🔹 Update <reference>/<citation>
                    NodeList citations = entryElement.getElementsByTagNameNS("http://uniprot.org/uniprot", "citation");
                    for (int j = 0; j < citations.getLength(); j++) {
                        Element citation = (Element) citations.item(j);
                        citation.setAttribute("date", String.valueOf(random.nextInt(1000000)));
                    }

                    // Get accession value (first one only)
                    String accession = entryElement.getElementsByTagNameNS("http://uniprot.org/uniprot", "accession")
                            .item(0).getTextContent();

                    // Create new document for single entry
                    Document newDoc = dBuilder.newDocument();

                    // Create root <uniprot> with namespace
                    Element root = newDoc.createElementNS("http://uniprot.org/uniprot", "uniprot");
                    newDoc.appendChild(root);

                    // Import the entry node into new document
                    Node importedEntry = newDoc.importNode(entryNode, true);
                    root.appendChild(importedEntry);

                    // Write new file as accession.xml
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    DOMSource source = new DOMSource(newDoc);
                    StreamResult result = new StreamResult(new File(outputDir,accession + ".xml"));
                    transformer.transform(source, result);

                    System.out.println("Saved: " + accession + ".xml");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
