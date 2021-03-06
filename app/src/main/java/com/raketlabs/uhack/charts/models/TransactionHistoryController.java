package com.raketlabs.uhack.charts.models;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class TransactionHistoryController {
    public List<TransactionHistoryItem> getTransactionHistoryDeposit() {
        TransactionHistoryItem item1 = new TransactionHistoryItem(1, DateTime.now().plusDays(1).toDate(), "7543", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item2 = new TransactionHistoryItem(1, DateTime.now().plusDays(15).toDate(), "8000", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item3 = new TransactionHistoryItem(1, DateTime.now().plusDays(30).toDate(), "4000", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item4 = new TransactionHistoryItem(1, DateTime.now().plusDays(45).toDate(), "6500", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item5 = new TransactionHistoryItem(1, DateTime.now().plusDays(90).toDate(), "6490", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item6 = new TransactionHistoryItem(1, DateTime.now().plusDays(105).toDate(), "3000", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item7 = new TransactionHistoryItem(1, DateTime.now().plusDays(120).toDate(), "4950", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item8 = new TransactionHistoryItem(1, DateTime.now().plusDays(135).toDate(), "2750", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item9 = new TransactionHistoryItem(1, DateTime.now().plusDays(150).toDate(), "3000", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item10 = new TransactionHistoryItem(1, DateTime.now().plusDays(165).toDate(), "6200", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item11 = new TransactionHistoryItem(1, DateTime.now().plusDays(180).toDate(), "5950", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item12 = new TransactionHistoryItem(1, DateTime.now().plusDays(195).toDate(), "3221", "1021231", "DEBIT", "desc");

        List<TransactionHistoryItem> transactionHistoryItems = new LinkedList<>();
        transactionHistoryItems.add(item1);
        transactionHistoryItems.add(item2);
        transactionHistoryItems.add(item3);
        transactionHistoryItems.add(item4);
        transactionHistoryItems.add(item5);
        transactionHistoryItems.add(item6);
        transactionHistoryItems.add(item7);
        transactionHistoryItems.add(item8);
        transactionHistoryItems.add(item9);
        transactionHistoryItems.add(item10);
        transactionHistoryItems.add(item11);
        transactionHistoryItems.add(item12);

        return transactionHistoryItems;
    }

    public List<TransactionHistoryItem> getTransactionHistoryWithdrawal() {
        TransactionHistoryItem item1 = new TransactionHistoryItem(1, DateTime.now().plusDays(1).toDate(), "2400", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item2 = new TransactionHistoryItem(1, DateTime.now().plusDays(15).toDate(), "3695", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item3 = new TransactionHistoryItem(1, DateTime.now().plusDays(30).toDate(), "1995", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item4 = new TransactionHistoryItem(1, DateTime.now().plusDays(45).toDate(), "0", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item5 = new TransactionHistoryItem(1, DateTime.now().plusDays(90).toDate(), "0", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item6 = new TransactionHistoryItem(1, DateTime.now().plusDays(105).toDate(), "5450", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item7 = new TransactionHistoryItem(1, DateTime.now().plusDays(120).toDate(), "5450", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item8 = new TransactionHistoryItem(1, DateTime.now().plusDays(135).toDate(), "3390", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item9 = new TransactionHistoryItem(1, DateTime.now().plusDays(150).toDate(), "595", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item10 = new TransactionHistoryItem(1, DateTime.now().plusDays(165).toDate(), "12000", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item11 = new TransactionHistoryItem(1, DateTime.now().plusDays(180).toDate(), "397", "1021231", "DEBIT", "desc");
        TransactionHistoryItem item12 = new TransactionHistoryItem(1, DateTime.now().plusDays(195).toDate(), "999", "1021231", "DEBIT", "desc");

        List<TransactionHistoryItem> transactionHistoryItems = new LinkedList<>();
        transactionHistoryItems.add(item1);
        transactionHistoryItems.add(item2);
        transactionHistoryItems.add(item3);
        transactionHistoryItems.add(item4);
        transactionHistoryItems.add(item5);
        transactionHistoryItems.add(item6);
        transactionHistoryItems.add(item7);
        transactionHistoryItems.add(item8);
        transactionHistoryItems.add(item9);
        transactionHistoryItems.add(item10);
        transactionHistoryItems.add(item11);
        transactionHistoryItems.add(item12);

        return transactionHistoryItems;
    }
}

