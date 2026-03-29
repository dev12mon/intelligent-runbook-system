package com.runbook.ai_analyzer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/runbooks")
public class RunbookController {

    @Autowired
    private RunbookRepository runbookRepository;

    // This endpoint will fetch all runbooks from the database
    @GetMapping
    public List<RunbookEntity> getAllRunbooks() {
        return runbookRepository.findAll();
    }
    @GetMapping("/payment-issues")
    public Lust<RunbookEntity> getAllRunbooks(){
       /* List<RunbookEntity> filterList = new ArrayList<RunbookEntity>();
        for(RunbookEntity runbook : runbookRepository.getAllRunbooks){
            if("paymentService".equals(runbook.getServiceName())){
                filterList.add(runbook);
            }
        }*/
        return runbookRepository.findAll().steam()
               .filter(runbook -> "paymentService".equals(runbook.getServiceName()))
               .sorted(Comparator.comparing(RunbookEntity::getCreatedAt).reversed())
               .collect(Collectors::toList);

    }
    @GetMapping("/{id}")
    public ResponseEntity<RunbookEntity> getRunBookByID(@PathVarible Long id){
     return runbookRepository.getRunBookByID(id)
     .map(runbook -> ResponseEntity.ok().body(runbook))
     .orElse(() -> ResponseEntity.notFound().build());
    }
}