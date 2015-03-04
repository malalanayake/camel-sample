package camel.routing.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
				model.addAttribute("message-processor","data");
				return "index";
		}
}
