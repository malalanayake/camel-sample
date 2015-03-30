package camel.routing.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.codahale.metrics.Meter;

import camel.routing.monitor.Monitor;

/**
 * Main entry point
 * 
 * @author malalanayake
 *
 */
@Component
public class MessageProcessor implements Processor {
		public static final String MESSAGE_PROCESSOR = "message-processor";
		private Logger log = Logger.getLogger(this.getClass());

		@Override
		public void process(Exchange exchange) throws Exception {
				Meter meter = Monitor.MATRICS.meter(MESSAGE_PROCESSOR);
				meter.mark();
				String input = (String) exchange.getIn().getBody(String.class);

				log.info("=====Need to process the data here=====");
				log.info("Input message " + input);
		}

}
