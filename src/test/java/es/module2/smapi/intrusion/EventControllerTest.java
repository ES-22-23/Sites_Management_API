package es.module2.smapi.intrusion;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.smapi.controller.EventController;
import es.module2.smapi.service.EventService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;


@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean                           
    private EventService eventService;

    List<String> videoKeys;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        videoKeys = new ArrayList<>();

        videoKeys.add("video1");
        videoKeys.add("video2");
        videoKeys.add("video3");
    }

    @Test
     void testgetAllVideos() throws IOException, Exception {
       
        Mockito.when(eventService.getAllVideoKeys()).thenReturn(videoKeys);

        given().get("/events")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(videoKeys.get(0))).and()
        .body("[1]", is(videoKeys.get(1))).and()
        .body("[2]", is(videoKeys.get(2)));
    }

    @Test
     void testgetAllVideosFromOwner() throws IOException, Exception {
       
        Mockito.when(eventService.getVideoKeysFromOwner(any(String.class))).thenReturn(videoKeys);

        given().get("/events/owner/"+"owner1")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(videoKeys.get(0))).and()
        .body("[1]", is(videoKeys.get(1))).and()
        .body("[2]", is(videoKeys.get(2)));
    }

    @Test
     void testgetAllVideosFromProperty() throws IOException, Exception {
       
        Mockito.when(eventService.getVideoKeysFromProperty(any(long.class))).thenReturn(videoKeys);

        given().get("/events/property/1")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(videoKeys.get(0))).and()
        .body("[1]", is(videoKeys.get(1))).and()
        .body("[2]", is(videoKeys.get(2)));
    }

    @Test
     void testgetAllVideosFromCamera() throws IOException, Exception {
       
        Mockito.when(eventService.getVideoKeysFromCamera(any(String.class))).thenReturn(videoKeys);

        given().get("/events/camera/1")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(videoKeys.get(0))).and()
        .body("[1]", is(videoKeys.get(1))).and()
        .body("[2]", is(videoKeys.get(2)));
    }

    @Test
     void testGetVideoUrl() throws IOException, Exception {
       
        String video = "https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132";

        Mockito.when(eventService.getVideoUrl(any(String.class))).thenReturn(video);

        given().get("/events/"+"?videoKey=" + "videoKey")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .body(is("https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132"));
    }
}
