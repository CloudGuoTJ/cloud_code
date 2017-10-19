package com.common.util;
//
//import com.google.zxing.*;
//import com.google.zxing.common.HybridBinarizer;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/6/21.
// * 识别二维码的工具类
// */
//public class BufferedImageLuminanceSource extends LuminanceSource {
//
//    /**
//     * BufferedImage是Image的一个子类，BufferedImage生成的图片在内存里有一个图像缓冲区，
//     * 利用这个缓冲区我们可以很方便的操作这个图片，通常用来做图片修改操作如大小变换、图片变灰、设置图片透明或不透明等。
//     */
//    private final BufferedImage image;
//    private final int left;
//    private final int top;
//
//
//
//    /**
//     * 构造方法
//     *
//     * @param image
//     */
//    private BufferedImageLuminanceSource(BufferedImage image) {
//        this(image, 0, 0, image.getWidth(), image.getHeight());
//    }
//
//    /**
//     * 重载的构造方法
//     *
//     * @param image
//     * @param left
//     * @param top
//     * @param width
//     * @param height
//     */
//    private BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
//        super(width, height);
//
//        /**
//         * 取出当前图片的宽度和高度
//         */
//        int sourceWidth = image.getWidth();
//        int sourceHeight = image.getHeight();
//        /**
//         *
//         */
//        if (left + width > sourceWidth || top + height > sourceHeight) {
//            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
//        }
//
//        for (int y = top; y < top + height; y++) {
//            for (int x = left; x < left + width; x++) {
//                if ((image.getRGB(x, y) & 0xFF000000) == 0) {
//                    image.setRGB(x, y, 0xFFFFFFFF); // = white
//                }
//            }
//        }
//
//        this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
//        this.image.getGraphics().drawImage(image, 0, 0, null);
//        this.left = left;
//        this.top = top;
//    }
//
//    @Override
//    public byte[] getRow(int y, byte[] row) {
//        if (y < 0 || y >= getHeight()) {
//            throw new IllegalArgumentException("Requested row is outside the image: " + y);
//        }
//        int width = getWidth();
//        if (row == null || row.length < width) {
//            row = new byte[width];
//        }
//        image.getRaster().getDataElements(left, top + y, width, 1, row);
//        return row;
//    }
//
//    @Override
//    public byte[] getMatrix() {
//        int width = getWidth();
//        int height = getHeight();
//        int area = width * height;
//        byte[] matrix = new byte[area];
//        image.getRaster().getDataElements(left, top, width, height, matrix);
//        return matrix;
//    }
//
//    @Override
//    public boolean isCropSupported() {
//        return true;
//    }
//
//    @Override
//    public LuminanceSource crop(int left, int top, int width, int height) {
//        return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
//    }
//
//    @Override
//    public boolean isRotateSupported() {
//        return true;
//    }
//
//    @Override
//    public LuminanceSource rotateCounterClockwise() {
//
//        int sourceWidth = image.getWidth();
//        int sourceHeight = image.getHeight();
//
//        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
//
//        BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
//
//        Graphics2D g = rotatedImage.createGraphics();
//        g.drawImage(image, transform, null);
//        g.dispose();
//
//        int width = getWidth();
//        return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
//    }
//
//    /**
//     * 识别二维码调用该接口
//     * @param qrCodePath : 二维码的存储路径
//     * @return
//     */
//    public static String qrCodeToString(String qrCodePath) {
//        Result result = null;
//        try {
//            MultiFormatReader formatReader = new MultiFormatReader();
//            File file = new File(qrCodePath);
//            BufferedImage image = ImageIO.read(file);
//            // 将图像数据转换为1 bit data
//            LuminanceSource source = new BufferedImageLuminanceSource(image);
//            Binarizer binarizer = new HybridBinarizer(source);
//            // BinaryBitmap是ZXing用来表示1 bit data位图的类，Reader对象将对它进行解析
//            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
//            // 创建一个map集合
//            Map hints = new HashMap();
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            // 对图像进行解码
//            result = formatReader.decode(binaryBitmap, hints);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//
//
//}
