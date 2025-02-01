package com.profitkey.stock.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public void redirectToSwagger(HttpServletResponse response) throws IOException {
		response.sendRedirect("/swagger-ui/index.html");
	}
}
