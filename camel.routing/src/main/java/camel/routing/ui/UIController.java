package camel.routing.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codahale.metrics.Meter;

import camel.routing.monitor.Monitor;
import camel.routing.processor.MessageProcessor;

/**
 * UI Controller
 * 
 * @author malalanayake
 *
 */
@Controller
public class UIController {
		
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String mainView(Model model) {
			Meter meter=	Monitor.MATRICS.meter(MessageProcessor.MESSAGE_PROCESSOR);

				model.addAttribute("count",meter.getCount());
				model.addAttribute("mean",meter.getMeanRate());
				return "index";
		}
}
