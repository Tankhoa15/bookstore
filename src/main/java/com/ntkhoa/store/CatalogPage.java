package com.ntkhoa.store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/catalog-page")
public class CatalogPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		Map<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		List<Product> products = new ArrayList<>();
        products.add(new Product("book001", "Java Programming", 49.25,"Java","java 8"));
        products.add(new Product("book002", "Python for Beginners", 39.05,"Python","python 3"));
        products.add(new Product("book003", "HTML & CSS Mastery", 50,"Frontend","HTML 5 - CSS 3"));
        products.add(new Product("book004", "Spring Boot Tutorial", 100,"Java","spring framework"));
        products.add(new Product("book005", "Spring Data JPA", 99,"Java","spring boot"));
        products.add(new Product("book006", "JDBC tutorial", 79.50,"Java","java code with database"));
        products.add(new Product("book007", "Deep Learning", 200,"Data Science","Machine Learning Specialization"));
        products.add(new Product("book008", "NodeJs", 60,"Backend","javascript"));
       	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Home</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<ul>");
		for (Product product : products) {
		    out.println("<div class='book'>");
		    out.println("<h3>" + product.getName() + "</h3>");
		    out.println("<p>Category: " + product.getCategory() + "</p>");
		    out.println("<p>Description: " + product.getDescription() + "</p>");
		    out.println("<p>Price: $" + product.getPrice() + "</p>");
		    out.println("<form action=\"http://localhost:8085/BookStore/cart\" METHOD=\"post\">");
		    out.println("<input type='hidden' name='productId' value='" + product.getId() + "'>");
		    out.println("<input type='submit' value='Add to Cart'>");
		    out.println("</form>");
		    out.println("</div>");
		}

        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");		 	
	}
}
