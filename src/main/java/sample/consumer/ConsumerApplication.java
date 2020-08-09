package sample.consumer;

import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.Sensor;


@SpringBootApplication
//@EnableSchemaRegistryClient
public class ConsumerApplication {

	private final Log logger = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
	// injected from application.properties
    @Value("${topic.name}")  
	  private String topicName;

    @Value("${topic.partitions-num}")
	  private int numPartitions;

    @Value("${topic.replication-factor}")
	  private int replicas;

	  @Bean
	  NewTopic moviesTopic() {
	    return new NewTopic(topicName, numPartitions, (short) replicas);
	  }
		
	 @KafkaListener(topics="sensor_topic",groupId="simple-consumer")
	  public void avroConsume(ConsumerRecord<String,Sensor> sense) { 
		  System.out.println("Sensor message consumed!!" +sense.toString());
		 // System.out.println(String.format("Consumed message -> %s", sense));
	  }
}
/*	 
	@Configuration
	static class ConfluentSchemaRegistryConfiguration {
		@Bean
		public SchemaRegistryClient schemaRegistryClient(@Value("${spring.kafka.properties.schema.registry.url}") String endpoint){
			ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
			client.setEndpoint("endpoint");
			return client;
		}*/

