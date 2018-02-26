package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;
import java.util.Optional;

public interface MassSpectrometryComment extends Comment {

	public Double getMolWeight();

	public Optional<Double> getMolWeightError();

	public Optional<String> getNote();

	public List<MassSpectrometryRange> getRanges();

	public MassSpectrometryMethod getMethod();

	public List<Evidence> getEvidences();

}
