/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.services;
import jakarta.mail.MessagingException;
/**
 *
 * @author jimal
 */
public interface CorreoService {
    public void enviarCorreoHTML(
            String to,
            String asunto,
            String contenido
    )throws MessagingException;
}
