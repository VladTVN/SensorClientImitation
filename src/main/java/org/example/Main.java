package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.MeasurementDTO;
import org.example.dto.MeasurementResponse;
import org.example.dto.SensorDTO;
import org.example.requests.RequestSender;
import org.example.util.exception.MeasurementNotCreateException;
import org.example.util.exception.SensorNotCreateException;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("testName1231223");

        try {
            RequestSender.registerSensor(sensorDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (SensorNotCreateException e) {
            System.out.println(e.getErrorResponse().getMessage());
        }

        for(int i = 0; i<500; i++) {
            try {
                RequestSender.registerMeasurement(generateMeasurement(sensorDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (MeasurementNotCreateException e) {
                System.out.println(e.getErrorResponse().getMessage());
            }
        }

        try {
            System.out.println(RequestSender.getRainyDaysCounter());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        MeasurementResponse measurementResponse = RequestSender.getListMeasurement();
        System.out.println(measurementResponse.getMeasurementDTOList().size());

        crateChart(measurementResponse.getMeasurementDTOList());
    }

    public static MeasurementDTO generateMeasurement(SensorDTO sensorDTO){
        Random random = new Random();
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setSensor(sensorDTO);
        measurement.setRaining(random.nextBoolean());
        measurement.setValue((random.nextFloat() * 200 ) - 100);
        return measurement;
    }

    public static void crateChart(List<MeasurementDTO> measurementList){
        int measurementListSize = measurementList.size();
        double[] xData = IntStream.range(0, measurementListSize).asDoubleStream().toArray();
        double[] yData = measurementList.stream().mapToDouble(MeasurementDTO::getValue).toArray();

        XYChart chart = QuickChart.getChart("Temperature Chart", "X", "Y", "temperature", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }


}