package com.nabgha.ecommerce.orderLines;

import com.nabgha.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineMapper orderLineMapper;
    private final OrderLineRepository orderLineRepository;

    public void saveOrderline(OrderLineRequest orderLineRequest) {

        var orderLine = orderLineMapper.toOrderLine(orderLineRequest);

        orderLineRepository.save(orderLine);
    }

    public List<OrderLineResponse> findByOrderId(String orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream().map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
