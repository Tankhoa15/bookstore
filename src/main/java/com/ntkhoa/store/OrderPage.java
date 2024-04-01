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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        List<Product> cartItems = (ArrayList<Product>) session.getAttribute("cartItems");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Shopping Cart</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Your Shopping Cart</h1>");
        if (cartItems != null && !cartItems.isEmpty()) {
            out.println("<ul>");
            for (Product item : cartItems) {
                out.println("<li>" + item.getName() + " - $" + item.getPrice() + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>Your shopping cart is empty.</p>");
        }
        
        out.println("</body>");
        out.println("</html>");
	}

}
