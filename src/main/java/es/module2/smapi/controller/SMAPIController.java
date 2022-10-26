// package es.module2.smapi.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import es.module2.smapi.datamodel.AlarmDTO;
// import es.module2.smapi.datamodel.CameraDTO;
// import es.module2.smapi.datamodel.OwnerDTO;
// import es.module2.smapi.datamodel.PropertyDTO;
// import es.module2.smapi.model.Alarm;
// import es.module2.smapi.model.Camera;
// import es.module2.smapi.model.Owner;
// import es.module2.smapi.model.Property;
// import es.module2.smapi.service.OwnerService;
// import es.module2.smapi.service.PropertyService;
// import es.module2.smapi.service.CameraService;
// import es.module2.smapi.service.AlarmService;

// @RestController
// class SMAPIController {

//     @Autowired
//     private OwnerService owService;

//     @Autowired
//     private PropertyService propService;

//     @Autowired
//     private CameraService camService;

//     @Autowired
//     private AlarmService alService;
    
//     // Owner endpoints
//     @PostMapping("/newOwner")
//     Owner createOwner(@RequestBody OwnerDTO newOwner) {
//         return owService.createOwner(newOwner);
//     }

//     @GetMapping("/getOwner")
//     Owner getOwner(@RequestParam  String username) {
//         return owService.getOwner(username);
//     }

//     @PostMapping("/updateOwner")
//     Owner updateOwner(@RequestBody OwnerDTO newOwner) {
//         return owService.updateOwner(newOwner);
//     }
//     @DeleteMapping("/deleteOwner")
//     void deleteOwner(@RequestBody String username) {
//         owService.deleteOwner(username);
//     }



//     // Property endpoints

//     @PostMapping("/newProperty")
//     Property createProperty(@RequestBody PropertyDTO newProperty) {
//         return propService.createProperty(newProperty);
//     }

//     @GetMapping("/getProperty")
//     Property getProperty(@RequestParam  String name,String address) {
//         return propService.getProperty(name, address);
//     }

//     @PostMapping("/updateProperty")
//     Property updateProperty(@RequestBody PropertyDTO newProperty) {
//         return propService.updateProperty(newProperty);
//     }

//     @DeleteMapping("/deleteProperty")
//     void deleteProperty(@RequestParam  String name,String address) {
//         propService.deleteProperty(name, address);
//     }

//         // Camera endpoints

//     @PostMapping("/newCamera")
//     Camera createCamera(@RequestBody CameraDTO newCamera) {
//         return camService.createCamera(newCamera);
//     }

//     @GetMapping("/getCamera")
//     Camera getCamera(@RequestParam  long privateId) {
//         return camService.getCamera(privateId);
//     }

//     @PostMapping("/updateCamera")
//     Camera updateCamera(@RequestBody CameraDTO newCamera) {
//         return camService.updateCamera(newCamera);
//     }

//     @DeleteMapping("/deleteCamera")
//     void deleteCamera(@RequestParam long privateId) {
//         camService.deleteCamera(privateId);
//     }

//     // Alarm endpoints

//     @PostMapping("/newAlarm")
//     Alarm createAlarm(@RequestBody AlarmDTO newAlarm) {
//         return AlarmService.createAlarm(newAlarm);
//     }

//     @GetMapping("/getAlarm")
//     Alarm getAlarm(@RequestParam  long privateId) {
//         return AlarmService.getAlarm(privateId);
//     }

//     @PostMapping("/updateAlarm")
//     Alarm updateAlarm(@RequestBody AlarmDTO newAlarm) {
//         return AlarmService.updateAlarm(newAlarm);
//     }

//     @DeleteMapping("/deleteAlarm")
//     void deleteAlarm(@RequestParam long privateId) {
//         AlarmService.deleteAlarm(privateId);
//     }
// }