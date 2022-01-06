package com.fruitfresh.categories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/admin/api/v1")
public class CategoriesController {


    @Autowired
    private CategoriesRepo categoriesRepo;

    @GetMapping(value = "/categories")
    public List<Categories> catePage(){
        List<Categories> listCate = categoriesRepo.findAll();
        return listCate;
    }
    @PostMapping(value = "/savecategories")
    public ResponseEntity<Categories> addCate(@RequestBody Categories newCate){
        newCate.setCreator("admin");
        newCate.setCreateDate(new Date());
        categoriesRepo.save(newCate);
        return new ResponseEntity<>(newCate, HttpStatus.OK);
    }


    @PutMapping(value = "/updatecategories")
    public ResponseEntity<Categories> updateCate(@RequestBody Categories updateCate){
        updateCate.setCreateDate(new Date());
        return new ResponseEntity<>(categoriesRepo.save(updateCate), HttpStatus.OK);
    }

    @DeleteMapping(value = "categories/{cateid}")
    public ResponseEntity<?> deleteCate(@PathVariable int cateid){
        Categories cate = categoriesRepo.findById(cateid).get();
        categoriesRepo.delete(cate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
