package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.CartItemDto;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.converters.OrderConverter;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.entities.User;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.march.market.core.repositories.OrderItemRepository;
import ru.geekbrains.march.market.core.repositories.OrderRepository;
import ru.geekbrains.march.market.core.repositories.ProductRepository;
import ru.geekbrains.march.market.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final ProductService productService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final OrderConverter orderConverter;
    private final CartServiceIntegration cartServiceIntegration;

    public List<OrderDto> getAllOrders(String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createNewOrder(String username) {
        CartDto cart = cartServiceIntegration.getCurrentCart();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDto cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerProduct(cartItem.getPricePerProduct());
            Product product = productService.findById(cartItem.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + cartItem.getProductId() + " не найден"));
            orderItem.setProduct(product);
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartServiceIntegration.clearCart();
    }
}
