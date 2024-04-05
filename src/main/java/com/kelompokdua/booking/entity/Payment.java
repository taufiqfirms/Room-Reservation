package com.kelompokdua.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "token")
    private String token;
    @Column(name = "redirect_url")
    private String redirectUrl;
    @Column(name = "transaction_status")
    private String transactionStatus;

}

