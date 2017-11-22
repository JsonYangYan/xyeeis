package nercel.javaweb.qxprint;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

public class CreateBarChart {

	private ChartPanel panel;
	ByteArrayOutputStream stream = new ByteArrayOutputStream();

	/**
	 * 在柱状图中创建表格的步骤如下： 1、创建表格chart,需要注意相关参数的含义， 2、传进去的数据集是CategoryDataset格式
	 * 3、获得表格区域块，设置横轴，纵轴及相关字体（防止出现乱卡的状况）
	 * 4、设置chart的图例legend，并设置条目的字体格式（同样是为了防止出现乱码）
	 */
	public ByteArrayOutputStream CreateBarChart(String title, String htitle,
			String vtitle, float value1, float value2, float value3,
			float value4, float value5, String type1, String type2,
			String type3, String type4, String type5) {
		CategoryDataset dataset = (CategoryDataset) getDataset(value1, value2,
				value3, value4, value5, type1, type2, type3, type4, type5);

		JFreeChart chart = ChartFactory.createBarChart(title,
				htitle, vtitle, dataset, PlotOrientation.VERTICAL, true,
				false, false);

		CategoryPlot plot = (CategoryPlot) chart.getCategoryPlot();
		CategoryAxis axis = plot.getDomainAxis();
		axis.setLabelFont(new Font("宋体", Font.BOLD, 15));
		axis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));

		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 15));

		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 20));
		chart.getTitle().setFont(new Font("黑体", Font.ITALIC, 18));

		panel = new ChartPanel(chart, true);

		try {
			ChartUtilities.writeChartAsJPEG(stream, chart, 580, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stream;
	}

	/**
	 * 需要注意的是在向数据集中添加数据的时候
	 * 使用的是dataset.addValue()方法，而在饼状图的数据集添加数据的过程中，使用的是dataset.setValue()方法
	 * 这一点应该尤其注意。以免出错！
	 * 
	 * @return
	 */
	private static Dataset getDataset(float value1, float value2, float value3,
			float value4, float value5, String type1, String type2,
			String type3, String type4, String type5) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(value1, type1, type1);
		dataset.addValue(value2, type2, type2);
		dataset.addValue(value3, type3, type3);
		dataset.addValue(value4, type4, type4);
		dataset.addValue(value5, type5, type5);

		return dataset;
	}

}