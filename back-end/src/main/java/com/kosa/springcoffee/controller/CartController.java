package com.kosa.springcoffee.controller;

import com.kosa.springcoffee.dto.CartDetailDTO;
import com.kosa.springcoffee.dto.CartItemDTO;
import com.kosa.springcoffee.dto.CartOrderDTO;
import com.kosa.springcoffee.entity.Member;
import com.kosa.springcoffee.repository.MemberRepository;
import com.kosa.springcoffee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v4")
public class CartController {
    private CartService cartService;
    private MemberRepository memberRepository;
    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity cart(@RequestBody CartItemDTO cartItemDTO, String email){
        Long cartItemNo;

        try{
            cartItemNo = cartService.create(cartItemDTO, email);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemNo, HttpStatus.OK);

    }

    @GetMapping(value = "/cart")
    public String cartList(String email, Model model) {
        Member member =  memberRepository.findByEmail(email);
        List<CartDetailDTO> cartDetailDTOList = cartService.getCartList(member.getName());
        model.addAttribute("cartItems", cartDetailDTOList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemNo}")
    @ResponseBody
    public ResponseEntity changeCartItemCount(@PathVariable("cartItemNo") Long cartItemNo, int count, String email){
        Member member =  memberRepository.findByEmail(email);
        if (count < 0){
            return new ResponseEntity<String>("최소 1개 이상 담아야합니다.", HttpStatus.BAD_REQUEST);
        }
        else if(cartService.validateCartItem(cartItemNo, member.getName()))
            return new ResponseEntity<String>("자신의 카트가 아닙니다.", HttpStatus.FORBIDDEN);

        cartService.changeCartItemCount(cartItemNo,count);
        return new ResponseEntity<Long>(cartItemNo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemNo}")
    @ResponseBody
    public ResponseEntity deleteCartItem(@PathVariable("cartItemNo") Long cartItemNo, String email){
        Member member =  memberRepository.findByEmail(email);
        if(cartService.validateCartItem(cartItemNo, member.getName()))
            return new ResponseEntity<String>("자신의 카트가 아닙니다.", HttpStatus.FORBIDDEN);

        cartService.deleteCartItem(cartItemNo);
        return new ResponseEntity<Long>(cartItemNo, HttpStatus.OK);

    }

    @PostMapping(value = "/cart/cartOrder")
    @ResponseBody
    public ResponseEntity orderCartItem(@RequestBody CartOrderDTO cartOrderDTO, String email){
        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();
        Member member =  memberRepository.findByEmail(email);
        if (cartOrderDTOList == null || cartOrderDTOList.size() == 0){
            return new ResponseEntity<String>("상품이 없습니다.", HttpStatus.BAD_REQUEST);
        }


        for (CartOrderDTO dto : cartOrderDTOList){
            if(cartService.validateCartItem(dto.getCartItemNo(), member.getName()))
                return new ResponseEntity<String>("자신의 카트가 아닙니다.", HttpStatus.FORBIDDEN);
        }
        Long orderNo = cartService.orderCartItem(cartOrderDTOList, member.getName());
        return new ResponseEntity<Long>(orderNo, HttpStatus.OK);
    }

}
