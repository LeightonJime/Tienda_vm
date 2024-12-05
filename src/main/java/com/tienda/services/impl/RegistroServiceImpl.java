/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.services.impl;

import com.tienda.domain.Usuario;
import com.tienda.services.CorreoService;
import com.tienda.services.FirebaseStorageService;
import com.tienda.services.RegistroService;
import com.tienda.services.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jimal
 */
@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;  //Para recuperar del archivo de mensajes...

    @Override
    public Model crear(Model model, Usuario usuario) throws MessagingException {
        String mensaje;
        String titulo;
        //Se consulta si ya existe un usuario con el username o el correo enviados...
        if (!usuarioService.existeUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo())) {
            //No existe aún...
            usuario.setPassword(generaClave());
            usuario.setActivo(false);
            usuarioService.save(usuario, false);
            enviarCorreoActivar(usuario);

            mensaje = messageSource.getMessage("registro.mensaje.activacion.ok", null, Locale.getDefault());
            mensaje = String.format(mensaje, usuario.getCorreo());
            titulo = messageSource.getMessage("registro.activar", null, Locale.getDefault());
        } else {
            //Ya existe un usuario con el username o el correo
            mensaje = messageSource.getMessage("registro.mensaje.usuario.o.correo", null, Locale.getDefault());
            mensaje = String.format(mensaje, usuario.getUsername(), usuario.getCorreo());
            titulo = messageSource.getMessage("registro.activar.error", null, Locale.getDefault());
        }
        model.addAttribute("titulo", titulo);
        model.addAttribute("mensaje", mensaje);
        return model;
    }

    @Override
    public Model activar(Model model, String username, String password) {
        
        Usuario usuario= usuarioService.getUsuarioPorUsernameYPassword(username, password);
        
        if (usuario != null) {
            //Encontró el usuario
            model.addAttribute("usuario", usuario);
        } else {
            //NO se encontró... se debe presentar la página de salida
            String titulo= messageSource.getMessage("registro.activar", null, Locale.getDefault());
            String mensaje= messageSource.getMessage("registro.activar.error", null, Locale.getDefault());
            model.addAttribute("titulo", titulo);
            model.addAttribute("mensaje", mensaje);
        }
        
        return model;
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @Override
    public void habilitar(Usuario usuario, MultipartFile imagenFile) {
        var encriptador = new BCryptPasswordEncoder();
        usuario.setPassword(encriptador.encode(usuario.getPassword()));
        if (!imagenFile.isEmpty()) {
            //Se guarda la imagen en el Storage
            String rutaImagen;
            rutaImagen=firebaseStorageService
                            .cargaImagen(
                                    imagenFile, 
                                    "usuarios", 
                                    usuario.getIdUsuario());
            usuario.setRutaImagen(rutaImagen);
        }
        usuarioService.save(usuario, true);
    }

    @Override
    public Model recordar(Model model, Usuario usuario) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String generaClave() {
        String tira = "ABCDEFGHIJKLMNOPQRSTUWYYZabcdefghijklmnopqrstuwxyz0123456789_*+-";
        String clave = "";
        for (int i = 0; i < 40; i++) {
            clave += tira.charAt((int) (Math.random() * tira.length()));
        }
        return clave;
    }

    @Value("${servidor.http}")
    private String servidor;

    @Autowired
    private CorreoService correoService;

    private void enviarCorreoActivar(Usuario usuario) throws MessagingException {
        String mensaje = messageSource
                .getMessage("registro.correo.activar", null, Locale.getDefault());

        mensaje = String.format(mensaje,
                usuario.getNombre(),
                usuario.getApellidos(),
                servidor,
                usuario.getUsername(),
                usuario.getPassword());

        String asunto = messageSource
                .getMessage("registro.mensaje.activacion", null, Locale.getDefault());

        correoService.enviarCorreoHTML(usuario.getCorreo(), asunto, mensaje);
    }

}

