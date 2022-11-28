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
     void testgetVideo() throws IOException, Exception {
       
        byte[] video = "hello".getBytes();

        Mockito.when(eventService.getVideoFile(any(String.class))).thenReturn(video);

        given().get("/events/"+"?videoKey=" + "videoKey")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .header("Content-type", is("application/octet-stream")).and()
        .header("Content-disposition",is("attachment; filename=\"" + "videoKey" + "\""));
    }
}
