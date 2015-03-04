package camel.routing.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.processor.RedeliveryPolicy;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import camel.routing.monitor.Monitor;

/**
 * Configuration class
 * 
 * @author malalanayake
 *
 */
@Configuration
public class Config extends CamelConfiguration {

		// Inject activeMq url
		@Value("${activemq.uri}")
		String activeMQBrokerURL;

		// Inject activeMq username
		@Value("${activemq.username}")
		String activeMQUserName;

		// Inject activeMq passsword
		@Value("${activemq.password}")
		String activeMQPassword;

		// Inject queue name
		@Value("${queue_name}")
		String queueName;

		public Config() {
				Monitor.startReport();
		}

		/**
		 * Expose queue name into camel context
		 * 
		 * @return
		 */
		@Bean
		String queueName() {
				return queueName;
		}

		/**
		 * Expose default exchange message
		 * 
		 * @return
		 */
		@Bean
		DefaultMessage exchangeMessage() {
				return new DefaultMessage();
		}

		/**
		 * Expose the data formatter
		 * 
		 * @return
		 */
		@Bean
		XmlJsonDataFormat xmlJsonDataFormat() {
				XmlJsonDataFormat format = new XmlJsonDataFormat();
				format.setEncoding("UTF-8");
				return format;
		}

		/**
		 * Expose actibve mq factory
		 * 
		 * @return
		 */
		@Bean
		ConnectionFactory amqConnectionFactory() {
				ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
				factory.setBrokerURL(activeMQBrokerURL);
				factory.setUserName(activeMQUserName);
				factory.setPassword(activeMQPassword);
				return factory;
		}

		/**
		 * Expose activeMq component as "jms"
		 * 
		 * @return
		 */
		@Bean
		ActiveMQComponent jms() {
				ActiveMQComponent component = new ActiveMQComponent();
				component.setConnectionFactory(connectionFactory());
				component.setTransacted(true);
				return component;
		}

		@Bean
		CsvDataFormat csv() {
				return new CsvDataFormat();
		}

		/**
		 * Expose connection factory
		 * 
		 * @return
		 */
		@Bean
		ConnectionFactory connectionFactory() {
				PooledConnectionFactory factory = new PooledConnectionFactory();
				factory.setMaxConnections(3);
				factory.setConnectionFactory(amqConnectionFactory());
				return factory;
		}

		/**
		 * Expose re delivery policy
		 * 
		 * @return
		 */
		@Bean
		RedeliveryPolicy redeliveryPolicy() {
				RedeliveryPolicy policy = new RedeliveryPolicy();
				policy.setMaximumRedeliveries(2);
				policy.setRedeliveryDelay(1000);
				policy.setLogHandled(true);
				policy.setAllowRedeliveryWhileStopping(false);
				policy.setRetryAttemptedLogLevel(LoggingLevel.INFO);
				return policy;
		}

		/**
		 * Expose default error handler
		 * 
		 * @return
		 */
		@Bean
		DeadLetterChannelBuilder deadLetterErrorHandler() {
				DeadLetterChannelBuilder builder = new DeadLetterChannelBuilder();
				builder.setDeadLetterUri("direct:error");
				builder.setUseOriginalMessage(true);
				builder.setRedeliveryPolicy(redeliveryPolicy());
				return builder;
		}

		/**
		 * Congigure the internal resource view resolver
		 * 
		 * @return
		 */
		@Bean
		public InternalResourceViewResolver initViewResolver() {
				InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
				internalResourceViewResolver.setPrefix("/WEB-INF/views/");
				internalResourceViewResolver.setSuffix(".jsp");
				return internalResourceViewResolver;
		}

}
