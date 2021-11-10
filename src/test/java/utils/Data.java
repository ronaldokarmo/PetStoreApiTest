package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {

    /**
     * Essa método permite o uso de arquivos nos formtato JSON.
     *
     * @param json o arquivo
     * @throws IOException no caso de falha ao ler o arquivo json.
     * @author Ronaldo do Carmo
     * @see utils.Data
     */
    public String getJson(String json) throws IOException {
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    /**
     * Essa método permite o uso de arquivos nos formtato CSV.
     *
     * @param csv o arquivo
     * @throws IOException no caso de falha ao ler o arquivo csv.
     * @author Ronaldo do Carmo
     * @see utils.Data
     */
    public List<String[]> getCsv(String csv) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(csv));
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> users = csvReader.readAll();
        return users;
    }
}