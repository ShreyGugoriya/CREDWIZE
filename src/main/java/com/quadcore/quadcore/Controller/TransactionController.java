package com.quadcore.quadcore.Controller;

import com.lowagie.text.DocumentException;
import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.CardAllocation;
import com.quadcore.quadcore.Entities.Transaction;
import com.quadcore.quadcore.Service.CardAllocationService;
import com.quadcore.quadcore.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    CardAllocationService cardAllocationService;

    // Get all transactions
    @RequestMapping(value = "/all-transaction-details/", method = RequestMethod.GET)
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    // transaction details by card ID and Month of billing
    @RequestMapping(value = "/transaction-details/{id}/{startDate}/{endDate}/", method = RequestMethod.GET)
    public List<Transaction> getAllTransactionByIdAndMonth(@PathVariable("id") int id, @PathVariable("startDate") String startDate, @PathVariable("endDate")
    String endDate) {
        return transactionService.getAlldata(id, startDate, endDate);


    }

    @RequestMapping(value ="transaction-details/byUser/{userId}", method = RequestMethod.GET)
    public List<Transaction> getTransactionByUserId (@PathVariable("userId") int userId){

        List<Transaction> transactionList4 = transactionService.getAllTransaction();
        List<List<Transaction>> transactionByUser = new ArrayList<>();

        List< CardAllocation> cardAllocationUser = cardAllocationService.getCardAllocation();
        List<Card> cardByUSer = new ArrayList<>();

        for(CardAllocation cardAllocation : cardAllocationUser)
        {
            if(cardAllocation.getUser().getEmployee_id()==userId)
            {
                cardByUSer.add(cardAllocation.getCard());
            }
        }

        for(Card card : cardByUSer){
            transactionByUser.add(card.getTransactions());
        }

        List<Transaction> transactionResult = new ArrayList<>();

        for(List<Transaction> transactions1 : transactionByUser)
        {
            for(Transaction transaction2 : transactions1)
            {
                transactionResult.add(transaction2);
            }
        }
        return  transactionResult;

    }

    // GET ALL TRANSACTIONS BY CARD ID
    @RequestMapping(value = "/transaction-details/{id}/", method = RequestMethod.GET)
    public List<Transaction> getAllTransactionById(@PathVariable("id") int id) {
        return transactionService.getAlldataByCardId(id);
    }

    // GET ALL TRANSACTIONS BY CARD iD AND MONTH
    @RequestMapping(value = "/transaction-details-month/{id}/{month}/{year}", method = RequestMethod.GET)
    public List<Transaction> getAllTransactionBySelectedMonth(@PathVariable("id") int id, @PathVariable("month") int month,@PathVariable("year") int year) {
        return transactionService.getAllTransactionByMonth(id, month,year);
    }

    // GET SUM OF ALL TRANSACTIONS OF A PARTICULAR MONTH
    @RequestMapping(value = "/transaction-amount-month/{id}/", method = RequestMethod.GET)
    public Long getAllTransactionAmountByCurrMonth(@PathVariable("id") int id) {
        return transactionService.getAllAmountByMonth(id);
    }

    //DOWNLOAD THE TRANSACTION SUMMARY OF A PARTICULAR MONTH
    @GetMapping("/transaction-details/{id}/{month}/{year}/export/pdf/")
    public void exportToPDF(HttpServletResponse response, @PathVariable("id") int id, @PathVariable("month") int month,@PathVariable("year") int year)
            throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transaction_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Transaction> listTransactions = transactionService.getAllTransactionByMonth(id, month,year);
        PdfExporter exporter = new PdfExporter(listTransactions);
        exporter.export(response);
    }

    @GetMapping("/transaction-details/{empId}/export/pdf/")
    public void last6MonthExportToPDF(HttpServletResponse response, @PathVariable("empId") int empId)
            throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transaction_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Transaction> listTransactions = transactionService.getAllTransactionOfLast6Month(empId);
        PdfExporter exporter = new PdfExporter(listTransactions);
        exporter.export(response);
    }
    @RequestMapping(value = "/transaction-details/", method = RequestMethod.POST)
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);

    }

    @RequestMapping(value = "/user-graph/second/{card_id}/{year}",method = RequestMethod.GET)
    public List<?> getTransactionPerMonth(@PathVariable("card_id") int card_id,@PathVariable("year") int year){
        return  transactionService.getTransactionPerMonth(card_id,year);
    }
    @RequestMapping(value = "/user-graph/first/{card_id}",method = RequestMethod.GET)
    public List<?> getTransactionPerEType(@PathVariable("card_id") int card_id){
        return  transactionService.getTransactionPerEtype(card_id);
    }
    @RequestMapping(value = "/sum-year/{card_id}/{year}",method = RequestMethod.GET)
    public List<?> getTransactionAmountPerYear(@PathVariable("card_id") int card_id, @PathVariable("year") int year){
        return  transactionService.getTransactionPerYearPerCard(card_id, year);
    }
    @RequestMapping(value = "/transaction-rewards/{card_id}",method = RequestMethod.GET)
    public Long getRewardPerCard(@PathVariable("card_id") int card_id){
        return  transactionService.getRewardsPerCard(card_id);
    }
    @RequestMapping(value = "/pending-payments/",method = RequestMethod.GET)
    public Long getRewardPerCard(){
        return  transactionService.getPendingPayments();
    }

    @RequestMapping(value = "/second-graph/admin/", method = RequestMethod.GET)
        public List<?> getCardExpenditureOfAdmin (){
        return transactionService.getCardExpenditureOfAdmin();
    }

    @RequestMapping(value = "/third-graph/admin" , method = RequestMethod.GET)
    public List<?> getShareOfExpenseOfAdmin(){
        return transactionService.getShareOfExpenseOfAdmin();
    }


    @RequestMapping(value = "/employee-details/graph/{card_id}/{month}/{year}" , method = RequestMethod.GET)
    public List<?> getShareOfExpenseOfUserByMonth(@PathVariable("card_id") int card_id, @PathVariable("month") int month,@PathVariable("year") int year){
        return transactionService.getShareOfExpenseOfUserByMonth(card_id,month,year);
    }

}
