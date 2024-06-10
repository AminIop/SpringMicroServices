package org.idb.Address.mapper;

import org.idb.Address.dto.AddressDto;
import org.idb.Address.entity.Address;

public class AddressMapper {
    public static Address fromAddressDtoToAddress(AddressDto addressDto){
        /*Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        return address;*/
        // another way to copy properties using builder pattern
        return new Address().builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .build();
    }
}
