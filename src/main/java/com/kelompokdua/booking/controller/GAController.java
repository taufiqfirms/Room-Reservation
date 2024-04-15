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
//import com.kelompokdua.booking.entity.GA;
//import com.kelompokdua.booking.model.response.PagingResponse;
//import com.kelompokdua.booking.model.response.WebResponse;
//import com.kelompokdua.booking.service.GAService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/ga")
//@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('ADMIN')")
//public class GAController {
//
//    private final GAService gaService;
//
//    @GetMapping
//    public ResponseEntity<?> getAllUser(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size
//    ){
//        Page<GA> gaList = gaService.getAllGA(page, size);
//
//        PagingResponse pagingResponse = PagingResponse.builder()
//                .page(page).size(size)
//                .totalPages(gaList.getTotalPages())
//                .totalElement(gaList.getTotalElements())
//                .build();
//
//        WebResponse<List<GA>> response = WebResponse.<List<GA>>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get List GA")
//                .paging(pagingResponse)
//                .data(gaList.getContent())
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping(path = "/{id}")
//    public ResponseEntity<?> getGAById(@PathVariable String id) {
//        GA findGA = gaService.getGAById(id);
//        WebResponse<GA> response = WebResponse.<GA>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get Admin By Id ")
//                .data(findGA)
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> deleteGAById(@PathVariable String id){
//        gaService.deleteGAById(id);
//        WebResponse<String> response = WebResponse.<String>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Delete GA By Id ")
//                .data("OK")
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateUserById(@RequestBody GA ga){
//        GA updateGA = gaService.updateGA(ga);
//        WebResponse<GA> response = WebResponse.<GA>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Update GA By Id ")
//                .data(updateGA)
//                .build();
//        return ResponseEntity.ok(response);
//    }
//
//}
