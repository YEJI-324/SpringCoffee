package com.kosa.springcoffee.controller;

import com.kosa.springcoffee.dto.OrderDTO2;
import com.kosa.springcoffee.dto.OrderHistDTO;
import com.kosa.springcoffee.dto.PageResultDTO;
import com.kosa.springcoffee.entity.Order;
import com.kosa.springcoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/v3")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity order(@RequestBody OrderDTO2 orderDTO2, String email){
        Long orderNo;
        try {
            orderNo = orderService.create(orderDTO2, email);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderNo, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable(name = "page") Optional<Integer> page, String email, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<OrderHistDTO> orderHistDtos = orderService.getOrderList(email, pageable);
        model.addAttribute("orders", orderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHist";
    }

    @PostMapping("/{orderNo}/cancel")
    @ResponseBody
    public ResponseEntity cancel(@PathVariable("orderNo") Long orderNo, String email){
        if(!orderService.validateOrder(orderNo, email)){
            return new ResponseEntity<String>("주문취소권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderNo);
        return new ResponseEntity<Long>(orderNo, HttpStatus.OK);
    }

}
