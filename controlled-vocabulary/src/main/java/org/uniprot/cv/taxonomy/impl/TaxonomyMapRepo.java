package org.uniprot.cv.taxonomy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.cv.taxonomy.TaxonMappingException;
import org.uniprot.cv.taxonomy.TaxonomicNode;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A taxonomy service that stores the various taxonomy nodes in a map The node can be fetched by the
 * taxonomyId
 */
public class TaxonomyMapRepo implements TaxonomyRepo {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<Integer, TaxonomicNode> taxonMap;

    public TaxonomyMapRepo(Iterable<TaxonomicNode> nodes) {
        if (nodes == null) {
            throw new IllegalArgumentException("Taxonomy iterable of nodes to process is null");
        }

        taxonMap = new HashMap<>();

        populateMap(nodes);
    }

    @Override
    public Optional<TaxonomicNode> retrieveNodeUsingTaxID(int taxonId) {
        return Optional.ofNullable(taxonMap.get(taxonId));
    }

    private void populateMap(Iterable<TaxonomicNode> nodes) {
        logger.info("Populating taxonomy map");

        for (TaxonomicNode node : nodes) {
            TaxonomicNode nodeProxy = createProxyNode(node);
            taxonMap.put(nodeProxy.id(), nodeProxy);
        }

        logger.info("Finished populating taxonomy map");
    }

    private TaxonomicNode createProxyNode(TaxonomicNode node) {
        TaxonomicNodeProxy proxyNode = new TaxonomicNodeProxy();

        proxyNode.id = node.id();
        proxyNode.scientificName = node.scientificName();
        proxyNode.commonName = node.commonName();
        proxyNode.synonymName = node.synonymName();
        proxyNode.mnemonic = node.mnemonic();

        if (node.hasParent()) {
            proxyNode.parentTaxID = node.parent().id();
        }

        return proxyNode;
    }

    /**
     * A proxy class for the {@link TaxonomicNode}.
     *
     * <p>The proxy will gain access to the parent node through the Taxonomy repository
     */
    private class TaxonomicNodeProxy implements TaxonomicNode {
        int id;
        Integer parentTaxID;

        String scientificName;
        String commonName;
        String synonymName;
        String mnemonic;

        @Override
        public int id() {
            return id;
        }

        @Override
        public String scientificName() {
            return scientificName;
        }

        @Override
        public String commonName() {
            return commonName;
        }

        @Override
        public String synonymName() {
            return synonymName;
        }

        @Override
        public String mnemonic() {
            return mnemonic;
        }

        @Override
        public TaxonomicNode parent() {
            TaxonomicNode parentNode = null;

            if (parentTaxID != null) {
                parentNode =
                        retrieveNodeUsingTaxID(parentTaxID)
                                .orElseThrow(
                                        () ->
                                                new TaxonMappingException(
                                                        "Taxonomy repo is pointing to a parent"
                                                            + " that does not exist: "
                                                                + parentTaxID));
            }

            return parentNode;
        }

        @Override
        public boolean hasParent() {
            return parentTaxID != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TaxonomicNodeProxy that = (TaxonomicNodeProxy) o;
            return Objects.equals(this.id, that.id)
                    && Objects.equals(this.commonName, that.commonName)
                    && Objects.equals(this.parentTaxID, that.parentTaxID)
                    && Objects.equals(this.scientificName, that.scientificName)
                    && Objects.equals(this.synonymName, that.synonymName)
                    && Objects.equals(this.mnemonic, that.mnemonic);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    this.id,
                    this.parentTaxID,
                    this.scientificName,
                    this.commonName,
                    this.synonymName,
                    this.mnemonic);
        }

        @Override
        public String toString() {
            return "TaxonomicNodeProxy{"
                    + "id="
                    + id
                    + ", parentTaxID="
                    + parentTaxID
                    + ", scientificName='"
                    + scientificName
                    + '\''
                    + ", commonName='"
                    + commonName
                    + '\''
                    + ", synonymName='"
                    + synonymName
                    + '\''
                    + ", mnemonic='"
                    + mnemonic
                    + '\''
                    + '}';
        }
    }
}
