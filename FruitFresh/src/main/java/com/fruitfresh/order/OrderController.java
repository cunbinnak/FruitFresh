package com.fruitfresh.order;


import com.fruitfresh.OrderDetail.OrderDetail;
import com.fruitfresh.OrderDetail.OrderDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/api/v1")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

//    @PostMapping(value = "/order")
//    public String newOrder(@RequestBody Order newOrder){
//
//        String check = "";
//
//        if(newOrder.getFirstname() !=null && newOrder.getAddress()!=null && newOrder.getLastname()!=null && newOrder.getEmail()!=null
//        && newOrder.getPhone()!=null && newOrder.getShipdate()!=null){
//        newOrder.setOrderdate(new Date());
//        newOrder.setOrderStatus("Đang Xét Duyệt");
//
//        orderRepo.save(newOrder);
//
//        for (OrderDetail item: newOrder.getListDetail() ) {
//            item.setOrderid(newOrder.getOrderid());
//            orderDetailRepo.save(item);
//            }
//         check = "ok";
//        }else {
//            check = "false";
//        }
//        return check;
//    }

    @PostMapping(value = "/order")
    public ResponseEntity<Order> addnewOrder(@RequestBody Order newOrder1){
        if(newOrder1.getFirstname() !=null && newOrder1.getAddress()!=null && newOrder1.getLastname()!=null && newOrder1.getEmail()!=null
                && newOrder1.getPhone()!=null && newOrder1.getShipdate()!=null){
            newOrder1.setOrderdate(new Date());
            newOrder1.setOrderStatus("Đang Xét Duyệt");

            orderRepo.save(newOrder1);

            for (OrderDetail item: newOrder1.getListDetail() ) {
                item.setOrderid(newOrder1.getOrderid());
                orderDetailRepo.save(item);
            }
            return new ResponseEntity<>(newOrder1, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/orders")
    public List<Order> getlistOrder(){
        return orderRepo.findAll();
    }

    @PutMapping(value = "/order")
    public ResponseEntity<Order> updateOrder(@RequestBody Order updateOrder){

        orderRepo.save(updateOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId){
        Order order = orderRepo.findById(orderId).get();
        orderRepo.delete(order);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
