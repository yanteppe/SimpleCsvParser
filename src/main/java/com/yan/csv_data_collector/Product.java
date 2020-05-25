package com.yan.csv_data_collector;

public class Product implements Comparable<Product> {
   private int id;
   private String name;
   private String condition;
   private String state;
   private float price;

   public Product(String csvRecord) {
      String[] split = csvRecord.split(", ");
      this.id = Integer.parseInt(split[0]);
      this.name = split[1];
      this.condition = split[2];
      this.state = split[3];
      this.price = Float.parseFloat(split[4]);
   }

   public float getPrice() {
      return price;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (!(object instanceof Product)) return false;
      Product that = (Product) object;
      if (id != that.id) return false;
      if (Float.compare(that.price, price) != 0) return false;
      if (name != null ? !name.equals(that.name) : that.name != null) return false;
      if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;
      return state != null ? state.equals(that.state) : that.state == null;
   }

   @Override
   public int hashCode() {
      int result = id;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (condition != null ? condition.hashCode() : 0);
      result = 31 * result + (state != null ? state.hashCode() : 0);
      result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
      return result;
   }

   @Override
   public String toString() {
      return id + ", " + name + ", " + condition + ", " + state + ", " + price;
   }

   @Override
   public int compareTo(Product product) {
      return Float.compare(price, product.price);
   }
}
