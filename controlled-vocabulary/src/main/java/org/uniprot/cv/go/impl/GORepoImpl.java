package org.uniprot.cv.go.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.*;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.util.Utils;
import org.uniprot.cv.go.GORepo;
import org.uniprot.cv.go.RelationshipType;

/**
 * Created 25/11/2020
 *
 * @author Edd
 */
public class GORepoImpl implements GORepo {
    private static final List<RelationshipType> DEFAULT_RELATIONSHIPS_FOR_ANCESTORS =
            asList(RelationshipType.IS_A, RelationshipType.PART_OF);
    final Map<String, Set<GeneOntologyEntry>> ancestorCache;
    private final Map<String, GeneOntologyEntry> goTermMap;
    private final Map<String, Set<GeneOntologyEntry>> isAMap;
    private final Map<String, Set<GeneOntologyEntry>> partOfMap;

    public GORepoImpl(String goDir) {
        goTermMap =
                GOTermCache.INSTANCE.get(goDir).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(GeneOntologyEntry::getId, val -> val));
        List<GORelationFileReader.GORelationshipsEntry> relationships =
                GORelationsCache.INSTANCE.get(goDir);

        Map<RelationshipType, Map<String, Set<String>>> relationshipsMap =
                relationships.stream()
                        .collect(Collectors.toMap(l -> l.relationship, l -> l.relationships));
        isAMap =
                relationshipsMap.get(RelationshipType.IS_A).entrySet().stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        val -> convert(val.getValue(), goTermMap)));

        partOfMap =
                relationshipsMap.get(RelationshipType.PART_OF).entrySet().stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        val -> convert(val.getValue(), goTermMap)));

        ancestorCache = new HashMap<>();
    }

    @Override
    public Set<GeneOntologyEntry> getIsA(String goId) {
        return isAMap.getOrDefault(goId, Collections.emptySet());
    }

    @Override
    public Set<GeneOntologyEntry> getPartOf(String goId) {
        return partOfMap.getOrDefault(goId, Collections.emptySet());
    }

    @Override
    public Set<GeneOntologyEntry> getAncestors(String goId) {
        return getAncestors(goId, DEFAULT_RELATIONSHIPS_FOR_ANCESTORS);
    }

    @Override
    public Set<GeneOntologyEntry> getAncestors(
            String fromGoTerm, Collection<RelationshipType> relationships) {
        String cacheKey = createKey(fromGoTerm, relationships);
        if (ancestorCache.containsKey(cacheKey)) {
            return ancestorCache.get(cacheKey);
        } else {
            Set<GeneOntologyEntry> ancestorsI = getAncestorsFirstTime(fromGoTerm, relationships);
            if (!ancestorsI.isEmpty()) {
                ancestorCache.put(cacheKey, ancestorsI);
            }
            return ancestorsI;
        }
    }

    @Override
    public GeneOntologyEntry getById(String goId) {
        return goTermMap.get(goId);
    }

    private String createKey(String fromGoTerm, Collection<RelationshipType> relationships) {
        return fromGoTerm
                + relationships.stream()
                        .map(RelationshipType::getDisplayName)
                        .sorted()
                        .collect(Collectors.joining(","));
    }

    private Set<GeneOntologyEntry> getAncestorsFirstTime(
            String fromGoTerm, Collection<RelationshipType> relationships) {
        Collection<RelationshipType> relationshipsToUse = relationships;
        if (Utils.nullOrEmpty(relationshipsToUse)) {
            relationshipsToUse = DEFAULT_RELATIONSHIPS_FOR_ANCESTORS;
        }

        if (Utils.notNull(fromGoTerm)) {
            Set<GeneOntologyEntry> ancestorsFound = new HashSet<>();
            addAncestors(singleton(fromGoTerm), ancestorsFound, relationshipsToUse);
            return ancestorsFound;
        } else {
            return emptySet();
        }
    }

    private Set<GeneOntologyEntry> convert(
            Set<String> goIds, Map<String, GeneOntologyEntry> gotermMap) {
        return goIds.stream()
                .map(gotermMap::get)
                .filter(Utils::notNull)
                .collect(Collectors.toSet());
    }

    private void addAncestors(
            Set<String> baseGoIds,
            Set<GeneOntologyEntry> ancestors,
            Collection<RelationshipType> relationships) {
        for (String base : baseGoIds) {
            Set<GeneOntologyEntry> parents = new HashSet<>();
            if (relationships.contains(RelationshipType.IS_A)) {
                parents.addAll(getIsA(base));
            }

            if (relationships.contains(RelationshipType.PART_OF)) {
                parents.addAll(getPartOf(base));
            }

            ancestors.addAll(parents);
            addAncestors(
                    parents.stream().map(GeneOntologyEntry::getId).collect(Collectors.toSet()),
                    ancestors,
                    relationships);
        }
    }
}
