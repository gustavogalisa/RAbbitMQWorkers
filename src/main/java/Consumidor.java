import com.rabbitmq.client.*;

import java.io.IOException;


public class Consumidor {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();

        final String NOME_FILA = "plica"
                + "";
        canal.queueDeclare(NOME_FILA, false, false, false, null);

        DeliverCallback callback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String mensagem = new String(delivery.getBody());
                System.out.println("Eu " + consumerTag + " Recebi: " + mensagem);
            }
        };

        // fila, noAck, callback, callback em caso de cancelamento (por exemplo, a fila foi deletada)
        canal.basicConsume(NOME_FILA, true, callback, new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
                System.out.println("Cancelaram a fila: " + NOME_FILA);
            }
        });
    }
}


