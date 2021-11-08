package com.kosa.springcoffee.service;

import com.kosa.springcoffee.dto.OrderDTO;
import com.kosa.springcoffee.dto.OrderDTO2;
import com.kosa.springcoffee.dto.OrderHistDTO;
import com.kosa.springcoffee.dto.OrderItemDTO;
import com.kosa.springcoffee.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OrderService {
    //주문
    Long create(OrderDTO2 orderDTO2, String email);
    //주문취소
//    void orderCancel(Long orderNo);
    Page<OrderHistDTO> getOrderList(String email, Pageable pageable);
    boolean validateOrder(Long orderNo, String email);

    void cancelOrder(Long orderNo);



}
