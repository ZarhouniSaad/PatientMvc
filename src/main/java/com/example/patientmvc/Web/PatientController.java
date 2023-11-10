package com.example.patientmvc.Web;

import com.example.patientmvc.Entities.Patient;
import com.example.patientmvc.Repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {

    //@Autowired on peut faire l'Injection avec autowired mais c'est pas recommandé on utilise le constructeur avec parametre
    private PatientRepository patientRepository;
    public PatientController(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }

    @GetMapping(path="/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name="keyword", defaultValue = "") String keyword){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listePatients",pagePatients.getContent());
        //model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",pagePatients.getPageable().getPageNumber());
        model.addAttribute("lastPage",pagePatients.getTotalPages());
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping(path = "/admin/delete")
    public String delete(@RequestParam(name = "id") int id,
                         String keyword,int page
                         ){
        patientRepository.deleteById((long) id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/patients")
    // @ ResponseBody pour dire que la requete http renvoyer en client contient des données Json
    // et pas une page html
    @ResponseBody
    public List<Patient> listePatients(){
        return patientRepository.findAll();
    }
    @GetMapping(path = "/admin/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    //Dans une application Spring, lorsque vous injectez l'objet Model dans différentes méthodes
    // de contrôleur, il s'agit du même objet Model pour la même requête HTTP. Cela signifie que
    // les objets que vous ajoutez à Model dans la méthode exemple seront accessibles dans la méthode
    // exemple2 de la même requête HTTP.
    @PostMapping(path = "/admin/save")
    //=>Validation des données avec @Valid : Vous utilisez l'annotation @Valid pour indiquer
    // à Spring de valider l'objet Patient en fonction des contraintes de validation que vous
    // avez définies dans la classe Patient.
    //=>BindingResult comme réceptacle des erreurs : Le paramètre BindingResult est utilisé pour
    // récupérer les résultats de la validation de l'objet Patient. Si des erreurs de validation
    // sont détectées, elles seront stockées dans l'objet BindingResult
    public String save(@Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page){
        if(bindingResult.hasErrors())return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping(path = "/admin/EditPatients")
    public String EditPatient(Model model,Long id,String keyword,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null)throw new RuntimeException("Patient introuvable");

        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }
}
