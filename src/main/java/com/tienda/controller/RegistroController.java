
package com.tienda.controller;

import com.tienda.domain.Usuario;
import com.tienda.services.CorreoService;
import com.tienda.services.RegistroService;
import com.tienda.services.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @GetMapping("/nuevo")
    public String nuevo(Model model, Usuario usuario) {
        return "/registro/nuevo";
    }

    @Autowired
    private RegistroService registroService;

    @PostMapping("/crear")
    public String crear(Model model, Usuario usuario) throws MessagingException {
        model = registroService.crear(model, usuario);
        return "/registro/salida";
    }

    @GetMapping("/activacion/{username}/{password}")
    public String activar(Model model,
            @PathVariable("username") String username,
            @PathVariable("password") String password) {
        
        model = registroService.activar(model, username, password);
        
        if (model.containsAttribute("usuario")) {
            return "/registro/activa";
        } else {
            return "/registro/salida";
        }
    }
    
    @PostMapping("/habilitar")
    public String habilitar(Model model, Usuario usuario,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        
        registroService.habilitar(usuario, imagenFile);
        
        return "redirect:/";
    }
    
}


