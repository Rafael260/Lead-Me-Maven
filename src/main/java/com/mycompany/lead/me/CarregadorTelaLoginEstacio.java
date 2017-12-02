/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lead.me;

/**
 *
 * @author diego
 */
public class CarregadorTelaLoginEstacio implements CarregadorTelaLogin{
    @Override
    public String coletarNomeTelaLogin() {
        return "TelaLoginWebView.fxml";
    }
}
