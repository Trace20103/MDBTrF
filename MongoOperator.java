package sample;


import javafx.scene.control.Alert;

import java.io.*;

/**
 * Class to work with mongoDB queries
 * Standard Java driver didn't have the opportunity to work with direct queries
 * so I had to use mongo Shell directly from application through the cmd process
 **/
class MongoOperator {

    /**
     * This command here resets the DB to the point it was never accessed before.
     * Using the json file here. Still it takes too much time to do that
     * after each assignment session.
     **/
    void resetDB() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "mongoimport --db video --collection movies --mode=upsert  --type json --file src/sample/config/movies.json");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "not null";
        while (line != null) {
            line = r.readLine();
            System.out.println(line);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Импорт данных");
        alert.setHeaderText(null);
        alert.setContentText("Данные были успешно импортированы");
        alert.showAndWait();
    }

    /**
     * Idk why but shell just can't take these " symbols.
     * The string which is operated with is then returned with / before " symbols
     * just to make sure it works correctly. Just a quick fix, nothing interesting.
     **/
    private String fixShellSymbols(String oldString) {
        final char dm = (char) 34;
        String r = "/" + dm;
        oldString = oldString.replace(Character.toString(dm), r);
        return oldString;
    }

    /**
     * The way the access is granted is connecting to the mongoDB Shell
     * through cmd. Standard Java driver wouldn't allow us to do that kind of stuff
     * (putting the full query and executing it with a result afterwards).
     * So, first we fix the " symbols, then the execution. Just like that we get the response
     * that user would get if he'd use the mongoDB shell directly from cmd.
     **/
    String getQueryResult(String sentQuery) throws IOException {
        sentQuery = fixShellSymbols(sentQuery);
        String res;
        Runtime rt = Runtime.getRuntime();
        int a = 0;
        String db = "video";
        Process pr = rt.exec(new String[]{"mongo", db, "--eval", sentQuery});
        InputStream in = pr.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            a++;
            if (a > 3) out.append(line);
        }
        res = out.toString();
        reader.close();
        return res;
    }
}
