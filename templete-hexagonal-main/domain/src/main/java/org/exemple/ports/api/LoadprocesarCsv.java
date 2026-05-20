package org.exemple.ports.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoadprocesarCsv {

    List<String> procesarCsv(MultipartFile archivo);
}
