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
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"bill.css\">");
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
