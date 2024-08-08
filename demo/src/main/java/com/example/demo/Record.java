package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Record") // 테이블 이름을 'Record'로 설정
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글번호 (최신의 글이 작음)
    private String title; // 제목
    private String content; // 내용
    private LocalDateTime createdDate; // 작성 날짜
    private String userId; // 사용자 아이디
}
