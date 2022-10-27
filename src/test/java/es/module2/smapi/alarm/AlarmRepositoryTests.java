package es.module2.smapi.alarm;

import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.AlarmRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Alarm;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
class AlarmRepositoryTests {



	@Autowired
    private AlarmRepository alRepository;

	@Autowired
    private TestEntityManager entityManager;


	// @Test
	// void whenFindPropByAddressThenReturnProp() {
    //     Alarm al1 = new Alarm(1, new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));
    //     entityManager.persistAndFlush(al1); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Alarm found = alRepository.findById(al1.getId());
    //     assertThat( found ).isEqualTo(al1);
	// }


	// @Test
	// void whenFindPropByNameThenReturnProp() {
    //     Alarm al2 = new Alarm(1, 
    //     new Property( "address2","DETI",
    //     new Owner( "alex@deti.com","1234","alex")));

    //     entityManager.persistAndFlush(al2); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Alarm found = alRepository.findById(al2.getId());
    //     assertThat( found ).isEqualTo(al2);
	// }

    // @Test
	// void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
    //     Alarm al3 = new Alarm(1, new Property( "address2","DETI",new Owner( "alex@deti.com","1234","alex")));
    //     entityManager.persistAndFlush(al3); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Alarm found = alRepository.findById(al3.getId());
    //     assertThat( found ).isEqualTo(al3);

    //     alRepository.deleteById(al3.getId());
    //     List<Alarm> found2 = alRepository.findAll();
    //     assertThat(found2.isEmpty()).isTrue();
	// }
	

}
