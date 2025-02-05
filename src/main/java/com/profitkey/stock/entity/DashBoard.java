package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "DashBoards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DashBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "TODAY_DATE", nullable = false)
	private LocalDate todayDate;

	@Column(name = "TODAY_VISITOR_CNT", nullable = false)
	private Integer todayVisitorCnt = 0;

	@Column(name = "NEW_SIGNUP_CNT", nullable = false)
	private Integer newSignupCnt = 0;

	@Builder
	private DashBoard(LocalDate todayDate, Integer todayVisitorCnt, Integer newSignupCnt) {
		this.todayDate = todayDate;
		this.todayVisitorCnt = todayVisitorCnt;
		this.newSignupCnt = newSignupCnt;
	}
}
