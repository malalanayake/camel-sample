package camel.routing.config;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Route Configuration
 * 
 * @author malalanayake
 *
 */
@Configuration
public class RouteConfig extends CamelConfiguration {
		@Autowired
		Config config;

		/**
		 * Message processing route
		 * 
		 * @return
		 */
		@Bean
		RouteBuilder messageProcessingRoute() {
				return new RouteBuilder() {
						@Override
						public void configure() throws Exception {
								from("jms:queue:" + config.queueName).to("messageProcessor").routeId("mySampleRoute");
						}
				};
		}

		/**
		 * Expose all routes
		 */
		@Override
		public List<RouteBuilder> routes() {
				return Arrays.asList(messageProcessingRoute());
		}
}
