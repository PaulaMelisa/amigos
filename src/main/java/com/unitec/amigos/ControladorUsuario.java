package com.unitec.amigos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ControladorUsuario {
    //Aqui va un metodo que representa cada uno  de los estados que vamos a transferir es decir va un GET, PUT y Delete como minimo.

    @Autowired RepositorioUsuario repoUsuario;

    //iplementar el codigo para guardar un usuario en Mongodb
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json) throws Exception{
        //primero leemos y convertimos el objeto json a objeto java

        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);
        //este usuario,ya en formato json lo guardamos en mongodb
        repoUsuario.save(u);
        //creamos un objeto de tipo estatus y este objeto lo retonamos añ cñiemte (android o postman)

Estatus estatus=new Estatus();
estatus.setSuccess(true);
estatus.setMensaje("Tu usuario se guardo sactisfactoriamente");
return estatus;
    }


    @GetMapping("/usuario/{id} ")
        public Usuario obtenerPorId(@PathVariable String id){
          //leemos un usuario con el metodo finbyid pansandole como argumento el id(email)
        //que queremos apoyarnos de repUusario
          Usuario u=  repoUsuario.findById(id).get();
            return u;

        }

        @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){

        return repoUsuario.findAll();
        }


        //metodo para actualizar
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception{
        //primero dbemos verificar que exista,por lo tanto primero lo buscamos
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);
        Estatus e=new Estatus();
        if(repoUsuario.findById(u.getEmail()).isPresent()){
            //lo volvemos a guardar
            repoUsuario.save(u);
            e.setMensaje("Usuario se actualizo con exito");
            e.setSuccess(true);
        }else{
            e.setMensaje("Ese usuario no existe, no se actualiza");
            e.setSuccess(false);
        }
    return e;
    }
    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){
        Estatus estatus= new Estatus();
        if(repoUsuario.findById(id).isPresent())  {
            repoUsuario.deleteById(id);
            estatus.setSuccess(true);
            estatus.setMensaje("Usuaio borrado con exito");
        }else{
            estatus.setSuccess(false);
            estatus.setMensaje("Este usuario no existe");

        }
    return estatus;
    }

}
