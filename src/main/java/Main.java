
import card.Card;
import exception.InvalidMoveException;
import session.SessionMove;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String dataFilename = "game_data_2.txt";
        String resultFilename = "analyzer_results.txt";
        Map<Integer, SessionMove> newMap = new TreeMap<>();
        long startTime = System.currentTimeMillis();
        //read data
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(dataFilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                try {
                    SessionMove move = new SessionMove(line);
                    checkIfMoveIsValid(move);


                } catch (InvalidMoveException e){
                    int sessionId = e.getIllegalMove().getSessionId();
                    SessionMove move = e.getIllegalMove();
                    if(!newMap.containsKey(sessionId) || newMap.get(sessionId).getTimeStamp() >  move.getTimeStamp()){
                        newMap.put(sessionId, move);
                    }
                
                } catch (IllegalArgumentException e){
                    //just ignore faulty lines in .txt file
                }
            }
            reader.close();

        } catch (Exception e){
            throw new RuntimeException("file named: " + dataFilename + " not found");
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Program ran in: " + (endTime-startTime) + "ms");

        try {
            File file = new File(resultFilename);
            if(file.exists()) {
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(resultFilename);
            for(SessionMove errorMove: newMap.values()){
                fileWriter.write(errorMove.getSessionDataString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error writing file");
        }

    }

    public static void checkIfMoveIsValid(SessionMove move) throws InvalidMoveException {
        checkIfCardsAreValid(move);

        switch (move.getMove().toLowerCase()) {

            case "hit":
                if(move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Cannot hit when bust", move);
                }
                if(move.getPlayerCardsValue() > 19 && move.getActor() == 'P') { //
                    throw new InvalidMoveException("Hitting will definitely make player go bust", move);
                }
                if (move.getDealerCardsValue() > 17) {
                    throw new InvalidMoveException("Dealer should not have hit if their hand exceeds 17", move);
                }
                break;

            case "stand":
                if(move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Player is already bust", move);
                }

                break;
            case "win":
                if(move.getActor() == 'P' && move.getDealerCardsValue() < 17) {
                    throw new InvalidMoveException("Dealer should have hit a new card", move);
                }

                if( move.getDealerCardsValue() > move.getPlayerCardsValue() || move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Player should have lost", move);
                }
                break;

            case "lose":
                if(move.getPlayerCardsValue() >= move.getDealerCardsValue() && move.getPlayerCardsValue() <= 21 ||
                        move.getDealerCardsValue() > 21 && move.getPlayerCardsValue() <= 21){
                    throw new InvalidMoveException("Player should have won", move);
                }
                break;
        }

    }

    private static void checkIfCardsAreValid(SessionMove move) {
        List<Card> usedCardsList = new ArrayList<>();
        usedCardsList.addAll(move.getPlayerCards());
        usedCardsList.addAll(move.getDealerCards());

        usedCardsList.forEach(card -> {
            if(card.getValue() < 0) {
                throw new InvalidMoveException("Move contains an invalid card", move);
            }
        });

        Set<Card> usedCardsSet = new HashSet<>(usedCardsList); //set does not allow duplicate cards
        if(usedCardsSet.size() != usedCardsList.size()){
            throw new InvalidMoveException("Move contains a duplicate card", move);
        }
    }
}
