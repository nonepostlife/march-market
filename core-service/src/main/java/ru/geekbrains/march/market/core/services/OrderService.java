package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.api.ContactInfo;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.OrderDetails;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.march.market.core.repositories.OrderRepository;
import ru.geekbrains.march.market.core.utils.OrderStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    public Page<Order> getAllOrders(String username, Integer pageNo, Integer pageSize, String sortBy) {
        return orderRepository.findAllByUsername(username, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ с id: " + id + " не найден"));
    }

    @Transactional
    public void createNewOrder(String username, ContactInfo contactInfo) {
        CartDto cart = cartServiceIntegration.getCurrentUserCart(username);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        Order order = new Order();
        order.setUsername(username);
        order.setStatus(OrderStatus.ORDER_WAIT_TO_PAYMENT.name());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDetails(new OrderDetails(order, getAddress(contactInfo), contactInfo.getPhone(), contactInfo.getAdditionalInformation()));

        List<OrderItem> orderItems = cart.getItems()
                .stream().map(i -> new OrderItem(order,
                        productService.findById(i.getProductId()),
                        i.getPricePerProduct(),
                        i.getPrice(),
                        i.getQuantity())).collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);
        cartServiceIntegration.clearCart(username);
    }

    public void orderChangeStatus(Long orderId, OrderStatus status) {
        Order order = findById(orderId);
        order.setStatus(status.name());
        orderRepository.save(order);
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
