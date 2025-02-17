package com.profitkey.stock.util;

import java.util.Random;

public class NicknameUtil {
	private static final String[] ADJECTIVES = {"재빠른", "똑똑한", "귀여운", "멍청한", "약삭빠른", "창의적인", "아름다운"};
	private static final String[] ANIMALS = {"햄스터", "고양이", "원숭이", "거북이", "노루", "개구리", "뱀"};

	private static final Random RANDOM = new Random();

	// 랜덤 닉네임 생성 메서드
	public static String generateRandomNickname() {
		String adjective = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
		String animal = ANIMALS[RANDOM.nextInt(ANIMALS.length)];
		int number = RANDOM.nextInt(1000);  // 0~999 사이의 랜덤 숫자
		return adjective + animal + number;  // ex)귀여운 햄스터1
	}
}
