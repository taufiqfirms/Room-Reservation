//package com.kelompokdua.booking.controller;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.kelompokdua.booking.entity.Admin;
//import com.kelompokdua.booking.model.response.PagingResponse;
//import com.kelompokdua.booking.model.response.WebResponse;
//import com.kelompokdua.booking.service.AdminService;
//
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasAnyRole('ADMIN')")
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @GetMapping
//    public ResponseEntity<?> getAllUser(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size
//    ){
//        Page<Admin> adminList = adminService.getAllAdmin(page, size);
//
//        PagingResponse pagingResponse = PagingResponse.builder()
//                .page(page).size(size)
//                .totalPages(adminList.getTotalPages())
//                .totalElement(adminList.getTotalElements())
//                .build();
//
//        WebResponse<List<Admin>> response = WebResponse.<List<Admin>>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get List Admin")
//                .paging(pagingResponse)
//                .data(adminList.getContent())
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping(path = "/{id}")
//    public ResponseEntity<?> getAdminById(@PathVariable String id) {
//        Admin findAdmin = adminService.getAdminById(id);
//        WebResponse<Admin> response = WebResponse.<Admin>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get Admin By Id ")
//                .data(findAdmin)
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> deleteAdminById(@PathVariable String id){
//        adminService.deleteAdminById(id);
//        WebResponse<String> response = WebResponse.<String>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Delete Admin By Id ")
//                .data("OK")
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateUserById(@RequestBody Admin admin){
//        Admin updateAdmin = adminService.updateAdmin(admin);
//        WebResponse<Admin> response = WebResponse.<Admin>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Update Admin By Id ")
//                .data(updateAdmin)
//                .build();
//        return ResponseEntity.ok(response);
//    }
//}
