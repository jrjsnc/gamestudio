package gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TestController extends GeneralController{
	
	@RequestMapping("/test")
	public String puzzle(@RequestParam(value = "value", required = false) String value, Model model) {		
		fillModel(model);
		return "test";
	}

	@Override
	protected String getGameName() {
		return "test";
	}

	@Override
	public String render() {
		StringBuilder sb = new StringBuilder();
		sb.append("<span>ahoj</span>");		
		return sb.toString();
	}
}