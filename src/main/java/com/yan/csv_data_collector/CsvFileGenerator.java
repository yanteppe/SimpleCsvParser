package com.yan.csv_data_collector;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Generate CSV files with initial data
 */
public class CsvFileGenerator {

   /**
    * Create initial data csv files
    *
    * @param pathToGeneratedFilesDir path to destination directory for generated csv files
    * @param filesCount number of generated csv files
    * @param rowsCount number of generate rows in csv file
    */
   public void createInitialDataCsvFile(String pathToGeneratedFilesDir, int filesCount, int rowsCount) {
      System.out.println("Generate CSV files");
      for (int i = 0; i < filesCount; i++) {
         File file = new File(String.format(pathToGeneratedFilesDir, "data" + i));
         if (!file.exists()) {
            try (var printWriter = new PrintWriter(file, StandardCharsets.UTF_8)) {
               printWriter.write(generateCsvData(rowsCount));
            } catch (IOException ioException) {
               ioException.printStackTrace();
            }
         } else {
            System.out.println("Files already generated\n");
            break;
         }
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
