package kopo.delivery.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;
import kopo.delivery.repository.AddressRepo;
import kopo.delivery.service.MainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService{
	
    private final AddressRepo addressRepo;
    
    @Override
    public void addressSave(AddressDTO addressdto) throws Exception {
    	System.out.println("service로 왓난요?");
    	Address addressentity = new Address();
    	addressentity.setId("habeen"); //아이디 고정
    	addressentity.setAddress(addressdto.getAddress()); //dto에서 http에서 넣은 값 가져오기
    	System.out.println(addressentity.getId());
    	System.out.println(addressentity.getAddress());
    	addressRepo.save(addressentity); //데이터베이스에 저장.
    }

	@Override
	public List<Address> getAllAddress() {
		return addressRepo.findAll();
	}


    

	

}
