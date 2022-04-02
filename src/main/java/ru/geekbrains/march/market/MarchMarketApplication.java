package ru.geekbrains.march.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarchMarketApplication {
	// Домашнее задание:
	// 1. Разобраться с кодом
	// 2. Создайте бин корзина с листов продуктов
	// 3. Добавьте на фронте к каждому продукту кнопку "добавить в корзину"
	// 4. Кнопка добавить в корзину должна складывать в бин корзина указанный проукт (продукт
	// может быть добавлен несколько раз, группировать продукты в корзине пока не надо)
	// 5. Под таблицей с продуктами отрисуйте таблицу с корзиной

	public static void main(String[] args) {
		SpringApplication.run(MarchMarketApplication.class, args);
	}
}
