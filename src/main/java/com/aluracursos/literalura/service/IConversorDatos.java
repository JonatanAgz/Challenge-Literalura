package com.aluracursos.literalura.service;

public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> tClass);
}
