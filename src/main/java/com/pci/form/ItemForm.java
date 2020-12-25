package com.pci.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pci.entity.MtItemGenre;

/**
 * 
 * 商品入力フォームビーン
 * 
 * @author endo_k01
 *
 */
public class ItemForm {

	@Size(min=8, max=8)
	private String itemCode;	// 商品コード

	@Size(min=1, max=20)
	private String itemName;	// 商品名

	@Min(1)
	@Max(9999999)
	@NotNull
	private Integer price;	// 単価

	@Size(max=10)
	private String spec;		// 仕様

	@NotNull
	private MtItemGenre mtItemGenre;	// 商品区分
	
	/**
	 * コンストラクタ
	 */
	public ItemForm() {
		super();
	}
	public ItemForm(String itemCode, String itemName, Integer price, String spec, MtItemGenre mtItemGenre) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.price = price;
		this.spec = spec;
		this.mtItemGenre = mtItemGenre;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}


	public MtItemGenre getMtItemGenre() {
		return mtItemGenre;
	}

	public void setMtItemGenre(MtItemGenre mtItemGenre) {
		this.mtItemGenre = mtItemGenre;
	}

	@Override
	public String toString() {
		return "ItemForm [itemCode=" + itemCode + ", itemName=" + itemName + ", price=" + price + ", spec=" + spec
				+ ", mtItemGenre=" + mtItemGenre.getItemGenreCode() + ":" + mtItemGenre.getItemGenreName() + "]";
	}


	
	
}
