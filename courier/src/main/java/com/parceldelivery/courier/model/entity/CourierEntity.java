package com.parceldelivery.courier.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "couriers")
public class CourierEntity {

    @Id
    private UUID id;

    @Column(name = "username", unique = true, updatable = false)
    private String username;

    @Column(name = "current_delivery_id")
    private UUID currentDeliveryId;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "courier")
    @ToString.Exclude
    private List<CourierStatusEntity> courierStatuses;
}
