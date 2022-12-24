package com.barogo.domain.auth.application;

import com.barogo.domain.auth.PrincipalUserDetails;
import com.barogo.domain.auth.User;
import com.barogo.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findFirstByUserId(userId);

        if(ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException(userId);

        return new PrincipalUserDetails(user);
    }
}
