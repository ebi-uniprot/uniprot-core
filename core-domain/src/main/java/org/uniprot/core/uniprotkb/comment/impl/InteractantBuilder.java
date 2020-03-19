package org.uniprot.core.uniprotkb.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 17 Mar 2020
 */
public class InteractantBuilder implements Builder<Interactant> {
  private UniProtKBAccession uniProtkbAccession;
  private String geneName;
  private String chainId;
  private String intActId;

  @Override 
  public Interactant build(){
	  return new InteractantImpl(
		       uniProtkbAccession,  geneName,  chainId,  intActId);
  }
  public static  @Nonnull InteractantBuilder from(@Nonnull Interactant instance) {
	  InteractantBuilder builder = new InteractantBuilder();
      builder.uniProtKBAccession(instance.getUniProtKBAccession())
              .geneName(instance.getGeneName())
              .chainId(instance.getChainId())
              .intActId(instance.getIntActId());
      return builder;
  }
  
  public @Nonnull InteractantBuilder geneName(String geneName) {
    this.geneName = geneName;
    return this;
  }

  public @Nonnull InteractantBuilder uniProtKBAccession(UniProtKBAccession uniprotAccession) {
    this.uniProtkbAccession = uniprotAccession;
    return this;
  }

  public @Nonnull InteractantBuilder uniProtKBAccession(String uniProtAccession) {
    this.uniProtkbAccession = new UniProtKBAccessionBuilder(uniProtAccession).build();
    return this;
  }

  public @Nonnull InteractantBuilder chainId(String chainId) {
	    this.chainId = chainId;
	    return this;
  }
  public @Nonnull InteractantBuilder intActId(String intActId) {
	    this.intActId = intActId;
	    return this;
}
}
