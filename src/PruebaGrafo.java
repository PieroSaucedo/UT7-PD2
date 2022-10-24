public class PruebaGrafo {

    public static void main(String[] args) {
        TGrafoDirigido gd = UtilGrafos.cargarGrafo("./src/aeropuertos_1.txt","./src/conexiones_1.txt",
                false, TGrafoDirigido.class);

        /*
        Object[] etiquetasarray = gd.getEtiquetasOrdenado();

        Double[][] matriz = UtilGrafos.obtenerMatrizCostos(gd.getVertices());
        UtilGrafos.imprimirMatrizMejorado(matriz, gd.getVertices(), "Matriz");
        Double[][] mfloyd = gd.floyd();
        UtilGrafos.imprimirMatrizMejorado(mfloyd, gd.getVertices(), "Matriz luego de FLOYD");
        */

        /*
        for (int i = 0; i < etiquetasarray.length; i++) {
            System.out.println("excentricidad de " + etiquetasarray[i] + " : " + gd.obtenerExcentricidad((Comparable) etiquetasarray[i]));
        }
        System.out.println();
        System.out.println("Centro del grafo: " + gd.centroDelGrafo());
        */


        // EJERCICIO 1
        TGrafoDirigido gd2 = UtilGrafos.cargarGrafo("./src/ciudades.txt","./src/ciudades_conexiones.txt",
                false, TGrafoDirigido.class);

        Double[][] matriz2 = UtilGrafos.obtenerMatrizCostos(gd2.getVertices());
        UtilGrafos.imprimirMatrizMejorado(matriz2, gd2.getVertices(), "Matriz");


        // EJERCICIO 2
        Double[][] mfloyd2 = gd2.floyd();
        UtilGrafos.imprimirMatrizMejorado(mfloyd2, gd2.getVertices(), "Matriz luego de FLOYD");


        // EJERCICIO 3
        System.out.println(gd2.obtenerExcentricidad("Durazno"));
        System.out.println("Centro del grafo: " + gd2.centroDelGrafo());


    }
}
