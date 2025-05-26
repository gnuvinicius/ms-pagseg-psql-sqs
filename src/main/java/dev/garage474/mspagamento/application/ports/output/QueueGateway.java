package dev.garage474.mspagamento.application.ports.output;

import java.util.Map;

public interface QueueGateway {

    void enviarMensagem(String message);
    Map<String, String> recebeMensagem();
    void apagaMensagemProcessada(String receiptHandle);
}
