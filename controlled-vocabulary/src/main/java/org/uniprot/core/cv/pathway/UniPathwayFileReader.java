package org.uniprot.core.cv.pathway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.util.Utils;

public class UniPathwayFileReader extends AbstractFileReader<UniPathway> {

    @Override
    public List<UniPathway> parseLines(List<String> lines) {
        List<UniPathway> pathways =
                lines.stream()
                        .filter(val -> !val.startsWith("**"))
                        .map(this::convert)
                        .collect(Collectors.toList());
        updateRelationship(pathways);
        cleanRelations(pathways);
        return pathways;
    }

    private UniPathway convert(String line) {
        int index = line.indexOf(" ");
        String accession = line.substring(0, index);
        String value = line.substring(index + 1);
        return new UniPathway(accession, value);
    }

    private void updateRelationship(List<UniPathway> pathways) {
        Map<String, UniPathway> pathwayIdMap =
                pathways.stream()
                        .collect(Collectors.toMap(UniPathway::getAccession, Function.identity()));
        for (UniPathway pathway : pathways) {
            UniPathway parent = getParent(pathway, pathwayIdMap);
            if (parent != null) {
                pathway.setParent(parent);
                if (!parent.getChildren().contains(pathway)) {
                    parent.getChildren().add(pathway);
                }
            }
        }
    }

    private UniPathway getParent(UniPathway pathway, Map<String, UniPathway> pathwayIdMap) {
        String id = pathway.getAccession();
        int index = id.lastIndexOf(".");
        if (index == -1) {
            return null;
        } else {
            String parentId = id.substring(0, index);
            return pathwayIdMap.get(parentId);
        }
    }

    private void cleanRelations(List<UniPathway> list) {
        for (UniPathway entry : list) {
            entry.setParent(cleanParentChildren(entry.getParent()));
            entry.setChildren(cleanChildrenParent(entry.getChildren()));
        }
    }

    private UniPathway cleanParentChildren(UniPathway parent) {
        if (parent != null) {
            UniPathway newEntry = new UniPathway(parent.getAccession(), parent.getName());
            newEntry.setParent(cleanParentChildren(parent.getParent()));
            return newEntry;
        } else {
            return parent;
        }
    }

    private List<UniPathway> cleanChildrenParent(List<UniPathway> children) {
        List<UniPathway> result = new ArrayList<>();
        if (Utils.notNullNotEmpty(children)) {
            for (UniPathway child : children) {
                UniPathway newEntry = new UniPathway(child.getAccession(), child.getName());
                newEntry.setChildren(cleanChildrenParent(child.getChildren()));
                result.add(newEntry);
            }
        }
        return result;
    }
}
