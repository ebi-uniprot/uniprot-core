package org.uniprot.core.json.parser.publication;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Value;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.impl.MappedPublicationsBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.uniprot.core.json.parser.publication.CommunityMappedReferenceTest.getCompleteCommunityMappedReference;
import static org.uniprot.core.json.parser.publication.ComputationallyMappedReferenceTest.getCompleteComputationallyMappedReference;
import static org.uniprot.core.json.parser.publication.UniProtKBMappedReferenceTest.getCompleteUniProtKBMappedReference;

/**
 * @author sahmad
 * Created 18/12/2020
 */
class MappedPublicationsJsonConfigTest {
    @Test
    void testSimpleMappedPublications() {
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        MappedPublications mappedPubs = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                MappedPublicationsJsonConfig.getInstance().getFullObjectMapper(), mappedPubs);
    }

    @Test
    void testCompleteMappedPublications() {
        MappedPublications mappedPublications = getCompleteMappedPublications();
        ValidateJson.verifyJsonRoundTripParser(
                MappedPublicationsJsonConfig.getInstance().getFullObjectMapper(), mappedPublications);
        ValidateJson.verifyEmptyFields(mappedPublications);
    }

    @Test
    void testPrettyPrintedMappedPublicationsIgnoresUniProtKBAccession() throws JsonProcessingException {
        MappedPublications mappedPublications = getCompleteMappedPublications();
        ObjectMapper mapper = MappedPublicationsJsonConfig.getInstance().getSimpleObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mappedPublications);

        System.out.println(json);
        assertThat(json, containsString("uniProtKBMappedReference"));
        assertThat(json, not(containsString("uniProtKBAccession")));
    }

    static MappedPublications getCompleteMappedPublications() {
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.uniProtKBMappedReference(getCompleteUniProtKBMappedReference());
        builder.computationalMappedReferencesAdd(getCompleteComputationallyMappedReference());
        builder.communityMappedReferencesAdd(getCompleteCommunityMappedReference());
        return builder.build();
    }

    public class Item {
        private int id;

        public int getId() {
            return id;
        }

        private UniProtKBAccession uniProtKBAccession;
        private Value value;
        private User owner;

        public UniProtKBAccession getUniProtKBAccession() {
            return uniProtKBAccession;
        }

        public Value getValue() {
            return value;
        }

        public User getOwner() {
            return owner;
        }
    }

    public interface UserIn extends Serializable {
        String getName();
        UniProtKBAccession getUniProtKBAccession();

        default boolean hasName() {
            return Utils.notNullNotEmpty(getName());
        }
    }

    public class User implements UserIn {
        private static final long serialVersionUID = -5830200688641831547L;
        private String name;
        private UniProtKBAccession uniProtKBAccession;

        User() {}
        public User(String string) {
            name = string;
        }

        public String getName() {
            return name;
        }

        @Override
        public UniProtKBAccession getUniProtKBAccession() {
            return uniProtKBAccession;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(name, user.name) && Objects
                    .equals(uniProtKBAccession, user.uniProtKBAccession);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, uniProtKBAccession);
        }
    }

    @JsonIgnoreType
    public class MyMixInForIgnoreType {}

    @Test
    void doIt() throws JsonProcessingException {
        //        u1.name = "thing";

        Item item = new Item();
        item.id = 1;
        item.uniProtKBAccession = new UniProtKBAccessionBuilder("acc").build();
        item.value = new ValueImpl("my value");
        User thing = new User("thing");
        thing.uniProtKBAccession = new UniProtKBAccessionBuilder("acc2").build();
        item.owner = thing;

        String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);
//        assertThat(result, containsString("owner"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Value.class, MyMixInForIgnoreType.class);
//        mapper.addMixIn(UserIn.class, MyMixInForIgnoreType.class);
        mapper.addMixIn(UniProtKBAccession.class, MyMixInForIgnoreType.class);

        result = mapper.writeValueAsString(item);
        System.out.println(result);
//        assertThat(result, not(containsString("owner")));
    }
}
