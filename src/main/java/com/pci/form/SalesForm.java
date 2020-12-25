package com.pci.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import com.pci.entity.MtCustomer;
import com.pci.entity.MtUser;

/**
 * 
 * 売上明細入力用フォームクラス
 * @author endo_k01
 *
 */
public class SalesForm {
	// 非表示
	private long salesId;	// 売上ID

	// Input
	private MtUser mtUser;	// 担当者情報

	private MtCustomer mtCustomer;	// 顧客情報
	
	@NotBlank
	private String salesDateString;		// 日付
	
//	@NotNull
//	@Valid
	private List<SalesItemForm> salesItemForm;	// 売上明細入力フォーム
	
	/**
	 * コンストラクタ
	 */
	public SalesForm() {
		super();
	}

	/**
	 * アクセッサメソッド
	 */
	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public MtUser getMtUser() {
		return mtUser;
	}

	public void setMtUser(MtUser mtUser) {
		this.mtUser = mtUser;
	}

	public MtCustomer getMtCustomer() {
		return mtCustomer;
	}

	public void setMtCustomer(MtCustomer mtCustomer) {
		this.mtCustomer = mtCustomer;
	}

	public String getSalesDateString() {
		return salesDateString;
	}

	public void setSalesDateString(String salesDateString) {
		this.salesDateString = salesDateString;
	}

	public List<SalesItemForm> getSalesItemForm() {
		return salesItemForm;
	}

	public void setSalesItemForm(List<SalesItemForm> salesItemForm) {
		this.salesItemForm = salesItemForm;
	}


}
