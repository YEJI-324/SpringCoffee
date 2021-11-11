package com.kosa.springcoffee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDetailDTO {
    private Long itemNo;
    private String itemName;
    private String content;
    private String imgUrl;
    private int price;



}
