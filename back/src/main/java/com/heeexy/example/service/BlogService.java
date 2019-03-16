package com.heeexy.example.service;

import com.github.pagehelper.PageHelper;
import com.heeexy.example.mapper.BlogMapper;
import com.heeexy.example.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:07
 */
@Service
public class BlogService {

	@Autowired
	private BlogMapper blogMapper;


	public List<Blog> getAll(Blog blog) {
		if (blog.getPageNum() != null && blog.getPageRow() != null) {
			PageHelper.startPage(blog.getPageNum(), blog.getPageRow());
		}
		return blogMapper.selectAll();
	}

	/**
	 * 新增或者更新文章
	 */
	public void save(Blog blog) {
		if (blog.getId() != null) {
			blogMapper.updateByPrimaryKey(blog);
		} else {
			blogMapper.insert(blog);
		}
	}

	///**
	// * 文章列表
	// */
	//public JSONObject listArticle(JSONObject jsonObject) {
	//	CommonUtil.fillPageParam(jsonObject);
	//	int count = blogMapper.countArticle(jsonObject);
	//	List<JSONObject> list = blogMapper.listArticle(jsonObject);
	//	return CommonUtil.successPage(jsonObject, list, count);
	//}

	///**
	// * 更新文章
	// */
	//@Transactional(rollbackFor = Exception.class)
	//public JSONObject updateArticle(JSONObject jsonObject) {
	//	blogMapper.updateArticle(jsonObject);
	//	return CommonUtil.successJson();
	//}
}
