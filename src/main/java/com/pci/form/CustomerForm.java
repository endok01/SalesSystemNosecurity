package com.pci.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * 顧客情報入力フォームビーン
 * @author kzhk9
 *
 */
public class CustomerForm{

	@NotNull
	@Size(min=1,max=8)
	private String customerCode;

	@NotNull
	@Size(min=1, max=20)
	private String customerName;


	public CustomerForm() {
	}
	
	public CustomerForm(String customerCode, String customerName) {
		super();
		this.customerCode = customerCode;
		this.customerName = customerName;
	}


	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// デバッグ用
	@Override
	public String toString() {
		return "CustomerForm [customerCode=" + customerCode + ", customerName=" + customerName + "]";
	}


	
}