package com.checkins.checkins.service;

import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.repo.EmployeeRepository;
import com.checkins.checkins.request.UserLoginRequest;
import com.checkins.checkins.response.AuthenticationResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Service
public class AuthenticationService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JwtService jwtService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

   public AuthenticationResponse login(UserLoginRequest request){
       LOGGER.info("request.getUsername()："+request.getUsername());
       Map<String, Object> map = new HashMap<>();
       EmployeeEntity employeeEntity = new EmployeeEntity();
       employeeEntity.setName(request.getUsername());
       employeeEntity =  employeeRepository.findByName(employeeEntity.getName()).orElse(null);
       LOGGER.info("userEntity.getUserName()："+employeeEntity.getName());
       map.put("Authority", employeeEntity.getPosition());
       LOGGER.info("userEntity.getUserAuth()："+employeeEntity.getAuth());
       String jwtToken = jwtService.generateToken(request);
       AuthenticationResponse response = new AuthenticationResponse();
       response.setAccessToken(jwtToken);
       return response;
    }

    //get request body
    public String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return stringBuilder.toString();
    }//getRequestBody

    public HashMap<String,String> getUserFromRequestBody(String requestBody) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonBody = new JSONObject(requestBody);
            String username2 = jsonBody.getString("Username");
            String password2 = jsonBody.getString("Password");
            map.put("Username", username2);
            map.put("Password", password2);

//            LOGGER.info("username2："+map.get("Username"));
//            LOGGER.info("password2："+map.get("Password"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
