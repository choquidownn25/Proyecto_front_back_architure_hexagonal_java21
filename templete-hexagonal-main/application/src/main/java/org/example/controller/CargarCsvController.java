package org.example.controller;

import org.exemple.ports.api.LoadprocesarCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/archivos/cvs")
public class CargarCsvController {
    @Autowired
    private LoadprocesarCsv loadprocesarCsv;
    @PostMapping("/cargar")
    public ResponseEntity<List<String>> cargarCsv(@RequestParam("archivo") MultipartFile archivo) {
        // Aquí puedes implementar la lógica para cargar el archivo CSV
        return ResponseEntity.ok(loadprocesarCsv.procesarCsv(archivo));
    }

}
