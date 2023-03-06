package com.parceldelivery.message.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message_metadata")
public class MessageMetadataEntity {

    @Id
    private UUID id;

    @Column(name = "tag")
    private Long tag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
