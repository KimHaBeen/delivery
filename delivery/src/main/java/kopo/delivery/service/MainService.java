package kopo.delivery.service;


import java.util.List;
import java.util.Map;

import kopo.delivery.entity.Category;
import org.springframework.ui.Model;
import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;

public interface MainService {
	
	void addressSave(AddressDTO addressdto) throws Exception;
	
	List<Address> getAllAddress();
		
	String roleAddress();
	
	String sessionValue(Object address);

	List<Category> getAllCategory();
	
}