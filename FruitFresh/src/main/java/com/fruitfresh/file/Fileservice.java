package com.fruitfresh.file;

import org.springframework.web.multipart.MultipartFile;

public interface Fileservice {


    public void init();
    public void saveFile(MultipartFile file);
}
