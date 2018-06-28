package lab5;

import com.company.Main;
import com.google.gson.*;
import lab6.DatagramReceiver;
import lab3.NPC;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class InteractiveMode {
    private final LinkedBlockingDeque<NPC> npcs = new LinkedBlockingDeque<>();
    Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class,
            (JsonDeserializer<OffsetDateTime>) (json, type, jsonDeserializationContext) ->
                    OffsetDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)).registerTypeAdapter(OffsetDateTime.class,
            (JsonSerializer<OffsetDateTime>) (date, type, jsonSerializationContext) ->
                    new JsonPrimitive(date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))).create();



    /**
     * inputFromFile fills collection of class NPC with objects of the latter lying in the {@code file},
     * also checking each object if it is maximal or minimal of the given ones;
     *
     * @param file is the input file where the objects are stored;
     * @return true if the reading from {@code file} operation was successful with all the objects
     * now stored in the collection.
     */

    public boolean inputFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String someBS;
            NPC readNPCs;

            while ((someBS = reader.readLine()) != null) {
                {
                    try {
                        readNPCs = gson.fromJson(someBS, NPC.class);
                        npcs.add(readNPCs);

                    } catch (Exception exc) {
                        System.err.println("An unexpected exception has occurred " + someBS);
                    }
                }
            }

            return true;

        } catch (FileNotFoundException exc) {
            System.err.printf("File %s was not found\n", file.getName());
        } catch (IOException exc) {
            System.err.println("An unexpected exception with I/O has occurred. Please try again.");
        }
        return false;
    }

    /**
     * saveToFile saves the collection of class NPC in the {@code file} in JSON;
     *
     * @param file is the output file where the objects will be stored;
     * @return true if the saving to {@code file} operation was successful.
     */

    public boolean saveToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            synchronized (npcs) {
                for (NPC readNPCs : npcs)
                    writer.write(gson.toJson(readNPCs) + '\n');
            }
            return true;
        } catch (IOException exc) {
            System.err.println("An unexpected exception with I/O has occurred. Please try again.");
        }

        return false;
    }



    /**
     * retrieveElements retrieves the elements of the collection and returns them to the terminal.
     */

    public String retrieveElements() {
        StringBuilder elements = new StringBuilder();
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
            npcs.clear();
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
        if (npcs.isEmpty())
            return "Collection is already empty - nothing to remove.";
        else {
            npcs.removeFirst();
            return "First element has been removed.";
        }
    }

    /**
     * importFromFile appends collection of class NPC with objects of the latter lying in the {@code file};
     *
     * @param path is the path of file from which the new objects are extracted;
     *             This method is used for retrieving objects from a non-root input file;
     *             otherwise call inputFromFile (see above).
     */

    public String importFromFile(String path) {
        Path pathToJson = Paths.get(path);

        if (!pathToJson.toFile().canRead()) {
            return "File " + pathToJson.toFile().getName() + " does not have " +
                    "read permission.";
        }

        File secondaryFile = new File(path);
        if (inputFromFile(secondaryFile))
            return "Collection has been successfully imported from " + new File(path).getName();
        else
            return "File was not found or there was an error while retrieving " +
                    "collection from this file. Please try again.";
    }

    /**
     * justNPCInput() invokes an NPC creator;
     *
     * @return NPC if the operation was successful, otherwise return null (if there were some errors in input
     * i.e. beauty level is out of scope).
     */

    public NPC justNPCInput(String data) {
        try {

            String userBsNpc = jsonNPCHandler(data);
            NPC newNPC = gson.fromJson(userBsNpc, NPC.class);

            if (newNPC.getNPCName() == null)
                throw new JsonParseException("");

            return newNPC;

        } catch (JsonSyntaxException exc) {
            System.err.println("An unexpected error has occurred. Check input syntax.");
        } catch (JsonParseException exc) {
            System.err.println("Typed object's name is null. " +
                    "Object should have a significant name.");
        }
        return null;
    }

    public String jsonNPCHandler (String jsonNPC) {

            String resultNPC = jsonNPC.replace("\n", "");
            return resultNPC;

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
                npcs.add(newNPC);
            }
            else
                return "Element with such id has already been found..";

            return "Element has been successfully added.";

        } catch (NullPointerException exc) {
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
                    npcs.remove(allNpcs);
                    npcs.add(newNPC);
                    break;
                }
            }
            if(indicator == 0) {
                npcs.add(newNPC);
            }
            else
                return "Element replaced.";

            return "Element with such id has not been found.";

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
                        npcs.remove(allNpcs);
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



    public boolean checkForEmptiness () {
            return npcs.isEmpty();
        }

    public LinkedBlockingDeque<NPC> getNpcs() {
        return npcs;
    }
}


