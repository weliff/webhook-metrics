package br.com.moip.webhook.reader.log;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import br.com.moip.webhook.reader.WebhookReader;

public class WebhookReaderLogTest {

    private WebhookReader webhookReader;
    
    @Test
    public void deveLerArquivoVazinho() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/logVazinho.txt");
        assertTrue(webhookReader.buscarStatusPorQuantidade().isEmpty());
        assertTrue(webhookReader.buscarUrlsPorQuantidade().isEmpty());
        
    }
    
    @Test
    public void deveLerArquivoComCaracteresEspeciais() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/logCaracteresEspeciais.txt");
        
        assertNotNull(webhookReader.buscarUrlsPorQuantidade());
        assertNotNull(webhookReader.buscarStatusPorQuantidade());
    }
    
    @Test
    public void deveLerArquivoComEspacos() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/logComEspacos.txt");
        
        assertNotNull(webhookReader.buscarUrlsPorQuantidade());
        assertNotNull(webhookReader.buscarStatusPorQuantidade());
    }
    
    @Test
    public void deveObterDuasUrlsDiferentesComQuantidadeDoisETresRespectivamente() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/log1.txt");
        
        Map<String, Long> urlsPorQuantidade = webhookReader.buscarUrlsPorQuantidade();

        long quantidadePrimeiro = urlsPorQuantidade.get("https://grimpottery.net.br");
        long quantidadeSegundo = urlsPorQuantidade.get("https://woodenoyster.com.br");
        
        assertEquals(2, urlsPorQuantidade.size());
        assertEquals(2L, quantidadePrimeiro);
        assertEquals(3L, quantidadeSegundo);
    }
    
    @Test
    public void deveObterQuatroStatusDiferentesTodosComQuantidadeUmEOutroComDois() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/log1.txt");
        
        Map<String, Long> urlsPorQuantidade = webhookReader.buscarStatusPorQuantidade();

        long quantidadeStatus500 = urlsPorQuantidade.get("500");
        long quantidadeStatus503 = urlsPorQuantidade.get("503");
        long quantidadeStatus400 = urlsPorQuantidade.get("400");
        long quantidadeStatus200 = urlsPorQuantidade.get("200");
        
        assertEquals(4, urlsPorQuantidade.size());
        assertEquals(2, quantidadeStatus500);
        assertEquals(1, quantidadeStatus503);
        assertEquals(1, quantidadeStatus400);
        assertEquals(1, quantidadeStatus200);
    }
    
    @Test
    public void deveObterApenasTresResultadosUrls() throws Exception {
        this.webhookReader = new WebhookReaderLog("src/test/resources/log/log2.txt");
        Map<String, Long> urlsPorQuantidade = webhookReader.buscarUrlsPorQuantidade();
        
        assertEquals(3, urlsPorQuantidade.size());
    }
    
    @Test(expected = FalhaLerArquivoException.class)
    public void deveFalharAoLerArquivoNaoExitente() throws Exception {
        this.webhookReader = new WebhookReaderLog("NaoExiste");
        webhookReader.buscarUrlsPorQuantidade();
        
    }
    
}
