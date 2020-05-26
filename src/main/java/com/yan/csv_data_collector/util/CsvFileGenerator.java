package com.yan.csv_data_collector.util;

import com.yan.csv_data_collector.Product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Generate CSV files with initial data
 */
public class CsvFileGenerator {
   private static final String PATH_TO_RESULT_DIR = "src" + File.separator + "main" + File.separator + "resources";

   /**
    * Create initial data csv files
    *
    * @param pathToGeneratedFilesDir path to destination directory for generated csv files
    * @param filesCount              number of generated csv files
    * @param rowsCount               number of generate rows in csv file
    */
   public void createInitialDataCsvFile(String pathToGeneratedFilesDir, int filesCount, int rowsCount) {
      System.out.println("Generate CSV files");
      for (int i = 0; i < filesCount; i++) {
         var file = new File(String.format(pathToGeneratedFilesDir, "data" + i));
         if (Objects.requireNonNull(new File(PATH_TO_RESULT_DIR).list()).length > 0) {
            System.out.println("Files already generated\n");
            break;
         } else {
            try (var printWriter = new PrintWriter(file, StandardCharsets.UTF_8)) {
               printWriter.write(generateCsvData(rowsCount));
            } catch (IOException ioException) {
               ioException.printStackTrace();
            }
         }
      }
   }

   /**
    * Write result list to csv file
    *
    * @param pathToResultFile path to result file: .../.../result.csv
    * @param resultList       list of sorted products
    */
   public void writeResultToFile(String pathToResultFile, List<Product> resultList) {
      try (var printWriter = new PrintWriter(new File(pathToResultFile), StandardCharsets.UTF_8)) {
         for (Product product : resultList) {
            printWriter.write(product.toString());
            printWriter.println();
         }
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }
   }

   /**
    * Generate CSV data
    *
    * @param rowsNumber number of generate rows in csv file
    * @return String - row for csv file
    */
   private String generateCsvData(int rowsNumber) {
      var stringBuilder = new StringBuilder();
      for (int i = 0; i < rowsNumber; i++) {
         stringBuilder.append(generateDataRow());
      }
      return stringBuilder.toString();
   }

   /**
    * Generate row for csv data
    *
    * @return StringBuilder - generated row
    */
   private StringBuilder generateDataRow() {
      return new StringBuilder()
            .append((int) (Math.random() * 1000))
            .append(", ")
            .append("name" + (int) (Math.random() * 1000))
            .append(", ")
            .append("condition" + (int) (Math.random() * 1000))
            .append(", ")
            .append("state" + (int) (Math.random() * 1000))
            .append(", ")
            .append(BigDecimal.valueOf(new Random().nextFloat() * 10_000F).setScale(2, RoundingMode.UP).floatValue())
            .append("\r\n");
   }
}
