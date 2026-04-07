package com.Statement.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Transaction {

    private LocalDate date;
    private double amount;
    private double postBalance;


}
