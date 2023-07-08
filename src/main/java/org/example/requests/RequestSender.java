package org.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.MeasurementDTO;
import org.example.dto.MeasurementResponse;
import org.example.dto.SensorDTO;
import org.example.util.ErrorResponse;
import org.example.util.exception.MeasurementNotCreateException;
import org.example.util.exception.SensorNotCreateException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RequestSender {
    private static final String SERVER_URL = "http://localhost:8081";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static SensorDTO registerSensor(SensorDTO sensorForRegistration) throws JsonProcessingException, SensorNotCreateException {
        String url =  SERVER_URL + "/sensors/registration";
        try {
            return restTemplate.postForObject(url, sensorForRegistration, SensorDTO.class);
        }catch (HttpClientErrorException | HttpServerErrorException e){
            ObjectMapper mapper = new ObjectMapper();
            throw new SensorNotCreateException(mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));
        }

    }

    public static MeasurementDTO registerMeasurement(MeasurementDTO measurementForRegistration) throws JsonProcessingException, MeasurementNotCreateException {
        String url = SERVER_URL + "/measurements/add";
        try {
            return restTemplate.postForObject(url, measurementForRegistration, MeasurementDTO.class);
        }catch (HttpClientErrorException | HttpServerErrorException e){
            ObjectMapper mapper = new ObjectMapper();
            throw new MeasurementNotCreateException(mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));
        }
    }

    public static int getRainyDaysCounter() throws JsonProcessingException {
        String url = SERVER_URL + "/measurements/rainyDaysCount";
        ObjectMapper mapper = new ObjectMapper();
        String response = restTemplate.getForObject(url, String.class);
        JsonNode jsonNode = mapper.readTree(response);
        return jsonNode.get("numberOfRainyDays").asInt();
    }

    public static MeasurementResponse getListMeasurement() {
        String url = SERVER_URL + "/measurements";
        return restTemplate.getForObject(url, MeasurementResponse.class);
    }

}
