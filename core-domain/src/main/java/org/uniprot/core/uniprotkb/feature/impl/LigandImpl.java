package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Objects;
import java.util.Optional;

import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;

public class LigandImpl implements Ligand {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String id;
	private final String label;
	private final String note;
	private final Optional<LigandPart> ligandPart;
	LigandImpl() {
		this("", null, null, null, null);
	}

	LigandImpl(String name, String id, String label, String note, LigandPart ligandPart) {
		this.name = name;
		this.id = id;
		this.label = label;
		this.note = note;
		this.ligandPart = Optional.ofNullable(ligandPart);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public Optional<LigandPart> getLigandPart() {
		return ligandPart;
	}
	@Override
	public int hashCode() {
		return Objects.hash(name, id, label, note, ligandPart);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigandImpl that = (LigandImpl) obj;
		return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(label, that.label)
				&& Objects.equals(note, that.note)
				&& Objects.equals(ligandPart, that.ligandPart);
	}
}
