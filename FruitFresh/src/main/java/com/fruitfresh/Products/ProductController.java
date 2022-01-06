package com.fruitfresh.Products;


import com.fruitfresh.categories.Categories;
import com.fruitfresh.file.Fileservice;
import com.fruitfresh.respondmessage.RespondMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/admin/api/v1")
@CrossOrigin
public class ProductController {

        @Autowired
        private Fileservice fileservice;

        @Autowired
        private ProductRepo productRepo;

        //đường dẫn lấy ảnh
        @GetMapping(value = "/products/{imgname}")
        public ResponseEntity<ByteArrayResource> addNewPro(@PathVariable String imgname){
            if(imgname!=""| imgname!=null){
                Path fileName = Paths.get("src/main/resources/images",imgname);
                try {
                    byte[] buffer = Files.readAllBytes(fileName);
                    ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                    return  ResponseEntity.ok()
                            .contentLength(buffer.length)
                            .contentType(MediaType.parseMediaType("image/png"))
                            .body(byteArrayResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ResponseEntity.badRequest().build();
        }


        //get all product
        @GetMapping(value = "/products")
        public ResponseEntity<Map<String, Object>> getListProducts(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam (value = "size", required = false, defaultValue = "9") int size){

            List<Products> listProduct = new ArrayList<>();

            Pageable paging = PageRequest.of(page,size);

            Page<Products> pagePro = productRepo.findAll(paging);
            listProduct = pagePro.getContent();

            Map<String, Object> results = new HashMap<>();
            results.put("listProducts",listProduct );
            results.put("currentpage", pagePro.getNumber());
            results.put("totalItem", pagePro.getTotalElements());
            results.put("totalPage", pagePro.getTotalPages());

            return  new ResponseEntity<>(results, HttpStatus.OK);
        }

        //add new product
        @PostMapping(value = "/saveproduct")
        public ResponseEntity<RespondMessage> addnewProduct(@RequestParam (value = "file", required = false)MultipartFile file,
                                                            @RequestParam (value = "productname") String productname,
                                                            @RequestParam (value = "price") Float price,
                                                            @RequestParam (value = "description") String description,
                                                            @RequestParam (value = "status") String status,
                                                            @RequestParam (value = "cateid")Categories cateid){

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String _date =   dateFormat.format(date).replaceAll("[/:]","_");
            String msg ="";
            try {
                Products newPro = new Products();
                if (file!=null){
                    fileservice.saveFile(file);
                    newPro.setUrlImg(_date+file.getOriginalFilename());

                }
                newPro.setProduct_name(productname);
                newPro.setPrice(price);
                newPro.setDescription(description);
                newPro.setCreateDate(new Date());
                newPro.setCreator("admin");
                newPro.setCategories(cateid);
                newPro.setProductStatus(Boolean.parseBoolean(status));
                productRepo.save(newPro);
                msg = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new RespondMessage(msg));

            }catch (Exception e){
                msg = "Could not create new product: ";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespondMessage(msg));
            }
        }



    @PutMapping(value = "/updateproduct")
    public ResponseEntity<RespondMessage> updateProduct(@RequestParam (value = "editfile", required = false)MultipartFile editfile,
                                                        @RequestParam (value = "productname") String productname,
                                                        @RequestParam (value = "price") Float price,
                                                        @RequestParam (value = "description") String description,
                                                        @RequestParam (value = "status") String status,
                                                        @RequestParam (value = "id") int id){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String _date =   dateFormat.format(date).replaceAll("[/:]","_");
        String msg ="";
        try {
            Products editPro = productRepo.findByProductid(id);
            if (editfile==null){

                editPro.setUrlImg(editPro.getUrlImg());

            }else {
                fileservice.saveFile(editfile);
                editPro.setUrlImg(_date+editfile.getOriginalFilename());
            }
            editPro.setProduct_name(productname);
            editPro.setPrice(price);
            editPro.setDescription(description);
            editPro.setCreateDate(new Date());
            editPro.setCreator("admin");
            editPro.setProductStatus(Boolean.parseBoolean(status));
            msg = "Uploaded the file successfully: ";
            productRepo.save(editPro);
            return ResponseEntity.status(HttpStatus.OK).body(new RespondMessage(msg));

        }catch (Exception e){
            msg = "Could not create new product: ";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespondMessage(msg));
        }
    }

    @DeleteMapping(value = "product/{productid}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productid){
            Products products = productRepo.findByProductid(productid);
            productRepo.delete(products);
            return  new ResponseEntity<>(HttpStatus.OK);
    }
}
