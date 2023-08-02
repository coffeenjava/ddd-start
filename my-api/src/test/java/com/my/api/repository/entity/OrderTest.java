package com.my.api.repository.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderTest {

    DeliveryInfo deliveryInfo =
            new DeliveryInfo("brian", "010-1111-2222", "seoul, korea");

    Product product = new Product(1000);

    @DisplayName("주문시 배송지와 상품정보는 필수")
    @Test
    void 주문시_배송지와_상품정보는_필수() {
        List<OrderDetail> orderDetails = new ArrayList<>();

        // 주문 생성시 상품정보 목록이 비어있으면 오류
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Order(deliveryInfo, orderDetails));

        // 위와 동일한 결과 예제
//        assertThatThrownBy(() -> new Order(deliveryInfo, orderDetails))
//                .isInstanceOf(IllegalArgumentException.class);

        // 주문 생성
        int orderCount = 3;
        orderDetails.add(new OrderDetail(product, orderCount));
        Order order = new Order(deliveryInfo, orderDetails);

        // 생성 후 배송상태는 준비중
        assertThat(order.getState())
                .isEqualTo(OrderState.PREPARING);

        // 주문 총 금액은 모든 상품가격과 개수를 곱한 값
        assertThat(order.getTotalAmount())
                .isEqualTo(product.getAmount() * orderCount);
    }

    @DisplayName("출고 이후 배송지 정보 변경과 주문 취소 불가")
    @Test
    void 출고_이후_배송지_정보_변경과_주문_취소_불가() {
        // given
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(new OrderDetail(product, 1));
        Order order = new Order(deliveryInfo, orderDetails);

        // when
        order.completePayment(); // 상품 준비
        order.completePrepareProduct(); // 출고

        // then
        DeliveryInfo newDeliveryInfo =
                new DeliveryInfo("brian", "010-1111-2222", "seoul, korea");

        // 배송지 변경 시 오류
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> order.changeDeliveryInfo(newDeliveryInfo));

        // 주문 취소 시 오류
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> order.cancel());
    }
}
