package com.Statement.demo.controller;


import com.Statement.demo.model.Account;
import com.Statement.demo.model.Transaction;
import com.Statement.demo.service.StatementGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statements")


public class StatementController {
    //Sample Transaction
    List<Transaction> transactions = new ArrayList<>();
    {   transactions.add(new Transaction(LocalDate.of(2026, 4, 1), 500, 1500));
        transactions.add(new Transaction(LocalDate.of(2026, 4, 2), -200, 1300));
        transactions.add(new Transaction(LocalDate.of(2026, 4, 3), 100, 1400));
        transactions.add(new Transaction(LocalDate.of(2026, 4, 4), -50, 1350));
        transactions.add(new Transaction(LocalDate.of(2026, 5, 4), -50, 1350));
        transactions.add(new Transaction(LocalDate.of(2026, 5, 4), -50, 1350));
        transactions.add(new Transaction(LocalDate.of(2026, 4, 7), -50, 1350));
        transactions.add(new Transaction(LocalDate.of(2026, 4, 1), -50, 1350));
         }

   Account account = new Account("ABCO1234","Ali",5000.0,transactions);
     private StatementGenerater statementGenerater;
     //Sample Account




    public StatementController(StatementGenerater statementGenerater) {
        this.statementGenerater = statementGenerater;
    }



     @GetMapping
    public ResponseEntity<byte[]> getAccountStatement(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) throws Exception {

        byte[] csv = statementGenerater.generateCSV(this.account, from, to);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"statement.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
