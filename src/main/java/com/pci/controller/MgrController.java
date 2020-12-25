package com.pci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtCustomer;
import com.pci.entity.MtItem;
import com.pci.entity.MtItemGenre;
import com.pci.form.ItemGenreForm;
import com.pci.form.CustomerForm;
import com.pci.form.ItemForm;
import com.pci.repository.CustomerRepository;
import com.pci.repository.ItemGenreRepository;
import com.pci.repository.ItemRepository;
import com.pci.repository.SalesDetailRepository;
import com.pci.repository.SalesOutlineRepository;
import com.pci.repository.UserRepository;
import com.pci.summary.ResultConverter;


/**
 * 
 * マネージャーロールのコントローラークラス
 * 
 * @author endo_k01
 *
 */
@Controller
@RequestMapping(value = "/Mgr")		// このコントローラが処理するURL
@SessionAttributes("loginUser")		// セッション情報の利用
public class MgrController {
	
	@Autowired
	UserRepository userRepo;			// ユーザ情報
	@Autowired							
	SalesOutlineRepository saleRepository;	// 売上概要
	@Autowired
	SalesDetailRepository saleDetailRepository;	// 売上明細
	@Autowired
	CustomerRepository customerRepository;	// 顧客情報
	@Autowired
	ItemRepository itemRepository;	// 商品情報
	@Autowired
	ItemGenreRepository itemGenreRepository;	// 商品区分
	@Autowired
	ResultConverter resultConverter;	// 集計情報変換

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 売上一覧
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * トップ画面用アクション(売上全件表示)
	 * @param mv
	 * @return ModelAndView
	 * 
	 */
	@RequestMapping(value = "/SalesList", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView top(ModelAndView mv) {

    	// 売上一覧の取得
		mv.addObject("salesList", saleRepository.findAll());
		
    	mv.setViewName("200manager/210salesList");

    	return mv;

	}

	/**
	 * 売上明細表示
	 * @param salesId	売上ID
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/salesDetailDisp/{salesId}",method=RequestMethod.POST)
	public ModelAndView salesDetailDisp(
			@PathVariable Long salesId,
			ModelAndView mv) {
		mv.addObject("saleOutline", saleRepository.getOne(salesId));
		mv.setViewName("/200manager/211salesDetailList");
		
		return mv;
	}
	
	/**
	 * 売上集計(日付別)
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/salesSummaryByDate",method=RequestMethod.POST)
	public ModelAndView salesSummaryByDate(ModelAndView mav) {
		mav.addObject("salesList", resultConverter.salesSummaryResultConverterForDate(saleDetailRepository.findBySalesSummaryByDate()));
		mav.setViewName("/200manager/216salesSummaryByDate");
		
		return mav;
	}
	
	/**
	 * 売上集計(商品別)
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/salesSummaryByItem",method=RequestMethod.POST)
	public ModelAndView salesSummaryByItem(ModelAndView mav) {
		mav.addObject("salesList", resultConverter.salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByItem()));
		mav.setViewName("/200manager/212salesSummaryByItem");
		return mav;
	}

	/**
	 * 売上集計(顧客別)
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/salesSummaryByCustomer",method=RequestMethod.POST)
	public ModelAndView salesSummaryByCustomer(ModelAndView mav) {
		mav.addObject("salesList", resultConverter.salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByCustomer()));
		mav.setViewName("/200manager/213salesSummaryByCustomer");
		return mav;
	}
	
	/**
	 * 売上集計(商品区分別)
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/salesSummaryByItemGenre",method=RequestMethod.POST)
	public ModelAndView salesSummaryByItemGenre(ModelAndView mav) {
		mav.addObject("salesList", resultConverter.salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByItemGenre()));
		mav.setViewName("/200manager/214salesSummaryByItemGenre");
		return mav;
	}

	/**
	 * 売上集計(課員別)
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/salesSummaryByStaff",method=RequestMethod.POST)
	public ModelAndView salesSummaryByStaff(ModelAndView mav) {
		mav.addObject("salesList", resultConverter.salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByStaff()));
		mav.setViewName("/200manager/215salesSummaryByStaff");
		return mav;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 顧客マスタ系処理
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 顧客一覧表示
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/CustomerList", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView custumerList(ModelAndView mv) {

		// 全顧客情報を取得(CustomerCode昇順で取得)
    	List<MtCustomer> customerList = customerRepository.findAllByOrderByCustomerCode();
    	mv.addObject("customerList",customerList);

    	// 顧客リストの表示を行う
    	mv.setViewName("200manager/260customerList");		

    	return mv;
	}

	/**
	 * 
	 * 顧客情報登録メソッド
	 * 顧客登録ボタンクリック時に呼び出される
	 * @param customerForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerCre", method = RequestMethod.POST)
	public ModelAndView custumerCre(
			@ModelAttribute CustomerForm customerForm,
			ModelAndView mv) {
		
		// Form初期化
		customerForm.setCustomerCode(null);
		customerForm.setCustomerName(null);
		mv.setViewName("200manager/261customerCre");
		return mv;

	}

	/**
	 * 
	 * 顧客情報登録確認メソッド
	 * @param customerForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerCreConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView customerCreConf(
			@ModelAttribute @Validated CustomerForm customerForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {	// Validationエラー
			mv.setViewName("200manager/261customerCre");
		} else {
			// 顧客コードが重複していないかチェックする
			if(!customerRepository.existsById(customerForm.getCustomerCode())) {	
				mv.setViewName("200manager/262customerCreConf");		
			}else {
				mv.addObject("errormessage","IDが重複しています");
				mv.setViewName("200manager/261customerCre");
			} 
		}
		
		return mv;

	}

	/**
	 * 
	 * 顧客情報登録・更新実行メソッド
	 * @param customerForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerRegExe", method = RequestMethod.POST)
	@Transactional
	public ModelAndView custmerRegExe(
			@ModelAttribute CustomerForm customerForm,
			ModelAndView mv) {

		// 顧客情報登録
		MtCustomer c = new MtCustomer(customerForm.getCustomerCode(), customerForm.getCustomerName());
		customerRepository.saveAndFlush(c);
		
		// 顧客リストへリダイレクト
		mv.setViewName("redirect:/Mgr/CustomerList");		
		
		return mv;

	}

	/**
	 * 
	 * 顧客情報更新メソッド
	 * 変更リンククリック時に呼び出される
	 * @param customerId
	 * @param customerForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerUpd/{customerCode}", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView custumerUpd(
			@PathVariable String customerCode,	// 顧客ID
			@ModelAttribute CustomerForm customerForm,
			ModelAndView mv) {
		
		// 変更対象の顧客エンティティ取得
		MtCustomer m = customerRepository.getOne(customerCode);

		// フォームビーンに顧客情報を設定
		customerForm.setCustomerCode(m.getCustomerCode());
		customerForm.setCustomerName(m.getCustomerName());
		
		// 顧客情報変更画面の表示を行う
		mv.setViewName("200manager/265customerUpd");

		return mv;

	}

	/**
	 * 
	 * 顧客情報変更確認メソッド
	 * @param customerForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerUpdConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView customerUpdConf(
			@ModelAttribute @Validated CustomerForm customerForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {
			// Validationエラー
			mv.setViewName("200manager/265customerUpd");
		} else {
			mv.setViewName("200manager/266customerUpdConf");		
		}
		
		return mv;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 商品マスタ系処理
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 商品一覧表示
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ItemList")
	@Transactional(readOnly = true)
	public ModelAndView itemList(ModelAndView mv) {

		// 全商品情報を取得
		// (顧客コードで昇順ソートされた全エンティティを取得する)
    	List<MtItem> itemList = itemRepository.findAllByOrderByItemCode();
    	mv.addObject("itemList",itemList);

    	mv.setViewName("200manager/220itemList");		
		return mv;
	}

	/**
	 * 
	 * 商品登録メソッド
	 * 商品登録ボタンクリック時に呼び出される
	 * @param itemForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemCre", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemAdd(
			@ModelAttribute ItemForm itemForm,
			ModelAndView mv) {
		
		// 商品区分リストをモデルに追加する
		List<MtItemGenre>itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
		mv.addObject("itemGenreList", itemGenreList);

		mv.setViewName("200manager/221itemCre");
		return mv;

	}

	/**
	 * 
	 * 商品情報登録確認メソッド
	 * @param itemrForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemCreConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemCreConf(
			@ModelAttribute @Validated ItemForm itemForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {	// Validationエラー
			// 商品区分リストをモデルに追加する
			List<MtItemGenre>itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
			mv.addObject("itemGenreList", itemGenreList);

			mv.setViewName("200manager/221itemCre");
		} else {
			// 商品コードが重複していないかチェックする
			if(!itemRepository.existsById(itemForm.getItemCode())) {
				MtItemGenre g = itemGenreRepository.getOne(itemForm.getMtItemGenre().getItemGenreCode());
				itemForm.setMtItemGenre(g);
				mv.setViewName("200manager/222itemCreConf");		
			}else {
				mv.addObject("errormessage","IDが重複しています");
				// 商品区分リストをモデルに追加する
				List<MtItemGenre>itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
				mv.addObject("itemGenreList", itemGenreList);

				mv.setViewName("200manager/221itemCre");
			} 
		}
		
		return mv;

	}

	/**
	 * 
	 * 商品更新メソッド
	 * 変更ボタンクリック時に呼び出される
	 * @param itemCode	商品コード
	 * @param itemForm	フォームビーン
	 * @param mv ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemUpd/{itemCode}", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemUpd(
			@PathVariable String itemCode,	// 商品コード
			@ModelAttribute ItemForm itemForm,
			ModelAndView mv) {
		
		// 変更対象の商品区分エンティティを取得する
		MtItem i = itemRepository.getOne(itemCode);

		// エンティティの情報をフォームビーンに設定する
		itemForm.setItemCode(i.getItemCode());
		itemForm.setItemName(i.getItemName());
		itemForm.setPrice(i.getPrice());
		itemForm.setSpec(i.getSpec());
		itemForm.setMtItemGenre(i.getMtItemGenre());
		// 商品区分リストをモデルに追加する
		List<MtItemGenre>itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
		mv.addObject("itemGenreList", itemGenreList);
		// 商品変更画面の表示を行う
		mv.setViewName("200manager/225itemUpd");
		
		return mv;

	}
	
	/**
	 * 
	 * 商品情報登録確認メソッド
	 * @param itemrForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemUpdConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemUpdConf(
			@ModelAttribute @Validated ItemForm itemForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {	// Validationエラー
			// 商品区分リストをモデルに追加する
			List<MtItemGenre>itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
			mv.addObject("itemGenreList", itemGenreList);
			mv.setViewName("200manager/225itemUpd");
		} else {
			MtItemGenre g = itemGenreRepository.getOne(itemForm.getMtItemGenre().getItemGenreCode());
			itemForm.setMtItemGenre(g);
			mv.setViewName("200manager/226itemUpdConf");		
		}
		
		return mv;

	}

	/**
	 * 
	 * 商品情報登録・更新実行メソッド
	 * @param customerForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemRegExe", method = RequestMethod.POST)
	@Transactional
	public ModelAndView itemRegExe(
			@ModelAttribute ItemForm itemForm,
			ModelAndView mv) {

		// デバッグ
		System.out.println(itemForm);
		// 商品情報登録
		MtItemGenre g = itemGenreRepository.getOne(itemForm.getMtItemGenre().getItemGenreCode());
		MtItem i = new MtItem(itemForm.getItemCode(), itemForm.getItemName(), itemForm.getPrice(), itemForm.getSpec(), g);
		itemRepository.saveAndFlush(i);
		
		// 商品区分リストへリダイレクト
		mv.setViewName("redirect:/Mgr/ItemList");		
		
		return mv;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 商品区分マスタ系処理
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 商品区分一覧表示
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ItemGenreList")
	@Transactional(readOnly = true)
	public ModelAndView itemGenreList(ModelAndView mv) {

		// 全商品区分情報を取得
    	List<MtItemGenre> itemGenreList = itemGenreRepository.findAllByOrderByItemGenreCode();
    	mv.addObject("itemGenreList", itemGenreList);

    	// 区分リスト画面の表示を行う
    	mv.setViewName("200manager/240itemGenreList");
    	
		return mv;
	}

	/**
	 * 商品区分情報登録メソッド
	 * 商品区分登録ボタンクリック時に呼び出される
	 * @param itemGenreForm フォームビーン
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/itemGenreCre", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemGenreCre(
			@ModelAttribute ItemGenreForm itemGenreForm,
			ModelAndView mv) {
		
		// Form初期化
		itemGenreForm.setItemGenreCode(null);;
		itemGenreForm.setItemGenreName(null);
		
		// 商品区分追加画面の表示を行う
		mv.setViewName("200manager/241itemGenreCre");

		return mv;

	}

	/**
	 * 
	 * 商品区分情報登録確認メソッド
	 * @param itemGenreForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemGenreCreConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemGenreCreConf(
			@ModelAttribute @Validated ItemGenreForm itemGenreForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {	// Validationエラー
			mv.setViewName("200manager/241itemGenreCre");
		} else {
			// 顧客コードが重複していないかチェックする
			if(!itemGenreRepository.existsById(itemGenreForm.getItemGenreCode())) {	
				mv.setViewName("200manager/242itemGenreCreConf");		
			}else {
				mv.addObject("errormessage","IDが重複しています");
				mv.setViewName("200manager/241itemGenreCre");
			} 
		}
		
		return mv;

	}

	/**
	 * 
	 * 商品区分更新メソッド
	 * 変更リンククリック時に呼び出される
	 * @param itemGenreCode	商品区分コード
	 * @param itemGenreForm	フォームビーン
	 * @param mv ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemGenreUpd/{itemGenreCode}", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemGenreUpd(
			@PathVariable String itemGenreCode,	// 商品区分コード
			@ModelAttribute ItemGenreForm itemGenreForm,
			ModelAndView mv) {
		
		// 変更対象の商品区分エンティティを取得する
		MtItemGenre i = itemGenreRepository.getOne(itemGenreCode);

		// エンティティの情報をフォームビーンに設定する
		itemGenreForm.setItemGenreCode(i.getItemGenreCode());
		itemGenreForm.setItemGenreName(i.getItemGenreName());
		
		// 商品区分変更画面の表示を行う
		mv.setViewName("200manager/245itemGenreUpd");
		
		return mv;

	}

	/**
	 * 
	 * 商品区分情報変更確認メソッド
	 * @param itemGenreForm
	 * @param result
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemGenreUpdConf", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ModelAndView itemGenreUpdConf(
			@ModelAttribute @Validated ItemGenreForm itemGenreForm,
			BindingResult result,
			ModelAndView mv) {

		if(result.hasErrors()) {
			// Validationエラー
			mv.setViewName("200manager/245itemGenreUpd");
		} else {
			mv.setViewName("200manager/246itemGenreUpdConf");		
		}
		
		return mv;

	}

	/**
	 * 
	 * 商品区分情報登録・更新実行メソッド
	 * @param customerForm
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/itemGenreRegExe", method = RequestMethod.POST)
	@Transactional
	public ModelAndView itemGenreRegExe(
			@ModelAttribute ItemGenreForm itemGenreForm,
			ModelAndView mv) {

		// 商品区分情報登録
		MtItemGenre i = new MtItemGenre(itemGenreForm.getItemGenreCode(), itemGenreForm.getItemGenreName());
		itemGenreRepository.saveAndFlush(i);
		
		// 商品区分リストへリダイレクト
		mv.setViewName("redirect:/Mgr/ItemGenreList");		
		
		return mv;

	}

}