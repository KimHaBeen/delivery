package kopo.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;

public interface AddressRepo extends JpaRepository<Address, String>{
	
	public String findAddressById(String id);

	public void save(AddressDTO address);
}
