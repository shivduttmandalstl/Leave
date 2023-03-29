package com.employee.management.system.Leave;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.Leave.model.Leaves;


import io.restassured.http.ContentType;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class LeaveApplicationTests {

	@Test
	void contextLoads() {
	}

	String AuthVerification() throws JSONException {
		String authCheck="{ \"email\":\"puneet.verma@gmail.com\", \"password\":\"puneet\", \"role\":\"EMPLOYEE\"}";
		
		String response = given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
	            .body(authCheck)
	            .when()
	            .post("http://localhost:9000/home/authenticate")
	            .then()
	            .assertThat().statusCode(200)
	            .extract().response().asString();
		
		JSONObject jsonToken = new JSONObject();
	    jsonToken = new JSONObject(response);
	    
	    String tokenAuth = "Bearer " + jsonToken.getString("token");
		return tokenAuth;
	}
	
	static int leaveId;
	
	
	
	
//	It will test the functionality that leave is adding or not.
	@Test
	@Order(1)
	void AddLeaveTest() throws JSONException{
	
        Leaves leave = new Leaves();
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(1);
        
        leave.setEmail("puneet.verma@gmail.com");
        leave.setFromDate(from);
        leave.setToDate(to);
        leave.setLeaveType("Sick Leave");
        leave.setReason("Fever");
        
        String token = AuthVerification();
          
        Leaves responsePost = given()
        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(leave)
        .when()
        .post("http://localhost:9001/leave/add")
        .then()
        .assertThat().statusCode(201)
        .extract().response().getBody().as(Leaves.class);
        
       LeaveApplicationTests.leaveId = responsePost.getLeaveId();
        
        	assertEquals(leave.getEmail(), responsePost.getEmail());
      		assertEquals(leave.getFromDate(), responsePost.getFromDate());
      		assertEquals(leave.getToDate(), responsePost.getToDate());
      		assertEquals(leave.getLeaveType(), responsePost.getLeaveType());
      		assertEquals(leave.getReason(), responsePost.getReason()); 
      		
	}
	
	
//  Read Leaves Functionality Test
	@Test
	@Order(2)
	void GetLeavesTest() throws JSONException {
		Leaves leave = new Leaves();
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(1);
        
        leave.setEmail("puneet.verma@gmail.com");
        leave.setFromDate(from);
        leave.setToDate(to);
        leave.setLeaveType("Sick Leave");
        leave.setReason("Fever");
        
        String token = AuthVerification();

        Leaves resultSaved = given()
       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
       .when()
       .get("http://localhost:9001/leave/id/"+leaveId)
       .then()
       .assertThat().statusCode(200)
       .extract().response().getBody().as(Leaves.class);
        
        assertEquals(leave.getEmail(), resultSaved.getEmail());
  		assertEquals(leave.getFromDate(), resultSaved.getFromDate());
  		assertEquals(leave.getToDate(), resultSaved.getToDate());
  		assertEquals(leave.getLeaveType(), resultSaved.getLeaveType());
  		assertEquals(leave.getReason(), resultSaved.getReason()); 
        
	}   
            
      
//	Update Leave Status Test
	@Test
	@Order(3)
	void UpdateLeaveStatusTest() throws JSONException {
		String token = AuthVerification();
		Leaves resultSaved = given()
			       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
			       .when()
			       .get("http://localhost:9001/leave/id/"+leaveId)
			       .then()
			       .assertThat().statusCode(200)
			       .extract().response().getBody().as(Leaves.class);
		
		resultSaved.setStatus("Accepted");		
		
		Leaves responsePut = given()
		        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
		        .body(resultSaved)
		        .when()
		        .put("http://localhost:9001/leave/update")
		        .then()
		        .assertThat().statusCode(200)
		        .extract().response().getBody().as(Leaves.class);
		
		assertEquals("Accepted", responsePut.getStatus());
		
		
	}
	
//	Get Task by Email Test
	@Test
	@Order(4)
	void GetLeavesByEmailTest() throws JSONException {
	String token = AuthVerification();
	String email = "puneet.verma@gmail.com";
	Leaves[] responseLeaves = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9001/leave/all/"+email)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Leaves[].class);
	
	for(int i =0; i<responseLeaves.length;i++) {
		assertEquals(email,responseLeaves[i].getEmail());
	}
	
	}
	
	
//	Get Leaves by Manager Email Test
	@Test
	@Order(5)
	void GetLeavesByManagerEmailTest() throws JSONException {
	String token = AuthVerification();
	String managerEmail = "kartik@gmail.com";
	Leaves[] responseLeaves = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9001/leave/manager/"+managerEmail)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Leaves[].class);
	
	for(int i =0; i<responseLeaves.length;i++) {
		assertEquals(managerEmail,responseLeaves[i].getManagerEmail());
	}
		 
}
	
//	Delete Leave Test
	@Test
	@Order(6)
	void DeleteLeaveTest() throws JSONException {
		String token = AuthVerification();
			
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9001/leave/delete/"+leaveId)
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
	}
	

}
