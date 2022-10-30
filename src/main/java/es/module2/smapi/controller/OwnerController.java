package es.module2.smapi.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/owners")
@Validated
class OwnerController {

    // @Autowired
    // private OwnerService owService;
    
    // // Owner endpoints
    // @PostMapping("/newOwner")
    // Owner createOwner(@RequestBody OwnerDTO newOwner) {
    //     return owService.createOwner(newOwner);
    // }

    // @GetMapping("/getOwner")
    // Owner getOwner(@RequestParam  String username) {
    //     return owService.getOwner(username);
    // }

    // @PostMapping("/updateOwner")
    // Owner updateOwner(@RequestBody OwnerDTO newOwner) {
    //     return owService.updateOwner(newOwner);
    // }
    // @DeleteMapping("/deleteOwner")
    // void deleteOwner(@RequestBody String username) {
    //     owService.deleteOwner(username);
    // }



}