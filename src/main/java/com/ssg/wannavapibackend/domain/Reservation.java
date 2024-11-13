package com.ssg.wannavapibackend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name="restaurant_id")
//    private Restaurant restaurant;

//    @OneToOne
//    @JoinColumn(name = "payment_id")
//    private Payment payment;

    @Column(name="is_reservable", nullable = false)
    private Boolean reservable;

    @Column(name="quest_count", nullable = false)
    private Integer guest;

    @Column(name="scheduled_at", nullable = false)
    private LocalDateTime scheduled;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}