package com.sds.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    // create
    public Device addNewDevice(Device device) throws IllegalArgumentException {
        if(device == null || device.getOwnerId() == null || device.getName() == null) {
            throw new IllegalArgumentException("Wrong device information");
        }else if (deviceRepository.existsById(device.getId())){
            throw new IllegalArgumentException("Device with this id already exists");
        } else {
           return deviceRepository.save(device);
        }
    }

    // read
    public Device findDeviceById(String id) throws NoSuchElementException{
        if (deviceRepository.existsById(id)){
            return deviceRepository.findById(id).get();
        } else {
            throw new NoSuchElementException("Device with this id does not exists");
        }
    }

    public List<Device> findDevicesByOwnerId(String ownerId) {
        return deviceRepository.findByOwnerId(ownerId);
    }


    // update
    public Device changeDeviceName(String deviceId, String newName) throws NoSuchElementException {
        if(deviceRepository.existsById(deviceId)){
            Device deviceToUpdate = deviceRepository.findById(deviceId).get();
            deviceToUpdate.setName(newName);
            return deviceRepository.save(deviceToUpdate);
        } else {
            throw new NoSuchElementException("Device with this id does not exists");
        }
    }

    // delete
    public void deleteDeviceById(String id) throws NoSuchElementException{
        if(deviceRepository.existsById(id)){
            deviceRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Device with this id does not exists");
        }
    }
}
