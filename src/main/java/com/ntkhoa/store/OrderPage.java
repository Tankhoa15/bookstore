package com.ntkhoa.store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class OrderPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Product> cart = (ArrayList<Product>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
			session.setAttribute("cart", cart);
		}

		String productId = request.getParameter("productId");
		Product product = getProductById(productId);
		if (product != null) {
			cart.add(product);
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Cart</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Your Cart</h1>");
		out.println("<ul>");
		for (Product productInCart : cart) {
			out.println("<li>" + productInCart.getName() + " - $" + productInCart.getPrice() + "</li>");
		}
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");
	}

	private Product getProductById(String productId) {
		
		return null;
	}

}
