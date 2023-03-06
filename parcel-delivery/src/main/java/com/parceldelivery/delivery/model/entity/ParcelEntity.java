package com.parceldelivery.delivery.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcels")
public class ParcelEntity {

    @Id
    private UUID id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @OneToOne(mappedBy = "parcel", cascade = CascadeType.ALL)
    @ToString.Exclude
    private DeliveryEntity delivery;
}
