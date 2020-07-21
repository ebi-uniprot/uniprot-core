package org.uniprot.core.json.parser.uniref;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.impl.RepresentativeMemberImpl;

/**
 * @author sahmad
 * @since 21/07/2020
 */
public class UniRefRepresentativeMemberJsonConfig extends JsonConfig {

  private static UniRefRepresentativeMemberJsonConfig instance;

  private final ObjectMapper objectMapper;
  private final ObjectMapper prettyMapper;

  private UniRefRepresentativeMemberJsonConfig() {
    this.objectMapper = initObjectMapper();
    this.prettyMapper = initPrettyObjectMapper();
  }

  public static synchronized UniRefRepresentativeMemberJsonConfig getInstance() {
    if (instance == null) {
      instance = new UniRefRepresentativeMemberJsonConfig();
    }
    return instance;
  }

  @Override
  public ObjectMapper getSimpleObjectMapper() {
    return this.prettyMapper;
  }

  @Override
  public ObjectMapper getFullObjectMapper() {
    return this.objectMapper;
  }

  private ObjectMapper initObjectMapper() {
    ObjectMapper objMapper = getDefaultFullObjectMapper();
    SimpleModule mod = new SimpleModule();
    mod.addAbstractTypeMapping(RepresentativeMember.class, RepresentativeMemberImpl.class);
    objMapper.registerModule(mod);
    return objMapper;
  }

  private ObjectMapper initPrettyObjectMapper() {
    ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
    return prettyObjMapper;
  }
}
