package br.com.petstore.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {

    /**
     * Este método permite o uso de arquivos nos formtato JSON.
     *
     * @param json o arquivo no formato json
     * @throws IOException no caso de falha ao ler o arquivo json.
     * @author Ronaldo do Carmo
     * @see Data
     */
    public String getJson(String json) throws IOException {
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    /**
     * Este método permite o uso de arquivos nos formtato CSV.
     *
     * @param csv o arquivo no formato cvs
     * @throws IOException no caso de falha ao ler o arquivo csv.
     * @author Ronaldo do Carmo
     * @see Data
     */
    public List<String[]> getCsv(String csv) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(csv));
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> users = null;
        try {
            users = csvReader.readAll();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}