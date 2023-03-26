
import exception.InvalidMoveException;
import session.Session;
import session.SessionMove;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String dataFilename = "game_data.txt";
        String resultFilename = "analyzer_results.txt";
        List<String> errors = new ArrayList<>();
        Map<Integer, Session> sessionMap = new TreeMap<>();

        //read data
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(dataFilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                try {
                    SessionMove move = new SessionMove(line);
                    int sessionId = move.getSessionId();
                    Session session;

                    if(!sessionMap.containsKey(sessionId)){
                        session = new Session();
                    } else {
                        session = sessionMap.get(sessionId);
                    }
                    session.addMove(move);
                    sessionMap.put(sessionId, session);

                } catch (IllegalArgumentException e){
                    //just ignore faulty lines in .txt file
                }
            }
            reader.close();

        } catch (Exception e){
            throw new RuntimeException("file named: " + dataFilename + " not found");
        }

        //sort each session by timestamps
        sessionMap.forEach((key, session) -> {
            Collections.sort(session.getSessionMoves());
            //session.getSessionMoves().forEach(move -> System.out.println(move.getSessionDataString()));
        });

        //check for invalid moves and add them to list if found
        sessionMap.forEach((key, session) -> {
            try {
                session.checkMoves();
            } catch (InvalidMoveException e){
                errors.add(e.getIllegalMoveString());
                System.out.println(e.getErrormessage() + ", Move: " + e.getIllegalMoveString());
            }
        });

        //write the output
        try {
            File file = new File(resultFilename);
            if(file.exists()) {
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(resultFilename);
            for(String error: errors){
                fileWriter.write(error + "\n");
            }
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error writing file");
        }

    }
}
