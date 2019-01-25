package com.example.tools.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.jfree.chart.HashUtilities;
import org.jfree.chart.plot.dial.DialLayerChangeEvent;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialScale;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Arc2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class MyDialPointer extends DialPointer {
    double radius;
    int datasetIndex;

    protected MyDialPointer() {
        this(0);
    }

    protected MyDialPointer(int datasetIndex) {
        this.radius = 0.9D;
        this.datasetIndex = datasetIndex;
    }

    @Override
    public int getDatasetIndex() {
        return this.datasetIndex;
    }

    @Override
    public void setDatasetIndex(int index) {
        this.datasetIndex = index;
        this.notifyListeners(new DialLayerChangeEvent(this));
    }

    @Override
    public double getRadius() {
        return this.radius;
    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
        this.notifyListeners(new DialLayerChangeEvent(this));
    }

    @Override
    public boolean isClippedToWindow() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof DialPointer)) {
            return false;
        } else {
            MyDialPointer that = (MyDialPointer) obj;
            if (this.datasetIndex != that.datasetIndex) {
                return false;
            } else {
                return this.radius != that.radius ? false : super.equals(obj);
            }
        }
    }

    @Override
    public int hashCode() {
        int result = 23;
        result = HashUtilities.hashCode(result, this.radius);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Pointer extends MyDialPointer {
        static final long serialVersionUID = -4180500011963176960L;
        private double widthRadius;
        private transient Paint fillPaint;
        private transient Paint outlinePaint;

        public Pointer() {
            this(0);
        }

        public Pointer(int datasetIndex) {
            super(datasetIndex);
            this.widthRadius = 0.05D;
            this.fillPaint = Color.gray;
            this.outlinePaint = Color.black;
        }

        public double getWidthRadius() {
            return this.widthRadius;
        }

        public void setWidthRadius(double radius) {
            this.widthRadius = radius;
            this.notifyListeners(new DialLayerChangeEvent(this));
        }

        public Paint getFillPaint() {
            return this.fillPaint;
        }

        public void setFillPaint(Paint paint) {
            if (paint == null) {
                throw new IllegalArgumentException("Null 'paint' argument.");
            } else {
                this.fillPaint = paint;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        public Paint getOutlinePaint() {
            return this.outlinePaint;
        }

        public void setOutlinePaint(Paint paint) {
            if (paint == null) {
                throw new IllegalArgumentException("Null 'paint' argument.");
            } else {
                this.outlinePaint = paint;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        @Override
        public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
            g2.setPaint(Color.blue);
            g2.setStroke(new BasicStroke(1.0F));
            Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
            Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, this.widthRadius, this.widthRadius);
            double value = plot.getValue(this.datasetIndex);
            DialScale scale = plot.getScaleForDataset(this.datasetIndex);
            double angle = scale.valueToAngle(value);
            Arc2D arc1 = new Double(lengthRect, angle, 0.0D, 0);
            Point2D pt1 = arc1.getEndPoint();
            Arc2D arc2 = new Double(widthRect, angle - 90.0D, 180.0D, 0);
            Point2D pt2 = arc2.getStartPoint();
            Point2D pt3 = arc2.getEndPoint();
            Arc2D arc3 = new Double(widthRect, angle - 180.0D, 0.0D, 0);
            Point2D pt4 = arc3.getStartPoint();
            GeneralPath gp = new GeneralPath();
            gp.moveTo((float) pt1.getX(), (float) pt1.getY());
            gp.lineTo((float) pt2.getX(), (float) pt2.getY());
            gp.lineTo((float) pt4.getX(), (float) pt4.getY());
            gp.lineTo((float) pt3.getX(), (float) pt3.getY());
            gp.closePath();
            g2.setPaint(this.fillPaint);
            g2.fill(gp);
            g2.setPaint(this.outlinePaint);
            Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt1.getX(), pt1.getY());
            g2.draw(line);
            line.setLine(pt2, pt3);
            g2.draw(line);
            line.setLine(pt3, pt1);
            g2.draw(line);
            line.setLine(pt2, pt1);
            g2.draw(line);
            line.setLine(pt2, pt4);
            g2.draw(line);
            line.setLine(pt3, pt4);
            g2.draw(line);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof DialPointer.Pointer)) {
                return false;
            } else {
                MyDialPointer.Pointer that = (MyDialPointer.Pointer) obj;
                if (this.widthRadius != that.widthRadius) {
                    return false;
                } else if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
                    return false;
                } else {
                    return !PaintUtilities.equal(this.outlinePaint, that.outlinePaint) ? false : super.equals(obj);
                }
            }
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = HashUtilities.hashCode(result, this.widthRadius);
            result = HashUtilities.hashCode(result, this.fillPaint);
            result = HashUtilities.hashCode(result, this.outlinePaint);
            return result;
        }

        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtilities.writePaint(this.fillPaint, stream);
            SerialUtilities.writePaint(this.outlinePaint, stream);
        }

        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.fillPaint = SerialUtilities.readPaint(stream);
            this.outlinePaint = SerialUtilities.readPaint(stream);
        }
    }

    public static class Pin extends MyDialPointer {
        static final long serialVersionUID = -8445860485367689750L;
        private transient Paint paint;
        private transient Stroke stroke;

        public Pin() {
            this(0);
        }

        public Pin(int datasetIndex) {
            super(datasetIndex);
            this.paint = Color.red;
            this.stroke = new BasicStroke(3.0F, 1, 2);
        }

        public Paint getPaint() {
            return this.paint;
        }

        public void setPaint(Paint paint) {
            if (paint == null) {
                throw new IllegalArgumentException("Null 'paint' argument.");
            } else {
                this.paint = paint;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        public Stroke getStroke() {
            return this.stroke;
        }

        public void setStroke(Stroke stroke) {
            if (stroke == null) {
                throw new IllegalArgumentException("Null 'stroke' argument.");
            } else {
                this.stroke = stroke;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        @Override
        public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
            g2.setPaint(this.paint);
            g2.setStroke(this.stroke);
            Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
            double value = plot.getValue(this.datasetIndex);
            DialScale scale = plot.getScaleForDataset(this.datasetIndex);
            double angle = scale.valueToAngle(value);
            Arc2D arc = new Double(arcRect, angle, 0.0D, 0);
            Point2D pt = arc.getEndPoint();
            Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt.getX(), pt.getY());
            g2.draw(line);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof DialPointer.Pin)) {
                return false;
            } else {
                MyDialPointer.Pin that = (MyDialPointer.Pin) obj;
                if (!PaintUtilities.equal(this.paint, that.paint)) {
                    return false;
                } else {
                    return !this.stroke.equals(that.stroke) ? false : super.equals(obj);
                }
            }
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = HashUtilities.hashCode(result, this.paint);
            result = HashUtilities.hashCode(result, this.stroke);
            return result;
        }

        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtilities.writePaint(this.paint, stream);
            SerialUtilities.writeStroke(this.stroke, stream);
        }

        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.paint = SerialUtilities.readPaint(stream);
            this.stroke = SerialUtilities.readStroke(stream);
        }
    }

    public static class MyPointer extends MyDialPointer {
        static final long serialVersionUID = -4180500011963176960L;
        private double widthRadius;
        private transient Paint fillPaint;
        private transient Paint outlinePaint;

        public MyPointer() {
            this(0);
        }

        public MyPointer(int datasetIndex) {
            super(datasetIndex);
            this.widthRadius = 0.04D;
            this.fillPaint = Color.red;
            this.outlinePaint = Color.red;
        }

        public MyPointer(int datasetIndex, Color color) {
            super(datasetIndex);
            this.widthRadius = 0.04D;
            this.fillPaint = color;
            this.outlinePaint = color;
        }

        public double getWidthRadius() {
            return this.widthRadius;
        }

        public void setWidthRadius(double radius) {
            this.widthRadius = radius;
            this.notifyListeners(new DialLayerChangeEvent(this));
        }

        public Paint getFillPaint() {
            return this.fillPaint;
        }

        public void setFillPaint(Paint paint) {
            if (paint == null) {
                throw new IllegalArgumentException("Null 'paint' argument.");
            } else {
                this.fillPaint = paint;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        public Paint getOutlinePaint() {
            return this.outlinePaint;
        }

        public void setOutlinePaint(Paint paint) {
            if (paint == null) {
                throw new IllegalArgumentException("Null 'paint' argument.");
            } else {
                this.outlinePaint = paint;
                this.notifyListeners(new DialLayerChangeEvent(this));
            }
        }

        @Override
        public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
            g2.setPaint(Color.blue);
            g2.setStroke(new BasicStroke(1.0F));
            Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
            Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, this.widthRadius, this.widthRadius);
            double value = plot.getValue(this.datasetIndex);
            DialScale scale = plot.getScaleForDataset(this.datasetIndex);
            double angle = scale.valueToAngle(value);
            Arc2D arc1 = new Double(lengthRect, angle, 0.0D, 0);
            Point2D pt1 = arc1.getEndPoint();
            Arc2D arc2 = new Double(widthRect, angle - 90.0D, 180.0D, 0);
            Point2D pt2 = arc2.getStartPoint();
            Point2D pt3 = arc2.getEndPoint();
            Arc2D arc3 = new Double(widthRect, angle - 180.0D, 0.0D, 0);
            Point2D pt4 = arc3.getStartPoint();
            GeneralPath gp = new GeneralPath();
            gp.moveTo((float) pt1.getX(), (float) pt1.getY());
            gp.lineTo((float) pt2.getX(), (float) pt2.getY());
            gp.lineTo((float) pt4.getX(), (float) pt4.getY());
            gp.lineTo((float) pt3.getX(), (float) pt3.getY());
            gp.closePath();
            g2.setPaint(this.fillPaint);
            g2.fill(gp);
            g2.setPaint(this.outlinePaint);
            Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt1.getX(), pt1.getY());
            g2.draw(line);
            line.setLine(pt2, pt3);
            g2.draw(line);
            line.setLine(pt3, pt1);
            g2.draw(line);
            line.setLine(pt2, pt1);
            g2.draw(line);
            line.setLine(pt2, pt4);
            g2.draw(line);
            line.setLine(pt3, pt4);
            g2.draw(line);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof DialPointer.Pointer)) {
                return false;
            } else {
                MyDialPointer.Pointer that = (MyDialPointer.Pointer) obj;
                if (this.widthRadius != that.widthRadius) {
                    return false;
                } else if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
                    return false;
                } else {
                    return !PaintUtilities.equal(this.outlinePaint, that.outlinePaint) ? false : super.equals(obj);
                }
            }
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = HashUtilities.hashCode(result, this.widthRadius);
            result = HashUtilities.hashCode(result, this.fillPaint);
            result = HashUtilities.hashCode(result, this.outlinePaint);
            return result;
        }

        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtilities.writePaint(this.fillPaint, stream);
            SerialUtilities.writePaint(this.outlinePaint, stream);
        }

        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.fillPaint = SerialUtilities.readPaint(stream);
            this.outlinePaint = SerialUtilities.readPaint(stream);
        }
    }

}
