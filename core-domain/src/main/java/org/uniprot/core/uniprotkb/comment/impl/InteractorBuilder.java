package org.uniprot.core.uniprotkb.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Interactor;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 17 Mar 2020
 */
public class InteractorBuilder implements Builder<Interactor> {
  private UniProtKBAccession uniProtkbAccession;
  private String geneName;
  private String chainId;
  private String intActId;

  @Override 
  public Interactor build(){
	  return new InteractorImpl(
		       uniProtkbAccession,  geneName,  chainId,  intActId);
  }
  public static  @Nonnull InteractorBuilder from(@Nonnull Interactor instance) {
	  InteractorBuilder builder = new InteractorBuilder();
      builder.uniProtAccession(instance.getUniProtkbAccession())
              .geneName(instance.getGeneName())
              .chainId(instance.getChainId())
              .intActId(instance.getIntActId());
      return builder;
  }
  
  public @Nonnull InteractorBuilder geneName(String geneName) {
    this.geneName = geneName;
    return this;
  }

  public @Nonnull InteractorBuilder uniProtAccession(UniProtKBAccession uniprotAccession) {
    this.uniProtkbAccession = uniprotAccession;
    return this;
  }

  public @Nonnull InteractorBuilder uniProtAccession(String uniProtAccession) {
    this.uniProtkbAccession = new UniProtKBAccessionBuilder(uniProtAccession).build();
    return this;
  }

  public @Nonnull InteractorBuilder chainId(String chainId) {
	    this.chainId = chainId;
	    return this;
  }
  public @Nonnull InteractorBuilder intActId(String intActId) {
	    this.intActId = intActId;
	    return this;
}
}
