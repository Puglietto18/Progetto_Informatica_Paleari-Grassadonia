package org.example;

import com.google.gson.*;
import java.net.URI;
import java.net.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TelegramBot {


    private static Map<String, Boolean> userActive = new HashMap<>();
    private static final String TOKEN = "8457637991:AAFyzfJ7t80W_sBSaPJpWPbbn9H4gIZKXCE"; // Token del tuo bot
    private static final String API = "https://api.telegram.org/bot" + TOKEN;
    private static int lastUpdateId = 0;
    private static final HttpClient client = HttpClient.newHttpClient();
    private static boolean running = true; // false:bot si ferma

    // INVIA MESSAGGIO
    public static void sendMessage(String chatId, String text) throws Exception {
        String url = API + "/sendMessage?chat_id=" + chatId + "&text=" +
                URLEncoder.encode(text, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // LEGGE MESSAGGI TELEGRAM
    public static void checkUpdates() throws Exception {
        String url = API + "/getUpdates?offset=" + (lastUpdateId + 1);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonArray result = json.getAsJsonArray("result");
        for (JsonElement elem : result) {
            JsonObject update = elem.getAsJsonObject();
            lastUpdateId = update.get("update_id").getAsInt();
            JsonObject message = update.getAsJsonObject("message");
            String chatId = message.getAsJsonObject("chat").get("id").getAsString();
            String text = message.get("text").getAsString();
            handleMessage(chatId, text);
        }
    }

    // LOGICA BOT
    private static void handleMessage(String chatId, String text) throws Exception {
        text = text.trim();

        // Se utente non esiste, lo attiviamo di default
        userActive.putIfAbsent(chatId, true);

        // /start → riattiva bot
        if (text.equalsIgnoreCase("/start")) {
            userActive.put(chatId, true);
            sendMessage(chatId, "✅ Bot attivato!");
            return;
        }
        // /scontrino -> il bot chiede lo scontirno
        if (text.equalsIgnoreCase("/scontrino")) {
            userActive.put(chatId, true);
            sendMessage(chatId, "Inserisci il codice dello scontrino.");
            return;
        }

        // /stop → disattiva bot
        if (text.equalsIgnoreCase("/stop")) {
            userActive.put(chatId, false);
            sendMessage(chatId, "🛑 Bot disattivato");
            return;
        }

        // 🚫 Se bot disattivato → ignora tutto tranne /start
        if (!userActive.get(chatId)) {
            return;
        }

        // Logica normale
        List<String> products = CsvService.getProducts(text);
        if (products.isEmpty()) {
            sendMessage(chatId, "❌ Scontrino non trovato. Controlla il codice e riprova.");
            return;
        }

        StringBuilder reply = new StringBuilder("🧾 Acquisti:\n");
        for (String p : products) reply.append("- ").append(p).append("\n");

        sendMessage(chatId, reply.toString());
    }

    // AVVIA BOT
    public static void startBot() throws Exception {
        System.out.println("Bot avviato...");

        // Ignora vecchi messaggi Telegram
        String url = API + "/getUpdates";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray result = json.getAsJsonArray("result");

        if (result.size() > 0) {
            lastUpdateId = result.get(result.size() - 1).getAsJsonObject().get("update_id").getAsInt();
        }

        System.out.println("Il bot ora ascolta solo nuovi messaggi...");

        // LOOP infinito
        while (running) {
            checkUpdates();
            Thread.sleep(2000);
        }
        System.out.println("Bot fermato.");
    }

    public static void stopBot() {
        running = false;
    }}