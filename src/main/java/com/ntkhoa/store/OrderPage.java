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
		Map<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}

		String productId = request.getParameter("productId");
        String action = request.getParameter("action");
        
        String lastAddedProduct = (String) session.getAttribute("lastAddedProduct");

        if (productId != null && !productId.isEmpty()) {
            if (action != null && !action.isEmpty()) {
                int quantity = cart.getOrDefault(productId, 0);
                if (action.equals("decrease")) { 
                    if (quantity > 1) {
                        cart.put(productId, quantity - 1);
                    }
                    else if (quantity == 1) {
                        cart.remove(productId);
                    }
                } else if (action.equals("increase")) {
                    cart.put(productId, quantity + 1); 
                }
            } else if (!productId.equals(lastAddedProduct)) { 
                cart.put(productId, cart.getOrDefault(productId, 0) + 1);
                session.setAttribute("lastAddedProduct", productId);
            }
        }
        
      	response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Cart</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Your Cart</h1>");
		out.println("<table>");
		out.println("<tr><th>ID</th><th>Name</th><th>Quantity</th><th>Price</th><th>Total</th></tr>");
		double total = 0;
		for (Map.Entry<String, Integer> entry : cart.entrySet()) {
			String productIdInCart = entry.getKey();
			int quantity = entry.getValue();
			Product productInCart = getProductById(productIdInCart);
			if (productInCart != null) {
                double price = productInCart.getPrice();
                double subTotal = price * quantity;
                total += subTotal;
                out.println("<tr><td>" + productInCart.getId() + "</td><td>" + productInCart.getName() + "</td><td>");
                out.println("<form action=\"cart\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"productId\" value=\"" + productIdInCart + "\">");
                out.println("<input type=\"hidden\" name=\"action\" value=\"decrease\">");
                out.println("<input type=\"submit\" value=\"-\">");
                out.println("</form>");
                out.println(quantity);
                out.println("<form action=\"cart\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"productId\" value=\"" + productIdInCart + "\">");
                out.println("<input type=\"hidden\" name=\"action\" value=\"increase\">");
                out.println("<input type=\"submit\" value=\"+\">");
                out.println("</form>");
                out.println("</td><td>" + price + "</td><td>" + subTotal + "</td></tr>");
            }
		}
		out.println("</table>");
		out.println("<h2>Total: " + total + "</h2>");
		out.println("<form action=\"checkout\" method=\"post\">");
		out.println("<input type=\"submit\" value=\"Checkout\">");
		out.println("</form>");
		out.println("<form action=\"catalog-page\" method=\"get\">");
		out.println("<input type=\"submit\" value=\"Back to Catalog\">");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");		      
	}
	
	private Product getProductById(String productId) {
		List<Product> products = new ArrayList<>();
        products.add(new Product("book001", "Java Programming", 49.25,"Java","java 8"));
        products.add(new Product("book002", "Python for Beginners", 39.05,"Python","python 3"));
        products.add(new Product("book003", "HTML & CSS Mastery", 50,"Frontend","HTML 5 - CSS 3"));
        products.add(new Product("book004", "Spring Boot Tutorial", 100,"Java","spring framework"));
        products.add(new Product("book005", "Spring Data JPA", 99,"Java","spring boot"));
        products.add(new Product("book006", "JDBC tutorial", 79.50,"Java","java code with database"));
        products.add(new Product("book007", "Deep Learning", 200,"Data Science","Machine Learning Specialization"));
        products.add(new Product("book008", "NodeJs", 60,"Backend","javascript"));

		for (Product product : products) {
			if (product.getId().equals(productId)) {
				return product;
			}
		}
		return null;
	}

}
