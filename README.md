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
        } else { 
          cart.put(productId, cart.getOrDefault(productId, 0) + 1);
  }
}
