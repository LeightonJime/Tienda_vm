
package com.tienda.controller;

import com.tienda.domain.Usuario;
import com.tienda.services.CorreoService;
import com.tienda.services.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroController {
   
    @GetMapping("/nuevo")
    public String nuevo(Model model, Usuario usuario){
        return "/registro/nuevo";
    }
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/registro/crear")
    public String crear(Model model, Usuario usuario) throws MessagingException{
        String mensaje;
        if(!usuarioService.existeUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo())){
            usuario.setPassword(generaClave());
            usuario.setActivo(false);
            usuarioService.save(usuario, true);
            enviarCorreoActivar(usuario);
        }else{
            mensaje="ya existe";
        }
        model.addAttribute("titulo", "Activación de usuario");
        model.addAttribute("mensaje", "Revise su cuenta de correo");
        
        return "/registro/salida";
    }
    
    private String generaClave(){
        String tira="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890_-+*";
        String clave="";
        for(int i=0; i<40; i++){
            clave+=tira.charAt((int)(Math.random()*tira.length()));
        }
        return clave;
    }
    
    @Value("${servidor.http}")
    private String servidor;
    
    @Autowired
    private CorreoService correoService;
    
    private void enviarCorreoActivar(Usuario usuario) throws MessagingException {
        String mensaje= "registro.correo.activar=<h1>Saludos</h1><br><strong>%s %s</strong><hr><p>Para activar su cuenta en TechShop siga el siguiente enlace <a href='%s/registro/activacion/%s/%s'>Activar</a></p><br><hr><h2>Equipo de TechShop</h2>";
        mensaje=String.format(mensaje, usuario.getNombre(), usuario.getApellidos(), servidor, usuario.getUsername(), usuario.getPassword());
        String asunto= "Activación de cuenta en TechShop";
        correoService.enviarCorreoHTML(usuario.getCorreo(), asunto, mensaje);
    }
}

