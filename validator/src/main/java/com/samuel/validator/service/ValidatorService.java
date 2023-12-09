package com.samuel.validator.service;

import com.samuel.validator.model.Order;
import com.samuel.validator.service.exceptions.InsufficientFundsException;
import com.samuel.validator.service.exceptions.LimitUnavailable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ValidatorService {

    private final EmailService emailService;

    public void validatorOrder(Order order) {
        validateAvailableLimit(order);
        validatePurchaseWithLimit(order);
        emailService.notifyCustomerSuccessfulPurchase(order);
    }

    private void validatePurchaseWithLimit(Order order) {
        if (order.getPrice().longValue() > order.getCreditCard().getLimit().longValue()) {
            emailService.notifyCustomerInsufficientFunds(order);
            throw new InsufficientFundsException("You have no limit to make the purchase");
        }

    }

    private void validateAvailableLimit(Order order){
        if (order.getCreditCard().getLimit().longValue() <= 0 ) {
            emailService.notifyLimitUnavailable(order);
            throw new LimitUnavailable("Limit unavailable");
        }
    }
}
