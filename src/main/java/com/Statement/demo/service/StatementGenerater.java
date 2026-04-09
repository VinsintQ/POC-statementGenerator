package com.Statement.demo.service;

import com.Statement.demo.model.Account;
import com.Statement.demo.model.Transaction;
import com.Statement.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    public byte[] generateCSV(Long id, LocalDate from, LocalDate to) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()){
            throw new IllegalArgumentException("account not exist");
        }
        Account account = accountOpt.get();


















//        StringBuilder csv = new StringBuilder();
//        // Account  table header
//        csv.append("accountNumber,holderName,balance\n");
//
//        csv.append(account.getAccountNumber()).append(",");
//        csv.append(account.getHolderName()).append(",");
//        csv.append(account.getBalance()).append("\n");
//        //trasactions Table
//        csv.append("Date,Amount,post Blanace\n");

//       List<Transaction> sorted= account.getTransactions().stream().sorted(Comparator.comparing(Transaction::getDate)).toList();
//
//
//        for (Transaction transaction :sorted){
//            if (to!=null) {
//                if (!transaction.getDate().isAfter(to) & !transaction.getDate().isBefore(from)) {
//                    csv.append(transaction.getDate()).append(",")
//                            .append(transaction.getAmount()).append(",")
//                            .append(transaction.getPostBalance()).append("\n");
//                }
//            }else{
//                csv.append(transaction.getDate()).append(",")
//                        .append(transaction.getAmount()).append(",")
//                        .append(transaction.getPostBalance()).append("\n");
//            }
//        }

          return csv.toString().getBytes(StandardCharsets.UTF_8);
    }
}
