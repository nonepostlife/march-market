package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.ContactInfo;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.OrderDto;
import ru.geekbrains.march.market.core.converters.OrderConverter;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.OrderDetails;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.march.market.core.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final CartServiceIntegration cartServiceIntegration;

    public List<OrderDto> getAllOrders(String username) {
        List<Order> orders = orderRepository.findAllByUsername(username);
        return orders.stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createNewOrder(String username, ContactInfo contactInfo) {
        CartDto cart = cartServiceIntegration.getCurrentUserCart(username);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDetails(new OrderDetails(order, getAddress(contactInfo), contactInfo.getPhone(), contactInfo.getAdditionalInformation()));

        List<OrderItem> orderItems = cart.getItems()
                .stream().map(i -> new OrderItem(order,
                        productService.findById(i.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + i.getProductId() + " не найден")),
                        i.getPricePerProduct(),
                        i.getPrice(),
                        i.getQuantity())).collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);
        cartServiceIntegration.clearCart(username);
    }

    private String getAddress(ContactInfo contactInfo) {
        return contactInfo.getZip() +
                ", " +
                contactInfo.getCountry() +
                ", " +
                contactInfo.getRegion() +
                ", " +
                contactInfo.getCity() +
                ", " +
                contactInfo.getAddress();
    }
}
