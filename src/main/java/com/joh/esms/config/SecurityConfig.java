package com.joh.esms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.joh.esms.service.AppUserDetailService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private AppUserDetailService appUserDetailService;

	@Autowired
	private Environment environment;

	public SecurityConfig() {
		logger.info("SecurityConfig->fired");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off	
		
		http.authorizeRequests()
		        .antMatchers("/login/**", "/logout","/jsLang.js").permitAll()
		        //
				.antMatchers("/adminRoot","/").authenticated()
			   	//
				.antMatchers("/customers/add/**").hasAnyRole("ADMIN", "CUSTOMER_WRITE")
				.antMatchers("/customers/edit/**", "/customers/update/**", "/customers/delete/**").hasAnyRole("ADMIN", "CUSTOMER_EDIT")
				.antMatchers("/customers/searchCustomer/**", "/customers/searchCustomerId/**").hasAnyRole("ADMIN", "CUSTOMER_READ")
				.antMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN", "CUSTOMER_READ")
				 //
				.antMatchers("/customerOrders/add/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_WRITE")
				.antMatchers("/customerOrders/edit/**", "/customerOrders/update/**", "/customerOrders/delete/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_EDIT")
				.antMatchers(HttpMethod.GET, "/customerOrders/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_READ")
				//
				
				//
				.antMatchers("/customerOrderReturns/add/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_RETURN_WRITE")
				.antMatchers("/customerOrderReturns/edit/**", "/customerOrderReturns/update/**", "/customerOrderReturns/delete/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_RETURN_EDIT")
				.antMatchers(HttpMethod.GET, "/customerOrderReturns/**").hasAnyRole("ADMIN", "CUSTOMER_ORDER_RETURN_READ")
				//
				
				
				//
				.antMatchers("/customerPayments/add/**").hasAnyRole("ADMIN", "CUSTOMER_PAYMENT_WRITE")
				.antMatchers("/customerPayments/edit/**", "/customerPayments/update/**", "/customerPayments/delete/**").hasAnyRole("ADMIN", "CUSTOMER_PAYMENT_EDIT")
				.antMatchers(HttpMethod.GET, "/customerPayments/**").hasAnyRole("ADMIN", "CUSTOMER_PAYMENT_READ")
				//
				
				
				//
				.antMatchers("/stocks/add/**").hasAnyRole("ADMIN", "STOCK_WRITE")
				.antMatchers("/stocks/edit/**", "/stocks/update/**", "/stocks/delete/**").hasAnyRole("ADMIN", "STOCK_EDIT")
				.antMatchers(HttpMethod.GET, "/stocks/**").hasAnyRole("ADMIN", "STOCK_READ")
				//
				
				
				//
				.antMatchers("/productCategories/add/**").hasAnyRole("ADMIN", "PRODUCT_WRITE")
				.antMatchers("/productCategories/edit/**", "/productCategories/update/**", "/productCategories/delete/**").hasAnyRole("ADMIN", "PRODUCT_EDIT")
				.antMatchers(HttpMethod.GET, "/productCategories/**").hasAnyRole("ADMIN", "PRODUCT_READ")
				//
				
				//
				.antMatchers("/productCategories/add/**").hasAnyRole("ADMIN", "PRODUCT_CATEGORY_WRITE")
				.antMatchers("/productCategories/edit/**", "/productCategories/update/**", "/productCategories/delete/**").hasAnyRole("ADMIN", "PRODUCT_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/productCategories/**").hasAnyRole("ADMIN", "PRODUCT_CATEGORY_READ")
				//
				
				
				//
				.antMatchers("/products/add/**").hasAnyRole("ADMIN", "PRODUCT_WRITE")
				.antMatchers("/products/edit/**", "/products/update/**", "/products/delete/**").hasAnyRole("ADMIN", "PRODUCT_EDIT")
				.antMatchers(HttpMethod.GET, "/products/**").hasAnyRole("ADMIN", "PRODUCT_READ")
				//
				
				
				//
				.antMatchers("/stockTransfers/add/**").hasAnyRole("ADMIN", "STOCK_TRANSFER_WRITE")
				.antMatchers("/stockTransfers/edit/**", "/stockTransfers/update/**", "/stockTransfers/delete/**").hasAnyRole("ADMIN", "STOCK_TRANSFER_EDIT")
				.antMatchers(HttpMethod.GET, "/stockTransfers/**").hasAnyRole("ADMIN", "STOCK_TRANSFER_READ")
				//
				
				//
				.antMatchers("/orderProductStepUps/add/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_WRITE")
				.antMatchers("/orderProductStepUps/edit/**", "/orderProductStepUps/update/**", "/orderProductStepUps/delete/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_EDIT")
				.antMatchers(HttpMethod.GET, "/orderProductStepUps/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_READ")
				//
				
				
				//
				.antMatchers("/orderProductStepUps/add/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_WRITE")
				.antMatchers("/orderProductStepUps/edit/**", "/orderProductStepUps/update/**", "/orderProductStepUps/delete/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_EDIT")
				.antMatchers(HttpMethod.GET, "/orderProductStepUps/**").hasAnyRole("ADMIN", "ORDER_PRODUCT_STEPUP_READ")
				//
				
				
				
				
				//
				.antMatchers("/vendorPayments/add/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_WRITE")
				.antMatchers("/vendorPayments/edit/**", "/vendorPayments/update/**", "/vendorPayments/delete/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_EDIT")
				.antMatchers(HttpMethod.GET, "/vendorPayments/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_READ")
				//
				
				
				
				//
				.antMatchers("/vendors/add/**").hasAnyRole("ADMIN", "VENDOR_WRITE")
				.antMatchers("/vendors/edit/**", "/vendors/update/**", "/vendors/delete/**").hasAnyRole("ADMIN", "VENDOR_EDIT")
				.antMatchers(HttpMethod.GET, "/vendors/**").hasAnyRole("ADMIN", "VENDOR_READ")
				//
				

				//
				.antMatchers("/vendors/add/**").hasAnyRole("ADMIN", "VENDOR_WRITE")
				.antMatchers("/vendors/edit/**", "/vendors/update/**", "/vendors/delete/**").hasAnyRole("ADMIN", "VENDOR_EDIT")
				.antMatchers(HttpMethod.GET, "/vendors/**").hasAnyRole("ADMIN", "VENDOR_READ")
				//
				
				//
				.antMatchers("/priceCategories/add/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_WRITE")
				.antMatchers("/priceCategories/edit/**", "/priceCategories/update/**", "/priceCategories/delete/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/priceCategories/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_READ")
				//
				
				//
				.antMatchers("/priceCategories/add/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_WRITE")
				.antMatchers("/priceCategories/edit/**", "/priceCategories/update/**", "/priceCategories/delete/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/priceCategories/**").hasAnyRole("ADMIN", "PRICE_CATEGORY_READ")
				//
				
				//
				.antMatchers("/vendorPayments/add/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_WRITE")
				.antMatchers("/vendorPayments/edit/**", "/vendorPayments/update/**", "/vendorPayments/delete/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_EDIT")
				.antMatchers(HttpMethod.GET, "/vendorPayments/**").hasAnyRole("ADMIN", "VENDOR_PAYMENT_READ")
				//

				.antMatchers("/report/**").hasAnyRole("ADMIN", "ACCOUNT_STATEMENT_READ")
				.antMatchers("/projectStatement/**").hasAnyRole("ADMIN", "ACCOUNT_STATEMENT_READ")
				.antMatchers("/psearch/**").hasAnyRole("ADMIN", "PRODUCT_RECORD_READ")
				.antMatchers("/profit/**").hasAnyRole("ADMIN", "PROFIT")

				//
				.antMatchers("/expenses/add/**").hasAnyRole("ADMIN", "EXPENSE_WRITE")
				.antMatchers("/expenses/edit/**", "/expenses/update/**", "/expenses/delete/**").hasAnyRole("ADMIN", "EXPENSE_EDIT")
				.antMatchers(HttpMethod.GET, "/expenses/**").hasAnyRole("ADMIN", "EXPENSE_READ")
				//
				
				
				//
				.antMatchers("/expenseCategories/add/**").hasAnyRole("ADMIN", "EXPENSE_CATEGORY_WRITE")
				.antMatchers("/expenseCategories/edit/**", "/expenseCategories/update/**", "/expenseCategories/delete/**").hasAnyRole("ADMIN", "EXPENSE_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/expenseCategories/**").hasAnyRole("ADMIN", "EXPENSE_CATEGORY_READ")
				//
				
				//
				.antMatchers("/incomes/add/**").hasAnyRole("ADMIN", "INCOME_WRITE")
				.antMatchers("/incomes/edit/**", "/incomes/update/**", "/incomes/delete/**").hasAnyRole("ADMIN", "INCOME_EDIT")
				.antMatchers(HttpMethod.GET, "/incomes/**").hasAnyRole("ADMIN", "INCOME_READ")
				//
				
				
				//
				.antMatchers("/incomeCategories/add/**").hasAnyRole("ADMIN", "INCOME_CATEGORY_WRITE")
				.antMatchers("/incomeCategories/edit/**", "/incomeCategories/update/**", "/incomeCategories/delete/**").hasAnyRole("ADMIN", "INCOME_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/incomeCategories/**").hasAnyRole("ADMIN", "INCOME_CATEGORY_READ")
				//
				
				
				//
				.antMatchers("/orderPreProducts/add/**").hasAnyRole("ADMIN", "ORDER_PRE_PRODUCT_WRITE")
				.antMatchers("/orderPreProducts/edit/**", "/orderPreProducts/update/**", "/orderPreProducts/delete/**").hasAnyRole("ADMIN", "ORDER_PRE_PRODUCT_EDIT")
				.antMatchers(HttpMethod.GET, "/orderPreProducts/**").hasAnyRole("ADMIN", "ORDER_PRE_PRODUCT_READ")
				//

				//
				.antMatchers("/customerWastedProducts/add/**").hasAnyRole("ADMIN", "CUSTOMER_WASTED_PRODUCT_WRITE")
				.antMatchers("/customerWastedProducts/edit/**", "/customerWastedProducts/update/**", "/customerWastedProducts/delete/**").hasAnyRole("ADMIN", "CUSTOMER_WASTED_PRODUCT_EDIT")
				.antMatchers(HttpMethod.GET, "/customerWastedProducts/**").hasAnyRole("ADMIN", "CUSTOMER_WASTED_PRODUCT_READ")
				//
				
				
				//
				.antMatchers("/accounts/add/**").hasAnyRole("ADMIN", "ACCOUNT_WRITE")
				.antMatchers("/accounts/edit/**", "/accounts/update/**", "/accounts/delete/**").hasAnyRole("ADMIN", "ACCOUNT_EDIT")
				.antMatchers(HttpMethod.GET, "/accounts/**").hasAnyRole("ADMIN", "ACCOUNT_READ")
				//


				//
				.antMatchers("/boxAccounting/**").hasAnyRole("ADMIN", "BOX_ACCOUNTING_READ")
				//
				.antMatchers("/netProfit/**").hasAnyRole("ADMIN")
				//
				.antMatchers("/setting/**").hasAnyRole("ADMIN", "SETTING")
				//
				//
				.antMatchers("/withdraws/add/**").hasAnyRole("ADMIN", "WITHDRAW_WRITE")
				.antMatchers("/withdraws/edit/**", "/withdraws/update/**", "/withdraws/delete/**").hasAnyRole("ADMIN", "WITHDRAW_EDIT")
				.antMatchers(HttpMethod.GET, "/withdraws/**").hasAnyRole("ADMIN", "WITHDRAW_READ")
				//


				//
				.antMatchers("/withdrawCategories/add/**").hasAnyRole("ADMIN", "WITHDRAW_CATEGORY_WRITE")
				.antMatchers("/withdrawCategories/edit/**", "/withdrawCategories/update/**", "/withdrawCategories/delete/**").hasAnyRole("ADMIN", "WITHDRAW_CATEGORY_EDIT")
				.antMatchers(HttpMethod.GET, "/withdrawCategories/**").hasAnyRole("ADMIN", "WITHDRAW_CATEGORY_READ")
				//

				//
				.antMatchers("/venderReturn/add/**").hasAnyRole("ADMIN", "VENDOR_RETURN_WRITE")
				.antMatchers("/venderReturn/delete/**" ).hasAnyRole("ADMIN", "VENDOR_RETURN_DELETE")
				.antMatchers("/venderReturn/**").hasAnyRole("ADMIN", "VENDOR_RETURN_READ")

				.antMatchers("/**").hasRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/adminRoot").and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout")
				.logoutSuccessUrl("/login").permitAll().and().rememberMe().key("@#$j232Kdf19)__")
				.tokenValiditySeconds(86400).and().exceptionHandling()
				.accessDeniedPage("/WEB-INF/views/accessDenied.jsp").and().csrf().disable();
		
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser(environment.getRequiredProperty("application.userName"))
		// .password(environment.getRequiredProperty("application.password")).roles("ADMIN");
		auth.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

}
