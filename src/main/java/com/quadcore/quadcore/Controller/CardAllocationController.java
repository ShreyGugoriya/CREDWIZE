package com.quadcore.quadcore.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.quadcore.quadcore.Entities.*;
import com.quadcore.quadcore.Enum.CardType;
import com.quadcore.quadcore.Enum.EmailSubjects;
import com.quadcore.quadcore.Enum.Role;

import com.quadcore.quadcore.Repo.CredentialRepository;
import com.quadcore.quadcore.Service.*;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CardAllocationController {

    @Autowired
    CardAllocationService cardAllocationService;
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;
    @Autowired
    RestrictionService restrictionService;

    @Autowired
    CredentialService credentialService;

    @Autowired
    EmailService emailService;

    @Autowired
    CredentialRepository credentialRepository;


    @RequestMapping(value = "/card-allocation/",method = RequestMethod.GET)
    public List<CardAllocation> getCardAllocation(){
        try {
            return cardAllocationService.getCardAllocation();
        }catch (Exception exception){
            System.out.println(exception);
            return  null;
        }
    }
    @RequestMapping(value = "/card-allocation/{Userid}/",method = RequestMethod.GET)
    public List<Card> getAllCardByUserId(@PathVariable("Userid") int Userid ){
        List<CardAllocation> cardAllocationsList = cardAllocationService.getCardAllocation();
        List<Card> newCardList = new ArrayList<>();
        for(CardAllocation cardAllocation : cardAllocationsList){
            if(cardAllocation.getUser().getEmployee_id()==Userid && cardAllocation.getCard().getCardStatus()==true) {
                newCardList.add(cardAllocation.getCard());
            }
        }
        return newCardList;
    }


    @RequestMapping(value = "/card-allocation/{cardType}/{employeeId}/",method = RequestMethod.POST)
    public ResponseEntity<ResponseHandler> cardAllocation (@PathVariable("cardType") CardType cardType, @PathVariable("employeeId")
    int employeeId,@RequestBody RestrictionResponse restrictionResponse) throws MessagingException, TemplateException, IOException {
        List<Card> cardFind = cardService.getCard();
        int flag = 0;
        Card cardAllocated = null;
        Credential credential = new Credential();
        for (Card card3 : cardFind) {
            if (card3.getCardType() == cardType && card3.getCardAvailable()) {
                flag = 1;
                card3.setCardAvailable(false);
                cardAllocated = card3;
                Restrictions restrictions = new Restrictions();
                restrictions.setCard(cardAllocated);
                if(restrictionResponse.getFood()!=null) restrictions.setFood(restrictionResponse.getFood());
                if(restrictionResponse.getHealth()!=null) restrictions.setHealth(restrictionResponse.getHealth());
                if(restrictionResponse.getShopping()!=null) restrictions.setShopping(restrictionResponse.getShopping());
                if(restrictionResponse.getTravel()!=null) restrictions.setTravel(restrictionResponse.getTravel());
                restrictionService.createRestrictions(restrictions);
                if(!credentialService.getCredentialsByEmployeeID(employeeId)){
//                    User user1 = userService.getUserById(employeeId);
                    credential.setUsername(userService.getUserById(employeeId).getEmail());
                    credential.setPassword("password");
                    credential.setRole(Role.ROLE_USER);
                    credential.setUser(userService.getUserById(employeeId));

                    credentialRepository.save(credential);


                    UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Onboarding,0,null,0);

                    emailService.sendEmail(userInfo);

                }
                else {
                    List<Credential> credentialList = credentialService.getCredentials();
                    for(Credential credential1 : credentialList)
                    {
                        if(credential1.getUser().getEmployee_id()==employeeId && credential1.getRole()==Role.ROLE_ADMIN)
                        {
                            credential1.setRole(Role.ROLE_BOTH);
                            System.out.println("in else ..........okokokj");
                            break;
                        }
                    }
                }
                break;
            }
        }

        CardAllocation cardAllocation = new CardAllocation();
        User user1 = userService.getUserById(employeeId);
        UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Card_Allocation, 0,cardType,0);
//          Card card1 = cardService.getCardById((int) cardAllocated.getCardId());
        cardAllocation.setCard(cardAllocated);
        cardAllocation.setUser(user1);
        if(flag==1){

            emailService.sendEmail(userInfo);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseHandler("Card Allocated successfully: " ,
                            HttpStatus.OK, cardAllocationService.createCardAllocation(cardAllocation)));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseHandler("Card type is not available: " ,
                        HttpStatus.BAD_REQUEST, null));
    }


    @RequestMapping(value = "/card-allocation/rewards/{employeeId}/",method = RequestMethod.GET)
    public long getTotalRewardPointsByEmployeeID(@PathVariable("employeeId") int employeeId ){
        List<CardAllocation> cardAllocationsList1 = cardAllocationService.getCardAllocation();
        List<Card> newCardList1 = new ArrayList<>();
        for(CardAllocation cardAllocation : cardAllocationsList1){
            if(cardAllocation.getUser().getEmployee_id()==employeeId) {
                newCardList1.add(cardAllocation.getCard());
            }
        }
        List<Transaction> transactionList = new ArrayList<>();

        long sum = 0;
        for (Card card1 : newCardList1)
        {
             transactionList= card1.getTransactions();
             for(Transaction transaction2 : transactionList)
             {
                 sum+=transaction2.getRewardPoints();
             }
        }
        return sum;

    }

    @RequestMapping(value = "/card-deallocate/{cardId}", method = RequestMethod.PUT)
    public void deallocateCardByCardId(@PathVariable("cardId") long cardId, @RequestBody Card card){
        cardAllocationService.deallocateCardByCardId(cardId, card);

        }

    }

