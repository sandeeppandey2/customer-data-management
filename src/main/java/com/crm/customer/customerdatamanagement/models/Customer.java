package com.crm.customer.customerdatamanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "firstname")
    @NotBlank(message = "Customer First Name is mandatory")
    @Size(message = "Customer First Name must not  greater than 255 character", max = 255)
    private String customerFirstName;

    @Column(name = "lastname")
    @NotBlank(message = "Customer Last Name is mandatory")
    @Size(message = "Customer Last Name must not  greater than 255 character", max = 255)
    private String customerLastName;

    @Column(name = "customer_age")
    @NotNull(message = "Customer age is mandatory")
    @Positive(message = "Age must be positive number")
    private int age;

    @Column(name = "customer_email", unique = true)
    @Email(message = "Email should be in proper format", regexp = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")
    private String customerEmailAddress;

    @Embedded
    @Valid
    private CustomerAddress customerAddress;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedDateTime;
}

