//package com.kelompokdua.booking.service.Impl;
//
//import java.util.Optional;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.kelompokdua.booking.constant.ERole;
//import com.kelompokdua.booking.entity.Admin;
//import com.kelompokdua.booking.entity.UserCredential;
//import com.kelompokdua.booking.model.request.AdminRequest;
//import com.kelompokdua.booking.repository.AdminRepository;
//import com.kelompokdua.booking.service.AdminService;
//
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//@Service
//public class AdminServiceImpl implements AdminService{
//
//    private final AdminRepository adminRepository;
//
//    @Override
//    public Admin createAdmin(AdminRequest adminRequest, UserCredential userCredential) {
//
//        Admin admin = Admin.builder()
//        .name(adminRequest.getName())
//        .email(adminRequest.getEmail())
//        .roles(ERole.ROLE_ADMIN)
//        .userCredential(userCredential)
//        .build();
//
//        Admin newAdmin = adminRepository.saveAndFlush(admin);
//
//        return newAdmin;
//    }
//
//    @Override
//    public Page<Admin> getAllAdmin(Integer page, Integer size) {
//        if (page <=0) {
//            page = 1;
//        }
//        Pageable pageable = PageRequest.of(page-1, size);
//        return adminRepository.findAll(pageable);
//    }
//
//    @Override
//    public Admin getAdminById(String id) {
//        Optional<Admin> optionalAdmin = adminRepository.findById(id);
//        if(optionalAdmin.isPresent()) return optionalAdmin.get();
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with id : " + id + " Not Found");
//    }
//
//    @Override
//    public Admin updateAdmin(Admin admin) {
//        this.getAdminById(admin.getId());
//        return adminRepository.save(admin);
//    }
//
//    @Override
//    public void deleteAdminById(String id) {
//        this.getAdminById(id);
//        adminRepository.deleteById(id);
//    }
//
//}
