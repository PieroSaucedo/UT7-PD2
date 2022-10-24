import java.util.*;

public class TGrafoDirigido implements IGrafoDirigido {

    private Map<Comparable, TVertice> vertices; // vertices del grafo.-

    public TGrafoDirigido(Collection<TVertice> vertices, Collection<TArista> aristas) {
        this.vertices = new HashMap<>();
        for (TVertice vertice : vertices) {
            insertarVertice(vertice.getEtiqueta());
        }
        for (TArista arista : aristas) {
            insertarArista(arista);
        }
    }

    /**
     * Metodo encargado de eliminar una arista dada por un origen y destino. En
     * caso de no existir la adyacencia, retorna falso. En caso de que las
     * etiquetas sean invalidas, retorna falso.
     *
     */
    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            TVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }

    
    /**
     * Metodo encargado de verificar la existencia de una arista. Las etiquetas
     * pasadas por par�metro deben ser v�lidas.
     *
     * @return True si existe la adyacencia, false en caso contrario
     */
    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        TVertice vertOrigen = buscarVertice(etiquetaOrigen);
        TVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de un vertice dentro del
     * grafo.-
     *
     * La etiqueta especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return True si existe el vertice con la etiqueta indicada, false en caso
     * contrario
     */
    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    /**
     * Metodo encargado de verificar buscar un vertice dentro del grafo.-
     *
     * La etiqueta especificada como parametro debe ser valida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return El vertice encontrado. En caso de no existir, retorna nulo.
     */
    private TVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }

    /**
     * Metodo encargado de insertar una arista en el grafo (con un cierto
     * costo), dado su vertice origen y destino.- Para que la arista sea valida,
     * se deben cumplir los siguientes casos: 1) Las etiquetas pasadas por
     * parametros son v�lidas.- 2) Los vertices (origen y destino) existen
     * dentro del grafo.- 3) No es posible ingresar una arista ya existente
     * (miso origen y mismo destino, aunque el costo sea diferente).- 4) El
     * costo debe ser mayor que 0.
     *
     * @return True si se pudo insertar la adyacencia, false en caso contrario
     */
    public boolean insertarArista(TArista arista) {
        if ((arista.getEtiquetaOrigen() != null) && (arista.getEtiquetaDestino() != null)) {
            TVertice vertOrigen = buscarVertice(arista.getEtiquetaOrigen());
            TVertice vertDestino = buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen != null) && (vertDestino != null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }

    /**
     * Metodo encargado de insertar un vertice en el grafo.
     *
     * No pueden ingresarse vertices con la misma etiqueta. La etiqueta
     * especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a ingresar.
     * @return True si se pudo insertar el vertice, false en caso contrario
     */
    public boolean insertarVertice(Comparable unaEtiqueta) {
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            TVertice vert = new TVertice(unaEtiqueta);
            getVertices().put(unaEtiqueta, vert);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    @Override

    public boolean insertarVertice(TVertice vertice) {
        Comparable unaEtiqueta = vertice.getEtiqueta();
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            getVertices().put(unaEtiqueta, vertice);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    public Object[] getEtiquetasOrdenado() {
        TreeMap<Comparable, TVertice> mapOrdenado = new TreeMap<>(this.getVertices());
        return mapOrdenado.keySet().toArray();
    }

    /**
     * @return the vertices
     */
    public Map<Comparable, TVertice> getVertices() {
        if (vertices.size() > 0) {
            return vertices;
        }
        return null;
    }

    @Override
    public Comparable<Double> centroDelGrafo() {
        Comparable resultado = null;
        double min = Double.MAX_VALUE;
        for (Map.Entry<Comparable, Double> entry : obtenerExcentricidades().entrySet()) {
            if (min > entry.getValue()) {
                min = entry.getValue();
                resultado = entry.getKey();
            }
        }
        return resultado;
    }

    @Override
    public Double[][] floyd() {
        Double[][] matrix = UtilGrafos.obtenerMatrizCostos(vertices);

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
        return matrix;
    }

    @Override
    public Comparable<Double> obtenerExcentricidad(Comparable etiquetaVertice) {
        return obtenerExcentricidades().get(etiquetaVertice);
    }

    private Map<Comparable, Double> obtenerExcentricidades() {
        Map<Comparable, Double> resultado = new HashMap<>();
        Double[][] matrizFloyd = floyd();
        Comparable[] etiquetas = new Comparable[vertices.size()];

        int i = 0;
        for (Comparable etiqueta : vertices.keySet()) {
            etiquetas[i] = etiqueta;
            i++;
        }

        for (int columns = 0; columns < vertices.size(); columns++) {
            double max = 0;
            for (int rows = 0; rows < vertices.size(); rows++) {
                if (max < matrizFloyd[rows][columns]) {
                    max = matrizFloyd[rows][columns];
                }
            }
            resultado.put(etiquetas[columns], max);
        }
        return resultado;
    }

    @Override
    public boolean[][] warshall() {
        Double[][] C = UtilGrafos.obtenerMatrizCostos(this.getVertices());

        boolean[][] warshallMatrix = new boolean[C.length][C.length];
        for (int i = 0; i < warshallMatrix.length; i++) {
            for (int j = 0; j < warshallMatrix.length; j++) {
                if (C[i][j] != Double.MAX_VALUE) {
                    warshallMatrix[i][j] = true;
                }
            }
        }

        for (int k = 0; k < warshallMatrix.length; k++) {
            for (int i = 0; i < warshallMatrix.length; i++) {
                if (!warshallMatrix[i][k]) {
                    continue;
                }
                for (int j = 0; j < warshallMatrix.length; j++) {
                    warshallMatrix[i][j] |= warshallMatrix[i][k] && warshallMatrix[k][j];
                }
            }
        }

        return warshallMatrix;
    }

    @Override
    public boolean eliminarVertice(Comparable nombreVertice) {
        if (this.vertices.remove(nombreVertice) == null) {
            return false;
        }

        for (TVertice vertice : this.vertices.values()) {
            vertice.eliminarAdyacencia(nombreVertice);
        }
        return true;
    }

    /*
    public Comparable<Double> obtenerExcentricidad(Comparable etiquetaVertice) {
        Double max = 0.0; //La excentricidad de un vértice es el mayor valor en la su columna de la matriz de Floyd
        Double[][] matrizFloyd = floyd(); //Obtengo la matriz de Floyd

        Set<Comparable> etiquetasVertices = vertices.keySet();
        int posicion = 0;

        for (Comparable etiqueta : etiquetasVertices) {
            if (etiquetaVertice.equals(etiqueta)) { //Busco la posición en la matriz en la que tengo que buscar
                break;
            }
            posicion++;
        }

        for (Double[] doubles : matrizFloyd) { //Recorro la matriz
            if (doubles[posicion] > max) { //Comparando los valores de la columna del vértice y si es mayor que max, se sobreescribe
                max = doubles[posicion];
            }
        }

        if (max == Double.MAX_VALUE) { //Si es infinito, devuelvo -1
            max = -1.0;
        }
        return max;
    }
     */

}
