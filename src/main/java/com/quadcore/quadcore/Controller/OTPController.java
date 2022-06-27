package com.quadcore.quadcore.Controller;


import org.springframework.stereotype.Controller;


@Controller
public class OTPController {


//    @Autowired
//    public OTPService otpService;
//
//    @Autowired
//    public EmailSender emailService;
//
//
//    @RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
//    public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum){
//
//        final String SUCCESS = "Entered Otp is valid";
//        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        //Validate the Otp
//        if(otpnum >= 0){
//
//            int serverOtp = otpService.getOtp(username);
//            if(serverOtp > 0){
//                if(otpnum == serverOtp){
//                    otpService.clearOTP(username);
//
//                    return (SUCCESS);
//                }
//                else {
//                    return FAIL;
//                }
//            }else {
//                return FAIL;
//            }
//        }else {
//            return FAIL;
//        }
//    }
//}
}