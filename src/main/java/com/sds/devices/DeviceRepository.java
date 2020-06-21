package com.sds.devices;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {
    List<Device> findByOwnerId(String id);
}
