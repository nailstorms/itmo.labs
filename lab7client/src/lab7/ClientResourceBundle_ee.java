package lab7;

import java.util.ListResourceBundle;

public class ClientResourceBundle_ee extends ListResourceBundle {

    public static final Object[][] contents = {
            {"title", "Klient"},
            {"acquireData", "Hankige andmed serverilt"},
            {"helpBtn", "Abi"},
            {"startBtn", "Alusta"},
            {"stopBtn", "Peatus"},
            {"clickedObject", "Klõpsatav objekt -"},
            {"connectionInfo", "Sirvige serveriga staatust"},
            {"filtAnimInfo", "Animatsioonide/filtrite õigsus"},
            {"filtLabel", "Filtrid"},
            {"dataAcquired", "Andmed on saadud"},
            {"unableToPaint", "Ei saa joonistada "},
            {"animStart", "Animatsiooni käivitamine ..."},
            {"animStop", "Ellipsid naasevad algsesse asendisse"},
            {"formatError", "Viga vormingus"},
            {"dateFormatError", "Viga kuupäevavormingus"},
            {"idError", "ID on väärtus \"From\" suurem kui väärtus \"To\""},
            {"heightError", "height on väärtus \"From\" suurem kui väärtus \"To\""},
            {"weightError", "weight on väärtus \"From\" suurem kui väärtus \"To\""},
            {"dateError", "DateOfBirth väärtus \"From\" on suurem kui väärtus \"To\""},
            {"xError", "X-s on väärtus \"From\" suurem kui väärtus \"To\""},
            {"yError", "Y-s on väärtus \"From\" suurem kui väärtus \"To\""},
            {"serverUnav", "Server ei ole ajutiselt saadaval."},
            {"emptyColl", "Kogumik on tühi."},
            {"ioError", "I/O viga."},

            {"introLabel", "Mõned kasuliku teabe kogumise vaataja kohta:"},
            {"labelRetrieve", "NPC-kogu kogumiseks klõpsake nupul Hangi serveri andmed."},
            {"labelElements", "NPC-d tähistatakse XOY-koordinaatide süsteemina ellipsites."},
            {"labelElements1", "Objekti kohta lisateabe saamiseks klõpsake seda. Seda teavet saab näha ekraani ülaosas."},
            {"labelElements2", "Liiguta hiir objekti üle, et teada saada selle nimi."},
            {"labelFields", "Väljad: id - NPC kordumatu tunnus; name - NPC nimi;"},
            {"labelFields1", "height - NPC kasv; weight - NPC kaal; dateOfBirth - NPC sünniaeg;"},
            {"labelFields2", "x, y - kaardi NPC koordinaadid, color - NPC värv;"},
            {"labelFields3", "beautyLevel - ilu NPC; chinSharpness - NPC lõua teravus."},
            {"labelFilters", "Objektide filtreerimiseks määrake soovitud omadused."},
            {"labelFilters1", "Kõikidel väljadel, välja arvatud sünniaeg ja nimi, on numbrilised väärtused. Sünniaeg on vormingus \"yyyy-MM-dd\", nimi on string."},
            {"labelFilters2", "Kui jätate väljale \"Alates\" välja, tühistatakse kõik elemendid väärtusele \"Kuni\"."},
            {"labelFilters3", "Kui jätate väljale \"Kuni\" tühjaks, loetakse kõik elemendid, mis on väärtusest \"Alates\" kõrgemal."},
            {"labelFilters4", "Kui jätad mõlemad väljad tühjaks, seda omadust ei võeta filtreerimise ajal arvesse."},
            {"labelStart", "Nupu \"Alusta\" alustab animatsiooni. Filtritele vastavad elemendid hakkavad viie sekundi jooksul aeglaselt kaduma, seejärel naasevad nad 4 sekundiks oma eelmisele seisundile."},
            {"labelStop", "\"Peatus\" nupp lülitab animatsiooni välja."}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}

