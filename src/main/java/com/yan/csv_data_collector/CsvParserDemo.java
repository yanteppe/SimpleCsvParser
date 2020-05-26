package com.yan.csv_data_collector;

import com.yan.csv_data_collector.util.CsvFileGenerator;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParserDemo {
   private static final String PATH_TO_CSV_DIR = "src" +
                                File.separator + "main" +
                                File.separator + "resources" +
                                File.separator + "csv_files";

   private static final String PATH_TO_GENERATED_FILES_DIR = "src" +
                                            File.separator + "main" +
                                            File.separator + "resources" +
                                            File.separator + "csv_files/%s.csv";

   private static final String PATH_TO_RESULT_FILE = "src" +
                                    File.separator + "main" +
                                    File.separator + "resources" +
                                    File.separator + "result.csv";

   public static void main(String[] args) {
      // Generate initial CSV files
      var csvFileGenerator = new CsvFileGenerator();
      csvFileGenerator.createInitialDataCsvFile(PATH_TO_GENERATED_FILES_DIR, 10, 1_000_000);
      long startTime = System.currentTimeMillis();

      // Collect 1000 of the cheapest products from all csv files
      List<Product> sortedResult = new FileParserImpl().parseFile(PATH_TO_CSV_DIR)
            .parallelStream()
            .sorted()
            .collect(Collectors.toList());

      // Write result to result.csv file
      csvFileGenerator.writeResultToFile(PATH_TO_RESULT_FILE, sortedResult);
      long endTime = System.currentTimeMillis();

      // Print results
      for (int i = 0; i < 1000; i++) {
         System.out.println(i + ") " + sortedResult.get(i));
      }
      System.out.println("\nRESULTS:");
      System.out.println("Average file parse time - sec: " + (endTime - startTime) / 1000.0 / 10); // 10 - input number of parsed files
      System.out.println("Total execution time - sec: " + (endTime - startTime) / 1000.0);
      System.out.println("\nEND");
   }
}
