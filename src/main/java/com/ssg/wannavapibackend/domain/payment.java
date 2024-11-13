package com.ssg.wannavapibackend.domain;

import com.ssg.wannavapibackend.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "payment_number", nullable = false)
    private Long paymentNumber;

    @Column(name = "actual_price", nullable = false)
    private Double actualPrice;

    @Column(name = "final_price", nullable = false)
    private Double finalPrice;

    @Column(name = "points_used")
    private Integer pointsUsed;

    @Column(name = "percentage_discount")
    private Double percentageDiscount;

    @Column(name = "fixed_discount")
    private Double fixedDiscount;

    @Column(name = "coupon_code")
    private Double couponCode;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private Address address;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}
