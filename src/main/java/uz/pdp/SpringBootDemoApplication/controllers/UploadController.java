package uz.pdp.SpringBootDemoApplication.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.SpringBootDemoApplication.daos.UploadDao;
import uz.pdp.SpringBootDemoApplication.domains.Upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
public class UploadController {
// get edit
    // id get ,crud
    private final Path rootPath= Path.of("/Users/safixonabdusattorov/Documents/Upload/");
    private final UploadDao uploadDao;

    public UploadController(UploadDao uploadDao) {
        this.uploadDao = uploadDao;
    }

    @PostMapping("/upload")
    public  ResponseEntity<Upload> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {
        Upload uploads= Upload.builder()
                .originalName(file.getOriginalFilename())
                .generatedName(UUID.randomUUID()+"."+StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();
        uploadDao.save(uploads);

        Files.copy(file.getInputStream(), rootPath.resolve(uploads.getGeneratedName()), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("file uploaded " + file.getOriginalFilename());

        return ResponseEntity.ok(uploads);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadPage(@PathVariable String filename) {

        FileSystemResource fileSystemResource = new FileSystemResource(rootPath.resolve(filename));

        Upload uploads=uploadDao.findByGeneratedName(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploads.getMimeType()))
                .contentLength(uploads.getSize())
                .header("Content-Disposition","attachment; filename = "+uploads.getOriginalName())
                .body(fileSystemResource);
    }



}
