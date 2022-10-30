package es.module2.smapi.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import es.module2.smapi.SmapiApplication;
import es.module2.smapi.datamodel.OwnerDTO;
import es.module2.smapi.model.Owner;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.service.OwnerService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SmapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OwnerServiceTest {

    @Mock(lenient = true)
    private OwnerRepository owRepository;
    
    @InjectMocks
    private OwnerService service;        

    @Test
    void whenValidInputThenCreateOwner() throws IOException, Exception {
        Owner alex = buildOwnerObject(1);
        OwnerDTO dto = buildOwnerDTO(1);
        Mockito.when(owRepository.saveAndFlush(any(Owner.class))).thenReturn(alex);

        Owner created = service.createOwner(dto);
        assertEquals(alex, created);        
    }

    @Test
    void whenValidInputThenUpdateOwner() throws IOException, Exception {

        Owner bob = buildOwnerObject(2);
        OwnerDTO dto = buildOwnerDTO(2);
        Mockito.when(owRepository.saveAndFlush(any(Owner.class))).thenReturn(bob);

        Owner updated = service.createOwner(dto);
        assertEquals(bob, updated);   
    }

    @Test
     void whenValidInputThenGetOwner() throws IOException, Exception {
        Owner bob = buildOwnerObject(2);
        OwnerDTO dto = buildOwnerDTO(2);
        Mockito.when(owRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(bob));
        
        Owner found = service.getOwner(dto.getUsername());
        assertEquals(bob, found);
    }

    OwnerDTO buildOwnerDTO(long id){
        OwnerDTO ow = new OwnerDTO();
        ow.setName("name"+id);
        ow.setEmail("email"+id);
        ow.setUsername("username"+id);
        return ow;
    }

    Owner buildOwnerObject(long id){
        Owner ow = new Owner();
        ow.setName("name"+id);
        ow.setEmail("email"+id);
        ow.setUsername("username"+id);
        return ow;
    }
	
 }
