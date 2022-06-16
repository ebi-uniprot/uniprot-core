package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.feature.LigandPart;

public class LigandPartBuilder implements Builder<LigandPart> {
	private String name;
	private String id;
	private String label;
	private String note;
	@Override
	public LigandPart build() {
		return new LigandPartImpl(name, id, label, note);
	}
	
    public static @Nonnull LigandPartBuilder from(@Nonnull LigandPart instance) {
        return new LigandPartBuilder()
                .name(instance.getName())
                .id(instance.getId())
                .label(instance.getLabel())
                .note(instance.getNote())
                ;       
    }
	
	public LigandPartBuilder name(String name) {
		this.name = name;
		return this;
	}

	public LigandPartBuilder id(String id) {
		this.id = id;
		return this;
	}
	public LigandPartBuilder label(String label) {
		this.label = label;
		return this;
	}

	public LigandPartBuilder note(String note) {
		this.note = note;
		return this;
	}
}
