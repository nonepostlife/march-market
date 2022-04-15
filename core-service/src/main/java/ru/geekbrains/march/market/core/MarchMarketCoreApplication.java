package ru.geekbrains.march.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarchMarketCoreApplication {
	// Домашнее задание:
	// Доработайте корзину:
	// 1. Отображение корзины на фронте
	// 2. Для корзины и айтемов корзины пропишите ДТО
	// 3. Реализуйте очистку корзины с фронта
	// 4. Реализуйте возможность на фронте добавлять продукты в корзину
	// *. С помощью кнопок +/- менять количество товаров в одной записи

	public static void main(String[] args) {
		SpringApplication.run(MarchMarketCoreApplication.class, args);
	}
}
