package com.example.tools.util;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * @author: tiankuokuo
 * @description: excel中的仪表盘生成类
 * @date: 2019/1/9 16:03
 */
public class DashboardUtil {

    private static final Logger logger = LoggerFactory.getLogger(DashboardUtil.class);

    /**
     * @author: tiankuokuo
     * @description: 仪表盘声场方法
     * @date: 2019/1/9 16:04
     * @return:
     * @throws:
     */
    public void dashboard(String name, Number pointValue, int percentageFontSize,
                          int tickLabelFontSize, OutputStream outputStream) {

        Color green = new Color(145, 199, 174);
        Color blue = new Color(99, 134, 158);
        Color red = new Color(194, 53, 49);
        //1,数据集合对象 此处为DefaultValueDataset
        DefaultValueDataset dataset = new DefaultValueDataset();
        //  当前指针指向的位置，即：我们需要显示的数据
        if (pointValue.doubleValue() < 0) {
            pointValue = 0;
        }
        if (pointValue.doubleValue() > 100) {
            pointValue = 100;
        }
        dataset.setValue(pointValue.intValue());
        Number v = 0;
        if (pointValue.doubleValue() % 1 == 0) {
            v = pointValue.intValue();
        } else {
            v = new BigDecimal(pointValue.toString())
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

        }
        /**
         *  获取图表区域对象
         *
         * A. setDataSet(int index, DataSet dataSet);
         * 为表盘设定使用的数据集，通常一个表盘上可能存在多个指针，
         * 因此需要制定该数据集与哪个指针相互关联。
         * 可以将指针想象成数据集的一种体现方式。
         */
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, dataset);
        /**
         * 开始设置显示框架结构
         * B. setDailFrame(DailFrame dailFrame);设置表盘的底层面板图像，通常表盘是整个仪表的最底层。
         */
        // 开始设置显示框架结构
        StandardDialFrame simpledialframe = new StandardDialFrame();
        simpledialframe.setBackgroundPaint(Color.lightGray);//Color.lightGray //仪表盘边框内部颜色
        simpledialframe.setForegroundPaint(Color.darkGray);//Color.darkGray //仪表盘边框外部颜色
        dialplot.setDialFrame(simpledialframe);
        /**
         * 结束设置显示框架结构
         * C. setBackground(Color color);设置表盘的颜色，可以采用Java内置的颜色控制方式来调用该方法。
         */
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        //  设置显示在表盘中央位置的信息
        DialTextAnnotation dialtextannotation = new DialTextAnnotation(v + "%");
        dialtextannotation.setFont(new Font("Dialog", 1, percentageFontSize));
        dialtextannotation.setPaint(Color.red);
        dialtextannotation.setRadius(0.59999999999999996D);
        dialplot.addLayer(dialtextannotation);
        ////指针指示框 ,仪表盘中间的小方框
//        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
//        dialvalueindicator.setFont(new Font("宋体", 1, 14));
//        dialvalueindicator.setOutlinePaint(new Color(229,229,229));
//        dialvalueindicator.setBackgroundPaint(new Color(229,229,229));
//        dialvalueindicator.setRadius(0.39999999999999998D);
//        dialvalueindicator.setPaint(Color.BLACK);
//        dialvalueindicator.setAngle(90);
//        dialplot.addLayer(dialvalueindicator);
        //  根据表盘的直径大小（0.75），设置总刻度范围
        /**
         * E. addScale(int index, DailScale dailScale);
         * 用于设定表盘上的量程，index指明该量程属于哪一个指针所指向的数据集，
         * DailScale指明该量程的样式，如量程的基本单位等信息。
         *
         * StandardDialScale(double lowerBound, double upperBound, double startAngle,
         * double extent, double majorTickIncrement, int minorTickCount)
         * new StandardDialScale(-40D, 60D, -120D, -300D,30D);
         */
        //
        StandardDialScale standarddialscale = new StandardDialScale();
        standarddialscale.setLowerBound(0D);
        standarddialscale.setUpperBound(100D);
        standarddialscale.setStartAngle(-120D);
        standarddialscale.setExtent(-300D);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, tickLabelFontSize));
        dialplot.addScale(0, standarddialscale);
        /**
         * F. addLayer(DailRange dailRange);
         * 用于设定某一特定量程的特殊表现，通常位于量程之下，如红色范围标注，绿色范围标注等。
         * 在调用该方法之前需要设定DailRange的一些信息，包括位置信息，颜色信息等等。
         */
        //设置刻度范围（绿色）
        StandardDialRange standarddialrange = new StandardDialRange(0D, 20D, green);
        standarddialrange.setInnerRadius(0.52000000000000002D);
        standarddialrange.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange);
        //设置刻度范围（蓝色）
        StandardDialRange standarddialrange1 = new StandardDialRange(20D, 80D, blue);
        standarddialrange1.setInnerRadius(0.52000000000000002D);
        standarddialrange1.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange1);
        //设置刻度范围（红色）
        StandardDialRange standarddialrange2 = new StandardDialRange(80D, 100D, red);
        standarddialrange2.setInnerRadius(0.52000000000000002D);
        standarddialrange2.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange2);

        Color color = Color.red;
        if (pointValue.intValue() >= 0 && pointValue.intValue() < 20) {
            color = green;
        } else if (pointValue.intValue() >= 20 && pointValue.intValue() < 80) {
            color = blue;
        } else {
            color = red;
        }
        /**
         * 设置指针
         * G. addPointer(DailPointer dailPointer);
         * 用于设定表盘使用的指针样式，JFreeChart中有很多可供选择指针样式，
         * 用户可以根据使用需要，采用不同的DailPoint的实现类来调用该方法
         */
        MyDialPointer.MyPointer pointer = new MyDialPointer.MyPointer(0, color); //内部内
        dialplot.addPointer(pointer); //addLayer(pointer);
        /**
         * 实例化DialCap
         * H. setCap(DailCap dailCap);设定指针上面的盖帽的样式。
         */
        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.0800000000000001D);

        dialcap.setFillPaint(color);
        dialcap.setOutlinePaint(color);
        dialplot.setCap(dialcap);
        //生成chart对象
        JFreeChart jfreechart = new JFreeChart(dialplot);
        //设置标题
        jfreechart.setTitle(name);
//        ChartFrame frame = new ChartFrame("测试", jfreechart,true);
//        frame.pack();
//        //屏幕居中
//        RefineryUtilities.centerFrameOnScreen(frame);
//        frame.setVisible(true);
//        File file = new File("C:\\var\\data\\templates\\export\\manufacture\\img\\01.png");
        try {
//            ChartUtilities.saveChartAsJPEG(file, jfreechart, 250, 250);
            BufferedImage bufferedImage = jfreechart.createBufferedImage(250, 250, BufferedImage.TYPE_INT_BGR, null);
            ChartUtilities.writeBufferedImageAsJPEG(outputStream, bufferedImage);
        } catch (IOException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
    }
}
