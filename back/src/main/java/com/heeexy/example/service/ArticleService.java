package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.heeexy.example.mapper.ArticleMapper;
import com.heeexy.example.model.Article;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:07
 */
@Service
public class ArticleService {

	@Autowired
	private ArticleMapper articleMapper;


	public List<Article> getAll(Article article) {
		if (article.getPage() != null && article.getRows() != null) {
			PageHelper.startPage(article.getPage(), article.getRows());
		}
		return articleMapper.selectAll();
	}

	/**
	 * 新增或者更新文章
	 */
	public void save(Article article) {
		if (article.getId() != null) {
			articleMapper.updateByPrimaryKey(article);
		} else {
			articleMapper.insert(article);
		}
	}

	///**
	// * 文章列表
	// */
	//public JSONObject listArticle(JSONObject jsonObject) {
	//	CommonUtil.fillPageParam(jsonObject);
	//	int count = articleMapper.countArticle(jsonObject);
	//	List<JSONObject> list = articleMapper.listArticle(jsonObject);
	//	return CommonUtil.successPage(jsonObject, list, count);
	//}

	///**
	// * 更新文章
	// */
	//@Transactional(rollbackFor = Exception.class)
	//public JSONObject updateArticle(JSONObject jsonObject) {
	//	articleMapper.updateArticle(jsonObject);
	//	return CommonUtil.successJson();
	//}
}
