package com.pci.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * 商品区分入力フォームビーン
 * @author endo_k01
 *
 */
public class ItemGenreForm {

	@NotNull
	@Size(min=8, max=8)
	private String itemGenreCode;

	@Size(min=1, max=10)
	private String itemGenreName;

	 // コンストラクタ
	public ItemGenreForm() {
		super();
	}
	public ItemGenreForm(String itemGenreCode, String itemGenreName) {
		super();
		this.itemGenreCode = itemGenreCode;
		this.itemGenreName = itemGenreName;
	}

	// Getter/Setter
	public String getItemGenreCode() {
		return itemGenreCode;
	}

	public void setItemGenreCode(String itemGenreCode) {
		this.itemGenreCode = itemGenreCode;
	}

	public String getItemGenreName() {
		return itemGenreName;
	}

	public void setItemGenreName(String itemGenreName) {
		this.itemGenreName = itemGenreName;
	}

	// デバッグ用
	@Override
	public String toString() {
		return "ItemGenreForm [itemGenreCode=" + itemGenreCode + ", itemGenreName=" + itemGenreName + "]";
	}

}