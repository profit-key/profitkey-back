package com.profitkey.stock.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
@Table(name = "DashBoards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DashBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "today_date", nullable = false)
	private LocalDate todayDate;

	@Column(name = "today_visitor_cnt", nullable = false)
	private Integer todayVisitorCnt = 0;

	@Column(name = "new_signup_cnt", nullable = false)
	private Integer newSignupCnt = 0;

	@Builder
	private DashBoard(LocalDate todayDate, Integer todayVisitorCnt, Integer newSignupCnt) {
		this.todayDate = todayDate;
		this.todayVisitorCnt = todayVisitorCnt;
		this.newSignupCnt = newSignupCnt;
	}
}
