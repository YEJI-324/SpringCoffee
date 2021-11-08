package com.kosa.springcoffee.controller;

import com.kosa.springcoffee.dto.QnaReplyDTO;
import com.kosa.springcoffee.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v4")
@Log4j2
@RequiredArgsConstructor
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;

    @PostMapping("/register")
    public ResponseEntity<Long> create(@RequestBody QnaReplyDTO dto) {
        log.info(dto.getQnaBoardNo() + " 답변 등록");
        System.out.println(dto);
        Long num = qnaReplyService.create(dto);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping(value = "/{qnaBoardNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QnaReplyDTO> read(@PathVariable("qnaBoardNo") Long qnaBoardNo) {
        log.info(qnaBoardNo + " 답변 조회");
        return new ResponseEntity<>(qnaReplyService.get(qnaBoardNo), HttpStatus.OK);
    }

    @PutMapping(value = "/{qnaBoardNo}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody QnaReplyDTO qnaReplyDTO){
        log.info(qnaReplyDTO + " 수정");
        qnaReplyService.modify(qnaReplyDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
