package es.module2.smapi.property;

import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.PropertyRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
class RepositoryTests {



	@Autowired
    private PropertyRepository propRepository;

	@Autowired
    private TestEntityManager entityManager;


	@Test
	void whenFindPropByAddressThenReturnProp() {
        Property porp1 = new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex"));
        entityManager.persistAndFlush(porp1); //ensure data is persisted at this point

        // test the query method of interest
        Property found = propRepository.findByAddress(porp1.getAddress());
        assertThat( found ).isEqualTo(porp1);
	}


	@Test
	void whenFindPropByNameThenReturnProp() {
        Property prop2 = new Property( "address2","Psicologia",new Owner( "alex@deti.com","1234","alex"));
        entityManager.persistAndFlush(prop2); //ensure data is persisted at this point

        // test the query method of interest
        Property found = propRepository.findByName(prop2.getName());
        assertThat( found ).isEqualTo(prop2);
	}

    @Test
	void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
        Property prop3 = new Property( "address3","Bio",new Owner( "alex@deti.com","1234","alex"));
        entityManager.persistAndFlush(prop3); //ensure data is persisted at this point

        // test the query method of interest
        Property found = propRepository.findByName(prop3.getName());
        assertThat( found ).isEqualTo(prop3);

        propRepository.deleteById(prop3.getId());
        List<Property> found2 = propRepository.findAll();
        assertThat(found2.isEmpty()).isTrue();
	}
	

}
