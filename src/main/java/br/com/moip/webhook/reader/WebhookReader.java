package br.com.moip.webhook.reader;

import java.util.Map;
/**
 * Contrato que define quais informações precisamos obter dos Webhooks
 * 
 * OBS: Outras implementações dessa classe poderiam existir, como para busca em algum banco de dados
 * 
 * @author Weliff
 * 
 */
public interface WebhookReader {
    
    public Map<String, Long> buscarUrlsPorQuantidade();
    
    public Map<String, Long> buscarStatusPorQuantidade();

}
