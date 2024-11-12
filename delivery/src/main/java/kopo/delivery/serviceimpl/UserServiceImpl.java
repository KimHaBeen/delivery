package kopo.delivery.serviceimpl;

import kopo.delivery.dto.UserDTO;
import kopo.delivery.entity.User;
import kopo.delivery.repository.UserRepo;
import kopo.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //db에 동일한 userID를 가진 회원이 존재하는지 검증

    @Override
    public void signUpComplete(UserDTO userDTO) {

        boolean isUser = userRepo.existsByUserID(userDTO.getUserID());
        if (isUser) {
            return;
        }

        User user = new User();
        user.setUserID(userDTO.getUserID());
        user.setUserPW(bCryptPasswordEncoder.encode(userDTO.getUserPW()));
        user.setUserName(userDTO.getUserName());
        user.setUserPhone(user.getUserPhone());
        user.setUserEmail(userDTO.getUserEmail());

        userRepo.save(user);
    }


}
