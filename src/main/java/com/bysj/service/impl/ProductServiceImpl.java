package com.bysj.service.impl;

import com.bysj.beans.Product;
import com.bysj.mapper.ProductMapper;
import com.bysj.service.ProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 浪子
 * 
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


}
