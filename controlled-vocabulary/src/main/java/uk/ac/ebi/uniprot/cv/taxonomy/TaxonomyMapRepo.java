package uk.ac.ebi.uniprot.cv.taxonomy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A taxonomy service that stores the various taxonomy nodes in a
 */

public class TaxonomyMapRepo implements TaxonomyRepo {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<Integer, TaxonomicNode> taxonMap;

	public TaxonomyMapRepo(Iterable<TaxonomicNode> nodes) {
		if(nodes == null) {
			throw new IllegalArgumentException("Taxonomy iterable of nodes to process is null");
		}

		taxonMap = new HashMap<>();

		populateMap(nodes);
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

	@Override
	public Optional<TaxonomicNode> retrieveNodeUsingTaxID(int taxonId) {
		return Optional.ofNullable(taxonMap.get(taxonId));
	}

	/**
	 * A proxy class for the {@link TaxonomicNode}.
	 * <p>
	 * The proxy will gain access to the parent node through the Taxonomy repository
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
				parentNode = retrieveNodeUsingTaxID(parentTaxID)
						.orElseThrow(() ->
								new TaxonMappingException("Taxonomy repo is pointing to a parent that does not exist: "
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

			if (id != that.id) {
				return false;
			}

			if (commonName != null ? !commonName.equals(that.commonName) : that.commonName != null) {
				return false;
			}

			if (parentTaxID != null ? !parentTaxID.equals(that.parentTaxID) : that.parentTaxID != null) {
				return false;
			}

			if (scientificName != null ? !scientificName.equals(that.scientificName) : that.scientificName != null) {
				return false;
			}

			if (synonymName != null ? !synonymName.equals(that.synonymName) : that.synonymName != null) {
				return false;
			}

			if (mnemonic != null ? !mnemonic.equals(that.mnemonic) : that.mnemonic != null) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + (parentTaxID != null ? parentTaxID.hashCode() : 0);
			result = 31 * result + (scientificName != null ? scientificName.hashCode() : 0);
			result = 31 * result + (commonName != null ? commonName.hashCode() : 0);
			result = 31 * result + (synonymName != null ? synonymName.hashCode() : 0);
			result = 31 * result + (mnemonic != null ? mnemonic.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "TaxonomicNodeProxy{" +
					"id=" + id +
					", parentTaxID=" + parentTaxID +
					", scientificName='" + scientificName + '\'' +
					", commonName='" + commonName + '\'' +
					", synonymName='" + synonymName + '\'' +
					", mnemonic='" + mnemonic + '\'' +
					'}';
		}
	}
}