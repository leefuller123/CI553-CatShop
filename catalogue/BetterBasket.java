package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BetterBasket extends Basket implements Serializable {
  private static final long serialVersionUID = 1L;

  public boolean add(Product pr) {
    if (super.add(pr)) {
      sortBasket();
      removeZeroQuantityProducts();
      return true;
    }
    return false;
  }

  private void removeZeroQuantityProducts() {
    Iterator<Product> iterator = this.iterator();
    while (iterator.hasNext()) {
      Product product = iterator.next();
      if (product.getQuantity() == 0) {
        iterator.remove();
      }
    }
  }

  private void sortBasket() {
    Collections.sort(this, new ProductPriceComparator());
  }

  private class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product pr1, Product pr2) {
      double price1 = pr1.getPrice();
      double price2 = pr2.getPrice();

      if (price1 == price2) {
        pr2.setQuantity(pr2.getQuantity() + 1);
        pr1.setQuantity(0);
        return 0;
      }

      return Double.compare(price2, price1); // Reverse order (highest price first)
    }
  }
}
