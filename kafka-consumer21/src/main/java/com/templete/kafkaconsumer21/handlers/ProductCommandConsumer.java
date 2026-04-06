package com.templete.kafkaconsumer21.handlers;



import com.templete.kafkaconsumer21.models.Command;
import com.templete.kafkaconsumer21.models.dto.ProductoDTO;
import com.templete.kafkaconsumer21.models.dto.Reply;
import com.templete.kafkaconsumer21.service.IProduceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class ProductCommandConsumer {
    private static final Logger log = LoggerFactory.getLogger(ProductCommandConsumer.class);
    private final IProduceService iProduceService;

    @Bean
    public Function<Message<Command<ProductoDTO>>, Message<Reply<?>>> handleCommands() {
        return msg -> {
            Command<ProductoDTO> cmd = msg.getPayload();
            String type = cmd.type() == null ? "" : cmd.type().toUpperCase();
            Reply<?> reply;
            switch (type) {
                case "CREATE" -> {
                    if(cmd.body() == null) {
                        log.warn("Create Empty body");
                        reply = new Reply<>("ERROR", "Create Empty body", null);
                        break;
                    }
                    ProductoDTO productSave = iProduceService.save(cmd.body());

                    log.info("Creating product id={} name={}, price={}, cantidad={}, descripcion={}, imagen={}",
                            productSave.getId(), productSave.getNombre(), productSave.getPrecio(), productSave.getCantidad(), productSave.getDescripcion(), productSave.getImagen());
                    reply = new Reply<>("SUCCESS", "Create product name", productSave);

                }
                case "READ" -> {
                    if(cmd.id() == null) {
                        log.warn("Id is required");
                        reply = new Reply<>("ERROR", "Id is required", null);
                        break;
                    }
                    ProductoDTO dto = iProduceService.findById(cmd.id());
                    reply = (dto == null)?
                            new Reply<>("ERROR", "Product not found", null):
                            new Reply<>("SUCCESS", "Read product name", dto);
                    log.info("Reading product by id");
                }
                case "READ_ALL" -> {
                    reply = new Reply<>("SUCCESS", "Read all products", iProduceService.findAll());
                    log.info("Reading all products");
                }
                case "UPDATE" -> {
                    if(cmd.body() == null || cmd.id() == null) {
                        log.warn("Id and body is required");
                        reply = new Reply<>("ERROR", "Id and body is required", null);
                        break;
                    }
                    ProductoDTO dto = iProduceService.findById(cmd.id());

                    if(dto != null) {
                        log.info("Updating product Nombre={}, precioce={}", dto.getNombre(), dto.getPrecio());
                        reply = new Reply<>("SUCCESS", "Update product name", dto);

                    } else  {
                        log.info("Product not found, null dto");
                        reply = new Reply<>("ERROR", "Product not found", null);

                    }
                }
                case "DELETE" -> {
                    if(cmd.id() == null) {
                        log.warn("Id is required");
                        reply = new Reply<>("ERROR", "Id is required", null);
                        break;
                    }
                    boolean result = iProduceService.delete(cmd.id());
                    reply = (result)? new Reply<>("SUCCESS", "Deleting Product", "deleted"):
                            new Reply<>("ERROR", "Product not found", null);

                    log.info("Deleting product");
                }
                default -> {
                    log.warn("Unknown command type={}", type);
                    reply = new Reply<>("ERROR", "Unknown command type", null);
                }
            }
            String correlationId = msg.getHeaders().get("correlationId", String.class);
            log.info("Recibiendo CorrelationId={}", correlationId);

            MessageBuilder<Reply<?>> out = MessageBuilder.withPayload(reply);
            if(correlationId != null) {
                out.setHeader("correlationId", correlationId);
            }

            return out.build();
        };
    }

}
