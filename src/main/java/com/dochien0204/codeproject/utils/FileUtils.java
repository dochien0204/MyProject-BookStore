package com.dochien0204.codeproject.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

  private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources");

  public static String uploadFile(MultipartFile multipartFile) throws IOException {
    Path staticPath = Paths.get("static");
    Path imagePath = Paths.get("image");
    if(!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
      Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
    }

    Path fileImage = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    try(OutputStream output = Files.newOutputStream(fileImage)) {
      output.write(multipartFile.getBytes());
    }

    return imagePath.resolve(multipartFile.getOriginalFilename()).toString();
  }
}
