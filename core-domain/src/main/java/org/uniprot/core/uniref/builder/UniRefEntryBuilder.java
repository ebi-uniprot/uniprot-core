package org.uniprot.core.uniref.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryImpl;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class UniRefEntryBuilder implements Builder<UniRefEntryBuilder, UniRefEntry> {
	private UniRefEntryId id;
	private String name;
	private LocalDate updated;
	private UniRefType entryType;
	private long commonTaxonId;
	private String commonTaxonName;
	private List<GoTerm> goTerms =new ArrayList<>();
	private RepresentativeMember representativeMember;
	private List<UniRefMember> members  =new ArrayList<>();
	
	
	
	
	@Override
	public UniRefEntry build() {
		return new UniRefEntryImpl(  id,
				  name,
				  updated,
				  entryType,
				  commonTaxonId,
				  commonTaxonName,
				  goTerms,
				  representativeMember,
				  members) ;
	}

	@Override
	public UniRefEntryBuilder from(UniRefEntry instance) {
		return this.id(instance.getId())
				.name(instance.getName())
				.updated(instance.getUpdated())
				.entryType(instance.getEntryType())
				.commonTaxonId(instance.getCommonTaxonId())
				.commonTaxonName(instance.getCommonTaxonName())
				.goTerms(instance.getGoTerms())
				.representativeMember(instance.getRepresentativeMember())
				.members(instance.getMembers());
	}
	public UniRefEntryBuilder id(UniRefEntryId id) {
		this.id = id;
		return this;
	}
	public UniRefEntryBuilder id(String id) {
		this.id = new UniRefEntryIdBuilder(id).build();
		return this;
	}
	public UniRefEntryBuilder name(String name) {
		this.name = name;
		return this;
	}
	public UniRefEntryBuilder updated(LocalDate updated) {
		this.updated = updated;
		return this;
	}
	public UniRefEntryBuilder entryType(UniRefType entryType) {
		this.entryType = entryType;
		return this;
	}
	public UniRefEntryBuilder commonTaxonId(long commonTaxonId) {
		this.commonTaxonId = commonTaxonId;
		return this;
	}
	
	public UniRefEntryBuilder commonTaxonName(String commonTaxonName) {
		this.commonTaxonName = commonTaxonName;
		return this;
	}
	
	public UniRefEntryBuilder goTerms(List<GoTerm> goTerms) {
		this.goTerms =  Utils.nonNullList(goTerms);
		return this;
	}
	public UniRefEntryBuilder addGoTerm(GoTerm goTerm) {
		Utils.nonNullAdd(goTerm, this.goTerms);
		return this;
	}
	public UniRefEntryBuilder representativeMember(RepresentativeMember representativeMember) {
		this.representativeMember = representativeMember;
		return this;
	}
	
	public UniRefEntryBuilder members(List<UniRefMember> unirefMembers) {
		this.members =  Utils.nonNullList(unirefMembers);
		return this;
	}
	public UniRefEntryBuilder addMember(UniRefMember unirefMember) {
		Utils.nonNullAdd(unirefMember, this.members);
		return this;
	}
}

