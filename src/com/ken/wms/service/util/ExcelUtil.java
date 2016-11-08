package com.ken.wms.service.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Ken
 *
 */
public class ExcelUtil {

	// 默认配置文件名
	private static final String DEFAULT_FILE_NAME = "ExcelUtilConfig.xml";

	private XMLConfiguration xmlConfig;

	// 实体类与Excel的映射关系
	private Map<String, MappingInfo> excelMappingInfo;

	public ExcelUtil() {
		init(DEFAULT_FILE_NAME);
	}

	public ExcelUtil(String fileLocation) {
		init(fileLocation);
	}

	/**
	 * 对 ExcelUtil 进行初始化 将扫描配置文件，加载配置的参数
	 * 
	 * @throws ConfigurationException
	 */
	private void init(String fileLocation) {
		// 创建对象的 excelMappingInfo 映射
		excelMappingInfo = new HashMap<>();
		
		Configurations configs = new Configurations();
		try {
			xmlConfig = configs.xml(fileLocation);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		if(xmlConfig == null){
			return;
		}
		
		/*
		 * 扫描 XMl 并配置参数
		 */
		// 扫描 entity 节点
		List<Object> entities = xmlConfig.getList("entity[@class]");
		if(entities == null){
			return;
		}
		int entityNum = entities.size();
		for (int i = 0; i < entityNum; i++) {
			MappingInfo mappingInfo = new MappingInfo();

			// 获得全限定类名
			String className = xmlConfig.getString("entity(" + i + ")[@class]");
			mappingInfo.setClassName(className);

			// 扫描 property 节点
			List<HierarchicalConfiguration<ImmutableNode>> properties = xmlConfig
					.configurationsAt("entity(" + i + ").property");
			for (HierarchicalConfiguration<ImmutableNode> property : properties) {
				// 解析
				String field = property.getString("field");
				String value = property.getString("value");

				mappingInfo.addFieldsMap(field, value);
				mappingInfo.addValuesMap(value, field);
			}

			// 将 entity 添加到 excelMappingInfo
			excelMappingInfo.put(className, mappingInfo);
		}
	}

	/**
	 * 讀取 Excel 文件中的内容 Excel 文件中的每一行代表了一个对象实例，而行中各列的属性值对应为对象中的各个属性值
	 * 读取时，需要指定读取目标对象的类型以获得相关的映射信息，并且要求该对象已在配置文件中注册
	 * 
	 * @param classType
	 *            目标对象的类型
	 * @param file
	 *            数据来源的 Excel 文件
	 * @return 包含若干个目标对象实例的 List
	 */
	public List<Object> excelReader(Class<? extends Object> classType, MultipartFile file) {
		if (file == null)
			return null;

		// 初始化存放读取结果的 List
		List<Object> content = new ArrayList<>();

		// 获取类名和映射信息
		String className = classType.getName();
		MappingInfo mappingInfo = excelMappingInfo.get(className);
		if (mappingInfo == null)
			return null;

		// 读取 Excel 文件
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet dataSheet = workbook.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator<Row> rowIterator = dataSheet.iterator();
			Iterator<Cell> cellIterator;

			// 读取第一行表头信息
			if (!rowIterator.hasNext())
				return null;
			List<String> methodList = new ArrayList<>();// setter 方法列表
			List<Class<?>> fieldTypeList = new ArrayList<>();// 目标对象属性类型列表
			row = rowIterator.next();
			cellIterator = row.iterator();
			String field;
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				field = mappingInfo.valuesMap.get(cell.getStringCellValue());
				Class<?> fieldType = classType.getDeclaredField(field).getType();

				fieldTypeList.add(cell.getColumnIndex(), fieldType);
				methodList.add(cell.getColumnIndex(), getSetterMethodName(field));
			}

			// 逐行读取表格内容，创建对象赋值并导入
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				cellIterator = row.iterator();
				Object elem = classType.newInstance();

				// 读取单元格
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();

					Class<?> fieldType = fieldTypeList.get(columnIndex);
					String methodName = methodList.get(columnIndex);

					// 获取单元格的值，并设置对象中对应的属性
					Object value = getCellValue(fieldType, cell);
					setField(elem, classType, methodName, fieldType, value);
				}
				// 放入结果
				content.add(elem);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;
	}
	
	/**
	 * 该方法用于获取单元格 cell 中的值
	 * 
	 * @param fieldType
	 *            指定获取的值的类型
	 * @param cell
	 *            单元格
	 * @return 单元格中的值
	 */
	private Object getCellValue(Class<?> fieldType, Cell cell) {
		if (cell == null)
			return null;

		int cellType = cell.getCellType();
		Object value = null;
		if (cellType == Cell.CELL_TYPE_STRING) {
			if (fieldType.equals(String.class)) {
				value = cell.getStringCellValue();
			}
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			if (fieldType.equals(String.class)) {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			} else if (fieldType.equals(Date.class) && HSSFDateUtil.isCellDateFormatted(cell)) {
				value = new java.sql.Date(cell.getDateCellValue().getTime());
			} else {
				value = cell.getNumericCellValue();
			}
		} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			if (fieldType.equals(Boolean.class)) {
				value = cell.getBooleanCellValue();
			}
		} else if (cellType == Cell.CELL_TYPE_FORMULA) {

		} else if (cellType == Cell.CELL_TYPE_ERROR) {

		} else if (cellType == Cell.CELL_TYPE_BLANK) {

		}
		return value;
	}

	/**
	 * 该方法用于设置对象中属性的值 通过调用目标对象属性对应的 setter 方法，因而要求目标对象必须设置 setter对象，否则赋值不成功
	 * 
	 * @param targetObject
	 *            目标对象
	 * @param targetObjectType
	 *            目标对象的类型
	 * @param methodName
	 *            setter 方法名
	 * @param fieldType
	 *            方法参数的类型
	 * @param field
	 *            方法参数的值
	 * @throws Exception
	 */
	private void setField(Object targetObject, Class<?> targetObjectType, String methodName, Class<?> fieldType,
			Object field) throws Exception {
		// 获得 setter 方法实例
		Method setterMethod = targetObjectType.getMethod(methodName, fieldType);

		// 调用方法
		setterMethod.invoke(targetObject, field);
	}

	/**
	 * 构造 setter 方法的方法名
	 * 
	 * @param field
	 * @return
	 */
	private String getSetterMethodName(String field) {
		// 转换为首字母大写
		String name = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
		// 拼接 set 并返回
		return "set" + name;
	}

	/**
	 * 该对象代表了各个注册到 ExcelUtil 对象的映射信息 映射信息包括：注册对象的类型，对象属性与 Excel 列名的映射
	 * 
	 * @author Ken
	 *
	 */
	private class MappingInfo {
		private String className;
		private Map<String, String> fieldsMap = new HashMap<>();
		private Map<String, String> valuesMap = new HashMap<>();

		@SuppressWarnings("unused")
		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public void addFieldsMap(String field, String value) {
			fieldsMap.put(field, value);
		}

		public void addValuesMap(String value, String field) {
			valuesMap.put(value, field);
		}
	}
}
