package kopo.delivery;

import jakarta.transaction.Transactional;
import kopo.delivery.entity.Address;
import kopo.delivery.entity.User;
import kopo.delivery.repository.AddressRepo;
import kopo.delivery.repository.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired private UserRepo userRepo;
    @Autowired private AddressRepo addressRepo;

    @Test
    @DisplayName("user 테스트")
    public void test() {
        User user = new User();
        user.setUserID("habeen");
        user.setUserPW("1234");
        user.setUserPhone(1234);
        user.setUserName("김하빈");
        user.setUserEmail("habeen@gmail.com");

        Address address1 = new Address();
        address1.setAddress("용마산로");
        address1.setDetailAddress("701호");
        address1.setUser(user);

        Address address2 = new Address();
        address2.setAddress("망우동");
        address2.setDetailAddress("402호");
        address2.setUser(user);

        user.setAddresses(Arrays.asList(address1, address2));

        userRepo.save(user);
    }
}
