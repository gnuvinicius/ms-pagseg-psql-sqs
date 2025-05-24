package dev.garage474.mspagamento.infraestructure.external;

import dev.garage474.mspagamento.application.ports.output.QueueGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueueGatewayImpl implements QueueGateway {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue.url}")
    private String SQL_URL;

    public QueueGatewayImpl(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void enviarMensagem(String message) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(SQL_URL)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(request);
    }

    @Override
    public Map<String, String> recebeMensagem() {
        var request = ReceiveMessageRequest.builder()
                .queueUrl(SQL_URL)
                .maxNumberOfMessages(1)
                .waitTimeSeconds(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();
        return messages.stream()
                .collect(Collectors.toMap(Message::receiptHandle, Message::body));
    }

    @Override
    public void apagaMensagemProcessada(String receiptHandle) {
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(SQL_URL)
                .receiptHandle(receiptHandle)
                .build());
    }

}
