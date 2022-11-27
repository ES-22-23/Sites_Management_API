package es.module2.smapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
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

    public byte[] getVideoFile(String videoKey){

        S3Object s3Object = s3Client.getObject(bucketName, videoKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] content;
        try {
            content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
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

    public List<String> getVideoKeysFromProperty(String propertyId){
        
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

        String propertyId = cameraRepository.findById(cameraId).get().getProperty().getId();

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
