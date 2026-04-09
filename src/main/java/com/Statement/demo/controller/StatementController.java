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





    private StatementGenerater statementGenerater;
    public StatementController(StatementGenerater statementGenerater) {
        this.statementGenerater = statementGenerater;
    }



     @GetMapping
    public ResponseEntity<byte[]> getAccountStatement(
             @RequestParam(required = true) Long id,
     @RequestParam(required = false) LocalDate from ,
            @RequestParam(required = false) LocalDate to,
             @RequestParam(required = false)String sort) throws Exception {

        byte[] pdfBytes = statementGenerater.generatePDF(id, from, to,sort);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"account_" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
