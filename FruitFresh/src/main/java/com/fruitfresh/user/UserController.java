package com.fruitfresh.user;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruitfresh.Modal.InformailResetPass;
import com.fruitfresh.exception.UsersNotFoundException;
import com.fruitfresh.role.Role;
import com.fruitfresh.role.RoleRepo;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;



import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(value = "admin/api/v1")
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration config;
    public UserController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(value = "/users")
    public ResponseEntity<Map<String ,Object>> getListUser(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam (value = "size", required = false, defaultValue = "10") int size ){

        List<User> listUser= new ArrayList<>();
        Pageable paging = PageRequest.of(page,size);
        Page<User> pageUser = userRepo.findAll(paging);
        listUser = pageUser.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("listUsers", listUser);
        response.put("currentpage", pageUser.getNumber());
        response.put("totalItem", pageUser.getTotalElements());
        response.put("totalPage", pageUser.getTotalPages());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping(value = "/user/{username}")
    public User getUserByName(@PathVariable String username){
        User user = userRepo.findByUsername(username);
        return  user;
    }

    @GetMapping(value = "/user/listroles")
    public List<Role> getlistRole(){
        List<Role> listRole = roleRepo.findAll();
        return listRole;
    }

    @PostMapping(value = "/saveuser")
    public ResponseEntity<User> saveUser(@RequestBody User newUser){

        Role role = roleRepo.findByRolename("User");

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.getListRoles().add(role);
        newUser.setUserstatus(true);
        return ResponseEntity.ok().body(userRepo.save(newUser));
    }

    @PutMapping(value = "/user/edit")
    public String editUser( @RequestParam (value = "id") int id, @RequestParam (value = "rolename", required = false) String rolename,
                            @RequestParam (value = "status") boolean status){

        User editUser = userRepo.findById(id).get();

        boolean flag = true;
        if (rolename!=null){

                if (rolename.equals("undefined")){
                    editUser.setUserstatus(status);
                   userRepo.save(editUser);
                }else {
                    Role role = roleRepo.findByRolename(rolename);
                    List<Role> listRole = editUser.getListRoles();

                    for (Role l : listRole) {
                        if (l.getRolename().equals(role.getRolename())) {
                            flag = false;
                            return "false";
                        }
                    }
                    if (flag){
                        editUser.getListRoles().add(role);
                        userRepo.save(editUser);
                    }
                }

        }else {
            return "false";
        }

        return "OK" ;
    }

    @DeleteMapping(value = "/deleteuser/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable int userid){
        User deleteUser = userRepo.findById(userid).get();
        userRepo.delete(deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/refreshtoken")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userRepo.findByUsername(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() +10 *60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getListRoles().stream().map(Role::getRolename).collect(Collectors.toList()))
                        .sign(algorithm);
                System.out.println("token là: ");
                System.out.println(accessToken);
                Map<String, String> tokens = new HashMap<>();
                 tokens.put("accessToken",accessToken);
                tokens.put("refreshToken",refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){

                response.setHeader("Error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> errors = new HashMap<>();
                errors.put("ErrorMgs",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(),errors);
            }
        }else {
            throw new RuntimeException("Refresh Token is missing");
        }
    }

    //forgot password--------------------


    @PostMapping(value = "/user/resetpassword")
    public String sendEmailToUser(@RequestParam String email, @RequestParam String username, HttpServletRequest request) throws UsersNotFoundException, IOException, MessagingException, TemplateException {
        boolean check = false;
        String msg ="";
        User user = userRepo.findByUsername(username);

        String token="";
        if (user!=null){

            token = RandomString.make(45);
            String url = new URL(request.getRequestURL().toString()).getHost();

            userService.setTokenForgotPass(token, username);
            String resetLink ="http://"+url +":4200/resetpass/token/"+token;

            sentResetPasswordMail(email,resetLink, user.getFullname());

        }else {
            msg = "Không tìm thấy tài khoản";
            return  msg;
        }
        return token;

    }


    @PostMapping(value = "user/changepassword")
    public ResponseEntity<User> changePass(@RequestBody User userChangePass){

        User changePass = userService.findUserByTokenForgotPass(userChangePass.getTokenforgotpass()).getBody();
        if (changePass!=null){
            String newpass = passwordEncoder.encode(userChangePass.getPassword());
            changePass.setPassword(newpass);
            changePass.setTokenforgotpass(null);
            userRepo.save(changePass);
            return new ResponseEntity<>(changePass, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public void sentResetPasswordMail(String email, String resetLink, String name) throws MessagingException, IOException, TemplateException {
        InformailResetPass infoSendMail = new InformailResetPass();

        infoSendMail.setName(name);
        infoSendMail.setResetLink(resetLink);
        String mailSubject = "Quên mật khẩu";

        Template t = config.getTemplate("resetPassword.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, infoSendMail);

        sendEmail( email, mailSubject,content);
    }

    private void sendEmail(String email, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

        //tạo form
        helper.setFrom("Enao" ,"EnterPrisenao Support");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);

    }
}
