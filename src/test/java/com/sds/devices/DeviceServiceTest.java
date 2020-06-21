package com.sds.devices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class DeviceServiceTest {
    @InjectMocks
    DeviceService deviceServiceMock;

    @Mock
    DeviceRepository deviceRepositoryMock;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNewDeviceWithNullDevice(){
        deviceServiceMock.addNewDevice(null);

        Mockito.verifyNoInteractions(deviceRepositoryMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNewDeviceWithNullName(){
        Device testDevice = new Device();
        testDevice.setName(null);
        testDevice.setOwnerId("123");

        deviceServiceMock.addNewDevice(testDevice);


        Mockito.verifyNoInteractions(deviceRepositoryMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNewDeviceWithNullOwnerId(){
        Device testDevice = new Device();
        testDevice.setName("testing device");
        testDevice.setOwnerId(null);


        deviceServiceMock.addNewDevice(testDevice);


        Mockito.verifyNoInteractions(deviceRepositoryMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNewDeviceWithAlreadyExistingId(){
        when(deviceRepositoryMock.existsById(any())).thenReturn(true);


        deviceServiceMock.addNewDevice(null);


        Mockito.verifyNoInteractions(deviceRepositoryMock);
    }

    @Test
    public void shouldFindDeviceById(){
        Device testDevice = new Device();
        testDevice.setName("testing device");
        testDevice.setId("123");
        testDevice.setOwnerId("321");
        when(deviceRepositoryMock.existsById("123")).thenReturn(true);
        when(deviceRepositoryMock.findById("123")).thenReturn(Optional.of(Optional.of(testDevice).get()));


        Device foundDevice = deviceServiceMock.findDeviceById("123");


        assertEquals(testDevice, foundDevice);
    }
    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindDeviceByWrongId(){
        Device testDevice = new Device();
        testDevice.setName("testing device");
        testDevice.setId("123");
        testDevice.setOwnerId("321");
        when(deviceRepositoryMock.findById("123")).thenReturn(Optional.of(Optional.of(testDevice).get()));


        Device foundDevice = deviceServiceMock.findDeviceById("654");


        assertNotEquals(testDevice, foundDevice);
    }

    @Test
    public void shouldFindDevicesByOwnerId(){
        Device testDevice1 = new Device();
        testDevice1.setName("testing device1");
        testDevice1.setId("123");
        testDevice1.setOwnerId("321");

        Device testDevice2 = new Device();
        testDevice2.setName("testing device2");
        testDevice2.setId("124");
        testDevice2.setOwnerId("321");

        List<Device> expectedDevicesList = new ArrayList<Device>();
        expectedDevicesList.add(testDevice1);
        expectedDevicesList.add(testDevice2);

        when(deviceRepositoryMock.findByOwnerId("321")).thenReturn(expectedDevicesList);


        List<Device> foundDevices = deviceServiceMock.findDevicesByOwnerId("321");


        assertEquals(2, foundDevices.size());
        assertTrue(foundDevices.contains(testDevice1));
        assertTrue(foundDevices.contains(testDevice2));
    }

    @Test
    public void shouldNotFindDevicesByWrongOwnerId(){
        Device testDevice1 = new Device();
        testDevice1.setName("testing device1");
        testDevice1.setId("123");
        testDevice1.setOwnerId("321");

        Device testDevice2 = new Device();
        testDevice2.setName("testing device2");
        testDevice2.setId("124");
        testDevice2.setOwnerId("321");

        List<Device> expectedDevicesList = new ArrayList<Device>();
        expectedDevicesList.add(testDevice1);
        expectedDevicesList.add(testDevice2);

        when(deviceRepositoryMock.findByOwnerId("321")).thenReturn(expectedDevicesList);


        List<Device> foundDevices = deviceServiceMock.findDevicesByOwnerId("777");


        assertEquals(0, foundDevices.size());
        assertFalse(foundDevices.contains(testDevice1));
        assertFalse(foundDevices.contains(testDevice2));
    }

    @Test
    public void shouldChangeName(){
        Device testDevice = new Device();
        testDevice.setName("testing device1");
        testDevice.setId("123");
        testDevice.setOwnerId("321");
        when(deviceRepositoryMock.existsById("123")).thenReturn(true);
        when(deviceRepositoryMock.findById("123")).thenReturn(Optional.of(Optional.of(testDevice).get()));

        deviceServiceMock.changeDeviceName("123", "updated name");

        deviceServiceMock.findDeviceById("123");

        assertEquals("updated name", testDevice.getName());
    }

    @Test(expected =  NoSuchElementException.class)
    public void shouldNotChangeNameWithWrongDeviceId(){
        Device testDevice = new Device();
        testDevice.setName("testing device");
        testDevice.setId("123");
        testDevice.setOwnerId("321");

        when(deviceRepositoryMock.findById("123")).thenReturn(Optional.of(Optional.of(testDevice).get()));


        deviceServiceMock.changeDeviceName("321", "updated name");


        assertEquals("testing device", testDevice.getName());
    }

    @Test(expected =  NoSuchElementException.class)
    public void ShouldNotDeleteNotExistingDevice(){
        Device testDevice = new Device();
        testDevice.setName("testing device");
        testDevice.setId("123");
        testDevice.setOwnerId("321");

        when(deviceRepositoryMock.findById("123")).thenReturn(Optional.of(Optional.of(testDevice).get()));


        deviceServiceMock.deleteDeviceById("777");


        verifyNoInteractions(deviceRepositoryMock);
    }
}