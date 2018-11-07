package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

public interface MassSpectrometryComment extends Comment {

	public Double getMolWeight();

	public Optional<Double> getMolWeightError();

	public Optional<String> getNote();

	public List<MassSpectrometryRange> getRanges();

	public MassSpectrometryMethod getMethod();

	public List<Evidence> getEvidences();

}
