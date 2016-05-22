package org.todoservice.interfaces;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.todoservice.exception.ErrorHandlerException;
import org.todoservice.utilities.JsonResponse;
import org.todoservice.utilities.ResponseUtils;

import com.payments.exception.BusinessException;
import com.payments.services.ChargeHandler;
import com.ppersistance.entities.Account;
import com.ppersistance.entities.AccountDet;
import com.ppersistance.exception.EntityHandlerException;
import com.ppersistance.management.EntityManagerHelper;
import com.ppersistance.services.Sequentialize;

@Path("/payment")
public class PaymentResource {

	private ResponseUtils respHandler;
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
	
	@Path("check/")
	@GET
	@Produces("Application/json")
	public Response test () throws ErrorHandlerException {
		respHandler = new JsonResponse();
		return respHandler.responseHandler("yeah, I'm a live!");
	}
	
	@Path("charge/{amount}/{number}/{cvc}/{exp_month}/{exp_year}/{username}/action")
//	@PUT
	@GET
	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response charge (
			@PathParam("amount") Double amount,
			@PathParam("number") String number,
			@PathParam("cvc") String cvc,
			@PathParam("exp_month") String exp_month,
			@PathParam("exp_year") String exp_year,
			@PathParam("username") String username) throws ErrorHandlerException {
		respHandler = new JsonResponse();
		String desc = "Entrada: " + username + " por monto total de $" + amount;
		ChargeHandler charge = new ChargeHandler(number, cvc, exp_month, exp_year, amount, desc);
		String ids;
		try {
			ids = charge.createCharge();
			Account account = fillAccount(username, number);
			AccountDet detail = fillAccountDet(account.getId(), amount, desc, ids);
			return respHandler.responseHandler(detail);
		} catch (EntityHandlerException e) {
			e.printStackTrace();
			return respHandler.responseHandler("Ocurrio un error al almacenar");
		}   catch (BusinessException e) {
			e.printStackTrace();
			return respHandler.responseHandler(e.getMessage());
		}
	}
	
	
	private Account fillAccount(String username, String number) throws EntityHandlerException {
		Account account = existingAccount(number, username);
		if(account == null){
			EntityManagerHelper<Account> emh = new EntityManagerHelper<>();
			Long current = Sequentialize.INSTANCE.getNextId(Sequentialize.INSTANCE.ACCOUNT);
			account = new Account();
			account.setId(current);
			account.setNumber(number);
			account.setUserId(username);
			emh.post(account);
		}
		return account;
	}
	
	private Account existingAccount(String number, String username) throws EntityHandlerException {
		EntityManagerHelper<Account> em = new EntityManagerHelper<>();
		List<Account> accounts = em.genQuery("SELECT a FROM Account a WHERE a.number = ?1 and a.userId = ?2", 
				number, username);
		
		Account account = null;
		if(!accounts.isEmpty()){
			account = accounts.get(0);
		}
		return account;
	}
	
	private AccountDet fillAccountDet(long accId, Double amount, String desc, String token) throws EntityHandlerException {
		BigDecimal bdAmount = BigDecimal.valueOf(amount);
		Long current = Sequentialize.INSTANCE.getNextId(Sequentialize.INSTANCE.ACCOUNTD);
		AccountDet accountDet = new AccountDet();
		accountDet.setRegisterDate(new Date());
		accountDet.setAmount(bdAmount);
		accountDet.setDescription(desc);
		accountDet.setId(current);
		accountDet.setIdAcc(accId);
		EntityManagerHelper<AccountDet> emh2 = new EntityManagerHelper<>();
		accountDet.setTransToken(token);
		emh2.post(accountDet);
		return accountDet;
	}
	
}
