package com.ecommerce.site.model;

import lombok.Data;

import java.util.Currency;

@Data
public class ChargeRequest {

 public enum Currency {
     USD, EUR;
 }
    private String Description;
    private int Amount;
    private Currency Currency;
    private String StripeEmail;
    private String StripeToken;
}
