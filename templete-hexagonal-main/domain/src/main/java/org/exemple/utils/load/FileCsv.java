package org.exemple.utils.load;

import org.exemple.ports.api.LoadprocesarCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileCsv implements LoadprocesarCsv {
//    @Value("${archive_csv.ruta}")
//    private String csvFilePath;
    @Override
    public List<String> procesarCsv(MultipartFile archivo) {
        if (archivo.isEmpty()) {
//            return ResponseEntity.badRequest().body(List.of("El archivo está vacío"));
            throw new IllegalArgumentException("El archivo no puede ser vacio");
        }
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Aplicar la limpieza de caracteres especiales
                String lineaLimpia = limpiarCaracteresEspeciales(line);
                //lineasProcesadas.add(lineaLimpia);
                data.add(lineaLimpia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    private String limpiarCaracteresEspeciales(String input) {
        // Expresión regular que mantiene solo letras (a-z, A-Z), números (0-9), espacios (\s) y comas (,) propias del CSV
        // Puedes ajustar la expresión regular según tus requerimientos exactos
        return input.replaceAll("[^a-zA-Z0-9\\\\s,]", "");
    }



}
