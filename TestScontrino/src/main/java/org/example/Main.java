package org.example;

public class
Main {

    public static void main(String[] args) throws Exception {

        // Genera un scontrino finto solo una volta
        String receiptId = ReceiptService.generateReceiptId();
        CsvService.savePurchase(receiptId, "Pane", 2.0);
        CsvService.savePurchase(receiptId, "Latte", 1.5);
        CsvService.savePurchase(receiptId, "Uova", 3.2);

        System.out.println("Codice scontrino generato: " + receiptId);
        System.out.println("Il bot è ora in ascolto su Telegram...");

        // Avvia il bot interattivo
        TelegramBot.startBot();
    }
}