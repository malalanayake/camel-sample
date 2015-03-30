package camel.routing.monitor;

import com.codahale.metrics.*;
import java.util.concurrent.TimeUnit;

public class Monitor {
		public static final MetricRegistry MATRICS = new MetricRegistry();

		public static void main(String args[]) {
				startReport();
				Meter requests = MATRICS.meter("requests");
				requests.mark();
				wait5Seconds();
		}

		public static void startReport() {
				ConsoleReporter reporter = ConsoleReporter.forRegistry(MATRICS)
				  .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
				reporter.start(1, TimeUnit.SECONDS);
		}

		static void wait5Seconds() {
				try {
						Thread.sleep(5 * 100000000);
				} catch (InterruptedException e) {
				}
		}
}
