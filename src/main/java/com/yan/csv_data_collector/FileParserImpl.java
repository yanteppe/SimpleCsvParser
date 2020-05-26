package com.yan.csv_data_collector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * CSV file parser
 */
public class FileParserImpl implements FileParser<Product> {
   private int indexOfMaxPrice;
   private float maxPrice = 0;
   private List<Product> resultList = new ArrayList<>(1000);

   /**
    * Parse csv file
    *
    * @param pathToCsvSource path csv source directory
    * @return results list - 1000 cheapest products
    */
   @Override
   public List<Product> parseFile(String pathToCsvSource) {
      long startTime = System.currentTimeMillis();
      List<File> files = getListFiles(pathToCsvSource);
      for (File file : files) {
         if (file.exists()) {
            try (var bufferedReader = Files.newBufferedReader(file.toPath())) {
               // Fill result list
               String row;
               while ((row = bufferedReader.readLine()) != null) {
                  if (resultList.size() == 1000) break;
                  resultList.add(new Product(row));
               }
               maxPrice = resultList.stream().max(Comparator.comparing(Product::getPrice)).get().getPrice();
               indexOfMaxPrice = resultList.indexOf(resultList.stream().max(Comparator.comparing(Product::getPrice)).get());
               // Next parsing - pushing of from the resulting list of expensive products
               while (row != null) {
                  var product = new Product(row);
                  if (product.getPrice() < maxPrice) {
                     resultList.set(indexOfMaxPrice, product);
                     maxPrice = resultList.stream().max(Comparator.comparing(Product::getPrice)).get().getPrice();
                     indexOfMaxPrice = resultList.indexOf(resultList.stream().max(Comparator.comparing(Product::getPrice)).get());
                  }
                  row = bufferedReader.readLine();
               }
            } catch (IOException ioException) {
               ioException.printStackTrace();
            }
         }
         long endTime = System.currentTimeMillis();
         System.out.println("File parse time: " + file.getName() + " - " + (endTime - startTime) / 10000.0 + " s\n");
      }
      return resultList;
   }

   /**
    * Collect all files from a directory
    *
    * @param pathToCsvSource path csv source directory
    * @return List of files from directory
    */
   private List<File> getListFiles(String pathToCsvSource) {
      return Arrays.asList(Objects.requireNonNull(new File(pathToCsvSource).listFiles()));
   }

//   public List<Product> quickSort(List<Product> list) {
//      if (list.size() <= 1) return list;
//      List<Product> sorted = new ArrayList<>();
//      List<Product> lesser = new ArrayList<>();
//      List<Product> greater = new ArrayList<>();
//      Product pivot = list.get(list.size() - 1); // Use last item as pivot
//      for (int i = 0; i < list.size() - 1; i++) {
//         //int order = list.get(i).compareTo(pivot);
//         if (list.get(i).compareTo(pivot) < 0)
//            lesser.add(list.get(i));
//         else
//            greater.add(list.get(i));
//      }
//      lesser = quickSort(lesser);
//      greater = quickSort(greater);
//      lesser.add(pivot);
//      lesser.addAll(greater);
//      sorted = lesser;
//      return sorted;
//   }
//
}
