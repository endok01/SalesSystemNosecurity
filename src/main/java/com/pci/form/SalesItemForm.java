package com.pci.form;

/**
 * 売上明細情報用フォームクラス
 * @author endo_k01
 *
 */
public class SalesItemForm {

	// Output
	private Long detailId;	// 明細ID
	private String itemCode;	// 商品コード
	private String itemName;	// 商品名
	private Integer price;	// 単価
	private String spec;		// 仕様

	// Input
	private Integer quantity;	// 数量

	/**
	 * コンストラクタ
	 */
	public SalesItemForm() {
		super();
	}
	public SalesItemForm(Long detailId, String itemCode, String itemName, Integer price, String spec, Integer quantity) {
		super();
		this.detailId = detailId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.price = price;
		this.spec = spec;
		this.quantity = quantity;
	}
	
	/**
	 * アクセッサメソッド
	 * @return
	 */
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
