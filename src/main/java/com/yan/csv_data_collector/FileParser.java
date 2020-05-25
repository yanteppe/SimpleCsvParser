package com.yan.csv_data_collector;

import java.util.List;

public interface FileParser<T> {

   List<T> parseFile(String pathToFile);
}
