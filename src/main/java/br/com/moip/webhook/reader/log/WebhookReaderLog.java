package br.com.moip.webhook.reader.log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.moip.webhook.model.Webhook;
import br.com.moip.webhook.reader.WebhookReader;

/**
 * @author Weliff Lima
 * Essa classe ler informações do Webhook a partir de arquivos de logs
 */
public class WebhookReaderLog implements WebhookReader {
    
    private String caminho;

    public WebhookReaderLog(String caminho) {
        this.caminho = caminho;
        
    }

    public Map<String, Long> buscarUrlsPorQuantidade() {
        Map<String, Long> urlsPorQuantidade = new HashMap<>();
        
        lerWebhooks(webhook -> urlsPorQuantidade.merge(webhook.getUrl(), 1L, Long::sum));
        
        return urlsPorQuantidade
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1 + e2, LinkedHashMap::new));

    }
    
    public Map<String, Long> buscarStatusPorQuantidade() {
        Map<String, Long> statusPorQuantidade = new HashMap<>();
        
        lerWebhooks(webhook -> statusPorQuantidade.merge(webhook.getStatus(), 1L, Long::sum));
        
        return statusPorQuantidade
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (l1, l2) -> l1 + l2, LinkedHashMap::new));
    }

    private void lerWebhooks(Consumer<Webhook> consumer) {
        try (Stream<String> linhas = Files.lines(Paths.get(this.caminho), StandardCharsets.ISO_8859_1)){
            linhas.forEach(s -> criarWebhook(s).ifPresent(consumer)); 
        } catch (IOException e) {
            throw new FalhaLerArquivoException("Falha ao ler o arquivo", e);
        }
    }
    
    private Optional<Webhook> criarWebhook(String linha) {
        return Optional.of(linha)
            .filter(s -> s.startsWith("level"))
            .map(s -> {
                String[] dados = s.split("\\s");
                return  new Webhook(dados[2].replaceAll("request_to=|\"", ""), dados[dados.length - 1].replaceAll("response_status=|\"", ""));
            });
    }
}
