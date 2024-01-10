package org.dadak.market.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dadak.market.dao.PickDao;
import org.dadak.market.dao.ProductDao;
import org.dadak.market.model.Account;
import org.dadak.market.model.OwnerTargetPick;
import org.dadak.market.model.Pick;
import org.dadak.market.model.PickAccount;
import org.dadak.market.model.Product;
import org.dadak.market.model.ProductImage;
import org.dadak.market.model.ProductRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductDao productDao;
	private final PickDao pickDao;

	@GetMapping("/{productId}")
	public String showProductDetail(@PathVariable int productId,
			@SessionAttribute(required = false) Account logonAccount, Model model) {

		Product found = productDao.findById(productId);
		int totalPick = pickDao.countByTarget(productId);

		if (logonAccount == null) {
			model.addAttribute("picked", false);
		} else {
			OwnerTargetPick one = OwnerTargetPick.builder().ownerAccountId(logonAccount.getId())
					.targetProductId(productId).build();
			int cnt = pickDao.findByOwnerAndTarget(one);
			model.addAttribute("picked", cnt == 1 ? true : false);
		}

		model.addAttribute("totalPick", totalPick);
		model.addAttribute("product", found);
		found.setViewCnt(found.getViewCnt() + 1);
		productDao.update(found);

		return "product/view";
	}

	// 찜하기
	@PostMapping("/{productId}")
	public String insertPick(@SessionAttribute Account logonAccount, @RequestParam int targetProductId, Model model) {

		Pick one = Pick.builder()//
				.ownerAccountId(logonAccount.getId())//
				.targetProductId(targetProductId).build();//

		pickDao.savePick(one);

		return "redirect:/product/" + targetProductId;
	}

	// 찜하기(Ajax) 등록
	@PostMapping("/pickajax")
	@ResponseBody
	public String insertAjaxPidck(@SessionAttribute Account logonAccount, @RequestParam int targetProductId,
			Model model) {

		Pick one = Pick.builder()//
				.ownerAccountId(logonAccount.getId())//
				.targetProductId(targetProductId).build();//

		pickDao.savePick(one);
		int updatePicked = pickDao.countByTarget(targetProductId);
		
		// ajax 응답을 보내줄떄는 JSON으로 보내준다. (MAP을 만들어서)
		Map<String, Object> response = new HashMap<>();
		response.put("result", "success");
		response.put("updatePick", updatePicked);
		
		Gson gson = new Gson();
		return gson.toJson(response);

	}

	// 찜하기 삭제
	@DeleteMapping("/{productId}")
	public String proceedRemovePick(@SessionAttribute Account logonAccount, @RequestParam int targetProductId,
			Model model) {

		OwnerTargetPick one = OwnerTargetPick.builder()//
				.ownerAccountId(logonAccount.getId())//
				.targetProductId(targetProductId).build();//

		pickDao.deleteByOwnerAndTarget(one);

		return "redirect:/product/" + targetProductId;
	}

	// 찜하기 삭제(Ajax)
	@DeleteMapping("/pickajax")
	@ResponseBody
	public String proceedAjaxRemovePick(@SessionAttribute Account logonAccount, @RequestParam int targetProductId,
			Model model) {

		OwnerTargetPick one = OwnerTargetPick.builder()//
				.ownerAccountId(logonAccount.getId())//
				.targetProductId(targetProductId).build();//

		pickDao.deleteByOwnerAndTarget(one);
		int updatePicked = pickDao.countByTarget(targetProductId);
		
		// ajax 응답을 보내줄떄는 JSON으로 보내준다. (MAP을 만들어서)
		Map<String, Object> response = new HashMap<>();
		response.put("result", "success");
		response.put("updatePick", updatePicked);
		
		Gson gson = new Gson();
		return gson.toJson(response);
	}

	@GetMapping("/register")
	public String showProductRegister(Model model) {

		return "product/register";
	}

	@PostMapping("/register")
	public String proceedAddNewProduct(@ModelAttribute ProductRegister productRegister,
			@SessionAttribute Account logonAccount, Model model) throws IllegalStateException, IOException {
		// System.out.println(productRegister.toString());
		Product one = Product.builder()//
				.title(productRegister.getTitle())//
				.type(productRegister.getType())//
				.price(productRegister.getPrice())//
				.description(productRegister.getDescription())//
				.accountId(logonAccount.getId())//
				.viewCnt(0).build();//

		productDao.saveProduct(one);
		// System.out.println("상품저장 결과--> "+x);

		MultipartFile[] images = productRegister.getImages();

		File dir = new File("c:\\upload\\productImage\\" + one.getId());
		dir.mkdirs();

		for (MultipartFile image : images) {
			if (image.isEmpty()) {
				continue;
			}
			String fileName = UUID.randomUUID().toString();
			File target = new File(dir, fileName);
			image.transferTo(target);
			ProductImage productImage = ProductImage.builder()//
					.url("/upload/productImage/" + one.getId() + "/" + fileName)//
					.path(target.getAbsolutePath())//
					.productId(one.getId()).build(); //

			productDao.saveProductImage(productImage);
			// System.out.println("상품저장 결과--> "+y);
		}

		return "redirect:/product/" + one.getId();
	}

	@GetMapping("/picklist")
	public String showPickList(@SessionAttribute(required = false) Account logonAccount, Model model) {
		if(logonAccount == null) {
			return "redirect:/signin";
		}
		
		List<PickAccount> picklist = pickDao.findAllByOwnerId(logonAccount.getId());
		//System.out.println("picklist 사이즈---> "+picklist.size());
		if (picklist.size() != 0) {
			

			model.addAttribute("picklist", picklist);
			//model.addAttribute("pickCnt", pickCnt);
		}
		return "product/picklist";
	}

}
