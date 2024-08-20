package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Tarefa;
import application.repository.TarefaRepository;

@RestController
@RequestMapping("tarefa")
public class TarefaCOntroller {
    @Autowired
    private TarefaRepository tarefaRepo;

    @GetMapping
    public Iterable<Tarefa> list(){
       return  tarefaRepo.findAll();
 
    }

    @GetMapping("/{id}")
    public Tarefa details(@PathVariable long id){
        Optional<Tarefa> resultado = tarefaRepo.findById(id);
        if(resultado.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno nao emcontrado");
        }
        return resultado.get();
    }

    @PostMapping
    public Tarefa insert(@RequestBody  Tarefa tarefa){
        return tarefaRepo.save(tarefa);
    }


    @PutMapping("/{id}")
    public Tarefa put(
        @PathVariable long id,
        @RequestBody Tarefa novosDados
        ){
        Optional<Tarefa> resultado= tarefaRepo.findById(id);
        if(resultado.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"tarefa nao emcontrado");
        }
        if(novosDados.getDescricao().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"NOme do tarefa nao encontrada");
        }
        resultado.get().setDescricao(novosDados.getDescricao());
        resultado.get().setConcluido(novosDados.getConcluido());
        return tarefaRepo.save(resultado.get());

    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        if(!tarefaRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"tarefa nao emcontrado");
        }
        tarefaRepo.deleteById(id);

    }

}
