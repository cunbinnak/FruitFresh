package com.fruitfresh.user;

import com.fruitfresh.exception.UsersNotFoundException;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {


    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameAndUserstatus(username , true);
        if (user==null){
            throw new UsernameNotFoundException("Username not found in system");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getListRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public void setTokenForgotPass(String token, String username) throws UsersNotFoundException {
        String msg ="";

        User user = userRepo.findByUsername(username);
        if(user!=null){
            user.setTokenforgotpass(token);
            userRepo.save(user);

        }else {
            msg ="Không tìm thấy tài khoản";
            throw  new UsersNotFoundException(msg);
        }
    }


    public ResponseEntity<User> findUserByTokenForgotPass(@RequestParam String token){

        User user = userRepo.findByTokenforgotpass(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public void updateNewPass(User user , String newpass){
        user.setPassword(passwordEncoder.encode(newpass));
        user.setTokenforgotpass(null);
        userRepo.save(user);
    }
}
