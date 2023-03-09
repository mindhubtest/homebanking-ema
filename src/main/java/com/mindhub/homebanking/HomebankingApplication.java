package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository repository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {


			Client cliente1 = repository.save(new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("123")));
			Client admin = repository.save(new Client("admin", "admin", "admin", passwordEncoder.encode("admin")));
			Client cliente2 = repository.save(new Client("Ema","Leiva","ema@gmail.com", passwordEncoder.encode("1234")));

			Account account1 = accountRepository.save(new Account("VIN001", LocalDateTime.now(),0,cliente1, AccountType.Ahorro,true));
			Account account2 = accountRepository.save(new Account("VIN002", LocalDateTime.now().plusDays(1),0,cliente1,AccountType.Corriente,true));

			/*transactionRepository.save(new Transaction(2000, LocalDate.now(), TransactionType.Credito, "Venta",account1.getBalance() + 2000, account1));
			account1.setBalance(account1.getBalance() + 2000);
			accountRepository.save(account1);

			transactionRepository.save(new Transaction(4000, LocalDate.now(), TransactionType.Credito, "Venta",account2.getBalance() + 4000, account2));
			account2.setBalance(account2.getBalance() + 4000);
			accountRepository.save(account2);

			transactionRepository.save(new Transaction(-8000, LocalDate.now(), TransactionType.Debito, "Compra",account1.getBalance() - 8000, account1));
			account1.setBalance(account1.getBalance() - 8000);
			accountRepository.save(account1);

			transactionRepository.save(new Transaction(-2000, LocalDate.now(), TransactionType.Debito, "Compra", account2.getBalance() - 2000, account2));
			account2.setBalance(account2.getBalance() - 2000);
			accountRepository.save(account2);

			transactionRepository.save(new Transaction(3000, LocalDate.now(), TransactionType.Credito, "Venta",account1.getBalance() + 3000, account1));
			account1.setBalance(account1.getBalance() + 3000);
			accountRepository.save(account1);

			transactionRepository.save(new Transaction(5000, LocalDate.now(), TransactionType.Credito, "Venta", account2.getBalance() + 5000, account2));
			account2.setBalance(account2.getBalance() + 2000);
			accountRepository.save(account2);*/

			Loan loan1 =loanRepository.save(new Loan("Hipotecary", 500000., List.of(12,24,36,48,60),0.30));
			Loan loan2 =loanRepository.save(new Loan("Personal", 100000., List.of(6,12,24),0.15));
			Loan loan3 =loanRepository.save(new Loan("Automotor", 300000., List.of(6,12,24,36),0.20));

			/*	clientLoanRepository.save(new ClientLoan(400000., 60, cliente1,loan1));
			clientLoanRepository.save(new ClientLoan(50000., 12, cliente1,loan2));

		ClientLoan clientLoan1 =clientLoanRepository.save(new ClientLoan(100000., 24, cliente2,loan1));

			ClientLoan clientLoan2 =clientLoanRepository.save(new ClientLoan(200000., 36, cliente2,loan2));*/

			cardRepository.save(new Card(CardColor.GOLD,"1000-0000-0000-0001",101, LocalDate.now(),LocalDate.now().plusYears(5), cliente1.getFirstName() + " " +cliente1.getLastName(),CardType.DEBIT,cliente1, true));
			cardRepository.save(new Card(CardColor.TITANIUM,"1000-0000-0000-0002",102,LocalDate.now(),LocalDate.now().plusYears(5), cliente1.getFirstName()+ " " + cliente1.getLastName(),CardType.CREDIT,cliente1, true));
			cardRepository.save(new Card(CardColor.SILVER,"1000-0000-0000-0003",103,LocalDate.now(),LocalDate.now().plusYears(5), cliente2.getFirstName() + " " + cliente2.getLastName(),CardType.CREDIT,cliente2, true));


		};
	}

}
