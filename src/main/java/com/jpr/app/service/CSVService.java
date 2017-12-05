package com.jpr.app.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class CSVService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVService.class);

	public void writeCities(PrintWriter writer, List<?> pojos, Class type, String[] columns) {

		try {

			@SuppressWarnings("rawtypes")
			HeaderColumnNameMappingStrategy mapStrategy = new HeaderColumnNameMappingStrategy();

			mapStrategy.setType(type.getClass());
			mapStrategy.generateHeader();

			StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
					.withMappingStrategy(mapStrategy).withSeparator(',').build();

			btcsv.write(pojos);

		} catch (CsvException ex) {

			LOGGER.error("Error mapping Bean to CSV", ex);
		}
	}

	public void writeAll(List<Map<String, Object>> values, PrintWriter writer) throws IOException {
		CSVWriter csv = new CSVWriter(writer);
		boolean headeradded = false;
		for (Map<String, Object> lt : values) {
			List<String> line = new ArrayList<>();
			if (!headeradded) {
				for (Map.Entry<String, Object> entry : lt.entrySet()) {
					line.add(entry.getKey().toString());
				}
				csv.writeNext(line.toArray(new String[line.size()]));
				headeradded = true;
				line.clear();
			}
			for (Map.Entry<String, Object> entry : lt.entrySet()) {
				line.add(entry.getValue().toString());
			}
			csv.writeNext(line.toArray(new String[line.size()]));
		}
		csv.close();

	}

}
