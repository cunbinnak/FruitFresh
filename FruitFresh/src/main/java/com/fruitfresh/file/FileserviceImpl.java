package com.fruitfresh.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileserviceImpl implements Fileservice{


    private final Path root = Paths.get("src/main/resources/images");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile(MultipartFile file) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String _date =   dateFormat.format(date).replaceAll("[/:]","_");

            Files.copy(file.getInputStream(), this.root.resolve(_date+file.getOriginalFilename()));
        } catch (IOException e) {
           throw  new RuntimeException("không thể lưu trữ ảnh" + e.getMessage());
        }

    }


}
