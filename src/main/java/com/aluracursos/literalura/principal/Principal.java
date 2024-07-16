package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.*;
import com.aluracursos.literalura.service.*;

import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi conexion = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    ConversorDatos conversor = new ConversorDatos();
    List<DatosLibro> datosLibros = new ArrayList<>();
    List<Optional<DatosLibro>> librosBuscados = new ArrayList<Optional<DatosLibro>>();
    private LibroRepositorio libroRepositorio;
    private AutorRepositorio autorRepositorio;

    public Principal(LibroRepositorio libroRepositorio, AutorRepositorio autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro      
                    2 - Mostrar libros buscados       
                    3 - Mostrar lista de autores   
                    4 - Buscar autor en un año en específico            
                    5 - Mostrar libros por lenguaje   
                    0 - Salir
                    """;
            System.out.println(menu);
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese una opción válida\n");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAtuoresPorFecha();
                    break;
                case 5:
                    mostrarLibrosPorLenguaje();
                    break;
                case 0:
                    System.out.println("Finalizando aplicación...");
                    break;
                default:
                    System.out.println("Ingrese una opción valida\n");
            }
        }

    }

    private DatosLibro obtenerDatosLibro() {
        System.out.println("Ingrese el nombre del libro a buscar: ");
        String nombreLibro = scanner.nextLine();
        var json = conexion.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+").toLowerCase());
        var datos = conversor.obtenerDatos(json, Datos.class);
        if (datos != null && !datos.datosLibros().isEmpty()) {
            return datos.datosLibros().get(0);
        } else {
            // Manejar el caso donde no se encuentren resultados
            System.out.println("No se pudo encontrar el libro");
            return null;
        }
    }

    public void buscarLibro() {
        DatosLibro datos = obtenerDatosLibro();

        if (datos != null) {
            // Verificar si el libro ya existe en la base de datos
            Libro libroExistente = libroRepositorio.findByTitulo(datos.titulo());
            if (libroExistente != null) {
                System.out.println("El libro ya está registrado: " + libroExistente);
                return;
            }

            Autor autor = null;
            if (!datos.autores().isEmpty()) {
                DatosAutor datosAutor = datos.autores().get(0);
                autor = autorRepositorio.findByNombre(datosAutor.nombre());
                if (autor == null) {
                    autor = new Autor(datosAutor);
                    autorRepositorio.save(autor);
                }

                Libro libro = new Libro(datos, autor);
                libroRepositorio.save(libro);
                System.out.println("Libro guardado:\n" + libro);
            } else {
                System.out.println("No se pudo encontrar el autor del libro.");
            }
        }
    }

    public void mostrarLibrosBuscados() {
        List<Libro> libros = libroRepositorio.findAll();
        libros.forEach(System.out::println);
    }

    public void listarAutores() {
        List<Autor> autores = autorRepositorio.findAllWithLibros();
        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    public void listarAtuoresPorFecha() {
        System.out.println("Ingrese el año para ver autores de la época: ");
        String fechaBuscada = scanner.nextLine();
        Integer fecha = null;
        try {
            fecha = Integer.parseInt(fechaBuscada);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese una opción válida\n");
            return;
        }

        List<Autor> autores = autorRepositorio.findByFecha(fecha);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores para la fecha ingresada");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void mostrarLibrosPorLenguaje() {
        System.out.println("Ingrese el código correspondiente al idioma para buscar libros");
        System.out.println("es - Español\n" +
                "en - Inglés\n" +
                "fr - Francés\n" +
                "pt - Portugués\n" +
                "it - Italiano\n" +
                "de - Alemán\n");
        String codigo = scanner.nextLine().toLowerCase();
        try {
            Lenguaje lenguaje = Lenguaje.fromString(codigo);
            List<Libro> librosBuscados = libroRepositorio.findByLenguaje(lenguaje);

            if (librosBuscados.isEmpty()) {
                System.out.println("No hay libros del idioma ingresado");
            } else {
                librosBuscados.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Código de idioma no válido. Intente nuevamente.");
        }
    }
}
