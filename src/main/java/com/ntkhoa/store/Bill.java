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

@WebServlet("/checkout")
public class Bill extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");

        // check cart
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        displayBill(request, response, cart);
    }

    private void displayBill(HttpServletRequest request, HttpServletResponse response, Map<String, Integer> cart) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Bill</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Bill</h1>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Name</th><th>Quantity</th><th>Total Price</th></tr>");
        double total = 0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productIdInCart = entry.getKey();
            int quantity = entry.getValue();
            Product productInCart = getProductById(productIdInCart);
            if (productInCart != null) {
                double price = productInCart.getPrice();
                double subTotal = price * quantity;
                total += subTotal;
                out.println("<tr><td>" + productInCart.getName() + "</td><td>" + quantity + "</td><td>" + subTotal + "</td></tr>");
            }
        }
        out.println("<tr><td colspan=\"2\">Total:</td><td>" + total + "</td></tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    private Product getProductById(String productId) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("book001", "Java Programming", 49.99,"Information Technology","rgerh"));
        products.add(new Product("book002", "Python for Beginners", 39.99,"information technology","rjyjty"));
        products.add(new Product("book003", "HTML & CSS Mastery", 29.99,"information technology","jtt7"));

        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}
