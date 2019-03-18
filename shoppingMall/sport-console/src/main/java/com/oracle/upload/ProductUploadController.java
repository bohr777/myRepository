package com.oracle.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.fdfs.Constants;
import com.oracle.fdfs.FastDFSUtils;
@Controller
public class ProductUploadController {

	@RequestMapping("/productLoad.do")
	@ResponseBody
	public List<String> productLoad(@RequestParam(required = false) MultipartFile[] pics) {
		List<String> list = new ArrayList<>();
		for (MultipartFile mf : pics) {
			String url;
			try {
				url = FastDFSUtils.uploadPic(mf.getBytes(), mf.getName(), mf.getSize());
				list.add(Constants.IMG_URL+url);
				System.out.println(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	@RequestMapping("/uploadFck.do")
	@ResponseBody
	public String uploadFck(MultipartFile uploadFile) {
		String imgPath = "";
		try {
			imgPath = FastDFSUtils.uploadPic(uploadFile.getBytes(), uploadFile.getName(), uploadFile.getSize());
			System.out.println(imgPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Constants.IMG_URL+imgPath;
	}
	
	
}
