package kopo.delivery.serviceimpl;

import kopo.delivery.dto.CustomUserDetails;
import kopo.delivery.entity.User;
import kopo.delivery.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Received username: " + username);  // username 확인용 로그
        User user = userRepo.findByUserID(username); // user_id로 사용자 검색
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with user_id: " + username);
        }
        return new CustomUserDetails(user);
    }
}
