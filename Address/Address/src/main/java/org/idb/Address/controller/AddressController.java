package org.idb.Address.controller;

import lombok.extern.slf4j.Slf4j;
import org.idb.Address.dto.AddressDto;
import org.idb.Address.entity.Address;
import org.idb.Address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class AddressController {


    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<Object> addAddress(@RequestBody AddressDto addressDto) {
        log.info("add address");
        Address address = addressService.AddAddress(addressDto);
        if(address != null) {
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Address not added",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddresses() {
        log.info("get all addresses");
        List<Address> addressList = addressService.findAllAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Object> getAddressById(@PathVariable int id) {
        log.info("get address by id: "+id);
        Optional<Address> address = addressService.findAddressById(id);
        return address.<ResponseEntity<Object>>map(
                        value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND)
                );
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable int id) {
        log.error("delete address by id: "+id);
        boolean isDeleted = addressService.deleteAddressById(id);
        String responseMessage = isDeleted ? "Address is deleted" : "Address not found";
        HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseMessage, status);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Object> updateAddressById(@PathVariable int id,@RequestBody AddressDto addressDto) {
        log.info("put address by Id: "+id);
        boolean isUpdated = addressService.updateAddress(id,addressDto);
        Object responseMessage = null;
        if(isUpdated) {
            Map<String,Object> map = new HashMap<>();
            map.put("Is updated",true);
            map.put("data",addressDto);
            responseMessage = map;
        }else{
            responseMessage = "Address not found";
        }
        HttpStatus status = isUpdated ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseMessage, status);
    }

}
