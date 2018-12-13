package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.util.ClassUtil;
import uk.ac.ebi.uniprot.domain.EnumDisplay;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public enum UniProtXDbTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/drlineconfiguration.json";
	private List<UniProtXDbTypeDetail>  types = new ArrayList<>();
	private Map<String, UniProtXDbTypeDetail> typeMap ;
	UniProtXDbTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());
		try (InputStream is = UniProtXDbTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			types = objectMapper.readValue(is,
					new TypeReference<List<UniProtXDbTypeDetail>>() {
					});
			
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getDisplayName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<UniProtXDbTypeDetail> getAllDBXRefTypes() {
		return types;
	}
	
	public UniProtXDbTypeDetail getType(String typeName){
		UniProtXDbTypeDetail type =typeMap.get(typeName);
		if(type ==null) {
			throw new IllegalArgumentException (typeName + " does not exist in UniProt database type list");
		}
		return type;
	}

	public static class CustomAnnotationIntrospector extends AnnotationIntrospector {

		@Override
		public Version version() {
			return PackageVersion.VERSION;
		}

		public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
			return Arrays.stream(enumValues).map(en -> {
				EnumDisplay<?> jsonEnum = (EnumDisplay<?>) en;
				return jsonEnum.toDisplayName();
			}).toArray(String[]::new);
		}

		public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
			return ClassUtil.findFirstAnnotatedEnumValue(enumCls, JsonEnumDefaultValue.class);
		}
	}
	
}
