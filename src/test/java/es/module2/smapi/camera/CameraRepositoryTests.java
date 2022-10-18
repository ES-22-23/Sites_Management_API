package es.module2.smapi.camera;

import org.junit.jupiter.api.Test;
import java.util.List;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.model.Property;
import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Camera;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
class CameraRepositoryTests {



	@Autowired
    private CameraRepository camRepository;

	@Autowired
    private TestEntityManager entityManager;


	@Test
	void whenFindPropByAddressThenReturnProp() {
        Camera cam1 = new Camera(new Property( "address1","DETI",new Owner( "alex@deti.com","1234","alex")));
        entityManager.persistAndFlush(cam1); //ensure data is persisted at this point

        // test the query method of interest
        Camera found = camRepository.findById(cam1.getId());
        assertThat( found ).isEqualTo(cam1);
	}


	@Test
	void whenFindPropByNameThenReturnProp() {
        Camera cam2 = new Camera(new Property( "address2","DETI",new Owner( "alex@deti.com","1234","alex")));

        entityManager.persistAndFlush(cam2); //ensure data is persisted at this point

        // test the query method of interest
        Camera found = camRepository.findById(cam2.getId());
        assertThat( found ).isEqualTo(cam2);
	}

    @Test
	void whenDeletePropInRepositoryThenPropNoLongerInRepository() {
        Camera cam3 = new Camera(new Property( "address2","DETI",new Owner( "alex@deti.com","1234","alex")));
        entityManager.persistAndFlush(cam3); //ensure data is persisted at this point

        // test the query method of interest
        Camera found = camRepository.findById(cam3.getId());
        assertThat( found ).isEqualTo(cam3);

        camRepository.deleteById(cam3.getId());
        List<Camera> found2 = camRepository.findAll();
        assertThat(found2.isEmpty()).isTrue();
	}
	

}
