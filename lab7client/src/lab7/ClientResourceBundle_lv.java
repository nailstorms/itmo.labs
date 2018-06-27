package lab7;

import java.util.ListResourceBundle;

public class ClientResourceBundle_lv extends ListResourceBundle {

    public static final Object[][] contents = {
            {"title", "Klients"},
            {"acquireData", "Iegūstiet datus no servera"},
            {"helpBtn", "Palīdzība"},
            {"startBtn", "Sāciet"},
            {"stopBtn", "Apstāties"},
            {"clickedObject", "Noklikšķināts objekts - "},
            {"connectionInfo", "Mainīt statusu ar serveri"},
            {"filtAnimInfo", "Animāciju/filtru pareizība"},
            {"filtLabel", "Filtri"},
            {"dataAcquired", "Dati saņemti"},
            {"unableToPaint", "Nevar zīmēt "},
            {"animStart", "Darbojas animācija ..."},
            {"animStop", "Elipses atgriežas sākotnējā pozīcijā"},
            {"formatError", "Kļūda formātā"},
            {"dateFormatError", "Kļūda datuma formātā"},
            {"idError", "ID vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"heightError", "Height vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"weightError", "Weight vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"dateError", "DateOfBirth vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"xError", "x vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"yError", "y vērtība \"No\" ir lielāka par vērtību \"Pirms\""},
            {"serverUnav", "Serveris īslaicīgi nav pieejams."},
            {"emptyColl", "Kolekcija ir tukša."},
            {"ioError", "I/O kļūda."},

            {"introLabel", "Daži noderīgi dati par kolekcijas skatītāju:"},
            {"labelRetrieve", "Lai iegūtu NPC kolekciju, noklikšķiniet uz pogas Saņemt servera datus."},
            {"labelElements", "NPС tiek attēlotas kā elipses XOY koordinātu sistēmā."},
            {"labelElements1", "Lai uzzinātu vairāk informācijas par objektu, noklikšķiniet uz tā. Informāciju var redzēt ekrāna augšdaļā."},
            {"labelElements2", "Pārvietojiet peles kursoru uz objektu, lai uzzinātu tā nosaukumu."},
            {"labelFields", "Lauki: id - NPC unikālais identifikators; name - NPC nosaukums;"},
            {"labelFields1", "height - NPC pieaugums; weight - NPC svars; dateOfBirth - NPC dzimšanas datums;"},
            {"labelFields2", "x, y - NPC koordinātas kartē, color - NPC krāsa;"},
            {"labelFields3", "beautyLevel - skaistuma NPC līmenis; chinSharpness - NPC zoda asums."},
            {"labelFilters", "Lai filtrētu objektus, norādiet vajadzīgās īpašības."},
            {"labelFilters1", "Visiem laukiem, izņemot dzimšanas datumu un nosaukumu, ir skaitliskās vērtības. Dzimšanas datums ir formāts \"yyyy-MM-dd\", nosaukums ir virkne."},
            {"labelFilters2", "Ja jūs atstāsiet lauku \"No\" (tukšs), visi elementi tiks uzskatīti par zemākiem par vērtību \"Kam\"."},
            {"labelFilters3", "Ja jūs atstāsiet lauku \"Kam\", tukšos tiks ņemti visi elementi virs vērtības no \"No\"."},
            {"labelFilters4", "Ja atstājat abus laukus tukšu, šis raksturlielums netiks ņemts vērā, filtrējot."},
            {"labelStart", "Poga \"Sākt\" sāk animāciju. Elementi, kas atbilst filtriem, lēnām pazūd 5 sekundes, pēc tam 4 sekundes atgriežas iepriekšējā stāvoklī."},
            {"labelStop", "Poga \"Apturēt\" izslēdz animāciju."}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }

}
