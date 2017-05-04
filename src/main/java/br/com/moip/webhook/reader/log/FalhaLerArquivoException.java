package br.com.moip.webhook.reader.log;

public class FalhaLerArquivoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FalhaLerArquivoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FalhaLerArquivoException(String message) {
        super(message);
    }

    
    
}
