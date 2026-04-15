package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    private static final String FILE = "data/receipts.csv";

    private static void ensureFileExists() throws IOException {
        File file = new File(FILE);
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(file, true);
            writer.append("receipt_id,product,price\n");
            writer.close();
        }
    }

    public static void savePurchase(String receiptId, String product, double price) throws IOException {
        ensureFileExists();
        FileWriter writer = new FileWriter(FILE, true);
        writer.append(receiptId)
                .append(",")
                .append(product)
                .append(",")
                .append(String.valueOf(price))
                .append("\n");
        writer.close();
    }

    public static List<String> getProducts(String receiptId) throws IOException {
        ensureFileExists();
        List<String> products = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE));
        String line;
        reader.readLine(); // salta intestazione
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].equalsIgnoreCase(receiptId)) {
                products.add(parts[1] + " - €" + parts[2]);
            }
        }
        reader.close();
        return products;
    }
}