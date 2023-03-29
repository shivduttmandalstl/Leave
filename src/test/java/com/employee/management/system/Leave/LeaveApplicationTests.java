package com.employee.management.system.Leave;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.Leave.model.Leaves;
import io.restassured.http.ContentType;


@SpringBootTest
class LeaveApplicationTests {

	@Test
	void contextLoads() {
	}

	
//	It will first get token then use this for application of leave then verify it with get request
//	then update status then delete the data
	@Test
	void allLeaveFunctionalityTest() throws JSONException {
		
		String authCheck="{ \"email\":\"puneet.verma@gmail.com\", \"password\":\"puneet\", \"role\":\"EMPLOYEE\"}";
		
		String response = given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authCheck)
                .when()
                .post("http://localhost:9000/home/authenticate")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
		
		JSONObject jsonToken;
        jsonToken = new JSONObject(response);
        
        Leaves leave = new Leaves();
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(1);
        
        leave.setEmail("puneet.verma@gmail.com");
        leave.setFromDate(from);
        leave.setToDate(to);
        leave.setLeaveType("Sick Leave");
        leave.setReason("Fever");
        
        String token = "Bearer " + jsonToken.getString("token");

// 		Add Leave test     
        Leaves responsePost = given()
        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(leave)
        .when()
        .post("http://localhost:9001/leave/add")
        .then()
        .assertThat().statusCode(201)
        .extract().response().getBody().as(Leaves.class);

//       Read Leaves Test
        Leaves resultSaved = given()
       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
       .when()
       .get("http://localhost:9001/leave/id/"+responsePost.getLeaveId())
       .then()
       .assertThat().statusCode(200)
       .extract().response().getBody().as(Leaves.class);
            
        assertEquals(leave.getEmail(), resultSaved.getEmail());
		assertEquals(leave.getFromDate(), resultSaved.getFromDate());
		assertEquals(leave.getToDate(), resultSaved.getToDate());
		assertEquals(leave.getLeaveType(), resultSaved.getLeaveType());
		assertEquals(leave.getReason(), resultSaved.getReason()); 
		
//		Update Leave Status Test
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
		
		
		
//		Delete Leave Test
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9001/leave/delete/"+responsePost.getLeaveId())
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
	}
	

}
