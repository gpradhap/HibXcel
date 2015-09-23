package com.pg.common.dao;

import java.util.List;

import com.pg.common.bo.BaseBO;
import com.pg.stock.bo.Stock;

public interface BaseDao {

	public BaseBO get(int start, int count);

	public List<BaseBO> getAll();

	public boolean update();

	public boolean delete();

	public boolean create(BaseBO baseBo);

}
