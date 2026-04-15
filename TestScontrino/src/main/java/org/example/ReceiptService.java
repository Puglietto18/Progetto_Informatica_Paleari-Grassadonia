package org.example;

import java.util.UUID;

public class ReceiptService {

    // genera codice scontrino casuale
    public static String generateReceiptId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}