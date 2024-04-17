package com.kelompokdua.booking.service.Impl;

import java.util.List;

import com.kelompokdua.booking.model.request.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.Role;
import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.model.response.UserResponse;
import com.kelompokdua.booking.repository.UserCredentialRepository;
import com.kelompokdua.booking.security.JwtUtils;
import com.kelompokdua.booking.service.AuthService;
import com.kelompokdua.booking.service.RoleService;
import com.kelompokdua.booking.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final RoleService roleService;
    private final UserCredentialRepository userCredentialRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${app.enigma-coop.username-admin}")
    private String usernameAdmin;

    @Value("${app.enigma-coop.password-admin}")
    private String passwordAdmin;

//    @PostConstruct
//    public void initSuperAdmin(){
//        Optional<UserCredential> optionalUserCred = userCredentialRepository.findByUsername(usernameAdmin);
//        if(optionalUserCred.isPresent()) return;
//
//        Role superAdminRole = roleService.getOrSave(ERole.ROLE_GA);
//        Role adminRole = roleService.getOrSave(ERole.ROLE_ADMIN);
//        Role employeeRole = roleService.getOrSave(ERole.ROLE_EMPLOYEE);
//
//        String hashPassword = passwordEncoder.encode(passwordAdmin);
//
//        UserCredential userCredential = UserCredential.builder()
//        .username(usernameAdmin)
//        .password(hashPassword)
//        .roles(List.of(superAdminRole, adminRole, employeeRole))
//        .build();
//        userCredentialRepository.saveAndFlush(userCredential);
//    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        // untuk role
        Role roleEmployee = roleService.getOrSave(ERole.ROLE_EMPLOYEE);
        // hash password
        String hassPassword = passwordEncoder.encode(userRequest.getPassword());
        // user credential baru
        UserCredential userCredential = userCredentialRepository.saveAndFlush(UserCredential.builder()
        .username(userRequest.getUsername())
        .password(hassPassword)
        .roles(List.of(roleEmployee))
        .build());
        User user = userService.createEmployee(userRequest, userCredential);
        //list role
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .division(user.getEmail())
        .position(user.getPosition())
        .email(user.getEmail())
        .username(userRequest.getUsername())
        .roles(roles)
        .build();
    }

    @Override
    public UserResponse registerAdvance(UserAdvanceRequest userAdvanceRequest) {
        // untuk role
        Role roleEmployee = roleService.getOrSave(userAdvanceRequest.getRole());
        // hash password
        String hassPassword = passwordEncoder.encode(userAdvanceRequest.getPassword());
        // user credential baru
        UserCredential userCredential = userCredentialRepository.saveAndFlush(UserCredential.builder()
        .username(userAdvanceRequest.getUsername())
        .password(hassPassword)
        .roles(List.of(roleEmployee))
        .build());
        User user = userService.createAdminOrGA(userAdvanceRequest, userCredential);
        //list role
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .division(user.getEmail())
        .position(user.getPosition())
        .email(user.getEmail())
        .username(userAdvanceRequest.getUsername())
        .roles(roles)
        .build();
    }

    @Override
    public String login(AuthRequest authRequest) {
       Authentication authentication = new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(),
        authRequest.getPassword());
        // call method untuk kebutuhan validasi credential
        Authentication authenticated = authenticationManager.authenticate(authentication);
        // Jika username dan password valid, maka sesinya disimpan untuk akses resource tertentu
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // berikan token
        UserCredential userCredential = (UserCredential)authenticated.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }



//    @Override
//    public AdminResponse registerAdmin(AdminRequest adminRequest) {
//        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
//        String hassPassword = passwordEncoder.encode(adminRequest.getPassword());
//        UserCredential userCredential = userCredentialRepository.saveAndFlush(UserCredential.builder()
//        .username(adminRequest.getUsername())
//        .password(hassPassword)
//        .roles(List.of(roleAdmin))
//        .build());
//
//        Admin admin = adminService.createAdmin(adminRequest, userCredential);
//        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
//        return AdminResponse.builder()
//        .id(admin.getId())
//        .name(admin.getName())
//        .email(admin.getEmail())
//        .username(adminRequest.getUsername())
//        .roles(roles)
//        .build();
//
//    }
//
//    @Override
//    public GAResponse registerGA(GARequest gaRequest) {
//        Role roleSuperAdmin = roleService.getOrSave(ERole.ROLE_GA);
//        String hassPassword = passwordEncoder.encode(gaRequest.getPassword());
//        UserCredential userCredential = userCredentialRepository.saveAndFlush(UserCredential.builder()
//        .username(gaRequest.getUsername())
//        .password(hassPassword)
//        .roles(List.of(roleSuperAdmin))
//        .build());
//
//        GA ga = gaService.createGA(gaRequest, userCredential);
//        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
//        return GAResponse.builder()
//        .id(ga.getId())
//        .name(ga.getName())
//        .email(ga.getEmail())
//        .username(gaRequest.getUsername())
//        .roles(roles)
//        .build();
//    }
}
