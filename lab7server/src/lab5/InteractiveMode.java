package lab5;

import com.google.gson.*;
import lab3.NPC;
import lab8.orm.DB;
import lab8.orm.SQL;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.LinkedBlockingDeque;

public class InteractiveMode {
    private LinkedBlockingDeque<NPC> npcs = new LinkedBlockingDeque<>();
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                    LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (date, type, jsonSerializationContext) ->
                    new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).create();

    int currId;

    SQL<NPC> sql = new SQL<>(NPC.class);
    DB db = new DB("jdbc:postgresql://localhost:5432/lab8", "nailstorm", "qwerty");



    /**
     * inputFromFile fills collection of class NPC with objects of the latter lying in the {@code file},
     * also checking each object if it is maximal or minimal of the given ones;
     *
     * @return true if the reading from {@code file} operation was successful with all the objects
     * now stored in the collection.
     */

    public boolean inputFromDB() {
        try  {

            npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));

                currId = npcs.peekLast().getId();

            return true;

        } catch (Exception exc) {
            System.err.println("An unexpected exception with I/O has occurred. Please try again.");
        }
        return false;
    }

    /**
     * saveToFile saves the collection of class NPC in the {@code file} in JSON;
     *
     * @return true if the saving to {@code file} operation was successful.
     */

    public boolean saveToDB() {
        try {
            db.execute(sql.deleteAll());
            for (NPC npc : npcs)
                db.executeUpdate(sql.insert(npc));
            return true;
        } catch (Exception exc) {
            System.err.println("An unexpected exception with I/O has occurred. Please try again.");
        }

        return false;
    }



    /**
     * retrieveElements retrieves the elements of the collection and returns them to the terminal.
     */

    public String retrieveElements() {
        StringBuilder elements = new StringBuilder();
        npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
        if (npcs.isEmpty())
            return "Collection is empty - nothing to retrieve.";
        else {
            synchronized (npcs) {
                for (NPC retrievedNPCs : npcs)
                    elements.append(retrievedNPCs.toString()).append("\n");
            }
            return "Collection contents:\n" + elements;
        }
    }

    /**
     * clear() removes all elements from the collection;
     *
     * @return true if clearing was successful.
     */

    public String clear() {
        try {
            db.execute(sql.deleteAll());
            npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
            return "Collection has been cleared.";
        } catch (Exception exc) {
            return "An unexpected error has occurred. Please try again.";
        }
    }

    /**
     * removeFirst() checks for emptiness of collection;
     * if false, removes the first element of the collection.
     */

    public String removeFirst() {
        npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
        if (npcs.isEmpty())
            return "Collection is already empty - nothing to remove.";
        else {
            db.execute(sql.delete(npcs.removeFirst()));
            return "First element has been removed.";
        }
    }



    /**
     * addElement() calls justNPCInput() method (see above) and adds it to the collection,
     * also checking for maximal/minimal (see inputFromFile);
     *
     * @return true if the collection addition was successful.
     */

    public String addElement(String data) {
        try {
            NPC newNPC = gson.fromJson(data, NPC.class);
            int indicator = 0;
            int npcId = newNPC.getId();
            System.out.println(npcId);
            for (NPC allNpcs : npcs) {
                if(allNpcs.getId() == npcId) {
                    System.out.println(allNpcs.getId());
                    indicator++;

                }
            }
            if(indicator == 0) {
                currId = db.executeUpdateGetId(sql.insert(newNPC));
                npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
            }
            else
                return "Element with such id has already been found.";

            return "Element has been successfully added.";

        } catch (NullPointerException exc) {
            exc.printStackTrace();
            return "The object you are trying to add is null (missing name or incorrect input). " +
                    "Please try again.";
        }
    }

    public String changeElement(String data) {
        try {
            NPC newNPC = gson.fromJson(data, NPC.class);
            int indicator = 0;
            int npcId = newNPC.getId();
            System.out.println(npcId);
            for (NPC allNpcs : npcs) {
                if(allNpcs.getId() == npcId) {
                    System.out.println(allNpcs.getId());
                    indicator++;
                    db.execute(sql.update(newNPC));
                    npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
                    break;
                }
            }
            if(indicator == 0) {
                return "Element with such id has not been found.";
            }
            else
                return "Element replaced.";


        } catch (NullPointerException exc) {
            return "The object you are trying to add is null (missing name or incorrect input). " +
                    "Please try again.";
        }
    }

    /**
     * removeElement() removes an input-specified element from the collection;
     *
     * @return true if the removing operation was successful.
     */

    public String removeElement(String data) {
        try {
            if (npcs.isEmpty())
                return "Collection is already empty - nothing to remove.";

            else {
                NPC checkNPC = gson.fromJson(data, NPC.class);
                int npcId = checkNPC.getId();
                for (NPC allNpcs : npcs) {
                    if(allNpcs.getId() == npcId) {
                        db.execute(sql.delete(allNpcs));
                        npcs = new LinkedBlockingDeque<>(sql.resultsToObjects(db.fetch(sql.selectAll())));
                        return "Element has been removed.";
                    }
                }
                return "Such element has not been found.";

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return "An exception has been caught. Please try again.";
    }


    public int getCurrId() {
        return currId;
    }

    public boolean checkForEmptiness () {
            return npcs.isEmpty();
        }

    public LinkedBlockingDeque<NPC> getNpcs() {
        return npcs;
    }
}


