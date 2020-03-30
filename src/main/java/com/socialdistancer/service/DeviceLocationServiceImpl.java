package com.socialdistancer.service;

import com.socialdistancer.model.LocationDetails;
import com.socialdistancer.repo.LocationDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class DeviceLocationServiceImpl implements DeviceLocationService {

    @Autowired
    LocationDetailsRepository locationDetailsRepository;

    @Autowired
    KafkaSender kafkaSender;

    @Override
    @Transactional
    public void updateDeviceLocation(LocationDetails locationDetails) {
        locationDetailsRepository.save(locationDetails);
        CompletableFuture.runAsync(()-> computeDistance(locationDetails));
    }

    private void computeDistance(LocationDetails locationDetails) {
        List<LocationDetails> locationDetailsList = new ArrayList<>();
        locationDetailsRepository.findAll().spliterator().forEachRemaining(locationDetailsList::add);
        Comparator<LocationDetails> comparator = Comparator.comparing(locationDetail -> locationDetail.getLatitude());
        comparator = comparator.thenComparing(Comparator.comparing(locationDetail -> locationDetail.getLongitude()));

        List<LocationDetails> sorted = locationDetailsList.stream().sorted(comparator).collect(Collectors.toList());
        LinkedHashMap<String, LocationDetails> locationDetailsMap = new LinkedHashMap<>();
        sorted.forEach(s->{
            locationDetailsMap.put(s.getId(), s);
        });

        int current = new ArrayList<>(locationDetailsMap.keySet()).indexOf(locationDetails.getId());
        if(current!=sorted.size()-1) {
            double latDiff = Math.abs(Double.valueOf(locationDetails.getLatitude()) - Double.valueOf(sorted.get(current+1).getLatitude()));
            double longDiff = Math.abs(Double.valueOf(locationDetails.getLongitude()) - Double.valueOf(sorted.get(current+1).getLongitude()));
            if ((latDiff + longDiff) < .000001) {
                kafkaSender.send(locationDetails.getId());
                return;
            }
        }
        if(current != 0) {
            double latDiff = Math.abs(Double.valueOf(locationDetails.getLatitude()) - Double.valueOf(sorted.get(current-1).getLatitude()));
            double longDiff = Math.abs(Double.valueOf(locationDetails.getLongitude()) - Double.valueOf(sorted.get(current-1).getLongitude()));
            if ((latDiff + longDiff) < .000001) {
                kafkaSender.send(locationDetails.getId());
            }
        }
    }
}
