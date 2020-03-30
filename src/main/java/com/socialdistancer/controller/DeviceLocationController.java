package com.socialdistancer.controller;

import com.socialdistancer.model.LocationDetails;
import com.socialdistancer.service.DeviceLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceLocationController {
    @Autowired
    DeviceLocationService deviceLocationService;

    @CrossOrigin
    @PostMapping("/update-device-location")
    public void updateDeviceLocation(@RequestBody LocationDetails locationDetails) {
        deviceLocationService.updateDeviceLocation(locationDetails);
    }

}
