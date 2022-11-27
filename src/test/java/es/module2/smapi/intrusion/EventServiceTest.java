package es.module2.smapi.intrusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import es.module2.smapi.model.Camera;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.OwnerRepository;
import es.module2.smapi.service.EventService;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    
    @Mock(lenient = true)
    private CameraRepository cameraRepository;
    
    @Mock(lenient = true)
    private OwnerRepository ownerRepository;

    @Mock(lenient = true)
    private AmazonS3 s3Client;

    @InjectMocks
    private EventService service;

    @Mock
    private ObjectListing listingMock = new ObjectListing();

    private ArrayList<S3ObjectSummary> listObjectSummary;
    private S3ObjectSummary object1;
    private S3ObjectSummary object2;
    private S3ObjectSummary object3;

    @BeforeEach
    void setUp(){
        service.setBucketName("hdm-bucket");

        listObjectSummary = new ArrayList<>();
        object1 = new S3ObjectSummary();
        object1.setKey("video1");
        object2 = new S3ObjectSummary();
        object1.setKey("video2");
        object3 = new S3ObjectSummary();
        object1.setKey("video3");

        listObjectSummary.add(object1);
        listObjectSummary.add(object2);
        listObjectSummary.add(object3);
    }

    @Test
    void testGetVideoFile() throws IOException{
        byte[] array = "hello".getBytes();
        S3ObjectInputStream inputStream = new S3ObjectInputStream(new ByteArrayInputStream(array), new HttpGet("teste"));
        S3Object object = new S3Object();
        object.setObjectContent(inputStream);
        String videoKey = "propId1/cam1/Video1";
        object.setKey(videoKey);
        object.setBucketName("hdm-bucket");

        Mockito.when(s3Client.getObject("hdm-bucket", videoKey)).thenReturn(object);

        String expected = new String(array);
        String actual = new String(service.getVideoFile(videoKey));

        assertEquals(expected, actual);
    }

    @Test
    void testGetVideoKeysFromProperty(){

        Mockito.when(s3Client.listObjects(any(ListObjectsRequest.class))).thenReturn(listingMock);
        Mockito.when(listingMock.getObjectSummaries()).thenReturn(listObjectSummary);

        List<String> result = service.getVideoKeysFromProperty("1");

        assertEquals(listObjectSummary.size(), result.size());
        assertTrue(result.contains(object1.getKey()));
        assertTrue(result.contains(object2.getKey()));
        assertTrue(result.contains(object3.getKey()));
    }

    @Test
    void testGetAllFiles(){

        Mockito.when(s3Client.listObjects("hdm-bucket")).thenReturn(listingMock);
        Mockito.when(listingMock.getObjectSummaries()).thenReturn(listObjectSummary);

        List<String> result = service.getAllVideoKeys();

        assertEquals(listObjectSummary.size(), result.size());
        assertTrue(result.contains(object1.getKey()));
        assertTrue(result.contains(object2.getKey()));
        assertTrue(result.contains(object3.getKey()));
    }

    @Test
    void testGetVideoKeysFromCamera(){

        Camera cam1 = new Camera();
        cam1.setId("1");
        Property prop1 = new Property();
        prop1.setId("1");
        cam1.setProperty(prop1);

        Mockito.when(cameraRepository.findById("1")).thenReturn(Optional.of(cam1));

        Mockito.when(s3Client.listObjects(any(ListObjectsRequest.class))).thenReturn(listingMock);
        Mockito.when(listingMock.getObjectSummaries()).thenReturn(listObjectSummary);

        List<String> result = service.getVideoKeysFromCamera("1");

        assertEquals(listObjectSummary.size(), result.size());
        assertTrue(result.contains(object1.getKey()));
        assertTrue(result.contains(object2.getKey()));
        assertTrue(result.contains(object3.getKey()));
    }
}