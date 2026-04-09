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
import java.util.stream.Stream;


@Service
public class StatementGenerater {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TemplateEngine templateEngine;


    public byte[] generatePDF(Long id, LocalDate from, LocalDate to,String sort) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()){
            throw new IllegalArgumentException("account not exist");
        }
        Account account = accountOpt.get();
        List<Transaction> filteredTransactions = account.getTransactions()
                .stream()
                .filter(tx -> (from == null || !tx.getDate().isBefore(from))
                        && (to == null || !tx.getDate().isAfter(to)))
                .sorted((tx1, tx2) -> {
            if ("amount".equalsIgnoreCase(sort)) {
                return Double.compare(tx1.getAmount(), tx2.getAmount()); // primitive-safe comparison
            } else {
                return tx1.getDate().compareTo(tx2.getDate()); // default date comparison
            }
        }).toList();




        account.setTransactions(filteredTransactions);
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
