
package com.tienda.controller;

import com.tienda.domain.Usuario;
import com.tienda.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/registro/crear")
    public Model crear(Model model, Usuario usuario){
        String mensaje;
        if(!usuarioService.existeUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo())){
            usuario.setPassword("XYZ");
            usuario.setActivo(false);
            usuarioService.save(usuario, true);
            enviarCorreoActivar(usuario);
        }else{
            mensaje="ya existe";
        }
        
        return null;
    }

    private void enviarCorreoActivar(Usuario usuario) {
        
    }
}

