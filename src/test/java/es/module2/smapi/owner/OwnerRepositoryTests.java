package es.module2.smapi.owner;

import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.model.Owner;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;


@DataJpaTest
class OwnerRepositoryTests {



	@Autowired
    private OwnerRepository ownerRepository;

	@Autowired
    private TestEntityManager entityManager;


	// @Test
	// void whenFindAlexByNameThenReturnAlexOwner() {
    //     Owner alex = new Owner( "alex@deti.com","1234","alex");
    //     entityManager.persistAndFlush(alex); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Owner found = ownerRepository.findByName(alex.getName());
    //     assertThat( found ).isEqualTo(alex);
	// }


	// @Test
	// void whenFindBobByUsernameThenReturnAlexOwner() {
    //     Owner bob = new Owner( "bob@deti.com","1234","bob");
    //     entityManager.persistAndFlush(bob); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Owner found = ownerRepository.findByUsername(bob.getUsername());
    //     assertThat( found ).isEqualTo(bob);
	// }
	


    // @Test
	// void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
    //     Owner alex =  new Owner( "alex@deti.com","1234","alex");
    //     entityManager.persistAndFlush(alex); //ensure data is persisted at this point

    //     // test the query method of interest
    //     Owner found = ownerRepository.findByName(alex.getName());
    //     assertThat( found ).isEqualTo(alex);

    //     ownerRepository.deleteByUsername(alex.getUsername());
    //     List<Owner> found2 = ownerRepository.findAll();
    //     assertThat(found2.isEmpty()).isTrue();
	// }

}
