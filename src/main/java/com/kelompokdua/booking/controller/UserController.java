package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.entity.User;

import com.kelompokdua.booking.model.response.PagingResponse;

import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('GA', 'ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<User> nasabahList = userService.getAllUser(page, size);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page).size(size)
                .totalPages(nasabahList.getTotalPages())
                .totalElement(nasabahList.getTotalElements())
                .build();

        WebResponse<List<User>> response = WebResponse.<List<User>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List User")
                .paging(pagingResponse)
                .data(nasabahList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User findUser = userService.getUserById(id);
        WebResponse<User> response = WebResponse.<User>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get User By Id ")
                .data(findUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete User By Id ")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateUserById(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        WebResponse<User> response = WebResponse.<User>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Update Nasabah By Id ")
                .data(updateUser)
                .build();
        return ResponseEntity.ok(response);
    }

}