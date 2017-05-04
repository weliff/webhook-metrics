package br.com.moip.webhook;

import br.com.moip.webhook.reader.WebhookReader;
import br.com.moip.webhook.reader.log.WebhookReaderLog;

public class Main {

    public static void main(String[] args) {

        WebhookReader reader = new WebhookReaderLog("log.txt");
        
        System.out.println(reader.buscarUrlsPorQuantidade());
        System.out.println(reader.buscarStatusPorQuantidade());
    }
}
