package com.Statement.demo.service;

import com.Statement.demo.model.Account;
import com.Statement.demo.model.Transaction;
import com.Statement.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class StatementGenerater {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TemplateEngine templateEngine;


    public byte[] generatePDF(Long id, LocalDate from, LocalDate to) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()){
            throw new IllegalArgumentException("account not exist");
        }
        Account account = accountOpt.get();

        try{
            Context context = new Context();

            //setting the variable for template
            context.setVariable("account", account);

            //inject the varibles to the HTML
            String htmlContent = templateEngine.process("account-statement", context);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);

            renderer.layout();

            renderer.createPDF(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }



    }
}
