package camel.routing.config;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import camel.routing.Application;

/**
 * Test class which is testing the Camel Route
 * 
 * @author malalanayake
 *
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@MockEndpoints
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RouteConfigTest extends CamelSpringTestSupport {

	// Inject through Configuration class
	@Autowired
	private AbstractApplicationContext applicationContext;

	@Autowired
	private String queueName;

	private MockEndpoint mockEndpoint;

	public void configMockRoutes() throws Exception {
		context.getRouteDefinition("mySampleRoute").adviceWith(context, new AdviceWithRouteBuilder() {

			/**
			 * Mock the routes for testing
			 */
			@Override
			public void configure() throws Exception {

				interceptSendToEndpoint("jms:queue:" + queueName).skipSendToOriginalEndpoint().to(
					"mock:bean:messageProcessor");

			}
		});

	}

	@Test
	public void messageRouteTest() throws Exception {
		configMockRoutes();
		mockEndpoint = getMockEndpoint("mock:bean:messageProcessor");
		template.setDefaultEndpointUri("jms:queue:" + queueName);

		mockEndpoint.setExpectedMessageCount(1);
		template.sendBody("Test data");

		assertMockEndpointsSatisfied();
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return applicationContext;
	}

}
