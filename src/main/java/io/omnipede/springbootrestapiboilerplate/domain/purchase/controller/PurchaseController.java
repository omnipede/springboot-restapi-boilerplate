package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

}
