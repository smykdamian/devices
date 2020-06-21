package com.sds.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    // create
    @PostMapping("/new")
    public ResponseEntity.BodyBuilder createNewDevice(@RequestBody Device device){
        deviceService.addNewDevice(device);

        return ResponseEntity.created(URI.create(device.getId()));
    }

    // read
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable String id) {
        Device device = deviceService.findDeviceById(id);

        return ResponseEntity.ok(device);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<Device>> getDevicesByOwnerId(@PathVariable String id){
        List<Device> devices = deviceService.findDevicesByOwnerId(id);

        return ResponseEntity.ok(devices);
    }

    // update
    @PutMapping("/devices/{id}/rename/{newName}")
    public ResponseEntity.BodyBuilder renameDevice(@PathVariable String id, @PathVariable String newName){
        deviceService.changeDeviceName(id, newName);

        return ResponseEntity.ok();
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteDevice(@PathVariable String id){
        deviceService.deleteDeviceById(id);

        return ResponseEntity.ok();
    }
}
