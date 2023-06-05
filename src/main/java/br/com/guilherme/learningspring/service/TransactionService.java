package br.com.guilherme.learningspring.service;

import br.com.guilherme.learningspring.dto.CreateTransactionDto;
import br.com.guilherme.learningspring.model.Transaction;
import br.com.guilherme.learningspring.model.User;
import br.com.guilherme.learningspring.repository.TransactionRepository;
import br.com.guilherme.learningspring.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(final CreateTransactionDto transactionData) throws Exception {

        final User foundPayer = userRepository.findById(transactionData.getPayer_id()).orElseThrow(() -> new Exception("User not found"));
        final User foundPayee = userRepository.findById(transactionData.getPayee_id()).orElseThrow(() -> new Exception("User not found"));

        final float payerCurrentBalance = foundPayer.getBalance();
        final float payeeCurrentBalance = foundPayee.getBalance();

        foundPayer.setBalance(payerCurrentBalance - transactionData.getValue());
        foundPayee.setBalance(payeeCurrentBalance + transactionData.getValue());

        final Transaction newTransaction = new Transaction(foundPayer, foundPayee, transactionData.getValue());

        return transactionRepository.save(newTransaction);

    }

    public Transaction retrieveTransaction(final long id) throws Exception {
        return transactionRepository.findById(id).orElseThrow(() -> new Exception("Transaction not found"));
    }

}
