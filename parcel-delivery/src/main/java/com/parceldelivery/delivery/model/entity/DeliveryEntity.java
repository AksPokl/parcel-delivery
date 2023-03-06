package com.parceldelivery.delivery.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deliveries")
public class DeliveryEntity {

    @Id
    private UUID id;

    @Column(name = "courier_id")
    private UUID courierId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "last_status")
    private String lastStatus;

    @JoinColumn(name = "parcel_id")
    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private ParcelEntity parcel;

    @OneToMany(mappedBy = "delivery")
    @ToString.Exclude
    private List<DeliveryStatusEntity> deliveryStatuses;
}
