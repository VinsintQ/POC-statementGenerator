package com.Statement.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String holderName;
    private double balance;

    @OneToMany(orphanRemoval = true,mappedBy = "account",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Transaction> transactions =new ArrayList<>();


}
