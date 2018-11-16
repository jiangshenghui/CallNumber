//package com.bg.baseutillib.tool;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 二维码工具类
// */
//
//public class QRcodeUtils {
//    /**
//     * 生成二维码
//     *
//     * @param context
//     * @param qrcodeStr
//     * @param width
//     * @param height
//     * @return
//     */
//    public static Bitmap genQRCode(Context context, String qrcodeStr, int width, int height) {
//        return getQRCodeBitmap(qrcodeStr, width, height);
//    }
//
//
//    /**
//     * 生成带logo的二维码
//     *
//     * @param context
//     * @param qrcodeStr
//     * @param width
//     * @param height
//     * @param logoResId
//     * @return
//     */
//    public static Bitmap genQRCodeWithLogo(Context context, String qrcodeStr, int width, int height, int logoResId) {
//        Bitmap qrBitmap = getQRCodeBitmap(qrcodeStr, width, height);
//        Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), logoResId);
//        return addLogo(qrBitmap, logoBitmap);
//    }
//
//    /**
//     * 添加logo到二维码中间
//     *
//     * @param qrBitmap
//     * @param logoBitmap
//     * @return
//     */
//    private static Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
//        //创建空白的Bitmap
//        int width = qrBitmap.getWidth();
//        int height = qrBitmap.getHeight();
//        Bitmap blackBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//
//        //创建画布Canvas，参数bitmap必须是可变的
//        Canvas canvas = new Canvas(blackBitmap);
//        //初始化画笔Paint
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//
//        //画二维码到画布上
//        canvas.drawBitmap(qrBitmap, 0, 0, paint);
//        //画logo到画布上
//        canvas.drawBitmap(logoBitmap, (width - logoBitmap.getWidth()) / 2, (height - logoBitmap.getHeight()) / 2, paint);
//        return blackBitmap;
//    }
//
//    /**
//     * 获取生成的QRCode的bitmap
//     *
//     * @param content 二维码的字符串内容
//     * @param width   二维码的宽度
//     * @param height  二维码的高度
//     * @return
//     */
//    private static Bitmap getQRCodeBitmap(String content, int width, int height) {
//        QRCodeWriter writer = new QRCodeWriter();
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//字符编码
//
//        /**
//         * 容错级别
//         *     L 7%
//         *     M 15%  一般使用M,如果此时需要在中间放置logo，logo的长宽大概为二维码的长宽的1/5
//         *     Q 25%
//         *     H 30%
//         */
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//        try {
//            //返回的是一个矩阵，相当于二维数组，有数据为true，没数据为false
//            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            //把矩阵转换成一维数组
//            int[] colors = new int[width * height];
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    if (bitMatrix.get(j, i)) {
//                        colors[i * width + j] = Color.BLACK;
//                    } else {
//                        colors[i * width + j] = Color.WHITE;
//                    }
//                }
//            }
//            //参数三：bitmap水平宽度的像素点个数
//            //参数六：图片模式必须为Bitmap.Config.RGB_565
//            return Bitmap.createBitmap(colors, 0, width, width, height, Bitmap.Config.RGB_565);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
