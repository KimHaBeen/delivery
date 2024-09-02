package kopo.delivery.service;


import java.util.List;

import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;

public interface MainService {
	
	public void addressSave(AddressDTO addressdto) throws Exception;
	
	List<Address> getAllAddress();
}
