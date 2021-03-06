package org.jtester.module.database.dbop;

import java.util.ArrayList;
import java.util.List;

import org.jtester.core.IJTester.DataMap;
import org.jtester.json.JSON;
import org.jtester.module.database.datagenerator.AbastractDataGenerator;
import org.jtester.utility.ArrayHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractDataSet {
	private List<DataMap> datas = new ArrayList<DataMap>();

	public AbstractDataSet() {
	}

	public void data(int count, DataMap datas) {
		List list = AbstractDataSet.parseMapList(count, datas);
		this.datas.addAll(list);
	}

	public void data(DataMap data) {
		this.datas.add(data);
	}

	public void data(String json) {
		DataMap data = JSON.toObject(json, DataMap.class);
		this.datas.add(data);
	}

	/**
	 * 插入列表中的数据集<br>
	 * 插入完毕后列表不做清空，方便重用
	 * 
	 * @param table
	 */
	public void insert(String table) {
		for (DataMap map : this.datas) {
			InsertOp.insert(table, map);
		}
	}

	/**
	 * 根据要插入数据的数量count和数据集合datas，分解出count条待插入的数据集
	 * 
	 * @param count
	 * @param datas
	 * @return
	 */
	private static List<DataMap> parseMapList(int count, DataMap datas) {
		List<DataMap> list = new ArrayList<DataMap>();
		for (int index = 0; index < count; index++) {
			DataMap data = new DataMap();
			for (String key : datas.keySet()) {
				Object dataGenerator = datas.get(key);
				Object value = getObjectFromDataGenerator(data, dataGenerator, index);
				data.put(key, value);
			}
			list.add(data);
		}
		return list;
	}

	private static Object getObjectFromDataGenerator(DataMap dataMap, Object dataGenerator, int index) {
		if (ArrayHelper.isCollOrArray(dataGenerator)) {
			Object[] oa = ArrayHelper.toArray(dataGenerator);
			int count = oa.length;
			Object value = index < count ? oa[index] : oa[count - 1];
			return value;
		} else if (dataGenerator instanceof AbastractDataGenerator) {
			AbastractDataGenerator generator = (AbastractDataGenerator) dataGenerator;
			generator.setDataMap(dataMap);
			return generator.generate(index);
		} else {
			return dataGenerator;
		}
	}

	public final static class EmptyDataSet extends AbstractDataSet {
	}
}
