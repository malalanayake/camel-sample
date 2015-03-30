package camel.routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

		/**
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
				SpringApplication.run(Application.class, args);
		}

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
				return application.sources(applicationClass);
		}

		private static Class<Application> applicationClass = Application.class;
}
