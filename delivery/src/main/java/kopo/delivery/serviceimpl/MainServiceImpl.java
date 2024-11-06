package kopo.delivery.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import kopo.delivery.entity.Category;
import kopo.delivery.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.dto.AddressDTO;
import kopo.delivery.entity.Address;
import kopo.delivery.repository.AddressRepo;
import kopo.delivery.service.MainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService{
	
    private final AddressRepo addressRepo;
	private final CategoryRepo categoryRepo;
    Address addressentity = new Address();
    
    @Override
	@Transactional
    public void addressSave(AddressDTO addressdto) throws Exception {
    	System.out.println("service로 왓난요?");
    	addressentity.setId("habeen"); //아이디 고정
    	addressentity.setAddress(addressdto.getAddress()); //dto에서 http에서 넣은 값 가져오기
    	addressentity.setDetailAddress(addressdto.getDetailAddress());
    	System.out.println(addressentity.getId());
    	System.out.println(addressentity.getAddress());
    	System.out.println(addressentity.getDetailAddress());
    	addressRepo.save(addressentity); //데이터베이스에 저장.
    }

	@Override
	public List<Address> getAllAddress() {
		return addressRepo.findAll();
	}


	@Override
	public String roleAddress() {
		List<Address> roleY = addressRepo.findByRoleContains("Y"); //roleY = 저장되어있는 주소
		//주소를 객체로 변환
		return roleY.stream()
					.map(Address :: getAddress)
					.findFirst()
					.orElse("");
	}

	@Override
	public String sessionValue(Object address) {
		System.out.println("service: " + address);
		if(address != null) {
			 return (String) address;
		}else 
			return null;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepo.findAll();
	}


}
