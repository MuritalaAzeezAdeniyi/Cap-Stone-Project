package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.UserPrinciple;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
   private UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      Users users = usersRepo.findByUsername(username);
      if (users == null) {

          throw new UsernameNotFoundException("user not found " +username);
      }

      return new UserPrinciple(users);
    }
}
