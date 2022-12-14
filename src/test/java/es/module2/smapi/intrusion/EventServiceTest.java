package es.module2.smapi.intrusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
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
    void testGetVideoUrl() throws IOException{

        String videoKey = "propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21 20:46:12.66666.mp4";

        Mockito.when(s3Client.generatePresignedUrl(any())).thenReturn(new URL("https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132"));

        String expected = new String("https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132");
        String actual = new String(service.getVideoUrl(videoKey));

        assertEquals(expected, actual);
    }

    @Test
    void testGetVideoKeysFromProperty(){

        Mockito.when(s3Client.listObjects(any(ListObjectsRequest.class))).thenReturn(listingMock);
        Mockito.when(listingMock.getObjectSummaries()).thenReturn(listObjectSummary);

        List<String> result = service.getVideoKeysFromProperty(1);

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
        prop1.setId(1);
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