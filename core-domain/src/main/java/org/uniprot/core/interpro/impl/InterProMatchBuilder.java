package org.uniprot.core.interpro.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.MethodType;
import org.uniprot.core.interpro.Status;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
 */

public class InterProMatchBuilder implements Builder<InterProMatch> {
	private Integer fromPos;
	private Integer toPos;
	private Double score;
	private MethodType methodType;
	private List<InterProAc> interProAcs = new ArrayList<>();
	private String methodName;
	private Status status;
	private String methodAccession;
	private UniProtKBAccession uniprotKBAccession;

	@Override
	public @Nonnull InterProMatch build() {
		return new InterProMatchImpl(fromPos, toPos, score,interProAcs,  methodType, methodName, methodAccession,
				status, uniprotKBAccession);
	}
	
	 public static @Nonnull InterProMatchBuilder from(@Nonnull InterProMatch instance) {
	        return new InterProMatchBuilder()
	                .fromPos(instance.getFromPos())
	                .toPos(instance.getToPos())
	                .score(instance.getScore())
	                .interProAcsSet(instance.getInterProAcs())
	                .methodType(instance.getMethodType())
	                .methodName(instance.getMethodName())
	                .methodAccession(instance.getMethodAccession())
	                .status(instance.getStatus())
	                .uniprotKBAccession(instance.getUniProtKBAccession());
	    }

	
	
	public @Nonnull InterProMatchBuilder fromPos(Integer fromPos) {
		this.fromPos = fromPos;
		return this;
	}

	public @Nonnull InterProMatchBuilder toPos(Integer toPos) {
		this.toPos = toPos;
		return this;
	}

	public @Nonnull InterProMatchBuilder score(Double score) {
		this.score = score;
		return this;
	}

	public @Nonnull InterProMatchBuilder methodType(MethodType methodType) {
		this.methodType = methodType;
		return this;
	}

	public @Nonnull InterProMatchBuilder interProAcsSet(List<InterProAc> interProAcs) {
		this.interProAcs = Utils.modifiableList(interProAcs);
		return this;
	}

	public @Nonnull InterProMatchBuilder interProAcsAdd(InterProAc interProAc) {
		Utils.addOrIgnoreNull(interProAc, this.interProAcs);
		return this;
	}

	public @Nonnull InterProMatchBuilder methodName(String methodName) {
		this.methodName = methodName;
		return this;
	}

	public @Nonnull InterProMatchBuilder status(Status status) {
		this.status = status;
		return this;
	}

	public @Nonnull InterProMatchBuilder methodAccession(String methodAccession) {
		this.methodAccession = methodAccession;
		return this;
	}

	public @Nonnull InterProMatchBuilder uniprotKBAccession(UniProtKBAccession uniprotKBAccession) {
		this.uniprotKBAccession = uniprotKBAccession;
		return this;
	}

	
}
