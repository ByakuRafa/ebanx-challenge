package com.ebanx.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
@Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404ForNonExistentAccount() throws Exception {
        mockMvc.perform(get("/balance?account_id=9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateAccountWithDeposit() throws Exception {
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 10
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "destination": {
                            "id": "100",
                            "balance": 10
                        }
                    }
                """));
    }

    @Test
    void shouldDepositIntoExistingAccount() throws Exception {
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "type":"deposit",
                            "destination":"100",
                            "amount":10
                        }        
                        """));

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 10
                    }
                      """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "destination": {
                            "id": "100",
                            "balance": 20
                        }
                    }
                        """));
    }

    @Test
    void shouldWithdrawFromExistingAccount() throws Exception {
        
       // depoist amout 20 
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 20
                    }
                      """));

        // withdaw 5 and expect balance 15
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "withdraw",
                        "origin": "100",
                        "amount": 5
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "origin": {
                            "id": "100",
                            "balance": 15
                        }
                    }
                """));
    }

    @Test
    void shouldReturn404WhenWithdrawingFromNonExistingAccount() throws Exception {
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "withdraw",
                        "origin": "200",
                        "amount": 10
                    }
                """)).andExpect(status().isNotFound());
    }

    @Test
    void shouldTransferBetweenAccounts() throws Exception {
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 100
                    }
                      """));

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "transfer",
                        "origin": "100",
                        "amount": 50,
                        "destination": "300"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "origin": {
                            "id": "100",
                            "balance": 50
                        },
                        "destination": {
                            "id": "300",
                            "balance": 50
                        }
                    }
                """));
    }
    
    @Test
    void shouldReturn404WhenTransferringFromNonExistingAccount() throws Exception {
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "type": "transfer",
                        "origin": "999",
                        "amount": 50,
                        "destination": "300"
                    }
                """))
                .andExpect(status().isNotFound());
    }
    

}
