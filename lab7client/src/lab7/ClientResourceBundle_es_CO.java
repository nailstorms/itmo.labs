package lab7;

import java.util.ListResourceBundle;

public class ClientResourceBundle_es_CO extends ListResourceBundle {

    public static final Object[][] contents = {
            {"title", "Cliente"},
            {"acquireData", "Obtener datos del servidor"},
            {"helpBtn", "Ayuda"},
            {"startBtn", "Comenzar"},
            {"stopBtn", "Detener"},
            {"clickedObject", "Objeto cliqueado - "},
            {"connectionInfo", "Estado de intercambio con el servidor"},
            {"filtAnimInfo", "Corrección de animaciones/filtros"},
            {"filtLabel", "Filtros"},
            {"dataAcquired", "Datos recibidos"},
            {"unableToPaint", "Incapaz de dibujar "},
            {"animStart", "Ejecutando animación..."},
            {"animStop", "Los elipses vuelven a su posición original"},
            {"formatError", "Error en formato"},
            {"dateFormatError", "Error en el formato de fecha"},
            {"idError", "En ID, el valor \"De\" es mayor que el valor \"Antes\""},
            {"heightError", "En height, el valor \"De\" es mayor que el valor \"Antes\""},
            {"weightError", "En weight, el valor \"De\" es mayor que el valor \"Antes\""},
            {"dateError", "En dateOfBirth, el valor \"De\" es mayor que el valor \"Antes\""},
            {"xError", "En x, el valor \"De\" es mayor que el valor \"Antes\""},
            {"yError", "En y, el valor \"De\" es mayor que el valor \"Antes\""},
            {"serverUnav", "El servidor no está disponible temporalmente."},
            {"emptyColl", "La colección está vacía."},
            {"ioError", "Error de I/O."},

            {"introLabel", "Alguna información útil sobre el visor de colecciones:"},
            {"labelRetrieve", "Para obtener la colección de NPC, haga clic en el botón Obtener datos del servidor."},
            {"labelElements", "Los NPC se representan como elipses en el sistema de coordenadas XOY."},
            {"labelElements1", "Para obtener más información sobre el objeto, haga clic en él. La información se puede ver en la parte superior de la pantalla."},
            {"labelElements2", "Mueva su mouse sobre el objeto para descubrir su nombre."},
            {"labelFields", "Fields: id - identificador único de NPC; name - el nombre del NPC;"},
            {"labelFields1", "height - crecimiento de NPC; weight - peso de NPC; dateOfBirth - fecha de nacimiento del NPC;"},
            {"labelFields2", "x, y - coordenadas NPC en el mapa, color - color NPC;"},
            {"labelFields3", "beautyLevel - nivel de belleza NPC; chinSharpness - agudeza de la barbilla del NPC."},
            {"labelFilters", "Para filtrar objetos, especifique las características deseadas."},
            {"labelFilters1", "Todos los campos excepto la fecha de nacimiento y el nombre tienen valores numéricos. La fecha de nacimiento tiene el formato \"yyyy-MM-dd\", el nombre es una cadena."},
            {"labelFilters2", "Si deja el campo \"De\" en blanco, todos los elementos se considerarán debajo del valor en \"A\"."},
            {"labelFilters3", "Si deja el campo \"A\" en blanco, se considerarán todos los elementos por encima del valor en \"De\"."},
            {"labelFilters4", "Si deja ambos campos en blanco, esta característica no se tendrá en cuenta al filtrar."},
            {"labelStart", "El botón \"Comenzar\" inicia la animación. Los elementos correspondientes a los filtros comienzan a desaparecer lentamente durante 5 segundos, luego durante 4 segundos vuelven a su estado anterior."},
            {"labelStop", "El botón \"Detener\" apaga la animación."}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }

}
