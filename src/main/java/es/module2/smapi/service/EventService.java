package es.module2.smapi.service;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import es.module2.smapi.model.Owner;
import es.module2.smapi.model.Property;
import es.module2.smapi.repository.CameraRepository;
import es.module2.smapi.repository.OwnerRepository;
import software.amazon.awssdk.utils.IoUtils;

@Service
public class EventService {
    
    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    } 

    public String getVideoUrl(String videoKey){

        try {
            // Set the presigned URL to expire after half an  hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = Instant.now().toEpochMilli();
            expTimeMillis += 1000 * 30 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, videoKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            return null;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllVideoKeys(){
                   
        ObjectListing response = s3Client.listObjects(bucketName);
        List<S3ObjectSummary> objects = response.getObjectSummaries(); 
        List<String> objectKeys = new ArrayList<>();
        
        while(response.isTruncated()){
            response = s3Client.listNextBatchOfObjects(response);
            objects.addAll(response.getObjectSummaries());
        }

        for (S3ObjectSummary s3ObjectSummary : objects) {
            objectKeys.add(s3ObjectSummary.getKey());
        }

        return objectKeys;
    }

    public List<String> getVideoKeysFromOwner(String ownerUsername){
        
        List<String> objectKeys = new ArrayList<>();
        
        Optional<Owner> owner = ownerRepository.findByUsername(ownerUsername);

        if(owner.isEmpty()){
            return null;
        }

        for (Property property : owner.get().getProperties()) {
            objectKeys.addAll(getVideoKeysFromProperty(property.getId()));
        }
        
        return objectKeys;
    }

    public List<String> getVideoKeysFromProperty(long propertyId){
        
        List<String> objectKeys = new ArrayList<>();

        ListObjectsRequest objectsRequest = new ListObjectsRequest();
        objectsRequest.setBucketName(bucketName);
        objectsRequest.setPrefix("propId" + propertyId + "/");
        ObjectListing response = s3Client.listObjects(objectsRequest);
        List<S3ObjectSummary> objects = response.getObjectSummaries(); 

        while(response.isTruncated()){
            response = s3Client.listNextBatchOfObjects(response);
            objects.addAll(response.getObjectSummaries());
        }

        for (S3ObjectSummary s3ObjectSummary : objects) {
            objectKeys.add(s3ObjectSummary.getKey());
        }

        return objectKeys;
    }

    public List<String> getVideoKeysFromCamera(String cameraId){
        
        List<String> objectKeys = new ArrayList<>();

        long propertyId = cameraRepository.findById(cameraId).get().getProperty().getId();

        ListObjectsRequest objectsRequest = new ListObjectsRequest();
        objectsRequest.setBucketName(bucketName);
        objectsRequest.setPrefix("propId" + propertyId + "/cam" + cameraId);
        ObjectListing response = s3Client.listObjects(objectsRequest);
        List<S3ObjectSummary> objects = response.getObjectSummaries(); 

        while(response.isTruncated()){
            response = s3Client.listNextBatchOfObjects(response);
            objects.addAll(response.getObjectSummaries());
        }

        for (S3ObjectSummary s3ObjectSummary : objects) {
            objectKeys.add(s3ObjectSummary.getKey());
        }

        return objectKeys;
    }
    
}
