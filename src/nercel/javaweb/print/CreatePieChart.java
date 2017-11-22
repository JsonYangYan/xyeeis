package nercel.javaweb.print;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class CreatePieChart {

    ChartPanel panel;
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    /**
     * 创建饼状图的步骤如下： 1、创建一个饼状的实例，注意传参的格式，还有需要注意的是此时的数据集应该是defaultPieDataset，
     * 而不是CategoryDataset格式 2、获得饼状图的所在区域 3、设置两个格式化的数据格式，为后面的床架饼状图的实例做基础
     * 4、细节方面是对无数据、零值、负值等情况的处理 5、最后就是设置在出现汉字的地方进行字体内容的设置了（同样的，这是为了防止出现乱码的状况）
     */
    public ByteArrayOutputStream CreatePieChart(String title,String type1,String type2,String type3,String type4,float value1,float value2,float value3,float value4) {
        DefaultPieDataset dataset = getDataset(type1,type2,type3,type4,value1,value2,value3,value4);
       
        JFreeChart chart = ChartFactory.createPieChart(title, dataset,
                true, false, false);  //创建一个3D的视图

        PiePlot piePlot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%"); //获取DecimalFormat对象，主要涉及小数问题
        NumberFormat nf = NumberFormat.getInstance();  //获得一个NumberFormat对象  
        
        //设置饼状图的label标签
        StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
                "{0} {2}",                   //获得StandardPieSectionLabelGenerator对象,生成的格式，{0}表示section名，
                //{1}表示section的值，{2}表示百分比。可以自定义   
                nf, df);
        piePlot.setLabelGenerator(generator);// 设置百分比
        piePlot.setLabelFont(new Font("黑体", Font.ITALIC, 15));

        // 当饼状图内没有数据时，作如下数据中设置
        piePlot.setNoDataMessage("此时并没有任何数据可用");
        piePlot.setCircular(false);
        piePlot.setLabelGap(0.02D);

        piePlot.setIgnoreNullValues(true);// 设置不显示空位
        piePlot.setIgnoreZeroValues(true);// 设置不显示负值或零值
        
        //设置饼状图的框架
        panel = new ChartPanel(chart, true);
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 20));
		
        try {
			ChartUtilities.writeChartAsJPEG(stream, chart,580,400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
    }
    
    /**
     * 需要注意的是在向数据集中添加数据的时候调用的是dataset.setvalue()方法，而不是柱状图中的addValue()方法
     * 这一点应该尤其注意一下，以免在使用的时候出现错误
     * @return
     */
    private DefaultPieDataset getDataset(String type1,String type2,String type3,String type4,float value1,float value2,float value3,float value4) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue(type1, value1);
        dataset.setValue(type2, value2);
        dataset.setValue(type3, value3);
        dataset.setValue(type4, value4);
        return dataset;
    }

}