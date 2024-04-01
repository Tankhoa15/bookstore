package com.ntkhoa.store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
		List<Product> previousItems = (ArrayList<Product>) session.getAttribute("previousItems");
		if (previousItems == null) {
			previousItems = new ArrayList();
			session.setAttribute("previousItems", previousItems);
		}
		
		List<Product> products = new ArrayList<>();
        products.add(new Product("book001", "Java Programming", 49.99,"Information Technology","rgerh"));
        products.add(new Product("book002", "Python for Beginners", 39.99,"information technology","rjyjty"));
        products.add(new Product("book003", "HTML & CSS Mastery", 29.99,"information technology","jtt7"));
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
		out.println(docType + "<HTML>\n" +
			"<HEAD><TITLE></TITLE></HEAD>\n" +
		    "<BODY BGCOLOR=\"#FDF5E6\">\n");		  
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
