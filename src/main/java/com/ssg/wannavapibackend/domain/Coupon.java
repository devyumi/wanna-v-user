package com.ssg.wannavapibackend.domain;

import com.ssg.wannavapibackend.common.Type;
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
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name="created_by_id")
    private Admin createdBy;

    @ManyToOne
    @JoinColumn(name="updated_by_id")
    private Admin updatedBy;

    private String code;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "fixed_discount")
    private Integer fixedDiscount;

    @Column(name = "percentage_discount")
    private Integer percentageDiscount;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("0")
    private Boolean active;

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateAt;
}
