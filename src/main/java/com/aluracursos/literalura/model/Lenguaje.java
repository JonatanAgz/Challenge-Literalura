package com.aluracursos.literalura.model;

public enum Lenguaje {
    ESPANOL("es","Español"),
    INGLES("en","Inglés"),
    FRANCES("fr","Francés"),
    PORTUGUES("pt","Portugués"),
    ITALIANO("it", "Italiano"),
    ALEMAN("de", "Alemán");

    private String categoriaLenguaje;
    private String categoriaLenguajeEspanol;

    Lenguaje(String categoriaLenguaje, String categoriaLenguajeEspanol) {
        this.categoriaLenguaje = categoriaLenguaje;
        this.categoriaLenguajeEspanol = categoriaLenguajeEspanol;
    }

    public String getNombreEspanol() {
        return categoriaLenguajeEspanol;
    }

    public static Lenguaje fromString(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.categoriaLenguaje.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }

    public static Lenguaje fromEspanol(String text){
        for (Lenguaje lenguaje: Lenguaje.values()) {
            if(lenguaje.categoriaLenguajeEspanol.equalsIgnoreCase(text)){
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }
}
