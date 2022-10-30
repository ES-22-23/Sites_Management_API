package es.module2.smapi.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;


@DataJpaTest
class OwnerRepositoryTests {

	@Autowired
    private OwnerRepository ownerRepository;

	@Autowired
    private TestEntityManager entityManager;

	@Test
	void whenFindAlexByNameThenReturnAlexOwner() {
        Owner alex = new Owner( "alex@deti.com","1234","alex");
        alex = entityManager.persistAndFlush(alex); //ensure data is persisted at this point

        // test the query method of interest
        Optional<Owner> found = ownerRepository.findByName(alex.getName());
        assertEquals(alex, found.get());
    }


	@Test
	void whenFindBobByUsernameThenReturnAlexOwner() {
        Owner bob = new Owner( "bob@deti.com","1234","bob");
        bob = entityManager.persistAndFlush(bob); //ensure data is persisted at this point

        // test the query method of interest
        Optional<Owner> found = ownerRepository.findByUsername(bob.getUsername());
        assertEquals(bob, found.get());
	}
	


    @Test
	void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
        Owner alex =  new Owner( "alex@deti.com","1234","alex");
        alex = entityManager.persistAndFlush(alex); //ensure data is persisted at this point

        // test the query method of interest
        Optional<Owner> found = ownerRepository.findByName(alex.getName());
        assertEquals(alex, found.get());

        ownerRepository.deleteByUsername(alex.getUsername());
        List<Owner> found2 = ownerRepository.findAll();
        assertTrue(found2.isEmpty());
    }

}
